package com.jommobile.android.util.endpoints;

import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient;
import com.jommobile.android.util.AppExecutors;

/**
 * This class is used for ...
 *
 * @author MAO Hieng on 1/13/19.
 */
public abstract class EndpointsService<C extends AbstractGoogleJsonClient> {

    protected abstract AppExecutors getAppExecutors();

    protected abstract C getApiClient();

    protected <T> Promise.Builder<T> createPromiseBuilder() {
        return new PromiseImp.BuilderImpl<>(getAppExecutors());
    }
}
