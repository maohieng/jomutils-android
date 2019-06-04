package com.jommobile.android.util.ui.widget;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by MAO Hieng on 7/23/16. <br>
 * A listener handles click event from a {@link androidx.recyclerview.widget.RecyclerView.ViewHolder}'s {@code itemView} or
 * any views in that {@code ViewHolder}.
 */
public interface OnItemRecyclerClickListener {

    /**
     * Handles {@code holder.itemView}'s click listener or any view's click listener which is in {@code holder.itemView}.
     *
     * @param holder The {@link androidx.recyclerview.widget.RecyclerView.ViewHolder} that the action
     *               handles on.
     * @param clickedView Clicked view which is in {@code holder}.
     */
    public void onItemRecyclerClick(@NonNull RecyclerView.ViewHolder holder, @NonNull View clickedView/*, @Nullable Object payload*/);

}
