package com.jommobile.android.jomutils.db;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * Date and time milliseconds converter
 */
public class DateTimeConverter {

    @TypeConverter
    public static Date toDate(long timeMillis) {
        if (timeMillis <= 0) {
            return null;
        }

        // we should check if the given long timeMillis is really in java milliseconds

        return new Date(timeMillis);
    }

    @TypeConverter
    public static long toTimeMillis(Date date) {
        return date == null ? 0 : date.getTime();
    }
}