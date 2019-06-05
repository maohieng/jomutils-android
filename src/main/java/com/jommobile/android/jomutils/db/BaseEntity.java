package com.jommobile.android.jomutils.db;


import android.os.Parcel;

import com.jommobile.android.jomutils.model.BaseModel;
import com.jommobile.android.jomutils.model.Flavor;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

/**
 * @author MAO Hieng on 1/2/19.
 */
public class BaseEntity implements BaseModel/*, Parcelable*/ {

    @PrimaryKey
    private long id;

    @ColumnInfo(name = "created_date")
    private Date createdDate;
    @ColumnInfo(name = "modified_date")
    private Date modifiedDate;
    @ColumnInfo(name = "is_sync")
    private boolean isSync;

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

//    @Override
//    public int describeContents() {
//        return 0;
//    }

//    public static final Creator<BaseEntity> CREATOR = new Creator<BaseEntity>() {
//        @Override
//        public BaseEntity createFromParcel(Parcel in) {
//            return new BaseEntity(in);
//        }
//
//        @Override
//        public BaseEntity[] newArray(int size) {
//            return new BaseEntity[size];
//        }
//    };

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
