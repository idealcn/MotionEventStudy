package com.idealcn.event.study.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.ListView
import com.idealcn.event.study.R
import com.idealcn.event.study.widget.ScrollerLayout

/**
 * 日期: 2018/9/21 15:01
 * author: guoning
 * description:
 */
class ScrollerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_scroller)
//        val scrollerLayout = findViewById<ScrollerLayout>(R.id.scrollerLayout)
//        val recyclerView = scrollerLayout.findViewById<ListView>(R.id.recyclerView)
//
//        recyclerView.adapter =ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arrayListOf("hello","java"))
    }
}