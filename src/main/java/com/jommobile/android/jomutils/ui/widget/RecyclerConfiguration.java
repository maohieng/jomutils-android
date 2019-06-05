package com.jommobile.android.jomutils.ui.widget;


import com.jommobile.android.jomutils.BR;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by MAO Hieng on 7/26/16.
 */
public class RecyclerConfiguration extends BaseObservable {

    private boolean hasFixedSize = true;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.ItemDecoration itemDecoration;
    private RecyclerView.ItemAnimator itemAnimator;
    private RecyclerView.Adapter adapter;

    @BindingAdapter("app:recyclerConfig")
    public static void configureRecyclerView(RecyclerView recyclerView, RecyclerConfiguration configuration) {
        recyclerView.setHasFixedSize(configuration.hasFixedSize);
        recyclerView.setLayoutManager(configuration.layoutManager);
        recyclerView.setItemAnimator(configuration.itemAnimator);
        recyclerView.addItemDecoration(configuration.itemDecoration);
        recyclerView.setAdapter(configuration.adapter);
    }

    @NonNull
    @Bindable
    public RecyclerView.LayoutManager getLayoutManager() {
        return layoutManager;
    }

    @NonNull
    @Bindable
    public RecyclerView.ItemDecoration getItemDecoration() {
        return itemDecoration;
    }

    @Nullable
    @Bindable
    public RecyclerView.ItemAnimator getItemAnimator() {
        return itemAnimator;
    }

    @Nullable
    @Bindable
    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    @Bindable
    public boolean isHasFixedSize() {
        return hasFixedSize;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        notifyPropertyChanged(BR.layoutManager); // generated from @Bindable
    }

    public void setItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        this.itemDecoration = itemDecoration;
        notifyPropertyChanged(BR.itemDecoration); // generated from @Bindable
    }

    public void setItemAnimator(RecyclerView.ItemAnimator itemAnimator) {
        this.itemAnimator = itemAnimator;
        notifyPropertyChanged(BR.itemAnimator); // generated from @Bindable
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
        notifyPropertyChanged(BR.adapter); // generated from @Bindable
    }

    public void setHasFixedSize(boolean hasFixedSize) {
        this.hasFixedSize = hasFixedSize;
        notifyPropertyChanged(BR.hasFixedSize);
    }
}
