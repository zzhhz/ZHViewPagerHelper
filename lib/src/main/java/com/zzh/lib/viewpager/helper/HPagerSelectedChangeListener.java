package com.zzh.lib.viewpager.helper;


import android.support.v4.view.ViewPager;

/**
 * 选中非选中监听
 */
public abstract class HPagerSelectedChangeListener extends HPagerDataSetObserver {
    private int mLastSelected;

    @Override
    protected void onViewPagerChanged(ViewPager oldPager, ViewPager newPager) {
        super.onViewPagerChanged(oldPager, newPager);
        if (oldPager != null)
            oldPager.removeOnPageChangeListener(mOnPageChangeListener);

        if (newPager != null)
            newPager.addOnPageChangeListener(mOnPageChangeListener);
    }

    private final ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            setSelected(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    @Override
    protected void onDataSetChanged() {
        final ViewPager viewPager = getViewPager();
        if (viewPager != null)
            setSelected(viewPager.getCurrentItem());
    }

    private void setSelected(int index) {
        if (isIndexLegal(index)) {
            notifySelectedChanged(mLastSelected, false);
            notifySelectedChanged(index, true);
            mLastSelected = index;
        }
    }

    private void notifySelectedChanged(int index, boolean selected) {
        if (isIndexLegal(index))
            onSelectedChanged(index, selected);
    }

    /**
     * 某一页选中或者非选中回调
     *
     * @param index    第几页
     * @param selected true-选中，false-未选中
     */
    protected abstract void onSelectedChanged(int index, boolean selected);
}
