package com.jommobile.android.util.ui.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;

/**
 * A general simple {@link androidx.recyclerview.widget.RecyclerView.Adapter} using with data binding.
 *
 * @author MAO Hieng on 7/26/16.
 */
public class BindableRecyclerAdapter<T> extends DataListRecyclerAdapter<T, BindableViewHolder<T>> {

    private LayoutInflater mInflater;

    @LayoutRes
    private final int mViewLayoutRes;
    private final int mBindingVariableId;

    protected BindableRecyclerAdapter(@LayoutRes int viewLayoutRes, int bindingVariableId) {
        this.mViewLayoutRes = viewLayoutRes;
        this.mBindingVariableId = bindingVariableId;
    }

    public BindableRecyclerAdapter(Context context, int viewLayoutRes, int bindingVariableId) {
        this(viewLayoutRes, bindingVariableId);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public BindableViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }

        View view = mInflater.inflate(mViewLayoutRes, parent, false);
        BindableViewHolder<T> holder = new BindableViewHolder<>(view, mBindingVariableId);
        holder.setOnItemRecyclerClickListener(onItemRecyclerClickListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(BindableViewHolder<T> holder, int position) {
        final T item = getData().get(position);
        holder.bind(item);
    }
}
