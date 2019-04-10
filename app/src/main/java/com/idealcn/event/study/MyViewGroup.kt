package com.idealcn.event.study

import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import java.util.logging.Level
import java.util.logging.Logger


/**
 * author: guoning
 * date: 2018/7/10 3:02 PM
 * description:
 */
class MyViewGroup : ViewGroup {

    val logger = Logger.getLogger("motion")

    val displayMetrics: DisplayMetrics = resources.displayMetrics

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {


        val childCount = childCount

        if (childCount == 0) return

        val child = getChildAt(0)
        if (child.visibility == View.GONE) return
        val params = child.layoutParams as MyParams

        child.layout(params.leftMargin, params.topMargin, child.measuredWidth + params.leftMargin,
                child.measuredHeight + params.topMargin)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        /*
        继承的是ViewGroup,ViewGroup的onMeasure方法中没有对子view做测量处理.需要自己在这里做处理measureChildWithMargins()
        测量View要考虑margin,测量view内容要考虑padding
        与measureChild()作比较
         */

        for (x in 0 until childCount) {
            val child = getChildAt(x)
            //测量子view
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0)
            logger.info("child.measuredWidth : ${child.measuredWidth},child.measureHeight: ${child.measuredHeight}")
        }
        setMeasuredDimension(displayMetrics.widthPixels, displayMetrics.heightPixels)


    }

    override fun generateDefaultLayoutParams(): MyParams {

        return MyParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): MyParams {
        return MyParams(context, attrs)
    }

    override fun generateLayoutParams(p: LayoutParams?): MyParams {
        return MyParams(p)
    }


    class MyParams : MarginLayoutParams {
        constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
        constructor(width: Int, height: Int) : super(width, height)
        constructor(source: LayoutParams?) : super(source)
    }


    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        logger.log(Level.INFO,"ViewGroup:             dispatchTouchEvent-------start")

        when(ev.action){
            MotionEvent.ACTION_DOWN -> {
                logger.log(Level.INFO,"ViewGroup:             ACTION_DOWN")
            }
            MotionEvent.ACTION_MOVE -> {
                logger.log(Level.INFO,"ViewGroup:             ACTION_MOVE")
            }
            MotionEvent.ACTION_UP -> {
                logger.log(Level.INFO,"ViewGroup:             ACTION_UP")
            }
            MotionEvent.ACTION_CANCEL -> {
                logger.log(Level.INFO,"ViewGroup:             ACTION_CANCEL")
            }
            else
            -> {

            }
        }
        logger.log(Level.INFO,"ViewGroup:             dispatchTouchEvent-------end")
        return super.dispatchTouchEvent(ev)
    }

    //只处理ACTION_DOWN事件
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        logger.log(Level.INFO,"ViewGroup:             onInterceptTouchEvent-------start")

        when(ev.action){
            MotionEvent.ACTION_DOWN -> {
                logger.log(Level.INFO,"ViewGroup:             ACTION_DOWN")
            }
            MotionEvent.ACTION_MOVE -> {
                logger.log(Level.INFO,"ViewGroup:             ACTION_MOVE")
            }
            MotionEvent.ACTION_UP -> {
                logger.log(Level.INFO,"ViewGroup:             ACTION_UP")
            }
            MotionEvent.ACTION_CANCEL -> {
                logger.log(Level.INFO,"ViewGroup:             ACTION_CANCEL")
            }
            else
                 -> {

            }
        }
        logger.log(Level.INFO,"ViewGroup:             onInterceptTouchEvent-------end")

      //  return true
                //false和super是一样的
        //return false
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        logger.log(Level.INFO,"ViewGroup:             onTouchEvent-------start")

        when(event!!.action){
            MotionEvent.ACTION_DOWN -> {
                logger.log(Level.INFO,"ViewGroup:             ACTION_DOWN")
            }
            MotionEvent.ACTION_MOVE -> {
                logger.log(Level.INFO,"ViewGroup:             ACTION_MOVE")
            }
            MotionEvent.ACTION_UP -> {
                logger.log(Level.INFO,"ViewGroup:             ACTION_UP")
            }
            MotionEvent.ACTION_CANCEL -> {
                logger.log(Level.INFO,"ViewGroup:             ACTION_CANCEL")
            }
            else
            -> {

            }
        }
        logger.log(Level.INFO,"ViewGroup:             onTouchEvent-------end")
        return super.onTouchEvent(event)
       // return true
    }
}
