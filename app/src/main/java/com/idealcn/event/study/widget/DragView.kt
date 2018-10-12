package com.idealcn.event.study.widget

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewConfigurationCompat
import android.support.v4.widget.ViewDragHelper
import android.support.v4.widget.ViewDragHelper.STATE_SETTLING
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.*
import android.view.MotionEvent.*
import android.widget.FrameLayout
import android.widget.Scroller
import android.widget.TextView
import android.widget.Toast
import com.idealcn.event.study.R
import java.util.concurrent.TimeUnit
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

    var scrollWidth: Int = 0

    var deleteWidth = 0

    val scaledMaximumFlingVelocity: Int


//    val scroller: Scroller = Scroller(context)

    private lateinit var gestureDetector: GestureDetector

    constructor(context: Context) : this(context, null!!)

    constructor(context: Context, attributeSet: AttributeSet) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context, attributeSet, defStyle) {

        scaledMaximumFlingVelocity = ViewConfiguration.get(context).scaledMaximumFlingVelocity
        logger.info("scaledMaximumFlingVelocity: $scaledMaximumFlingVelocity")

        gestureDetector = GestureDetector(context,object : GestureDetector.SimpleOnGestureListener(){

        })

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


        })


    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //继承自FrameLayout,无需再去测量child

//        contentLayout.measure(widthMeasureSpec,heightMeasureSpec)

//        deleteLayout.measure(MeasureSpec.makeMeasureSpec(deleteWidth,MeasureSpec.EXACTLY),heightMeasureSpec)

//        val w = MeasureSpec.getSize(widthMeasureSpec)
//        val h = MeasureSpec.getSize(heightMeasureSpec)
//        setMeasuredDimension(w, h)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        contentLayout.layout(0, 0, contentLayout.width, height)
        deleteLayout.layout(contentLayout.width, 0, contentLayout.width + deleteLayout.width, height)
    }

    private var velocityTracker: VelocityTracker? = null
    private var lastX = 0
    private var lastY = 0
    private var pointerId = 0
    override fun onTouchEvent(event: MotionEvent): Boolean {



        if (null == velocityTracker)
            velocityTracker = VelocityTracker.obtain()
        velocityTracker!!.addMovement(event)
        if (event.action == MotionEvent.ACTION_DOWN) {
            lastX = event.x.toInt()
            lastY = event.y.toInt()
            pointerId = event.getPointerId(0)
        }
        if (event.action == ACTION_MOVE) {
            val slop = ViewConfiguration.get(context).scaledTouchSlop
            if (Math.abs(event.x - lastX) < Math.abs(event.y - lastY) && Math.abs(event.y - lastY) > slop) {
                parent.requestDisallowInterceptTouchEvent(false)
                dragHelper.processTouchEvent(event)
                return false
            }

            velocityTracker!!.computeCurrentVelocity(1000, scaledMaximumFlingVelocity.toFloat())

            logger.info("yVelocityTracker: ${velocityTracker!!.getYVelocity(pointerId)}")

            // TODO("待定")
            parent.parent.parent.requestDisallowInterceptTouchEvent(true)

        }

        if (event.action == ACTION_UP) {


            velocityTracker!!.clear()
            velocityTracker!!.recycle()
            velocityTracker = null

            lastX = 0
            lastY = 0
            parent.requestDisallowInterceptTouchEvent(false)
        }


        if (event.action == ACTION_CANCEL) {
            lastX = 0
            lastY = 0
            parent.requestDisallowInterceptTouchEvent(false)
        }

        dragHelper.processTouchEvent(event)
        return true
    }


    override fun computeScroll() {
        super.computeScroll()
        if (dragHelper.continueSettling(true))
            ViewCompat.postInvalidateOnAnimation(this)
    }

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