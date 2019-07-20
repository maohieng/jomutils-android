package com.jommobile.android.jomutils.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

class AlwaysFetch implements NetworkFetchOption {

    @Override
    public boolean needFetchNow() {
        return true;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj)
            return true;

        return obj instanceof AlwaysFetch && ((AlwaysFetch) obj).needFetchNow();
    }

    @Override
    public int hashCode() {
        return 31 + super.hashCode();
    }

    @NonNull
    @Override
    public String toString() {
        return "NetworkFetchOption:Always";
    }
}