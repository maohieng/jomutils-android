package com.jommobile.android.jomutils.ui.widget;

import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * A  subclass of {@link ClickableViewHolder} used with data binding by providing a
 * binding variable id (from generated class BR).
 *
 * @author MAO Hieng on 11/21/16.
 */
public class BindableViewHolder<D> extends ClickableViewHolder {
    private final ViewDataBinding mBinding;
    private final int mBindingVariableId;

    public BindableViewHolder(View v, int bindingVariableId) {
        super(v);
        this.mBindingVariableId = bindingVariableId;
        mBinding = DataBindingUtil.bind(v);
    }

    public void bind(D item) {
        mBinding.setVariable(mBindingVariableId, item);
        mBinding.executePendingBindings();
    }

    public ViewDataBinding getBinding() {
        return mBinding;
    }

    public int getBindingVariableId() {
        return mBindingVariableId;
    }
}