package com.idealcn.event.study.widget

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.MotionEvent
import com.youth.banner.Banner

/**
 * 日期: 2018/9/26 19:52
 * author: guoning
 * description:
 */
class InterceptRecyclerView : RecyclerView {
    constructor(context: Context?) : this(context,null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle){

    }


    var lastX = 0
    var lastY = 0

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event!!.action){
            MotionEvent.ACTION_DOWN -> {
                lastX = event.x.toInt()
                lastY  = event.y.toInt()


               // parent.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                if (Math.abs(event.x - lastX)< Math.abs(event.y - lastY)){
                    super.onTouchEvent(event)
                    return true
                }
                parent.requestDisallowInterceptTouchEvent(true)

            }
            MotionEvent.ACTION_CANCEL , MotionEvent.ACTION_UP -> {
                lastX = 0
                lastY = 0
                parent.requestDisallowInterceptTouchEvent(false)
            }
        }
         super.onTouchEvent(event)
        return true
    }
}