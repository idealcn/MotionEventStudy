package com.idealcn.event.study.widget

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewPropertyAnimator
import android.widget.Button
import java.util.jar.Attributes
import java.util.logging.Level
import java.util.logging.Logger


/**
 * author: guoning
 * date: 2018/7/10 2:41 PM
 * description:
 */
class MyButton : View {
    val logger = Logger.getLogger("motion")


    private  var lastDownX : Float = 0F
    private var lastDownY:Float = 0f
    private val scaledTouchSlop: Int = 0

    constructor( context: Context) : super(context)

    constructor( context: Context,attributes: AttributeSet) : super(context,attributes)

    constructor(context: Context,attributes: AttributeSet,defStyleAttr : Int) : super(context,attributes,defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(450,450)


    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        logger.log(Level.INFO,"view:                        onTouchEvent----------start")
        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                logger.log(Level.INFO,"view:                        ACTION_DOWN")
                //return true
                lastDownX = event.rawX
                lastDownY = event.rawY
                println("actionIndex: ${event.actionIndex},pointerCount: ${event.pointerCount}")
            }
            MotionEvent.ACTION_MOVE -> {
                logger.log(Level.INFO,"view:                        ACTION_MOVE")
                //return true

                println("lastDownX:$lastDownX,  lastDownY: $lastDownY")



//                ViewCompat.postInvalidateOnAnimation(this)
                //requestLayout()


            }
            MotionEvent.ACTION_UP -> {
                val moveX = event.rawX
                val moveY = event.rawY

                val diffX = moveX - lastDownX
                val diffY = moveY - lastDownY

                println("diffX:$diffX,  diffY: $diffY")

//                translationX = diffX
//                translationY = diffY

//                lastDownX = moveX
//                lastDownY = moveY

//                ObjectAnimator.ofFloat(this,"translationX",diffX).start()
//
//                ObjectAnimator.ofFloat(this,"translationY",diffY).start()




                lastDownX = 0f
                lastDownY = 0f
                logger.log(Level.INFO,"view:                        ACTION_UP")
            }
            MotionEvent.ACTION_CANCEL -> {
                logger.log(Level.INFO,"view:                        ACTION_CANCEL")
                lastDownX = 0f
                lastDownY = 0f
            }
            else
            -> {

            }
        }
        logger.log(Level.INFO,"view:                        onTouchEvent----------end")

        //返回false和super.onTouchEvent(event)是一样的.
//        return false
     //  return super.onTouchEvent(event)

        return true
    }

    override fun performClick(): Boolean {

        return super.performClick()
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        logger.log(Level.INFO,"view:                        dispatchTouchEvent----------start")
        when(event!!.action){
            MotionEvent.ACTION_DOWN -> {
                logger.log(Level.INFO,"view:                        ACTION_DOWN")
            }
            MotionEvent.ACTION_MOVE -> {
                logger.log(Level.INFO,"view:                        ACTION_MOVE")
            }
            MotionEvent.ACTION_UP -> {
                logger.log(Level.INFO,"view:                        ACTION_UP")
            }
            MotionEvent.ACTION_CANCEL -> {
                logger.log(Level.INFO,"view:                        ACTION_CANCEL")
            }
            else
            -> {

            }
        }
        logger.log(Level.INFO,"view:                        dispatchTouchEvent----------end")

        return super.dispatchTouchEvent(event)
        //return true //自己消费掉
       // return false
    }
}