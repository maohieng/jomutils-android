package com.jommobile.android.jomutils.endpoints;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.HttpUnsuccessfulResponseHandler;
import com.google.api.client.json.JsonFactory;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * This class is used for ...
 *
 * @author MAO Hieng on 1/13/19.
 */
public final class Endpoints {
    private Endpoints() {
        //no instance
    }

    /**
     *
     * @param bClass
     * @param transport
     * @param jsonFactory
     * @param httpRequestInitializer
     * @param <B>
     * @throws IllegalArgumentException in case it cannot create an instance.
     * @return
     */
    public static <B extends AbstractGoogleJsonClient.Builder> B newClientBuilder(Class<B> bClass,
                                                                           HttpTransport transport,
                                                                           JsonFactory jsonFactory,
                                                                           HttpRequestInitializer httpRequestInitializer) {
        try {
            Constructor<B> constructor = bClass.getConstructor(HttpTransport.class, JsonFactory.class,
                    HttpRequestInitializer.class);
            return constructor.newInstance(transport, jsonFactory, httpRequestInitializer);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static <B extends AbstractGoogleJsonClient.Builder> B newDefaultClientBuilder(Class<B> bClass) {
        return newClientBuilder(bClass, AndroidHttp.newCompatibleTransport(), AndroidJsonFactory.getDefaultInstance(), new RequestInitializer());
    }

    // An example in case you need it
    public static class RequestInitializer implements HttpRequestInitializer, HttpUnsuccessfulResponseHandler {

        @Override
        public void initialize(HttpRequest request) throws IOException {
            request.setUnsuccessfulResponseHandler(this);
        }

        @Override
        public boolean handleResponse(HttpRequest request, HttpResponse response, boolean supportsRetry) throws IOException {
            return false;
        }
    }
}
