package com.idealcn.event.study;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.idealcn.event.study.widget.NoScrollViewPager;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private String[] titleArray = {"RxJava", "Retrofit", "OkHttp", "Dagger2", "Room", "Realm", "Kotlin", "Java8"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        System.out.println("activity    oncreate-------");

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


        tabLayout.setupWithViewPager(viewPager);

        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return new HomeFragment();
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
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
