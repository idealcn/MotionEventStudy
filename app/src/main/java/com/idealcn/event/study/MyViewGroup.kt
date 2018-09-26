package com.idealcn.event.study

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import java.util.logging.Logger


/**
 * author: guoning
 * date: 2018/7/10 3:02 PM
 * description:
 */
class MyViewGroup : ViewGroup {

    val logger = Logger.getLogger(this.javaClass.simpleName)

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {


        val childCount = childCount

        if (childCount==0)return

        val child = getChildAt(0)

        val displayMetrics = resources.displayMetrics
        val scaledDensity = displayMetrics.scaledDensity


        val measuredWidth = measuredWidth
        val measuredHeight = measuredHeight

        if (changed) {
            layout((scaledDensity * 10).toInt(), (scaledDensity * 10).toInt(),
                    (measuredWidth-scaledDensity*10).toInt(), (measuredWidth-scaledDensity*10).toInt())
//            invalidate()
//            child.layout(30,30,30+child.measuredWidth,30+child.measuredHeight)

            println("child------${child.measuredWidth}")
            child.layout((scaledDensity * 20).toInt(),
                    (scaledDensity * 20).toInt(),
                    (scaledDensity * 20).toInt()+child.measuredWidth,
                    (scaledDensity * 20).toInt()+child.measuredHeight)

            requestLayout()
        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = width
        val height = height

        val childCount = childCount
        for (x in 0..childCount){
            val child = getChildAt(x)
            if (null==child)continue
            val visibility = child.visibility
            if (visibility== View.GONE)continue
            measureChildWithMargins(child,widthMeasureSpec,0,heightMeasureSpec,0)
            val myParams :MyParams = child.layoutParams as MyParams
            myParams.width = child.measuredWidth
            myParams.height = child.measuredHeight

        }



        setMeasuredDimension(measuredWidth,measuredHeight)
    }

    override fun generateDefaultLayoutParams(): MyParams {

        return MyParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): MyParams {
        return MyParams(context,attrs)
    }

    override fun generateLayoutParams(p: LayoutParams?): MyParams {
        return MyParams(p)
    }


    class MyParams : MarginLayoutParams{
        constructor(context: Context,attrs: AttributeSet?) : super(context,attrs)
        constructor(width: Int,height: Int) : super(width,height)
        constructor(source : LayoutParams?) : super(source)
    }


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        logger.info("dispatchTouchEvent")
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        logger.info("onInterceptTouchEvent")

        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        logger.info("onTouchEvent")
        return super.onTouchEvent(event)
    }
}
