package com.jommobile.android.jomutils.repository;

import com.jommobile.android.jomutils.AppExecutors;
import com.jommobile.android.jomutils.Logs;

import java.util.concurrent.Executor;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 *
 * @author MAO Hieng on 1/8/19.
 */
public abstract class NetworkBoundResource<RESULT, NR> {

    private static final String TAG = Logs.makeTag(NetworkBoundResource.class);

    private final AppExecutors appExecutors;
    private final MediatorLiveData<Resource<RESULT>> result = new MediatorLiveData<>();

    @MainThread
    public NetworkBoundResource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;

        result.setValue(Resource.Factory.loading());

        final LiveData<RESULT> dbSource = loadFromDb();
        result.addSource(dbSource, data -> {
            result.removeSource(dbSource);
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource);
            } else {
                result.addSource(dbSource, result -> setValue(Resource.Factory.success(result)));
            }
        });
    }

    private void setValue(Resource<RESULT> newValue) {
        if (!result.getValue().equals(newValue)) {
            result.setValue(newValue);
        }
    }

    public LiveData<Resource<RESULT>> asLiveData() {
        return result;
    }

    @MainThread
    protected abstract LiveData<RESULT> loadFromDb();

    @MainThread
    protected abstract boolean shouldFetch(RESULT data);

    private void fetchFromNetwork(LiveData<RESULT> dbSource) {
        final Executor mainThread = appExecutors.mainThread();

        final LiveData<Resource<NR>> callSource = createNetworkCall();

        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource, newData -> setValue(Resource.Factory.loading()));

        result.addSource(callSource, newNetworkSource -> {
            if (newNetworkSource == null)
                return;

            if (newNetworkSource.getStatus() != Resource.Status.LOADING) {
                Logs.D(TAG, "Network call result: " + newNetworkSource);

                result.removeSource(callSource);
                result.removeSource(dbSource);

                final NR data = newNetworkSource.getData();

                if (data != null) {
                    appExecutors.diskIO().execute(() -> {
                        Logs.D(TAG, "Save network result: " + data);
                        saveNetworkResult(data);

                        mainThread.execute(() -> {
                            // we specially request a new live data,
                            // otherwise we will get immediately last cached value,
                            // which may not be updated with latest results received from network.
                            result.addSource(loadFromDb(), newData -> setValue(Resource.Factory.success(newData)));
                        });
                    });
                } else if (newNetworkSource.getStatus() == Resource.Status.SUCCESS) { // empty data
                    mainThread.execute(() -> {
                        // reload from disk whatever we had
                        result.addSource(loadFromDb(), newData -> setValue(Resource.Factory.success(newData)));
                    });
                } else { // error
                    mainThread.execute(() -> {
                        onFetchFailed(newNetworkSource.getCause());
                        result.addSource(dbSource, newData -> setValue(Resource.Factory.error(newNetworkSource.getMessage(), newNetworkSource.getCause())));
                    });
                }
            }
        });
    }

    @MainThread
    protected abstract LiveData<Resource<NR>> createNetworkCall();

    @WorkerThread
    protected abstract void saveNetworkResult(@NonNull NR networkResult);

    @MainThread
    protected void onFetchFailed(Throwable cause) {
    }

}
