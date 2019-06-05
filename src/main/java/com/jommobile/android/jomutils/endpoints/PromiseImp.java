package com.jommobile.android.jomutils.endpoints;

import android.util.Log;

import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.util.Preconditions;
import com.jommobile.android.jomutils.AppExecutors;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.Executor;

import androidx.annotation.Nullable;


/**
 * Created by MAO Hieng on 1/5/19.
 */
final class PromiseImp<T> implements Promise<T> {

    private final AppExecutors executors;

    @Nullable
    private AbstractGoogleClientRequest<T> request;

    @Nullable
    private Throwable creationFailure; // Either a RuntimeException or IOException.

    private boolean executed;
//    private volatile boolean canceled;

    private PromiseImp(AppExecutors executors) {
        this.executors = executors;
    }

    private PromiseImp(BuilderImpl<T> builder) {
        this(builder.executors);
        this.request = builder.request;
        this.creationFailure = builder.creationFailure;
    }

    @Override
    public T execute() throws IOException {
        synchronized (this) {
            if (executed) throw new IllegalStateException("Already executed.");
            executed = true;

            if (creationFailure != null) {
                if (creationFailure instanceof IOException) {
                    throw (IOException) creationFailure;
                } else {
                    throw (RuntimeException) creationFailure;
                }
            }

            if (request == null) {
                RuntimeException e = new RuntimeException("Unable to create request.");
                creationFailure = e;
                throw e;
            }
        }

        return request.execute();
    }

    @Override
    public void enqueue(Callback<T> callback) {
        Preconditions.checkNotNull(callback, "callback == null");

        final Executor mainCall = this.executors.mainThread();
        Executor networkCall;
        Throwable failure;
        AbstractGoogleClientRequest<T> req = this.request;

        synchronized (this) {
            if (this.executed) throw new IllegalStateException("Already executed.");
            this.executed = true;
        }

        networkCall = this.executors.networkIO();
        failure = this.creationFailure;

        if (failure != null) {
            mainCall.execute(() -> callback.onFailure(this, this.creationFailure));
            return;
        }

        if (req == null) {
            failure = new RuntimeException("Unable to create request.");
            this.creationFailure = failure;
            throw (RuntimeException) failure;
        }

        networkCall.execute(() -> {
            try {
                final T response = req.execute();
                mainCall.execute(() -> callback.onResponse(PromiseImp.this, response));
            } catch (IOException e) {
                if (e instanceof HttpResponseException) {
                    HttpResponseException httpResponseException = (HttpResponseException) e;
                    Log.e("Promise", "Request error: code=" + httpResponseException.getStatusCode() + ", content=" + httpResponseException.getContent());
                } else if (e instanceof SocketTimeoutException) {
                    Log.e("Promise", "Request timeout. " + e.getMessage());
                } else {
                    Log.e("Promise", "Request error--no internet.");
                }

                mainCall.execute(() -> callback.onFailure(PromiseImp.this, e));
            }
        });
    }

    @Override
    public synchronized boolean isExecuted() {
        return executed;
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    @Override
    public Promise<T> clone() {
        PromiseImp<T> p = new PromiseImp<T>(executors);
        p.request = request;
        p.creationFailure = creationFailure;

        return p;
    }

    @Override
    public AbstractGoogleClientRequest<T> request() {
        return request;
    }

    static final class BuilderImpl<T> extends Builder<T> {

        BuilderImpl(AppExecutors executors) {
            super(executors);
        }

        public Promise<T> build() {
            return new PromiseImp<>(this);
        }
    }
}
