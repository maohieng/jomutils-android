package com.jommobile.android.jomutils.ui;

import android.view.View;

import androidx.databinding.ViewStubProxy;

/**
 * Utilities class used with UIs.
 *
 * @author MAO Hieng on 1/11/19.
 */
public final class JOMUIs {

    private JOMUIs() {
        //no instance
    }

    /**
     * Sets visibility on {@link android.view.ViewStub} by {@link ViewStubProxy}
     * which created by databinding ({@link androidx.databinding.ViewDataBinding}). <br /> <br />
     * <p>
     * <b>Note:</b> Create a boolean variable (loading) in a data binding layout is useless.
     * To use a {@code ViewStub}, means to include a general layout and show it when need (for reason of lazy).
     * Then you cannot use your {@code loading} variable with that {@code ViewStub} because it sets the
     * visibility only if it's not inflated yet. But to use the {@code loading} with &lt;include&gt;'s visibility,
     * the layout data binding ({@link androidx.databinding.ViewDataBinding}) will not generate the
     * {@code loading} variable because it says that variable is not used. _-|-_
     *
     * @param stubProxy Generated from the {@link androidx.databinding.ViewDataBinding}'s layout.
     * @param visible   true for {@link View#VISIBLE}, false for {@link View#GONE}.
     */
    public static void setBindingViewStubVisibility(ViewStubProxy stubProxy, boolean visible) {
        if (stubProxy == null)
            return;

        int visibility = visible ? View.VISIBLE : View.GONE;

        boolean isInflated = stubProxy.isInflated();
        if (!isInflated) {
            stubProxy.getViewStub().setVisibility(visibility);
        } else {
            stubProxy.getRoot().setVisibility(visibility);
        }
    }
}
