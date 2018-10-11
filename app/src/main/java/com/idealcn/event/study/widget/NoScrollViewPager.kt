package com.idealcn.event.study.widget

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import java.util.logging.Logger


/**
 * author: guoning
 * date: 2018/7/11 5:03 PM
 * description: 不允许viewpager左右滑动
 */
class NoScrollViewPager : ViewPager{

    val  logger = Logger.getLogger(this.javaClass.simpleName)


    constructor(context: Context) : super(context)

    constructor(context: Context,attributeSet: AttributeSet) : super(context,attributeSet)


    /**
     * 返回true自己消费
     */
//    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
//        logger.info("dispatchTouchEvent")
//
//        return super.dispatchTouchEvent(ev)
//    }



//    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
//        logger.info("onInterceptTouchEvent")
//        return false
////        return super.onInterceptTouchEvent(ev)
//    }

//    override fun onTouchEvent(ev: MotionEvent?): Boolean {
//        logger.info("onTouchEvent")
//        return false
////        return super.onTouchEvent(ev)
//    }







}