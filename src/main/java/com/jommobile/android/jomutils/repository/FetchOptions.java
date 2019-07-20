package com.jommobile.android.jomutils.repository;

public final class FetchOptions {

    private FetchOptions() {
        //no instance
    }

    public static AlwaysFetch always() {
        return new AlwaysFetch();
    }

    public static ScheduleFetch schedule(long timeGapSeconds) {
        return new ScheduleFetch(timeGapSeconds);
    }

    public static NeverFetch never() {
        return new NeverFetch();
    }
}