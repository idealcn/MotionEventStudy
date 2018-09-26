package com.idealcn.event.study.widget

import android.content.Context
import android.util.AttributeSet
import android.util.TimeUtils
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.widget.FrameLayout
import android.widget.Scroller
import java.util.concurrent.TimeUnit
import java.util.logging.Logger

/**
 * 日期: 2018/9/26 10:12
 * author: guoning
 * description:
 */
class ScrollerLayout : FrameLayout {


    private lateinit var scroller: Scroller

    val logger :Logger = Logger.getLogger(this.javaClass.simpleName)

    lateinit var tracker: VelocityTracker

     var canHorizontalScroll = true

    var currentIndex = 0

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        scroller = Scroller(context)
        tracker = VelocityTracker.obtain()
    }

    var lastDownX = 0


    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action = event.action
        when(action){

            MotionEvent.ACTION_DOWN -> {
                parent.requestDisallowInterceptTouchEvent(true)
                logger.info("left: $left,right:$right")
//                tracker.addMovement(event)
                lastDownX = event.x.toInt()
            }

            MotionEvent.ACTION_MOVE -> {
                parent.requestDisallowInterceptTouchEvent(true)

                scroller.startScroll(lastDownX,0,lastDownX - scrollX,0)
                //invalidate()

//                tracker.computeCurrentVelocity(100)
//                logger.info("xVelocity: ${tracker.xVelocity}" +
//                        ",yVelocity : ${tracker.yVelocity}")
//                val deltX = (event.x - lastDownX).toInt()
//                if(currentIndex==0&&deltX>0&&getChildAt(0).x>=0){
//                    return super.onTouchEvent(event)
//                }
//                if (canHorizontalScroll) {
//                    scrollBy( - deltX,0)
//                }
                lastDownX = event.x.toInt()
//
//                logger.info("scrollX: $scrollX")

//                val child = getChildAt(0)
//                logger.info("this: left: $left, x: $x,translationX: $translationX")
//                logger.info("paddingLeft: ${child.paddingLeft}, x: ${child.x},translateX: ${child.translationX}")
            }

            MotionEvent.ACTION_UP,MotionEvent.ACTION_CANCEL -> {
                parent.requestDisallowInterceptTouchEvent(false)

                lastDownX = 0
            }

            else -> {

            }
        }
        return true

    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var pWidth = 0
        var pHeight = 0
        for (x in 0 until childCount){
            val child = getChildAt(x)
            if (child.visibility==View.GONE)continue
            logger.info("child.measuredHeight: ${child.measuredHeight}," +
                    " child.measuredWidth : ${child.measuredWidth}")
            //child.measure(widthMeasureSpec,heightMeasureSpec) 这样不正确
//         下面是正确的,但是多此一举,child.measureWidth和child.measureHeight本身已经测量好了,可以直接用
// child.measure(MeasureSpec.makeMeasureSpec(child.measuredWidth, MeasureSpec.EXACTLY)
//                    ,MeasureSpec.makeMeasureSpec(child.measuredHeight, MeasureSpec.EXACTLY))
            val params = child.layoutParams as FrameLayout.LayoutParams
            logger.info("params: width: ${params.width},height: ${params.height}")
            pWidth += child.measuredWidth
            pHeight = Math.max(child.measuredHeight,pHeight)
        }
        if (pWidth <= resources.displayMetrics.widthPixels){
            canHorizontalScroll = false
        }
        setMeasuredDimension(pWidth,pHeight)
    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        var lastWidth = 0
        for (x in 0 until childCount){
            val child = getChildAt(x)
            if (child.visibility==View.GONE)continue
            child.layout(lastWidth,0,lastWidth+child.width,height)
            lastWidth += child.width
        }

    }





    override fun computeScroll() {
       if (scroller.computeScrollOffset()){

//           logger.info("scroller.currX==left?: ${scroller.currX},$left,$right")

//           scrollTo(scroller.currX,0)

           scrollBy(scrollX,0)
          postInvalidate()
       }
    }



}