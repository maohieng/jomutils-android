package com.jommobile.android.jomutils.repository;

import androidx.annotation.Nullable;

/**
 * This class is used for ...
 *
 * @autor MAO Hieng 7/19/2019
 */
public class NeverFetch implements NetworkFetchOption {
    @Override
    public boolean needFetchNow() {
        return false;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj)
            return true;

        return obj instanceof NeverFetch && !((NeverFetch) obj).needFetchNow();
    }

    @Override
    public int hashCode() {
        return 31 + super.hashCode();
    }
}
