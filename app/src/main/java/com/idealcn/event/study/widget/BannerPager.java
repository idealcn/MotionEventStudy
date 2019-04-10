package com.idealcn.event.study.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;

public class BannerPager extends ViewPager {

    private VelocityTracker mVelocityTracker;

    private static int currentIndex;

    private static Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            handler.post(loopTask);
        }
    };

    private static Runnable loopTask = new Runnable() {
        @Override
        public void run() {

        }
    };


    public BannerPager(@NonNull Context context) {
        super(context);
    }

    public BannerPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    float lastDownX = 0;

    @Override
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
                   /* if (mVelocityTracker.getXVelocity()>0 && null!=adapter){
                               postDelayed(new Runnable() {
                                   @Override
                                   public void run() {
                                       setCurrentItem(adapter.getCount()-1,false);
                                   }
                               },50);
                    }*/
                }

                if (null!=adapter && getCurrentItem() == adapter.getCount()-1 && -diffX > getWidth()/2){
                    /*if (mVelocityTracker.getXVelocity()<0){
                               postDelayed(new Runnable() {
                                   @Override
                                   public void run() {
                                       setCurrentItem(0,false);
                                   }
                               },50);
                    }*/
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
                break;


                default:break;
        }
        return super.onTouchEvent(ev);
    }


}
