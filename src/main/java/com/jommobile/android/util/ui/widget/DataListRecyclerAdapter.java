package com.jommobile.android.util.ui.widget;


import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author MAO Hieng on 8/6/16.
 */
public abstract class DataListRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends ClickableRecyclerAdapter<VH> {

    private List<T> mData;

    public List<T> getData() {
        return mData;
    }

    public void setData(List<T> mData) {
        this.mData = mData;
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }
}
