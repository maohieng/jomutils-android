/*
 * Copyright (c) 2016 CamMob Co.,Ltd.
 */

package com.jommobile.android.jomutils.ui.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.view.View;

import com.jommobile.android.jomutils.R;

import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by MAO Hieng on 2/4/16.
 */
public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int mMarginFirstTop;
    private int mMarginLeftAndRight;
    private int mMarginBottom;
    private int mMarginLastBottom;

    public VerticalSpaceItemDecoration(@NonNull Context context,
                                       @DimenRes int marginFirstTop,
                                       @DimenRes int marginLeftAndRight,
                                       @DimenRes int marginBottom,
                                       @DimenRes int marginLastBottom) {
        Resources resources = context.getResources();
        mMarginFirstTop = resources.getDimensionPixelSize(marginFirstTop);
        mMarginLeftAndRight = resources.getDimensionPixelSize(marginLeftAndRight);
        mMarginBottom = resources.getDimensionPixelSize(marginBottom);
        mMarginLastBottom = resources.getDimensionPixelSize(marginLastBottom);
    }

    /**
     * Constructor constructs the default item padding;
     * @param context
     */
    public VerticalSpaceItemDecoration(@NonNull Context context) {
        this(context, R.dimen.list_vertical_margin, R.dimen.list_horizontal_margin, R.dimen.list_vertical_margin, R.dimen.list_vertical_margin);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        initVerticalOffsets(outRect, view, parent);
        initHorizontalOffsets(outRect, view, parent);
    }

    protected void initVerticalOffsets(Rect outRect, View view, RecyclerView parent) {
        int position = parent.getChildAdapterPosition(view);
        int size = parent.getAdapter().getItemCount();

        if (position != (size - 1)) { // If not last item
            outRect.bottom = mMarginBottom;
        } else {
            outRect.bottom = mMarginLastBottom;
        }

        if (position == 0) { // First item
            outRect.top = mMarginFirstTop;
        }
    }

    protected void initHorizontalOffsets(Rect outRect, View view, RecyclerView parent) {
        outRect.left = mMarginLeftAndRight;
        outRect.right = mMarginLeftAndRight;
    }
}
