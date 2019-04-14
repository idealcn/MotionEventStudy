package com.idealcn.event.study.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.idealcn.event.study.R;
import com.idealcn.event.study.fragment.PagerFragment;
import com.idealcn.event.study.fragment.PagerFragment1;

public class ViewPagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
       ViewPager viewPager =  findViewById(R.id.viewPager);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position==0)
                return new PagerFragment();
                else
                    return new PagerFragment1();
            }

            @Override
            public int getCount() {
                return 2;
            }
        });
    }
}
