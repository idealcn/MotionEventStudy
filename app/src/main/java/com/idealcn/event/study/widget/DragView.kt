package com.idealcn.event.study.widget

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.widget.ViewDragHelper
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Scroller
import android.widget.TextView
import android.widget.Toast
import com.idealcn.event.study.R


/**
 * author: guoning
 * date: 2018/7/12 2:16 PM
 * description: 可以左右拖拽,仿qq侧滑删除功能
 */
class DragView : ConstraintLayout {


    lateinit var dragHelper : ViewDragHelper

    lateinit var delete: TextView

    var scrollWidth: Int = 0

    var lastX = 0

    val scroller : Scroller = Scroller(context)

    constructor(context: Context) : this(context,null!!)

    constructor(context: Context, attributeSet: AttributeSet) : this(context, attributeSet,0)

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context, attributeSet, defStyle){
        dragHelper = ViewDragHelper.create(this,1f,object : ViewDragHelper.Callback(){
            override fun tryCaptureView(child: View, pointerId: Int): Boolean {
                return  child == delete
            }

            override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {

                return left
            }
        })
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {


        val action = event!!.action


        if (action == MotionEvent.ACTION_DOWN) {

            lastX = event.rawX.toInt()

            val x = event.x

            println("down : $x, rawX: $lastX")

        } else if (action == MotionEvent.ACTION_MOVE) {

           val layoutParams = layoutParams as MarginLayoutParams

            val marginEnd = layoutParams.rightMargin

            println("marginEnd: $marginEnd")

            val rawX = event.rawX

            println("move: rawX: $rawX")

            var diffX = rawX - lastX

            println("move: diffX : $diffX")

            if (diffX > scrollWidth) {
                diffX = scrollWidth.toFloat()
            }

            scrollBy(-diffX.toInt(), 0)

//            scroller.startScroll(width,0, (-diffX).toInt(),0)
//            scroller.computeScrollOffset()

//            scrollTo(-(width+diffX).toInt(),0)

        } else if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {

            lastX = event.rawX.toInt()

            println("cancel: rawX : $lastX")

        }




        return true
    }


    override fun computeScroll() {
        super.computeScroll()
        if(scroller.computeScrollOffset()){
            val currVelocity = scroller.currVelocity
            val currX = scroller.currX

            println("currx: $currX")


        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        delete = findViewById<TextView>(R.id.delete)
        scrollWidth = delete.width
        scrollWidth = (100*resources.displayMetrics.scaledDensity).toInt()

        delete.setOnClickListener {
            Toast.makeText(context, "delete", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        delete = getChildAt(1) as TextView
    }





}