package com.jommobile.android.jomutils.repository;

import androidx.annotation.MainThread;

public interface NetworkFetchOption {
    @MainThread
    boolean needFetchNow();
}