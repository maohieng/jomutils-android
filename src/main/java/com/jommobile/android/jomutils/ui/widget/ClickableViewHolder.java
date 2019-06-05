package com.jommobile.android.jomutils.ui.widget;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author MAO Hieng on 7/23/16.
 */
public class ClickableViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private OnItemRecyclerClickListener mListener;

    public ClickableViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onItemRecyclerClick(this, v);
        }
    }

    public void setOnItemRecyclerClickListener(OnItemRecyclerClickListener listener) {
        mListener = listener;
        itemView.setOnClickListener(this);
    }

    public void addClickableView(View v) {
        v.setOnClickListener(this);
    }
}
