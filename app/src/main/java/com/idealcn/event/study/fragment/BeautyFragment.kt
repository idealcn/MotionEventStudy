package com.idealcn.event.study.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.idealcn.event.study.R


/**
 * author: guoning
 * date: 2018/7/10 4:48 PM
 * description:
 */
class BeautyFragment : Fragment() {


    override fun onAttach(context: Context) {
        super.onAttach(context)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        println("--------onCreateView-----------")


        return inflater.inflate(R.layout.fragment_beauty,container,false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("--------onViewCreated-----------")










    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        println("--------onActivityCreated-----------")

    }



}