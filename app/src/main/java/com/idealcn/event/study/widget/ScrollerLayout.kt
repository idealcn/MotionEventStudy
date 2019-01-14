package com.idealcn.event.study.widget

import android.content.Context
import android.support.v4.widget.ViewDragHelper
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.OverScroller
import java.util.logging.Logger

/**
 * 日期: 2018/9/26 10:12
 * author: guoning
 * description:
 */
class ScrollerLayout : FrameLayout {



    val logger :Logger = Logger.getLogger(this.javaClass.simpleName)

    lateinit var dragHelper: ViewDragHelper


    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        dragHelper = ViewDragHelper.create(this,object : ViewDragHelper.Callback() {
            override fun tryCaptureView(capturedView: View, pointerId: Int): Boolean {
                for (x in 0 until childCount){
                    val child = getChildAt(x)
                    if (child.visibility == View.GONE)continue
                    if (child == capturedView)return true;
                }
                return false
            }

            override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {


                if (getChildAt(0) == child){
                    if (left>0)return 0
                    if (left<-child.width * 2)return - child.width * 2
                }
                if (getChildAt(1) == child){
                    if (left>child.width)return child.width
                    if (left<-child.width) return -child.width
                }
                if (getChildAt(2) == child){
                    if (left>child.width * 2)return child.width * 2
                    if (left<0)return 0
                }
                return left


            }

            override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
                return 0
            }

            override fun onViewCaptured(capturedChild: View, activePointerId: Int) {
                super.onViewCaptured(capturedChild, activePointerId)
            }

            override fun onViewPositionChanged(changedView: View, left: Int, top: Int, dx: Int, dy: Int) {
                super.onViewPositionChanged(changedView, left, top, dx, dy)
                if (changedView == getChildAt(0)){
                    changedView.layout(left,0,left+changedView.width,height)
                    getChildAt(1).layout(getChildAt(0).width+left,0,getChildAt(0).width+left+getChildAt(1).width,height)
                    getChildAt(2).layout(
                            width * 2  + left,0, width * 3 + left,height
                    )
                } else if (changedView == getChildAt(1)){
                    changedView.layout(left,0,left+changedView.width,height)
                    getChildAt(0).layout(-changedView.width + left,0,left,height)
                    getChildAt(2).layout(changedView.width +left,0,changedView.width * 2 + left,height)
                }else if (changedView == getChildAt(2)){
                    changedView.layout(left,0,left+changedView.width,height)
                    getChildAt(0).layout(-2 * changedView.width + left,0,-changedView.width +left,height)
                    getChildAt(1).layout(-changedView.width+left,0,left,height)
                }
            }

            override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
                super.onViewReleased(releasedChild, xvel, yvel)
                val left = releasedChild.left
                if (releasedChild == getChildAt(0)){
                    if (left<0){
                        if (left+releasedChild.width/2<0){
                            dragHelper.smoothSlideViewTo(releasedChild,-releasedChild.width,0)
                            dragHelper.smoothSlideViewTo(getChildAt(1),0,0)
                            dragHelper.smoothSlideViewTo(getChildAt(2),releasedChild.width,0)
                        }
                        else {
                            dragHelper.smoothSlideViewTo(releasedChild,0,0)
                            dragHelper.smoothSlideViewTo(getChildAt(1),releasedChild.width,0)
                            dragHelper.smoothSlideViewTo(getChildAt(2),releasedChild.width*2,0)
                        }
                    }else {
                        dragHelper.smoothSlideViewTo(releasedChild,0,0)
                    }
                }
                if (releasedChild == getChildAt(1)){
                    if (left  > width/2 && left < width ){
                        dragHelper.smoothSlideViewTo(releasedChild,width,0)
                    }
                    if (left < width/2 && left > -width/2){
                        dragHelper.smoothSlideViewTo(releasedChild,0,0)
                    }
                    if (left < - width/2){
                        dragHelper.smoothSlideViewTo(releasedChild, -width, 0)
                    }
                }

                if (releasedChild == getChildAt(2)){
                    if (left> 3 *width/2){
                        dragHelper.smoothSlideViewTo(releasedChild,2*width,0)
                    }
                    if (left<= 3 *width/2 && left>=width/2){
                        dragHelper.smoothSlideViewTo(releasedChild,width,0)
                    }
                    if (left<width/2){
                        dragHelper.smoothSlideViewTo(releasedChild,0,0)
                    }
                }
                invalidate()
            }

            override fun onViewDragStateChanged(state: Int) {
                super.onViewDragStateChanged(state)
            }

            override fun getViewHorizontalDragRange(child: View): Int {
                return width * childCount
            }

            override fun getViewVerticalDragRange(child: View): Int {
                return 0
            }

        })
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        dragHelper.processTouchEvent(event)
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




}