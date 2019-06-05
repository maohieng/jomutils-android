package com.jommobile.android.jomutils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import java.util.Set;

import androidx.annotation.Nullable;

public final class SharedPrefs {

    private SharedPrefs() {
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void apply(Editor editor) {
        editor.apply();
        // SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Methods allows user to provide their SharePreferences object
    ///////////////////////////////////////////////////////////////////////////

    public static void clear(SharedPreferences sharedPreferences) {
        Editor editor = sharedPreferences.edit().clear();
        apply(editor);
    }

    public static void putFloat(SharedPreferences sharedPreferences, String key, float value) {
        Editor editor = sharedPreferences.edit().putFloat(key, value);
        apply(editor);
    }

    public static void putInt(SharedPreferences sharedPreferences, String key, int value) {
        Editor editor = sharedPreferences.edit().putInt(key, value);
        apply(editor);
    }

    public static void putString(SharedPreferences sharedPreferences, String key, @Nullable String value) {
        Editor editor = sharedPreferences.edit().putString(key, value);
        apply(editor);
    }

    public static void putBoolean(SharedPreferences sharedPreferences, String key, boolean value) {
        Editor editor = sharedPreferences.edit().putBoolean(key, value);
        apply(editor);
    }

    public static void putLong(SharedPreferences sharedPreferences, String key, long value) {
        Editor editor = sharedPreferences.edit().putLong(key, value);
        apply(editor);
    }

    public static void putStringSet(SharedPreferences preferences, String key, @Nullable Set<String> values) {
        Editor editor = preferences.edit().putStringSet(key, values);
        apply(editor);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Methods that use getSharePreferences().
    // All get methods must provide default value to return
    ///////////////////////////////////////////////////////////////////////////

    public static void clear(Context context) {
        clear(getSharedPreferences(context));
    }

    public static void putFloat(Context context, String key, float value) {
        putFloat(getSharedPreferences(context), key, value);
    }

    public static float getFloat(Context context, String key, float defaultValue) {
        return getSharedPreferences(context).getFloat(key, defaultValue);
    }

    public static void putString(Context context, String key, @Nullable String value) {
        putString(getSharedPreferences(context), key, value);
    }

    public static String getString(Context context, String key, @Nullable String defaultValue) {
        return getSharedPreferences(context).getString(key, defaultValue);
    }

    public static void putBoolean(Context context, String key, boolean value) {
        putBoolean(getSharedPreferences(context), key, value);
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        return getSharedPreferences(context).getBoolean(key, defaultValue);
    }

    public static void putInt(Context context, String key, int value) {
        putInt(getSharedPreferences(context), key, value);
    }

    public static int getInt(Context context, String key, int defaultValue) {
        return getSharedPreferences(context).getInt(key, defaultValue);
    }

    public static void putLong(Context context, String key, long value) {
        putLong(getSharedPreferences(context), key, value);
    }

    public static long getLong(Context context, String key, long defaultValue) {
        return getSharedPreferences(context).getLong(key, defaultValue);
    }

    public static void putStringSet(Context context, String key, @Nullable Set<String> values) {
        putStringSet(getSharedPreferences(context), key, values);
    }

    public static Set<String> getStringSet(Context context, String key, @Nullable Set<String> defValues) {
        return getSharedPreferences(context).getStringSet(key, defValues);
    }

    ///////////////////////////////////////////////////////////////////////////
    // No default Value provided
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Default is {@code null}.
     */
    public static String getString(Context context, String key) {
        return getString(context, key, null);
    }

    /**
     * Default is {@code false}.
     */
    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }

    /**
     * Default is {@code 0f}.
     */
    public static float getFloat(Context context, String key) {
        return getFloat(context, key, 0f);
    }

    /**
     * Default is {@code 0}.
     */
    public static long getLong(Context context, String key) {
        return getLong(context, key, 0);
    }

    /**
     * Default is {@code 0}.
     */
    public static int getInt(Context context, String key) {
        return getInt(context, key, 0);
    }

    /**
     * Default is {@code null}.
     */
    public static Set<String> getStringSet(Context context, String key) {
        return getStringSet(context, key, null);
    }
}
