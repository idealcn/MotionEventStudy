package com.idealcn.event.study.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 滚动到最后一项时,不会触发父ViewGroup的滚动
 */
public class MyViewPager extends ViewPager {
    public MyViewPager(@NonNull Context context) {
        super(context);
    }

    public MyViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return true;
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action ==MotionEvent.ACTION_DOWN || action ==MotionEvent.ACTION_MOVE) {
            PagerAdapter adapter = getAdapter();
            if (adapter != null) {
                int count = adapter.getCount();
                if (getCurrentItem() == count - 1) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
            }
        }
        if (action ==MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL){
            getParent().requestDisallowInterceptTouchEvent(false);
        }
        return super.onTouchEvent(ev);
    }
}
