package com.jommobile.android.jomutils.repository;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.jommobile.android.jomutils.AppExecutors;

import java.util.Objects;

/**
 * A generic class that can provide a resource backed by the sqlite database.
 *
 * @author MAO Hieng on 1/8/19.
 */
public abstract class DataBoundResource<RESULT> {

    interface DatabaseObserver<T> {
        void onChanged(LiveData<T> dbSource, T dbData);
    }

//    private static final String TAG = Logs.makeTag(DataBoundResource.class);

    protected final AppExecutors appExecutors;
    final MediatorLiveData<Resource<RESULT>> result = new MediatorLiveData<>();

    @MainThread
    public DataBoundResource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
    }

    protected void setValue(Resource<RESULT> newValue) {
        if (!result.getValue().equals(newValue)) {
            result.setValue(newValue);
        }
    }

    @MainThread
    public BoundResource<RESULT> bind() {
        return bind((dbSource, data) -> result.addSource(dbSource, result -> setValue(Resource.Factory.success(result))));
    }

    @MainThread
    BoundResource<RESULT> bind(@Nullable DatabaseObserver<RESULT> observer) {
        result.setValue(Resource.Factory.loading());

        final LiveData<RESULT> dbSource = loadFromDb();
        Objects.requireNonNull(dbSource, "loadFromDb() must not return null.");
        result.addSource(dbSource, data -> {
            result.removeSource(dbSource);

            if (observer != null)
                observer.onChanged(dbSource, data);
        });

        return () -> result;
    }

    @NonNull
    @MainThread
    protected abstract LiveData<RESULT> loadFromDb();

}
