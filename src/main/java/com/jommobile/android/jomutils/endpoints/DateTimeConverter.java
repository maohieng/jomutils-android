package com.jommobile.android.jomutils.endpoints;

import com.google.api.client.util.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Util class to convert between Google's {@link DateTime} and {@link Date}.
 *
 * @author MAO Hieng on 1/13/19.
 */
public final class DateTimeConverter {

    //    private static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-DD HH:mm:ss";
    private static final SimpleDateFormat sDefaultDateTimeFormat = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");

    private DateTimeConverter() {
        //no instance
    }

    public static DateTime toDateTime(Date date) {
        return date != null ? new DateTime(date.getTime()) : null;
    }

    public static Date toDate(DateTime dateTime) {
        return dateTime != null ? new Date(dateTime.getValue()) : null;
    }

    public static Date toDate(String formatted) throws ParseException {
        return sDefaultDateTimeFormat.parse(formatted);
    }

    public static Date toDate(long milliseconds) {
        return new Date(milliseconds);
    }
}
