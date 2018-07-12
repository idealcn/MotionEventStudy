package com.idealcn.event.study

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup


/**
 * author: guoning
 * date: 2018/7/10 3:02 PM
 * description:
 */
class MyViewGroup : ViewGroup {

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



        setMeasuredDimension(this.measuredWidth,measuredHeight)
    }
}
