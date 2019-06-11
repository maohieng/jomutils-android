package com.jommobile.android.jomutils.db;


import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import com.google.common.annotations.Beta;
import com.jommobile.android.jomutils.model.BaseModel;
import com.jommobile.android.jomutils.model.Flavor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Nonnull;

/**
 * @author MAO Hieng on 1/2/19.
 */
public class BaseEntity implements BaseModel/*, Parcelable*/ {

    protected static final String COLUMN_ID = "id";
    protected static final String COLUMN_CREATED_DATE = "created_date";
    protected static final String COLUMN_MODIFIED_DATE = "modified_date";
    protected static final String COLUMN_IS_SYNC = "is_sync";
    protected static final String COLUMN_FLAVOR = "flavor";

    @PrimaryKey
    private long id;

    /**
     * Client's database need to implement a DateConverter to convert
     * the Date type into String or long time milliseconds.
     * <p>
     * This library provides a {@link DateTimeConverter} to convert between
     * Date type and long time milliseconds.
     * </p>
     */
    @ColumnInfo(name = COLUMN_CREATED_DATE)
    private Date createdDate;
    @ColumnInfo(name = COLUMN_MODIFIED_DATE)
    private Date modifiedDate;
    @ColumnInfo(name = COLUMN_IS_SYNC)
    private boolean isSync;

    /**
     * Client database need to use {@link FlavorConverter} as a type converter
     */
    private Flavor flavor;

    /**
     * Default constructor.
     **/
    public BaseEntity() {
    }

    ///////////////////////////////////////////////////////////////////////////
    // Parcelable implementation
    ///////////////////////////////////////////////////////////////////////////

    protected void readFromParcel(Parcel in) {
        id = in.readLong();
        isSync = in.readByte() != 0;
    }

    //    @Override
    protected void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeByte((byte) (isSync ? 1 : 0));
    }

    /**
     * Puts data into the given {@link ContentValues} with their column name as key.
     * Used to support long old ancient hard coding of Android database implementation.
     *
     * @param values - Existing values
     * @return
     */
    @Beta
    public ContentValues putContentValues(@Nonnull ContentValues values) {
        // TODO: 6/11/2019 get converters from @TypeConverters
        values.put(COLUMN_ID, id);
        values.put(COLUMN_CREATED_DATE, DateTimeConverter.toTimeMillis(createdDate));
        values.put(COLUMN_CREATED_DATE, DateTimeConverter.toTimeMillis(modifiedDate));
        values.put(COLUMN_IS_SYNC, isSync);
        values.put(COLUMN_FLAVOR, FlavorConverter.toName(flavor));

        return values;
    }

    /**
     * Read data from the given {@link Cursor} with their column name.
     * Used to support long old ancient hard coding of Android database implementation.
     *
     * @param cursor
     */
    @Beta
    public void readFromCursor(@Nonnull Cursor cursor) {
        // TODO: 6/11/2019 get converters from @TypeConverters
        id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));

        int createdDateColumnIndex = cursor.getColumnIndex(COLUMN_CREATED_DATE);
        int type = cursor.getType(createdDateColumnIndex);
        if (type == Cursor.FIELD_TYPE_INTEGER) {
            createdDate = new Date(cursor.getLong(createdDateColumnIndex));
            modifiedDate = new Date(cursor.getLong(cursor.getColumnIndex(COLUMN_MODIFIED_DATE)));
        } else { // suppose it is a String type formatted yyyy-MM-dd HH:mm:ss
            // TODO: 6/11/2019 this depends on the date converter that registered with @TypeConverters
            String createdStr = cursor.getString(createdDateColumnIndex);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                createdDate = dateFormat.parse(createdStr);
                modifiedDate = dateFormat.parse(cursor.getString(cursor.getColumnIndex(COLUMN_MODIFIED_DATE)));
            } catch (ParseException e) {
                // as createdDate & modifiedDate is optional to get
                e.printStackTrace();
            }
        }

        isSync = cursor.getInt(cursor.getColumnIndex(COLUMN_IS_SYNC)) > 0;
        flavor = FlavorConverter.toFlavor(cursor.getString(cursor.getColumnIndex(COLUMN_FLAVOR)));
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public Date getCreatedDate() {
        return createdDate;
    }

    @Override
    public Date getModifiedDate() {
        return modifiedDate;
    }

    @Override
    public boolean isSync() {
        return isSync;
    }

    @Override
    public Flavor getFlavor() {
        return flavor;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public void setSync(boolean sync) {
        isSync = sync;
    }

    public void setFlavor(Flavor flavor) {
        this.flavor = flavor;
    }
}
