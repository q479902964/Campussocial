package com.example.huwei.campussocial.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.huwei.campussocial.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by HUWEI on 2017/5/1.
 */

public class SchoolFragment extends Fragment {
    private ViewPager viewPager; // android-support-v4中的滑动组件
    private List<ImageView> imageViews; // 滑动的图片集合
    private String[] titles; // 图片标题
    private int[] imageResId = new int[] { R.drawable.r1, R.drawable.r2, R.drawable.r3, R.drawable.r1};; // 图片ID
    private List<View> dots; // 图片标题正文的那些点
    private TextView tv_title;
    private int currentItem = 0; // 当前图片的索引号
    private String[] mTitle = {"全部","帖子","问答"};
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<android.support.v4.app.Fragment> list;
    private PagerAdapter pagerAdapter;
    private ScheduledExecutorService scheduledExecutorService;
    boolean isScrolled = false;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            viewPager.setCurrentItem(currentItem);// 切换当前显示的图片
        };
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.school,container,false);
        mTabLayout = (TabLayout) view.findViewById(R.id.tl);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        imageViews = new ArrayList<ImageView>();
        String userphone = getArguments().getString("userphone");
        String photo = getArguments().getString("photo");
        //设置TabLayout切换
        list = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putString("userphone",userphone);
        bundle.putString("photo",photo);
        Fragment mtab02 = new School_AllFragment();
        Fragment mtab03 = new School_PostingFragment();
        Fragment mtab04 = new School_QuestionFragment();
        mtab02.setArguments(bundle);
        mtab03.setArguments(bundle);
        mtab04.setArguments(bundle);
//        list.add(mtab01);
        list.add(mtab02);
        list.add(mtab03);
        list.add(mtab04);
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
        pagerAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        };
        mTabLayout.setTabsFromPagerAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
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
        final TabLayout.TabLayoutOnPageChangeListener listener =
                new TabLayout.TabLayoutOnPageChangeListener(mTabLayout);
        mViewPager.addOnPageChangeListener(listener);
        mViewPager.setAdapter(pagerAdapter);

        // 初始化图片资源
        for (int i = 0; i < imageResId.length; i++) {
            ImageView imageView = new ImageView(getActivity());
//            imageView.setImageResource(imageResId[i]);
             Glide.with(getActivity()).load(imageResId[i]).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageViews.add(imageView);
        }

        dots = new ArrayList<View>();
        dots.add(view.findViewById(R.id.v_dot0));
        dots.add(view.findViewById(R.id.v_dot1));
        dots.add(view.findViewById(R.id.v_dot2));
        dots.add(view.findViewById(R.id.v_dot3));


        viewPager = (ViewPager) view.findViewById(R.id.vp);
        viewPager.setAdapter(new SchoolFragment.MyAdapter());// 设置填充ViewPager页面的适配器
        // 设置一个监听器，当ViewPager中的页面改变时调用
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
//        viewPager.addOnPageChangeListener(new MyPageChangeListener());
        return view;
    }
    @Override
    public void onStart() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        // 当Activity显示出来后，每两秒钟切换一次图片显示
        scheduledExecutorService.scheduleAtFixedRate(new SchoolFragment.ScrollTask(), 1, 4, TimeUnit.SECONDS);
        super.onStart();
    }

    @Override
    public void onStop() {
        scheduledExecutorService.shutdown();
        super.onStop();
    }
    private class ScrollTask implements Runnable {
        public void run() {
            synchronized (viewPager) {
                currentItem = (currentItem + 1) % imageViews.size();
                handler.obtainMessage().sendToTarget(); // 通过Handler切换图片
            }
        }

    }
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        private int oldPosition = 0;
        /**
         * This method will be invoked when a new page becomes selected.
         * position: Position index of the new selected page.
         */
        public void onPageSelected(int position) {
            currentItem = position;
            dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
            dots.get(position).setBackgroundResource(R.drawable.dot_focused);
            oldPosition = position;
        }

        public void onPageScrollStateChanged(int arg0) {
            switch (arg0)
            {
                case 1:// 手势滑动
                    isScrolled = false;
                    break;
                case 2:// 界面切换
                    isScrolled = true;
                    break;
                case 0:// 滑动结束
// 当前为最后一张，此时从右向左滑，则切换到第一张
                    break;
            }
            if (viewPager.getCurrentItem() == viewPager.getAdapter()
                    .getCount()-1 && !isScrolled)
            {
                viewPager.setCurrentItem(0);
            }
// 当前为第一张，此时从左向右滑，则切换到最后一张
            else if (viewPager.getCurrentItem() == 0 && !isScrolled)
            {
                viewPager.setCurrentItem(viewPager.getAdapter()
                        .getCount() - 1);
            }

        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }
    }
    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageResId.length;
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(imageViews.get(arg1));
            return imageViews.get(arg1);
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView((View) arg2);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {

        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {

        }

        @Override
        public void finishUpdate(View arg0) {

        }
    }
    public class ChildViewPager extends ViewPager {

        private static final String TAG = "xujun";
        public ChildViewPager(Context context) {
            super(context);
        }

        public ChildViewPager(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            int curPosition;

            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    getParent().requestDisallowInterceptTouchEvent(true);
                    break;
                case MotionEvent.ACTION_MOVE:
                    curPosition = this.getCurrentItem();
                    int count = this.getAdapter().getCount();
                    Log.i(TAG, "curPosition:=" +curPosition);
                    // 当当前页面在最后一页和第0页的时候，由父亲拦截触摸事件
                    if (curPosition == count - 1|| curPosition==0) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    } else {//其他情况，由孩子拦截触摸事件
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }

            }
            return super.dispatchTouchEvent(ev);
        }
    }
}
