package com.idealcn.event.study.widget.pager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewParent;

public class AutoPlayPager extends ViewPager {

    private VelocityTracker mVelocityTracker;

    private  int currentIndex = 1;
    private int pageCount = 0;
    private float lastDownX = 0;
    private long autoPlayInterval = 2000L;
    private boolean autoPlay = true;


    public AutoPlayPager(@NonNull Context context) {
        super(context);
    }

    public AutoPlayPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
       // setCurrentItem(currentIndex);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE){
            final ViewParent parent = getParent();
            if (null!=parent){
                parent.requestDisallowInterceptTouchEvent(true);
                return true;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    /* @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        else {
            mVelocityTracker.clear();
        }
        mVelocityTracker.addMovement(ev);

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                stopAutoPlay();
                lastDownX = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                mVelocityTracker.computeCurrentVelocity(1000);
                final PagerAdapter adapter = getAdapter();
                final float diffX = ev.getX() - lastDownX;
                if (getCurrentItem() == 0){
                    if (diffX > getWidth()/2&& null!=adapter){
                        postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setCurrentItem(adapter.getCount()-1,false);
                            }
                        },50);
                    }
                   *//* if (mVelocityTracker.getXVelocity()>0 && null!=adapter){
                               postDelayed(new Runnable() {
                                   @Override
                                   public void run() {
                                       setCurrentItem(adapter.getCount()-1,false);
                                   }
                               },50);
                    }*//*
                }

                if (null!=adapter && getCurrentItem() == adapter.getCount()-1 && -diffX > getWidth()/2){
                    *//*if (mVelocityTracker.getXVelocity()<0){
                               postDelayed(new Runnable() {
                                   @Override
                                   public void run() {
                                       setCurrentItem(0,false);
                                   }
                               },50);
                    }*//*
                    postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setCurrentItem(0,false);
                        }
                    },50);
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (null!=mVelocityTracker){
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
                autoPlay();
                break;


                default:break;
        }
        return super.onTouchEvent(ev);
    }*/


}
