package com.jommobile.android.util.ui;

import android.app.Activity;

import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

/**
 * Created by MAO Hieng on 10/25/18.
 */
public class BaseFragment extends Fragment {

    // Generally, isDestroyed should be replaced by {@link Fragment#isDetached()}
//    private boolean isDestroyed = true;
//
//    @CallSuper
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        isDestroyed = false;
//    }
//
//    @CallSuper
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        isDestroyed = true;
//    }

    protected void setTitle(CharSequence title) {
        if (getActivity() != null) {
            getActivity().setTitle(title);  // super->setTitle()

            // This is should be condition only when its Activity not override setTitle()
            // while using any support action bar.
//            if (getActivity() instanceof AppCompatActivity) {
//                ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
//                if (actionBar != null) {
//                    actionBar.setTitle(title);
//                }
//            }
        }
    }

    protected void setTitle(@StringRes int resId) {
        if (getActivity() != null) {
            getActivity().setTitle(resId); // super->setTitle()

//            if (getActivity() instanceof AppCompatActivity) {
//                ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
//                if (actionBar != null) {
//                    actionBar.setTitle(resId);
//                }
//            }
        }
    }

    protected CharSequence getTitle() {
        Activity activity = getActivity();
        ActionBar actionBar;
        if (/*activity != null && */activity instanceof AppCompatActivity
                && (actionBar = ((AppCompatActivity) activity).getSupportActionBar()) != null) {
            return actionBar.getTitle();
        }

        return activity != null ? activity.getTitle() : null;
    }
}
