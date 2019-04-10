package com.idealcn.event.study.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import com.idealcn.event.study.R
import java.util.logging.Level
import java.util.logging.Logger


/**
 * author: guoning
 * date: 2018/7/12 10:25 AM
 * description:
 */
class DetailActivity : AppCompatActivity(){

    val logger = Logger.getLogger("motion")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_scroller)
    }


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        logger.log(Level.INFO,"Activity:                    dispatchTouchEvent ----------- start")

        when(ev!!.action){
            MotionEvent.ACTION_DOWN -> {
                logger.log(Level.INFO,"Activity:                    ACTION_DOWN")
            }
            MotionEvent.ACTION_MOVE -> {
                logger.log(Level.INFO,"Activity:                    ACTION_MOVE")

            }
            MotionEvent.ACTION_UP -> {
                logger.log(Level.INFO,"Activity:                    ACTION_UP")

            }

            MotionEvent.ACTION_CANCEL -> {
                logger.log(Level.INFO,"Activity:                    ACTION_CANCEL")

            }
        }
        logger.log(Level.INFO,"Activity:                    dispatchTouchEvent ----------- end")

        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        logger.log(Level.INFO,"Activity:                    onTouchEvent ----------- start")

        when(event!!.action){
            MotionEvent.ACTION_DOWN -> {
                logger.log(Level.INFO,"Activity:                    ACTION_DOWN")
            }
            MotionEvent.ACTION_MOVE -> {
                logger.log(Level.INFO,"Activity:                    ACTION_MOVE")

            }
            MotionEvent.ACTION_UP -> {
                logger.log(Level.INFO,"Activity:                    ACTION_UP")

            }

            MotionEvent.ACTION_CANCEL -> {
                logger.log(Level.INFO,"Activity:                    ACTION_CANCEL")

            }
        }
        logger.log(Level.INFO,"Activity:                    onTouchEvent ----------- end")
        return super.onTouchEvent(event)
    }

}