package com.idealcn.event.study.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.MotionEvent.*
import android.view.View
import android.view.ViewGroup
import android.widget.Scroller
import com.idealcn.event.study.R
import java.util.logging.Logger

/**
 * 日期: 2018/9/21 15:02
 * author: guoning
 * description:
 */
class ScrollerView : View {

    val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    val scroller = Scroller(context)


    val logger = Logger.getLogger(this.javaClass.simpleName)



    constructor(context: Context) : this(context,null)
    constructor(context: Context,attributeSet: AttributeSet?): this(context,attributeSet,0)

    constructor(context: Context,attributeSet: AttributeSet?,defStyle : Int) : super(context,attributeSet,defStyle)
    {
        paint.color = ContextCompat.getColor(context, R.color.colorAccent)
        paint.style = Paint.Style.FILL


    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRect(0f,0f, width.toFloat(), height.toFloat(),paint)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //除非你想给view限制大小,一般情况下,都是直接获取MeasureSpec.getSize的值即可

        var w =0//宽
        var h = 0//高

        val metrics = resources.displayMetrics

        val measureWidth = MeasureSpec.getSize(widthMeasureSpec)
        val measureHeight = MeasureSpec.getSize(heightMeasureSpec)

        val wMode = MeasureSpec.getMode(widthMeasureSpec)
        val hMode = MeasureSpec.getMode(heightMeasureSpec)

        if (wMode == MeasureSpec.EXACTLY){
            w = measureWidth
        }else{
            w = Math.min(measureWidth,metrics.widthPixels)
        }

        if (hMode == MeasureSpec.EXACTLY){
            h = measureHeight
        }else{
            h = Math.min(measureHeight,metrics.heightPixels)
        }

        setMeasuredDimension(w,h)
    }

    var downX :Float = 0f
    var downY = 0f
    override fun onTouchEvent(event: MotionEvent): Boolean {

        when(event.action){
            ACTION_DOWN -> {
                downX = event.x
                downY = event.y

            }
            ACTION_MOVE -> {




                logger.info("scrollerX : ${(parent as ViewGroup).scrollX}, scrollerY : ${(parent as ViewGroup).scrollY}")

                //logger.info("event: rawX: ${event.rawX} , rawY: ${event.rawY} , x : ${event.x} ,y: ${event.y}")

                //logger.info("x : $x, y : $y, left :$left, top: $top")
                if ((parent as ViewGroup).scrollX+left<=0 || (parent as ViewGroup).scrollY+top<=0){
                    return false
                }


                (parent as ViewGroup).scrollBy((-(event.x - downX)).toInt(), (-(event.y - downY)).toInt())


            }
            ACTION_UP , ACTION_CANCEL -> {
//                downX = event.rawX
//                downY = event.rawY
            }
        }
        return true
    }

    override fun computeScroll() {
        super.computeScroll()
        if (scroller.computeScrollOffset()){
            val currX = scroller.currX
            val currY = scroller.currY
            (parent as ViewGroup).scrollTo(currX,currY)
            postInvalidate()
        }
    }

}