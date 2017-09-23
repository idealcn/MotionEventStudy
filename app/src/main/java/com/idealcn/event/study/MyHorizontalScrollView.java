package com.idealcn.event.study;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by ideal-gn on 2017/9/23.
 */

public class MyHorizontalScrollView extends HorizontalScrollView {

    private static final String TAG = "slide";
    private int mMenuRightPadding;
    private boolean once;
    private LinearLayout mLayout;
    private ViewGroup mMenuLayout;
    private ViewGroup mContentLayout;
    private int mMenuWidth;
    private DisplayMetrics displayMetrics;

    private boolean isOpen;

    public MyHorizontalScrollView(Context context) {
        this(context,null);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setHorizontalScrollBarEnabled(false);

        displayMetrics = getResources().getDisplayMetrics();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyHorizontalScrollView);
        mMenuRightPadding = typedArray.getDimensionPixelSize(R.styleable.MyHorizontalScrollView_menu_right_padding,(int) (displayMetrics.scaledDensity * 50 +0.5f));

        typedArray.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!once){
            mLayout = (LinearLayout) getChildAt(0);
            mMenuLayout = (ViewGroup) mLayout.getChildAt(0);
            mContentLayout = (ViewGroup) mLayout.getChildAt(1);
            mMenuWidth = mMenuLayout.getLayoutParams().width = displayMetrics.widthPixels - mMenuRightPadding;
            mContentLayout.getLayoutParams().width = displayMetrics.widthPixels;
            once = true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed){
            this.scrollTo(mMenuWidth,0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();

        switch (action){
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                if (scrollX>=mMenuWidth/2) {//隐藏
                    smoothScrollTo(mMenuWidth, 0);//沿x轴是负的
                }else {//显示左侧菜单
                    smoothScrollTo(0,0);
                }
                return true;
                default:break;
        }

        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        float scale = l * 1.0f / mMenuWidth;  //1~0
        float leftAlpha = 0.6f + 0.4f * (1.0f - scale);  //透明度
        float leftScale = 1.0f - 0.3f * scale;

//        //属性动画TranslationX，默认动画有时间限制，需要自己去设置时间
        float transX = mMenuWidth * scale * 0.7f;
        Log.d(TAG, " transX:  "+transX);
        mMenuLayout.animate().translationX(transX).alpha(leftAlpha).setDuration(0)
             .scaleX(leftScale).scaleY(leftScale)  .start();

        float rightScale = 0.9f + scale * 0.1f;
        mContentLayout.animate().scaleXBy(0).scaleY(mContentLayout.getHeight())
                .scaleX(rightScale).scaleY(rightScale).start();
    }


    /**
     * 打开菜单
     */
    public void openMenu() {
        if (isOpen) return;
        this.smoothScrollTo(0, 0);
        isOpen = true;
    }

    /**
     * 关闭菜单
     * */
    public void closeMenu() {
        if (!isOpen) return;
        this.smoothScrollTo(mMenuWidth, 0);
        isOpen = false;
    }

    /**
     * 切换菜单
     * */
    public void changeMenu() {
        if (isOpen) {
            closeMenu();
        } else {
            openMenu();
        }
    }


}
