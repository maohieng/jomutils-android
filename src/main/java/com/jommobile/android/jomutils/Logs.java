package com.jommobile.android.jomutils;

import android.util.Log;

public class Logs {

    private static final String LOG_PREFIX = "jom_";
    private static final int LOG_PREFIX_LENGTH = LOG_PREFIX.length();
    private static final int MAX_LOG_TAG_LENGTH = 23;

    private Logs() {
    }

    public static String makeTag(String str) {
//        if (str.length() > MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH) {
//            return LOG_PREFIX + str.substring(0, MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH - 1);
//        }

        return LOG_PREFIX + str;
    }

    /**
     * Don't use this when obfuscating class names!
     */
    public static String makeTag(Class<?> cls) {
        return makeTag(cls.getSimpleName());
    }

    public static void D(final String tag, String message) {
        // noinspection PointlessBooleanExpression,ConstantConditions
        if (BuildConfig.DEBUG || Log.isLoggable(tag, Log.DEBUG)) {
            Log.d(tag, message);
        }
    }

    public static void D(final String tag, String message, Throwable cause) {
        // noinspection PointlessBooleanExpression,ConstantConditions
        if (BuildConfig.DEBUG || Log.isLoggable(tag, Log.DEBUG)) {
            Log.d(tag, message, cause);
        }
    }

    public static void V(final String tag, String message) {
        // noinspection PointlessBooleanExpression,ConstantConditions
        if (BuildConfig.DEBUG && Log.isLoggable(tag, Log.VERBOSE)) {
            Log.v(tag, message);
        }
    }

    public static void V(final String tag, String message, Throwable cause) {
        // noinspection PointlessBooleanExpression,ConstantConditions
        if (BuildConfig.DEBUG && Log.isLoggable(tag, Log.VERBOSE)) {
            Log.v(tag, message, cause);
        }
    }

    public static void I(final String tag, String message) {
        Log.i(tag, message);
    }

    public static void I(final String tag, String message, Throwable cause) {
        Log.i(tag, message, cause);
    }

    public static void W(final String tag, String message) {
        Log.w(tag, message);
    }

    public static void W(final String tag, String message, Throwable cause) {
        Log.w(tag, message, cause);
    }

    public static void E(final String tag, String message) {
        Log.e(tag, message);
    }

    public static void E(final String tag, String message, Throwable cause) {
        Log.e(tag, message, cause);
    }

}
