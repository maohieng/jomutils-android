package com.jommobile.android.jomutils.endpoints;

import com.google.api.client.util.DateTime;

import java.util.Date;

/**
 * Util class to convert between Google's {@link DateTime} and {@link Date}.
 *
 * @author MAO Hieng on 1/13/19.
 */
public final class DateTimeConverter {

    private DateTimeConverter() {
        //no instance
    }

    public static DateTime toDateTime(Date date) {
        return date != null ? new DateTime(date.getTime()) : null;
    }

    public static Date toDate(DateTime dateTime) {
        return dateTime != null ? new Date(dateTime.getValue()) : null;
    }
}
