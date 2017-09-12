package com.example.huwei.campussocial.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.huwei.campussocial.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HUWEI on 2017/5/2.
 */

public class MeFragment extends Fragment {
    private String[] mTitle ={"我的帖子","我参加的帖子","我的问答"};
    private TabLayout me_tablayout;
    private ViewPager me_viewpager;
    private List<Fragment> lists;
    private PagerAdapter me_pagerAdapter;
    private ImageView userIcon;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me,container,false);
        me_tablayout = (TabLayout) view.findViewById(R.id.me_tablayout);
        me_viewpager = (ViewPager) view.findViewById(R.id.me_viewpager);
        userIcon = (ImageView) view.findViewById(R.id.iv_usericon);
        String userphone = getArguments().getString("userphone");
        String photo = getArguments().getString("photo");
        Glide.with(getContext()).load(photo).into(userIcon);
        lists = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putString("userphone",userphone);
        bundle.putString("photo",photo);
        Fragment mtab01 = new Me_PostingFragment();
        Fragment mtab02 = new Me_PiPostingFragment();
        Fragment mtab03 = new Me_QuestionFragment();
        mtab01.setArguments(bundle);
        mtab02.setArguments(bundle);
        mtab03.setArguments(bundle);
        lists.add(mtab01);
        lists.add(mtab02);
        lists.add(mtab03);
        me_tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                me_viewpager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        me_pagerAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return lists.get(position);
            }

            @Override
            public int getCount() {
                return lists.size();
            }
        };
        me_tablayout.setTabsFromPagerAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return lists.get(position);
            }

            @Override
            public int getCount() {
                return lists.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view ==object;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle[position];
            }

        });
//        mTabLayout.setTabsFromPagerAdapter(new PagerAdapter() {
//            @Override
//            public int getCount() {
//                return imageResId.length;
//            }
//            @Override
//            public boolean isViewFromObject(View view, Object object) {
//                return view ==object;
//            }
//            @Override
//            public CharSequence getPageTitle(int position) {
//                return mTitle[position];
//            }
//            @Override
//            public Object instantiateItem(ViewGroup container, int position) {
//                ImageView image = new ImageView(UIAty.this);
//                image.setImageResource(imageResId[position]);
//                ((ViewPager) container).addView(image);
//                return image;
//            }
//            @Override
//            public void destroyItem(ViewGroup container, int position, Object object) {
//                ((ViewPager) container).removeView((View) object);
//            }
//        });
        final TabLayout.TabLayoutOnPageChangeListener listener =
                new TabLayout.TabLayoutOnPageChangeListener(me_tablayout);
        me_viewpager.addOnPageChangeListener(listener);
        me_viewpager.setAdapter(me_pagerAdapter);
        return view;
    }
}
