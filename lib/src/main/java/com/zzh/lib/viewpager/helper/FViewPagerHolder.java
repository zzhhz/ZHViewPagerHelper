package com.zzh.lib.viewpager.helper;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import java.lang.ref.WeakReference;


/**
 * ViewPager持有类
 */
public abstract class FViewPagerHolder {
    private WeakReference<ViewPager> mViewPager;

    public final ViewPager getViewPager() {
        return mViewPager == null ? null : mViewPager.get();
    }

    public final void setViewPager(ViewPager viewPager) {
        final ViewPager old = getViewPager();
        if (old != viewPager) {
            mViewPager = viewPager == null ? null : new WeakReference<>(viewPager);
            onViewPagerChanged(old, viewPager);
        }
    }

    /**
     * 返回Adapter
     *
     * @return
     */
    public final PagerAdapter getAdapter() {
        final ViewPager viewPager = getViewPager();
        return viewPager == null ? null : viewPager.getAdapter();
    }

    /**
     * 返回Adapter的数据量
     *
     * @return
     */
    public final int getAdapterCount() {
        final PagerAdapter adapter = getAdapter();
        return adapter == null ? 0 : adapter.getCount();
    }

    /**
     * 位置是否合法
     *
     * @param index
     * @return
     */
    public final boolean isIndexLegal(int index) {
        if (index < 0 || index >= getAdapterCount()) {
            return false;
        } else {
            return true;
        }
    }

    protected abstract void onViewPagerChanged(ViewPager oldPager, ViewPager newPager);
}
