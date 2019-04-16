package com.idealcn.event.study.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.idealcn.event.study.R;
import com.idealcn.event.study.widget.MyViewPager;
import com.youth.banner.transformer.ZoomOutSlideTransformer;

import java.util.ArrayList;
import java.util.List;

public class PagerFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_pager, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final MyViewPager viewPager =  view.findViewById(R.id.viewpager);
        viewPager.setPageTransformer(true,new ZoomOutSlideTransformer(),View.LAYER_TYPE_NONE);
        final List<ImageView> imageViewList = new ArrayList<>();
        ImageView imageView = null;
        for (int x = 0; x < 3; x++) {
            imageView = new ImageView(getContext());
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "----------", Toast.LENGTH_SHORT).show();
                }
            });
            if (x==0)
               imageView.setImageResource(R.drawable.fengjing);
            else if (x==1)
                imageView.setImageResource(R.drawable.fengjing1);
            else
                imageView.setImageResource(R.drawable.fengjing2);
            imageViewList.add(imageView);
        }

        viewPager.setPageMargin(30);
        viewPager.setPageMarginDrawable(new ColorDrawable(Color.BLUE));
       viewPager.setAdapter(new PagerAdapter() {
           @Override
           public int getCount() {
               return imageViewList.size();
           }

           @Override
           public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
               return view == object;
           }

           @NonNull
           @Override
           public Object instantiateItem(@NonNull ViewGroup container, int position) {
               ImageView iv = imageViewList.get(position);
               container.addView(iv);
               return iv;
           }

           @Override
           public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
               container.removeView((View)object);
           }

       });

       viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
           @Override
           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

           }

           @Override
           public void onPageSelected(int position) {
               System.out.println(viewPager.getChildCount());
               System.out.println(viewPager.getWidth());
           }

           @Override
           public void onPageScrollStateChanged(int state) {

           }
       });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
