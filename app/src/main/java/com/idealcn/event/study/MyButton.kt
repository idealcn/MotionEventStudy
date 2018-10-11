package com.idealcn.event.study

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
    val logger = Logger.getLogger(this.javaClass.simpleName)


    constructor( context: Context) : super(context)

    constructor( context: Context,attributes: AttributeSet) : super(context,attributes)

    constructor(context: Context,attributes: AttributeSet,defStyleAttr : Int) : super(context,attributes,defStyleAttr)


    override fun onTouchEvent(event: MotionEvent): Boolean {
        logger.info("onTouchEvent----------start")
        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                logger.log(Level.INFO,"ACTION_DOWN")
            }
            MotionEvent.ACTION_MOVE -> {
                logger.log(Level.INFO,"ACTION_MOVE")
            }
            MotionEvent.ACTION_UP -> {
                logger.log(Level.INFO,"ACTION_UP")
            }
            MotionEvent.ACTION_CANCEL -> {
                logger.log(Level.INFO,"ACTION_CANCEL")
            }
            else
            -> {

            }
        }
        logger.info("onTouchEvent----------end")

//        return super.onTouchEvent(event)
//        return true
        return false
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        logger.info("dispatchTouchEvent----------start")
        when(event!!.action){
            MotionEvent.ACTION_DOWN -> {
                logger.log(Level.INFO,"ACTION_DOWN")
            }
            MotionEvent.ACTION_MOVE -> {
                logger.log(Level.INFO,"ACTION_MOVE")
            }
            MotionEvent.ACTION_UP -> {
                logger.log(Level.INFO,"ACTION_UP")
            }
            MotionEvent.ACTION_CANCEL -> {
                logger.log(Level.INFO,"ACTION_CANCEL")
            }
            else
            -> {

            }
        }
        logger.info("dispatchTouchEvent----------end")

//        return super.dispatchTouchEvent(event)
        //return true //自己消费掉
        return false
    }
}