package com.jommobile.android.jomutils.ui.widget;


import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * @author MAO Hieng on 8/6/16.
 */
public abstract class DataListRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends ClickableRecyclerAdapter<VH> {

    private List<T> mData;

    public List<T> getData() {
        return mData;
    }

    public void notifyNewDataSetChanged(List<T> newData) {
        if (mData != null && !mData.isEmpty()) {
            mData.clear();
        }

        mData = newData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }
}
