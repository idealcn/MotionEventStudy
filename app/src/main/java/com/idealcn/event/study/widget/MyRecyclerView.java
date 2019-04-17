package com.idealcn.event.study.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

public class MyRecyclerView extends RecyclerView {
    public MyRecyclerView(Context context) {
        super(context);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    private   int scaledTouchSlop;

    private float lastDownX,lastDownY;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:{
                lastDownX = e.getRawX();
                lastDownY = e.getRawY();

                if (callback.hasOpenItems()){
                    callback.closeAllOpenAdapterItems();
                    return true;
                }

                break;
            }
            case MotionEvent.ACTION_MOVE:{
                float moveX = e.getRawX();
                float moveY = e.getRawY();
                if (Math.abs(moveY - lastDownY)>scaledTouchSlop && Math.abs(moveY - lastDownY)>Math.abs(moveX
                        - lastDownX)){
                    return true;
                }
                break;
            }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP: {
                lastDownX = lastDownY = 0;
                break;
            }
        }
        return super.onInterceptTouchEvent(e);
    }

    public void setCallback(OnAdapterOpenItemCallback callback) {
        this.callback = callback;
    }

    private OnAdapterOpenItemCallback callback;
    public interface OnAdapterOpenItemCallback{
        boolean hasOpenItems();
        void closeAllOpenAdapterItems();
    }
}
