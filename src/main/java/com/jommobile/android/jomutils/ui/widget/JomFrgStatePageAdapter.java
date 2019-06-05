package com.jommobile.android.jomutils.ui.widget;

import android.view.ViewGroup;

import java.util.List;

import androidx.collection.SparseArrayCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public abstract class JomFrgStatePageAdapter<E> extends FragmentStatePagerAdapter {

    private SparseArrayCompat<Fragment> registeredFragments = new SparseArrayCompat<Fragment>();

    protected List<E> mData;

    public JomFrgStatePageAdapter(FragmentManager fm, List<E> data) {
        super(fm);
        mData = data;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment frg = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, frg);
        return frg;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public void setData(List<E> data) {
        mData = data;
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
