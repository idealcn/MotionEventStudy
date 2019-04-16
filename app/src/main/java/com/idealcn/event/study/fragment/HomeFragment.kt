package com.idealcn.event.study.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.TOUCH_SLOP_PAGING
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.idealcn.event.study.R
import com.idealcn.event.study.activity.DetailActivity

import com.idealcn.event.study.widget.DragView
import com.idealcn.event.study.widget.pager.AutoPlayLayout
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
            "RxJava","Retrofit","OkHttp","Dagger2","Room","Realm","Kotlin","Java8",
            "RxJava","Retrofit","OkHttp","Dagger2","Room","Realm","Kotlin","Java8",
            "RxJava","Retrofit","OkHttp","Dagger2","Room","Realm","Kotlin","Java8",
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
    val map = hashMapOf<String,DragView>()

    val adapter = object : BaseQuickAdapter<String,BaseViewHolder>(R.layout.adapter_home,data){
        override fun convert(helper: BaseViewHolder?, item: String?) {
            val layoutPosition = helper!!.layoutPosition
            helper.setText(R.id.delete,data.get(layoutPosition)+"------------$layoutPosition")

            helper!!.getView<DragView>(R.id.dragView).setOnHorizontalDragListener(object : DragView.OnHorizontalDragListener{
                override fun open(open:Boolean,dragView: DragView) {

                    //打开一个新的item
                    if(layoutPosition!=openPosition && open){
                        //存储新打开的item
                        map["$layoutPosition"] = dragView
                        //关闭之前打开的item
                        map["$openPosition"]?.close()
                        openPosition = layoutPosition
                    }

                }
            })
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("--------onViewCreated-----------")

        recyclerView = getView()!!.findViewById(R.id.recyclerView)

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
        recyclerView.setRecyclerListener(object : RecyclerView.RecyclerListener{
            override fun onViewRecycled(holder: RecyclerView.ViewHolder?) {
                holder?.let {
                    val recyclable = it.isRecyclable
                    val adapterPosition = it.adapterPosition
                    val layoutPosition = it.layoutPosition
                    println("recyclable: $recyclable,adapterPosition: $adapterPosition,layoutPosition: $layoutPosition")
                }
            }

        })


        adapter.setOnItemClickListener { adapter, view, position ->
            //先关闭已经打开的item,仿qq消息列表界面 效果
            if(openPosition>-1){
                map["$openPosition"]?.close()
                openPosition = -1
                return@setOnItemClickListener
            }
            startActivity(Intent(context,DetailActivity::class.java))
        }

        val listOf = listOf<Int>(
                R.drawable.fengjing,
                R.drawable.fengjing1,
                R.drawable.fengjing2
        )


        val imageList = mutableListOf<ImageView>()
        var iv : ImageView? = null
        for (x in 0 until listOf.size){
            iv = ImageView(_context)
            iv.scaleType = ImageView.ScaleType.CENTER_CROP
            iv.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            iv.setImageResource(listOf[x])
            imageList.add(iv)
        }


        val autoPlayLayout = view.findViewById<AutoPlayLayout>(R.id.viewPager)
        autoPlayLayout.setImageList(listOf)
        autoPlayLayout.startPlay()

        /*    .adapter = object : PagerAdapter(){

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun getCount(): Int {
            return listOf.size
        }

        override fun instantiateItem(container: ViewGroup, position: Int): ImageView {
            var imageView :ImageView? =   imageList[position]
            if (imageView == null) {
                imageView = ImageView(_context)
                imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                imageView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                imageView.setImageResource(listOf[position])
            }
            container.addView(imageView)
            return imageView
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object`as View)
        }

    }
*/

        //轮播图


        val banner = getView()!!.findViewById<Banner>(R.id.banner)

        banner.isAutoPlay(false)
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
                "风景","美女","车展"
        ))
        banner.start()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        println("--------onActivityCreated-----------")

    }




}