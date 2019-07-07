package com.jommobile.android.jomutils.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.common.annotations.Beta;
import com.jommobile.android.jomutils.Logs;

import java.util.Objects;

@Beta
class ScheduleFetchOption implements NetworkFetchOption {

    private static final String TAG = Logs.makeTag(ScheduleFetchOption.class);

    private static long lastFetchSeconds;

    private final long timeGapSeconds;

    ScheduleFetchOption(long timeGapSeconds) {
        this.timeGapSeconds = timeGapSeconds;
    }

    @Override
    public boolean needFetchNow() {
        long last = lastFetchSeconds * 1000;
        long current = System.currentTimeMillis();
        long gapSeconds = Math.round((double) ((current - last) / 1000));
        boolean needFetch = gapSeconds >= timeGapSeconds;
        if (needFetch) {
            lastFetchSeconds = Math.round((double) (current / 1000));
        }

//        Logs.I(TAG, "Schedule fetch: " + needFetch + ", gap: " + gap);
        Logs.I(TAG, "Schedule fetch: last=" + last + ", current=" + current + ", gap=" + gapSeconds + ", fetch=" + needFetch);

        return needFetch;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof ScheduleFetchOption)) {
            return false;
        }

        ScheduleFetchOption schedule = (ScheduleFetchOption) obj;
        return this.timeGapSeconds == schedule.timeGapSeconds;
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeGapSeconds);
    }

    @NonNull
    @Override
    public String toString() {
        return "NetworkFetchOption:Schedule:" + timeGapSeconds + " sec";
    }
}