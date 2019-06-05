package com.jommobile.android.jomutils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings.Secure;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.reflect.Field;
import java.util.Locale;

import androidx.annotation.DimenRes;

public class DeviceUtils {

    // Prevent from creating an object
    private DeviceUtils() {
    }

    /**
     * Giving the app's package.
     */
    public static final String PLAY_STORE_APP_URL_FORMAT = "https://play.google.com/store/apps/details?id=%s";
    private static final String MARKET_APP_DETAIL_FORMAT = "market://details?id=%s";

    /**
     * Returns true if the running device is tablet.
     *
     * @param context
     * @return
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static String getDeviceId(Context context) {
        // https://android-developers.googleblog.com/2011/03/identifying-app-installations.html
        return Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
    }

    public static Locale getLanguage(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return context.getResources().getConfiguration().getLocales().get(0);
        } else {
            return context.getResources().getConfiguration().locale;
        }
    }

    public static void changeLocaleConfiguration(Context context, Locale newLocale) {
        Resources res = context.getResources();
        // Change locale setting in app
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            conf.setLocale(newLocale);
        } else {
            conf.locale = newLocale;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            context.createConfigurationContext(conf);
        } else {
            res.updateConfiguration(conf, dm);
        }
    }

    /**
     * Returns does the device network have connection to WIFI or Mobile.
     *
     * @param context : application context
     * @return true if the device connects to WIFI or Mobile, and vice versa.
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }

        return false;
    }

    /**
     * Returns does the device network is a WIFI connection.
     *
     * @param context
     * @return
     */
    public static boolean isWiFiConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();

        if (info != null && info.getType() == ConnectivityManager.TYPE_WIFI && info.isConnected()) {
            return true;
        }

        return false;
    }

    public static boolean isMobileDataConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            switch (info.getType()) {
                case ConnectivityManager.TYPE_MOBILE:
                case ConnectivityManager.TYPE_MOBILE_MMS:
                case ConnectivityManager.TYPE_MOBILE_SUPL:
                case ConnectivityManager.TYPE_MOBILE_DUN:
                case ConnectivityManager.TYPE_MOBILE_HIPRI:
                    return true;
                default:
                    return false;
            }
        }

        return false;
    }

//    /**
//     * Returns current connected WiFi's SSID (Service Set Identifider). May be <code>null</code>.</br> <b>Note:</b> You must check
//     * network or WiFi connection first by calling {@link #isWiFiConnected(Context)} or {@link #isOnline(Context)}
//     *
//     * @param context
//     * @return
//     */
//    public static String getWiFiSSID(Context context) {
//        String ssid = null;
//        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//        if (wifiInfo != null && !TextUtils.isEmpty(wifiInfo.getSSID())) {
//            ssid = wifiInfo.getSSID();
//        }
//
//        return ssid;
//    }

    /**
     * Returns the application version name of the application's <code>context</code> given.
     *
     * @param context : application context
     * @return the context's application version name which is set in Manifest file.
     */
    public static String getAppVersionName(Context context) {
        String appVersionName = "";
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            appVersionName = packageInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        return appVersionName;
    }

    /**
     * Returns the application version code of the application's <code>context</code> given.
     *
     * @param context : application context
     * @return the context's application version code which is set in Manifest file.
     */
    public static int getAppVersionCode(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    /**
     * Returns a full device name (model and manufacturer). E.g. Samsung GT-S5830L, Motorola MB860, Sony Ericsson LT18i, LGE
     * LG-P500, HTC Desire V, HTC Wildfire S A510e ...
     *
     * @return
     */
    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalizeFirstChar(model);
        } else {
            return capitalizeFirstChar(manufacturer) + " " + model;
        }
    }

    public static String capitalizeFirstChar(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    public static String getSDKCodeName() {
        String codeName = "";
        Field[] fields = Build.VERSION_CODES.class.getFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            int fieldValue = -1;

            try {
                fieldValue = field.getInt(new Object());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            if (fieldValue == Build.VERSION.SDK_INT) {
                codeName = fieldName;
            }
        }
        return codeName;
    }

    private static Point getScreenSize(WindowManager windowManager) {
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();

        display.getSize(size);

        return size;
    }

    /**
     * Returns the device's screen size which its width and height is in pixel.
     *
     * @param activity : Current {@link Activity}
     * @return a {@link Point}
     */
    public static Point getScreenSize(Activity activity) {
        WindowManager wm = activity.getWindowManager();
        Point size = getScreenSize(wm);

        return size;
    }

    /**
     * Returns the device's screen size which its width and height is in pixel.
     *
     * @param context : application context
     * @return a {@link Point}
     */
    public static Point getScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point size = getScreenSize(wm);

        return size;
    }

    public static float getPixelSizeFromDp(Context context, @DimenRes int id) {
        Resources resources = context.getApplicationContext().getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        float pixel = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, resources.getDimension(id), displayMetrics);

        return pixel;
    }

    /**
     * Convenience method to force dismissing the keyboard.
     *
     * @param editText
     */
    public static void dismissKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    /**
     * Convenience method to force showing the keyboard.
     *
     * @param editText
     */
    public static void showKeyboard(EditText editText) {
        InputMethodManager im = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        im.showSoftInput(editText, 0);
    }

    public static void dismissKeyboard(Activity activity) {
        InputMethodManager im = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View focusedView = activity.getCurrentFocus();
        if (focusedView != null) {
            im.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }
    }

    public static Intent createDialIntent(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phone));
        return intent;
    }

    public static Intent createShareChooserIntent(String title, String sharedText) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, sharedText);

        return Intent.createChooser(intent, title);
    }

    public static void startPlayStoreAppDetail(Context context) {
        String appPackageName = context.getPackageName();
        Intent intent;
        try {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format(Locale.US, MARKET_APP_DETAIL_FORMAT, appPackageName)));
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format(Locale.US, PLAY_STORE_APP_URL_FORMAT, appPackageName)));
            context.startActivity(intent);
        }
    }

}
