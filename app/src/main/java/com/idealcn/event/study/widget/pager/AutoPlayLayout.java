package com.idealcn.event.study.widget.pager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.idealcn.event.study.R;

import java.util.ArrayList;
import java.util.List;

public class AutoPlayLayout extends FrameLayout {

    private         AutoPlayPager viewPager;
    private AutoPlayAdapter playAdapter;


    private  List<ImageView> imageViewList = new ArrayList<>();


    private boolean autoPlay = false;
    private long autoPlayInterval = 1000L;
    private  int currentIndex = 1;
    private int pageCount = 0;

    public AutoPlayLayout(@NonNull Context context) {
        this(context,null);
    }

    public AutoPlayLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AutoPlayLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_auto_play,this,true);
        viewPager = findViewById(R.id.autoPlayPager);
        playAdapter = new AutoPlayAdapter();

    }

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            currentIndex = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {
         //  if (state == ViewPager.SCROLL_STATE_DRAGGING ){
               if (currentIndex==0){
                   viewPager.setCurrentItem(pageCount,false);
               }
               if (currentIndex==pageCount+1){
                   viewPager.setCurrentItem(1,false);
               }
         //  }else if (state == ViewPager.SCROLL_STATE_IDLE){
             /*   if (currentIndex==pageCount+1){
                    viewPager.setCurrentItem(1,false);
                }else if (currentIndex==0){
                    viewPager.setCurrentItem(pageCount,false);
                }*/
         //  }
        }
    };

    private Runnable autoPlayTask = new Runnable() {
        @Override
        public void run() {
            currentIndex = currentIndex % (pageCount+1) +1;

//            if (currentIndex==pageCount){
//                viewPager.setCurrentItem(1,false);
//                post(autoPlayTask);
//            } else {
            viewPager.setCurrentItem(currentIndex,true);
            postDelayed(autoPlayTask,autoPlayInterval);
//            }
        }
    };



    public void setImageList(List<Integer> list){
        final int size = list.size();
        pageCount = size;
        ImageView imageView = null;
        int resId = 0;
        for (int x = 0; x < size + 2; x++) {
            imageView = createImage();
            if (x==0){
               resId =  list.get(size-1);
            }else if (x==size+1){
                resId = list.get(0);
            }else {
               resId = list.get(x-1);
            }
            imageView.setImageResource(resId);
            imageViewList.add(imageView);
        }
    }

    private ImageView createImage() {
        ImageView    iv = new  ImageView(getContext());
        iv.setScaleType( ImageView.ScaleType.CENTER_CROP);
        iv.setLayoutParams( new  ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        return iv;
    }


    public void startPlay(){
        playAdapter.setImageList(imageViewList);
        viewPager.setAdapter(playAdapter);
        viewPager.addOnPageChangeListener(onPageChangeListener);
        viewPager.setCurrentItem(1);
        if (autoPlay){
            autoPlay();
        }
    }






    private  void autoPlay(){
        postDelayed(autoPlayTask,autoPlayInterval);
    }

    private void stopAutoPlay(){
        removeCallbacks(autoPlayTask);
    }

}
