package com.idealcn.event.study.widget.pager;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class AutoPlayAdapter extends PagerAdapter {

    private List<ImageView> imageViewList  = new ArrayList<>();

    public AutoPlayAdapter(){

    }


    @Override
    public int getCount() {
        return imageViewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView)object);
    }

    @NonNull
    @Override
    public ImageView instantiateItem(@NonNull ViewGroup container, int position) {
        final ImageView imageView = imageViewList.get(position);
        container.addView(imageView);
        return imageView;
    }


    public void setImageList(List<ImageView> viewList) {
        imageViewList.addAll(viewList);
    }
}
