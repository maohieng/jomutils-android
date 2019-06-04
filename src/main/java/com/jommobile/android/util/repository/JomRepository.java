package com.jommobile.android.util.repository;

import com.jommobile.android.util.AppExecutors;
import com.jommobile.android.util.JOMApp;

/**
 * Base repository class.
 *
 * @author MAO Hieng on 1/9/19.
 */
public abstract class JomRepository {

    // final Context mContext;
    final AppExecutors mAppExecutors;

    public JomRepository(JOMApp application) {
        // mContext = application.getApplicationContext();
        mAppExecutors = application.getAppExecutors();
    }

    protected AppExecutors getAppExecutors() {
        return mAppExecutors;
    }

}
