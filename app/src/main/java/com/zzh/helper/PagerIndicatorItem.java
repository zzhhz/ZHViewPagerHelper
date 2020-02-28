package com.zzh.helper;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;


public class PagerIndicatorItem extends AppCompatTextView {
    public PagerIndicatorItem(Context context) {
        super(context);
        init();
    }

    public PagerIndicatorItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PagerIndicatorItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setGravity(Gravity.CENTER);
        onSelectedChanged(false);
    }

    public void onSelectedChanged(boolean selected) {
        if (selected) {
            setTextColor(Color.RED);
        } else {
            setTextColor(Color.BLACK);
        }
    }
}
