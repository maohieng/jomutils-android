package com.jommobile.android.jomutils.repository;

import com.jommobile.android.jomutils.AppExecutors;

/**
 * Base repository class.
 *
 * @author MAO Hieng on 1/9/19.
 */
public abstract class JomRepository {

    // final Context mContext;
    final AppExecutors mAppExecutors;

    public JomRepository(AppExecutors appExecutors) {
        // mContext = application.getApplicationContext();
        mAppExecutors = appExecutors;
    }

    protected AppExecutors getAppExecutors() {
        return mAppExecutors;
    }

}
