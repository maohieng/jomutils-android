package com.jommobile.android.util.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * A base Activity class which replaces the actionbar to Toolbar and some general use case with Toolbar like setTitle()...
 *
 * @author MAO Hieng on 7/19/16.
 */
public abstract class AbstractToolbarActivity extends AppCompatActivity {

    private static final String KEY_TITLE = "KEY_STATE_TITLE";

    @Nullable
    protected Toolbar mToolbar;

    protected abstract Toolbar setContentViewAndBindToolbar();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar = setContentViewAndBindToolbar();
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayShowTitleEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        if (mToolbar != null) {
            mToolbar.setTitle(title);
        }
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        if (mToolbar != null) {
            mToolbar.setTitle(titleId);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            supportFinishAfterTransition();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(KEY_TITLE, getTitle());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        setTitle(savedInstanceState.getCharSequence(KEY_TITLE));
    }
}
