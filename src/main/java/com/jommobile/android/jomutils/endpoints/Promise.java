package com.jommobile.android.jomutils.endpoints;

import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.jommobile.android.jomutils.AppExecutors;

import java.io.IOException;

/**
 * Created by MAO Hieng on 1/4/19.
 */
public interface Promise<T> extends Cloneable {
    /**
     * Synchronously send the request and return its response.
     *
     * @throws IOException      if a problem occurred talking to the server.
     * @throws RuntimeException (and subclasses) if an unexpected error occurs creating the request
     *                          or decoding the response.
     */
    T execute() throws IOException;

    /**
     * Asynchronously send the request and notify {@code callback} of its response or if an error
     * occurred talking to the server, creating the request, or processing the response.
     */
    void enqueue(Callback<T> callback);

    /**
     * Returns true if this call has been either {@linkplain #execute() executed} or {@linkplain
     * #enqueue(Callback) enqueued}. It is an error to execute or enqueue a call more than once.
     */
    boolean isExecuted();

    /**
     * Create a new, identical call to this one which can be enqueued or executed even if this call
     * has already been.
     */
    Promise<T> clone();

    /**
     * The original HTTP request.
     */
    AbstractGoogleClientRequest<T> request();

    static abstract class Builder<T> {
        final AppExecutors executors;
        AbstractGoogleClientRequest<T> request;
        Throwable creationFailure;

        public Builder(AppExecutors executors) {
            this.executors = executors;
        }

        public Builder<T> request(AbstractGoogleClientRequest<T> request) {
            this.request = request;
            return this;
        }

        public Builder<T> creationFailure(Throwable creationFailure) {
            this.creationFailure = creationFailure;
            return this;
        }

        public abstract Promise<T> build();
    }
}
