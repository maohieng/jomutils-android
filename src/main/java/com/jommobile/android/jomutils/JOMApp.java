package com.jommobile.android.jomutils;

/**
 * JOM's base Android application class.
 *
 * @author MAO Hieng on 1/12/19.
 */
public interface JOMApp {

    default AppExecutors getAppExecutors() {
        return AppExecutors.Factory.get();
    }

}
