package com.idealcn.event.study.widget

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.view.ViewCompat
import android.support.v4.widget.ViewDragHelper
import android.support.v4.widget.ViewDragHelper.STATE_SETTLING
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import com.idealcn.event.study.R
import java.util.logging.Logger


/**
 * author: guoning
 * date: 2018/7/12 2:16 PM
 * description: 左侧滑动删除,仿qq侧滑删除功能
 */
class DragView : FrameLayout {

    val logger: Logger = Logger.getLogger(this.javaClass.simpleName)


    lateinit var dragHelper: ViewDragHelper

    lateinit var deleteLayout: ConstraintLayout
    lateinit var contentLayout: ConstraintLayout


    var deleteWidth = 0


    constructor(context: Context) : this(context, null!!)

    constructor(context: Context, attributeSet: AttributeSet) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context, attributeSet, defStyle) {



        dragHelper = ViewDragHelper.create(this, 1f, object : ViewDragHelper.Callback() {
            override fun tryCaptureView(child: View, pointerId: Int): Boolean {
                logger.info("tryCaptureView")
                return child == contentLayout || child == deleteLayout
            }


            override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
                logger.info("clampViewPositionHorizontal: left : $left; dx: $dx")

                //约束超过临界值的情况

                if (child == contentLayout) {
                    if (left > 0) {
                        return 0
                    } else if (left < 0 && left < -deleteWidth) {
                        return -deleteWidth
                    }
                }
                if (child == deleteLayout) {
                    if (left > contentLayout.width) {
                        return contentLayout.width
                    } else if (left < contentLayout.width - deleteWidth) {
                        return contentLayout.width - deleteWidth
                    }
                }
                return left
            }


            override fun onViewDragStateChanged(state: Int) {
                super.onViewDragStateChanged(state)
                logger.info("onViewDragStateChanged: state: $state")
                when (state) {
                    STATE_SETTLING -> requestDisallowInterceptTouchEvent(true)
                    else -> requestDisallowInterceptTouchEvent(false)
                }

            }

            override fun onViewPositionChanged(changedView: View, left: Int, top: Int, dx: Int, dy: Int) {
                super.onViewPositionChanged(changedView, left, top, dx, dy)
                logger.info("onViewPositionChanged")
                //left 是 changedView相对于父view左边界的距离
                //left随着changedview的改变而改变
                if (changedView == contentLayout) {
                    deleteLayout.layout(contentLayout.width + left,
                            0,
                            contentLayout.width + left + deleteWidth,
                            height)
                } else {
                    contentLayout.layout(left - contentLayout.width, 0,
                            left, height)
                }

            }


            override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
                super.onViewReleased(releasedChild, xvel, yvel)
                logger.info("onViewReleased")
                val left = contentLayout.left

                if (-left > deleteWidth / 2) {
                    dragHelper.smoothSlideViewTo(contentLayout, -deleteWidth, 0)
                    dragHelper.smoothSlideViewTo(deleteLayout,
                            contentLayout.width - deleteWidth,
                            0)

                    listener.open(true, this@DragView)

                } else {
                    dragHelper.smoothSlideViewTo(contentLayout, 0, 0)
                    dragHelper.smoothSlideViewTo(deleteLayout, contentLayout.width, 0)
                    listener.open(false, this@DragView)
                }

                ViewCompat.postInvalidateOnAnimation(this@DragView)
            }


            override fun onViewCaptured(capturedChild: View, activePointerId: Int) {
                super.onViewCaptured(capturedChild, activePointerId)

            }

        })


    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        contentLayout.layout(0, 0, contentLayout.width, height)
        deleteLayout.layout(contentLayout.width, 0, contentLayout.width + deleteLayout.width, height)
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {

        val action = event.action
        when (action){
            MotionEvent.ACTION_DOWN -> {
                lastDownX = event.rawX
                lastDownY = event.rawY
                //确保该View的点击事件可被调用
               // performClick()
            }
            MotionEvent.ACTION_MOVE -> {
                val rawX = event.rawX
                val rawY = event.rawY
                if (Math.abs(rawX - lastDownX) > Math.abs(rawY - lastDownY)){
                    parent.requestDisallowInterceptTouchEvent(true)
                }
            }
            else -> {
                lastDownX = 0f
                lastDownY = 0f
            }
        }

        dragHelper.processTouchEvent(event)
        return true
    }


    override fun computeScroll() {
        super.computeScroll()
        if (dragHelper.continueSettling(true))
            ViewCompat.postInvalidateOnAnimation(this)
    }
    private  var lastDownX : Float = 0f
    private  var lastDownY = 0f
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return dragHelper.shouldInterceptTouchEvent(ev)
    }


    override fun onFinishInflate() {
        super.onFinishInflate()
        deleteLayout = findViewById<ConstraintLayout>(R.id.layoutDelete)
        contentLayout = findViewById<ConstraintLayout>(R.id.layoutContent)
        deleteWidth = deleteLayout.layoutParams.width
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        logger.info("--------------------parent.parent.parent: ${parent.parent.parent.javaClass.simpleName}")
        val p = parent
        if(p is RecyclerView){
            p.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                }

                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    listener.open(false, this@DragView)
                }
            })
        }
    }


    lateinit var listener: OnHorizontalDragListener

    open interface OnHorizontalDragListener {
        fun open(open: Boolean, dragView: DragView)

    }

    fun setOnHorizontalDragListener(listener: OnHorizontalDragListener) {
        this.listener = listener
    }

    fun close() {
        logger.info("------------close------------")
        dragHelper.smoothSlideViewTo(contentLayout, 0, 0)
        dragHelper.smoothSlideViewTo(deleteLayout, contentLayout.width, 0)
        ViewCompat.postInvalidateOnAnimation(this@DragView)
    }

    fun open(position: Int) {
        logger.info("------------open------------$position")
    }


}