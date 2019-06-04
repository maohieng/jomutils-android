package com.jommobile.android.util.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author MAO Hieng on 1/4/19.
 */
public class Image implements Parcelable {

    private String image;
    private String thumbnail;

    /**
     * Default constructor.
     **/
    public Image() {
    }

    protected Image(Parcel in) {
        image = in.readString();
        thumbnail = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeString(thumbnail);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
