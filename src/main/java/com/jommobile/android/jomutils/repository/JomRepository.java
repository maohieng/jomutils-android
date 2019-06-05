package com.jommobile.android.jomutils.repository;

import com.jommobile.android.jomutils.AppExecutors;
import com.jommobile.android.jomutils.JOMApp;

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
