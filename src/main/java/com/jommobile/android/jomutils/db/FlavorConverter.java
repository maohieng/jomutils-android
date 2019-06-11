package com.jommobile.android.jomutils.db;

import androidx.room.TypeConverter;

import com.jommobile.android.jomutils.model.Flavor;

/**
 * Database type converter between {@link Flavor} and its name.
 *
 * @author MAO Hieng on 1/10/19.
 */
public class FlavorConverter {

    @TypeConverter
    public static Flavor toFlavor(String name) {
        if (name == null || name.isEmpty())
            return null;

        return Flavor.of(name);
    }

    @TypeConverter
    public static String toName(Flavor flavor) {
        return flavor == null ? null : flavor.toString();
    }
}
