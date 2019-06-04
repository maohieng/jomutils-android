package com.jommobile.android.util.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

final class ResourceImp<T> implements Resource<T> {

    private final Status status;

    private String message;

    private T data;

    private Throwable cause;

    private ResourceImp(Status status) {
        this.status = status;
    }

    ResourceImp(@NonNull Status status, @Nullable T data, @Nullable String message, Throwable cause) {
        this(status);
        this.data = data;
        this.message = message;
        this.cause = cause;
    }

    @NonNull
    @Override
    public Status getStatus() {
        return status;
    }

    @Nullable
    @Override
    public String getMessage() {
        return message;
    }

    @Nullable
    @Override
    public T getData() {
        return data;
    }

    @Nullable
    @Override
    public Throwable getCause() {
        return cause;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ResourceImp<?> resource = (ResourceImp<?>) o;

        if (status != resource.status) {
            return false;
        }
        if (message != null ? !message.equals(resource.message) : resource.message != null) {
            return false;
        }
        return data != null ? data.equals(resource.data) : resource.data == null;
    }

    @Override
    public int hashCode() {
        int result = status.hashCode();
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}