package com.jommobile.android.util.ui.widget;

import androidx.recyclerview.widget.RecyclerView;

/**
 * A RecyclerView's {@link androidx.recyclerview.widget.RecyclerView.Adapter} class
 * that provides a click listener {@link OnItemRecyclerClickListener} to item.
 *
 * @author MAO Hieng on 7/23/16.
 */
public abstract class ClickableRecyclerAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    OnItemRecyclerClickListener onItemRecyclerClickListener;

    public void setOnItemRecyclerClickListener(OnItemRecyclerClickListener onItemRecyclerClickListener) {
        this.onItemRecyclerClickListener = onItemRecyclerClickListener;
    }

//    public OnItemRecyclerClickListener getOnItemRecyclerClickListener() {
//        return onItemRecyclerClickListener;
//    }
}
