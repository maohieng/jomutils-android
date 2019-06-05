
package com.jommobile.android.jomutils.ui.widget;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * General implementation from FragmentPagerAdapter to create fragments and titles (optional).
 *
 * @author maohieng
 */
public class JomFrgPageAdapter extends FragmentPagerAdapter {

    protected Fragment[] mFragments = null;
    private CharSequence[] mTitles = null;

    /**
     * Constructor creates an adapter with given fragments. if your adapter also displays the titles as in tab etc., provide titles parameters.
     *
     * @param fm - Current Activity's or Fragment's FragmentManager.
     * @param fragments - the fragments to display
     * @param titles - optional titles. provide null if it is not need.
     */
    public JomFrgPageAdapter(FragmentManager fm, Fragment[] fragments, CharSequence[] titles) {
        super(fm);
        this.mFragments = fragments;
        this.mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments != null ? mFragments[position] : null;
    }

    @Override
    public int getCount() {
        return mFragments != null ? mFragments.length : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (mTitles == null) ? super.getPageTitle(position) : mTitles[position % mTitles.length];
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
