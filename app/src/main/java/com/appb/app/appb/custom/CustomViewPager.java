package com.appb.app.appb.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by 1 on 05.04.2017.
 */

public class CustomViewPager extends ViewPager {

    OnTouchListener onTouchListener;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        onTouchListener.onTouch(this, ev);
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        onTouchListener = l;
    }
}
