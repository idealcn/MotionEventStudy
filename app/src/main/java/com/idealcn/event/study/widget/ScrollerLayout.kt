package com.idealcn.event.study.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.Scroller
import java.util.logging.Logger

/**
 * 日期: 2018/9/26 10:12
 * author: guoning
 * description:
 */
class ScrollerLayout : FrameLayout {


    private var scroller: Scroller

    val logger :Logger = Logger.getLogger(this.javaClass.simpleName)

//    lateinit var tracker: VelocityTracker

     var canHorizontalScroll = true


    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        scroller = Scroller(context)
//        tracker = VelocityTracker.obtain()
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
                lastDownX = event.x.toInt()
            }

            MotionEvent.ACTION_MOVE -> {
                parent.requestDisallowInterceptTouchEvent(true)
                val x = event.x
                scroller.startScroll(lastDownX,0, (lastDownX - x).toInt(),0)
                lastDownX = x.toInt()
                invalidate()
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
/*        下面是正确的,
            child.measure(MeasureSpec.makeMeasureSpec(child.measuredWidth, MeasureSpec.EXACTLY)
                  ,MeasureSpec.makeMeasureSpec(child.measuredHeight, MeasureSpec.EXACTLY))
              考虑到继承自FrameLayout,child.measureWidth和child.measureHeight本身已经测量好了,可以直接用
                ,无需再次测量child
                如果继承ViewGroup,需要自己去测量child
                  */
            val params = child.layoutParams as FrameLayout.LayoutParams
            logger.info("params: width: ${params.width},height: ${params.height}")
            pWidth += child.measuredWidth
            pHeight = Math.max(child.measuredHeight,pHeight)
        }
        if (pWidth <= resources.displayMetrics.widthPixels){
            canHorizontalScroll = false
        }
        setMeasuredDimension(pWidth/childCount,pHeight)
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


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }




    override fun computeScroll() {
       if (scroller.computeScrollOffset()){

//           logger.info("scroller.currX==left?: ${scroller.currX},$left,$right")

           logger.info("currX: ${scroller.currX} ,startX: ${scroller.startX},finalX: ${scroller.finalX}")
//           scrollTo(scroller.currX,0)


           scrollBy(-scroller.currX + scroller.finalX,0)
          postInvalidate()
       }
    }



}