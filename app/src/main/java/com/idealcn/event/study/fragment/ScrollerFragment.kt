package com.idealcn.event.study.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.idealcn.event.study.R
import com.idealcn.event.study.activity.DetailActivity
import java.util.logging.Logger


/**
 * author: guoning
 * date: 2018/7/10 4:48 PM
 * description:
 */
class ScrollerFragment : Fragment() {


    val logger = Logger.getLogger(this.javaClass.simpleName)


    override fun onAttach(context: Context) {
        super.onAttach(context)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        println("--------onCreateView-----------")


        return inflater.inflate(R.layout.activity_detail,container,false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("--------onViewCreated-----------")

        view.findViewById<Button>(R.id.button).setOnClickListener {
            startActivity(Intent(activity,DetailActivity::class.java))
        }

//        val button = view.findViewById<Button>(R.id.btnScroller)
//        button.setOnClickListener {
//            //x和y为正数,表示向左和向上滑动,并且滚动的是view的内容
//            button.scrollBy(-10,-10)
//            logger.info("button.scrollX: ${button.scrollX}, button.scrollY: ${button.scrollX}")
//        }






    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        println("--------onActivityCreated-----------")

    }



}