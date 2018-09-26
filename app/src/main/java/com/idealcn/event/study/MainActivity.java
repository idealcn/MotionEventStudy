package com.idealcn.event.study;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.idealcn.event.study.activity.ScrollerActivity;
import com.idealcn.event.study.fragment.BeautyFragment;
import com.idealcn.event.study.fragment.ScrollerFragment;
import com.idealcn.event.study.fragment.HomeFragment;
import com.idealcn.event.study.fragment.RXJavaFragment;
import com.idealcn.event.study.widget.NoScrollViewPager;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {


    private String[] titleArray = {"风景", "军事","美女", "汽车" };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        System.out.println("activity    oncreate-------");
        final List<Fragment> fragmentList = Arrays.asList(new HomeFragment(),new RXJavaFragment(),
                new BeautyFragment(),new ScrollerFragment());

//        HorizontalScrollView
//      final  Button btn  = findViewById(R.id.btn);
//      final TextView textView = findViewById(R.id.textView);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                textView.animate().translationX(-300).translationY(-50).start();
////                textView.scrollTo(-300,-300);
//            }
//        });


        final TabLayout tabLayout = findViewById(R.id.tabLayout);
        final NoScrollViewPager viewPager = findViewById(R.id.viewPager);

        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //必须先设置了TabMode
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.setupWithViewPager(viewPager);

        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
//                if (position==fragmentList.size()-1){
//                    startActivity(new Intent(MainActivity.this,ScrollerActivity.class));
//                }
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return titleArray.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titleArray[position];
            }
        });

    }

    private Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        logger.info("onTouchEvent");
        return super.onTouchEvent(event);
    }

    /*
    复写这个方法的时候,只有返回super才会去调用父类的方法,否则都不会走父类中
    getWindow().superDispatchTouchEvent(ev)这个方法,那么事件也不会分发下去
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        logger.info("dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final List<Fragment> fragmentList = fragmentManager.getFragments();
        final int backStackEntryCount = fragmentManager.getBackStackEntryCount();


    }
}
