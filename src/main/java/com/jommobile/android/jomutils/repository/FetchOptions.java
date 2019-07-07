package com.jommobile.android.jomutils.repository;

public final class FetchOptions {

    private FetchOptions() {
        //no instance
    }

    public static AlwaysFetchOption always() {
        return new AlwaysFetchOption();
    }

    public static ScheduleFetchOption schedule(long timeGapSeconds) {
        return new ScheduleFetchOption(timeGapSeconds);
    }
}