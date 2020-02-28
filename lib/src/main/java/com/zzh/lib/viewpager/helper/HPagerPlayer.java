package com.zzh.lib.viewpager.helper;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;


/**
 * ViewPager轮播类
 */
public class HPagerPlayer extends HViewPagerHolder {
    /**
     * 默认轮播间隔
     */
    private static final long DEFAULT_PLAY_DURATION = 5 * 1000;

    private long mPlayDuration = DEFAULT_PLAY_DURATION;
    private boolean mIsNeedPlay = false;
    private boolean mIsPlaying = false;
    private CountDownTimer mTimer;

    private final Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onViewPagerChanged(ViewPager oldPager, ViewPager newPager) {
        if (newPager != null)
            newPager.setOnTouchListener(mInternalOnTouchListener);
    }

    private final View.OnTouchListener mInternalOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (v == getViewPager())
                processTouchEvent(event);

            return false;
        }
    };

    /**
     * 是否正在轮播中
     *
     * @return
     */
    public boolean isPlaying() {
        return mIsPlaying;
    }

    /**
     * 是否可以轮播
     *
     * @return
     */
    protected boolean canPlay() {
        if (getAdapterCount() <= 1) {
            stopPlay();
            return false;
        }
        return true;
    }

    /**
     * 开始轮播
     */
    public void startPlay() {
        startPlay(DEFAULT_PLAY_DURATION);
    }

    /**
     * 开始轮播
     *
     * @param duration 轮播间隔(毫秒)
     */
    public void startPlay(long duration) {
        if (!canPlay())
            return;

        if (duration <= 0)
            duration = DEFAULT_PLAY_DURATION;

        mPlayDuration = duration;
        mIsNeedPlay = true;
        startPlayInternal();
    }

    private void startPlayInternal() {
        if (mIsPlaying)
            return;

        if (!mIsNeedPlay)
            return;

        if (!canPlay())
            return;

        if (mTimer == null) {
            mTimer = new CountDownTimer(Long.MAX_VALUE, mPlayDuration) {
                @Override
                public void onTick(long millisUntilFinished) {
                    onChangePage();
                }

                @Override
                public void onFinish() {
                }
            };

            mHandler.removeCallbacks(mStartTimerRunnable);
            mHandler.postDelayed(mStartTimerRunnable, mPlayDuration);
            mIsPlaying = true;
        }
    }

    protected void onChangePage() {
        if (canPlay()) {
            int current = getViewPager().getCurrentItem();
            current++;
            if (current >= getAdapterCount())
                current = 0;

            getViewPager().setCurrentItem(current, true);
        }
    }

    private final Runnable mStartTimerRunnable = new Runnable() {
        @Override
        public void run() {
            if (mTimer != null)
                mTimer.start();
        }
    };

    /**
     * 停止轮播
     */
    public void stopPlay() {
        stopPlayInternal();
        mIsNeedPlay = false;
    }

    private void stopPlayInternal() {
        mHandler.removeCallbacks(mStartTimerRunnable);

        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
            mIsPlaying = false;
        }
    }

    /**
     * 调用此方法处理触摸事件，会根据事件暂停和恢复播放
     *
     * @param event
     */
    public void processTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                stopPlayInternal();
                break;
            case MotionEvent.ACTION_UP:
                startPlayInternal();
                break;
        }
    }
}
