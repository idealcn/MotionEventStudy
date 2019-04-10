package com.idealcn.event.study.widget

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import java.util.logging.Logger


/**
 * author: guoning
 * date: 2018/7/11 5:03 PM
 * description: 不允许viewpager左右滑动
 */
public class NoScrollViewPager : ViewPager{

    val  logger = Logger.getLogger(this.javaClass.simpleName)

    private var canScroll : Boolean = true

    constructor(context: Context) : super(context)

    constructor(context: Context,attributeSet: AttributeSet) : super(context,attributeSet)

    public fun setScroll(canScroll : Boolean){
        this.canScroll = canScroll
    }





    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        logger.info("onInterceptTouchEvent")
        //return false
        return canScroll && super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        logger.info("onTouchEvent")

        return canScroll && super.onTouchEvent(ev)
//        return super.onTouchEvent(ev)
    }


    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item,false)
    }


}