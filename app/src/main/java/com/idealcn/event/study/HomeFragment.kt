package com.idealcn.event.study

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.ViewDragHelper
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.youth.banner.Banner


/**
 * author: guoning
 * date: 2018/7/10 4:48 PM
 * description:
 */
class HomeFragment : Fragment() {

    lateinit var recyclerView : RecyclerView

    val data = arrayListOf<String>(
            "RxJava","Retrofit","OkHttp","Dagger2","Room","Realm","Kotlin","Java8"
    )

    lateinit var _context: Context

    companion object {
        val tag = HomeFragment::class.java.hashCode()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

        this._context = context

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        println("--------onCreateView-----------")


        return inflater!!.inflate(R.layout.fragment_home,container,false)
    }



    val adapter = object : BaseQuickAdapter<String,BaseViewHolder>(R.layout.adapter,data){
        override fun convert(helper: BaseViewHolder?, item: String?) {
            helper!!.setText(R.id.text,item)
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("--------onViewCreated-----------")

        recyclerView = getView()!!.findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(_context)

        recyclerView.addItemDecoration(DividerItemDecoration(_context,DividerItemDecoration.VERTICAL))

        recyclerView.adapter = adapter
        adapter.setOnItemClickListener { adapter, view, position ->

            val bundle = Bundle()
            bundle.putString("title",data[position])
            val intent = Intent(_context,DetailActivity::class.java).putExtras(bundle)
            startActivity(intent)
        }


        val dragHelper = ViewDragHelper.create(recyclerView, object : ViewDragHelper.Callback() {

            override fun tryCaptureView(child: View, pointerId: Int): Boolean {

                return true
            }

            override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
                super.onViewReleased(releasedChild, xvel, yvel)
            }

            override fun onEdgeLock(edgeFlags: Int): Boolean {
                return super.onEdgeLock(edgeFlags)
            }

            override fun onEdgeDragStarted(edgeFlags: Int, pointerId: Int) {
                super.onEdgeDragStarted(edgeFlags, pointerId)
            }

            override fun onViewDragStateChanged(state: Int) {
                super.onViewDragStateChanged(state)
            }

            override fun onViewPositionChanged(changedView: View, left: Int, top: Int, dx: Int, dy: Int) {
                super.onViewPositionChanged(changedView, left, top, dx, dy)
            }

            override fun onEdgeTouched(edgeFlags: Int, pointerId: Int) {
                super.onEdgeTouched(edgeFlags, pointerId)
            }

            override fun onViewCaptured(capturedChild: View, activePointerId: Int) {
                super.onViewCaptured(capturedChild, activePointerId)
            }

            override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
                return super.clampViewPositionHorizontal(child, left, dx)
            }

            override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
                return super.clampViewPositionVertical(child, top, dy)
            }

            override fun getViewHorizontalDragRange(child: View): Int {
                return super.getViewHorizontalDragRange(child)
            }


        })










        //轮播图


        val banner = getView()!!.findViewById<Banner>(R.id.banner)

        banner.isAutoPlay(true)
        banner.setDelayTime(1500)
        banner.setImages(arrayListOf<String>(
                "http://img.tukexw.com/img/f1a87f7dcda706c1.jpg",
                "http://imgtu.5011.net/uploads/content/20170705/9127871499227031.jpg",
                "http://img.tukexw.com/img/f1a87f7dcda706c1.jpg",
                "http://imgtu.5011.net/uploads/content/20170705/9127871499227031.jpg"
        ))
        banner.setBannerTitles(listOf(
                "风景","美女","车展","美食"
        ))
        banner.start()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        println("--------onActivityCreated-----------")

    }



}