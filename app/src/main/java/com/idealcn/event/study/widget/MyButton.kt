package com.idealcn.event.study.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
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

            }
            MotionEvent.ACTION_MOVE -> {
                logger.log(Level.INFO,"view:                        ACTION_MOVE")
                //return true
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
        logger.log(Level.INFO,"view:                        onTouchEvent----------end")

        //返回false和super.onTouchEvent(event)是一样的.
//        return false
       return super.onTouchEvent(event)

      //  return true
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