package com.jommobile.android.jomutils.endpoints;

import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient;
import com.jommobile.android.jomutils.AppExecutors;

/**
 * This class is used for ...
 *
 * @author MAO Hieng on 1/13/19.
 */
public abstract class EndpointsService<C extends AbstractGoogleJsonClient> {

    private final AppExecutors mExecutors;
    private final C mClient;

    protected EndpointsService(AppExecutors executors, C endpointsClients) {
        mExecutors = executors;
        mClient = endpointsClients;
    }

    protected AppExecutors getAppExecutors(){
        return mExecutors;
    }

    protected C getEndpointsClient() {
        return mClient;
    }

    protected <T> Promise.Builder<T> createPromiseBuilder() {
        return new PromiseImp.BuilderImpl<>(getAppExecutors());
    }
}
