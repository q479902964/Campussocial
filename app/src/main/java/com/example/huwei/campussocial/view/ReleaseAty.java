package com.example.huwei.campussocial.view;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.huwei.campussocial.Fragment.Release_PostingFragment;
import com.example.huwei.campussocial.Fragment.Release_QuestionFragment;
import com.example.huwei.campussocial.R;

import java.util.ArrayList;
import java.util.List;

public class ReleaseAty extends AppCompatActivity implements View.OnClickListener {
    private String[] mTitles = {"帖 子","发 问"};
    private String userphone;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private PagerAdapter pagerAdapter;
    private List<android.support.v4.app.Fragment> Fraglist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_aty);
//        Button bt_posting = (Button) findViewById(R.id.bt_posting);
//        Button bt_question= (Button) findViewById(R.id.bt_question);
//        bt_posting.setOnClickListener(this);
//        bt_question.setOnClickListener(this);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        userphone = getIntent().getStringExtra("userphone");
        Bundle bundle = new Bundle();
        bundle.putString("userphone",userphone);
        Fragment releaseposting = new Release_PostingFragment();
        Fragment releasequestion = new Release_QuestionFragment();
        releaseposting.setArguments(bundle);
        releasequestion.setArguments(bundle);
        Fraglist = new ArrayList<>();
        Fraglist.add(releaseposting);
        Fraglist.add(releasequestion);
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return Fraglist.get(position);
            }

            @Override
            public int getCount() {
                return Fraglist.size();
            }
        };
        mTabLayout.setTabsFromPagerAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return Fraglist.get(position);
            }

            @Override
            public int getCount() {
                return Fraglist.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view ==object;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position];
            }

        });
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
//        releaseposting.setArguments(bundle);
//        replaceFragment(releaseposting);
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
