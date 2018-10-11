package com.idealcn.event.study.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.TOUCH_SLOP_PAGING
import android.view.*
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.idealcn.event.study.R

import com.idealcn.event.study.widget.DragView
import com.youth.banner.Banner
import com.youth.banner.loader.ImageLoaderInterface


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


        return inflater.inflate(R.layout.fragment_home,container,false)
    }

    var openPosition = -1

    val adapter = object : BaseQuickAdapter<String,BaseViewHolder>(R.layout.adapter,data){
        override fun convert(helper: BaseViewHolder?, item: String?) {
            val layoutPosition = helper!!.layoutPosition



            //helper!!.setText(R.id.text,item)
            helper!!.getView<DragView>(R.id.dragView).setOnHorizontalDragListener(object : DragView.OnHorizontalDragListener{
                override fun open(open:Boolean,dragView: DragView) {
                    if (open){
                        openPosition = layoutPosition
                    }else{

                    }
                }

                override fun dragDown(dragView: DragView) {

                }


            })
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("--------onViewCreated-----------")

        recyclerView = getView()!!.findViewById(R.id.recyclerView)

        val slop = ViewConfiguration.get(_context).scaledTouchSlop
        recyclerView.setScrollingTouchSlop(TOUCH_SLOP_PAGING)

        recyclerView.layoutManager = LinearLayoutManager(_context)

        recyclerView.addItemDecoration(DividerItemDecoration(_context,DividerItemDecoration.VERTICAL))

        recyclerView.adapter = adapter
        adapter.setOnItemClickListener { adapter, view, position ->

//            val bundle = Bundle()
//            bundle.putString("title",data[position])
//            val intent = Intent(_context, DetailActivity::class.java).putExtras(bundle)
//            startActivity(intent)


        }


        val listOf = listOf<Int>(
                R.drawable.fengjing,
                R.drawable.fengjing1,
                R.drawable.fengjing2
        )

        view.findViewById<ViewPager>(R.id.viewPager).adapter = object : PagerAdapter(){

            override fun isViewFromObject(view: View, `object`: Any): Boolean {
                return view == `object`
            }

            override fun getCount(): Int {
                return 3
            }

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                val imageView = ImageView(_context)
                imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                imageView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
                container.addView(imageView)
                imageView.setImageResource(listOf[position])
                return imageView
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                container.removeView(`object`as View)
            }

        }


        //轮播图


        val banner = getView()!!.findViewById<Banner>(R.id.banner)

        banner.isAutoPlay(true)
        .setDelayTime(1500)
                .setImageLoader(object : ImageLoaderInterface<ImageView>{
                    override fun createImageView(context: Context?): ImageView {
                        val imageView :ImageView = ImageView(activity)
                        imageView.scaleType  = ImageView.ScaleType.CENTER_CROP
                        imageView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                200)
                        return imageView
                    }

                    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
                        if (path is Int){
                            imageView!!.setImageResource(path)
                        }
                    }

                })
        .setImages(arrayListOf<Int>(
                R.drawable.fengjing,
                R.drawable.fengjing1,
                R.drawable.fengjing2
                ))
       .setBannerTitles(listOf(
                "风景","美女","车展","美食"
        ))
        banner.start()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        println("--------onActivityCreated-----------")

    }




}