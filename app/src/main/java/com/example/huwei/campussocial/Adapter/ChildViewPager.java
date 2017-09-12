package com.example.huwei.campussocial.Adapter;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by HUWEI on 2017/9/9.
 */

public class ChildViewPager extends ViewPager{
    private boolean DISABLE=false;
    public ChildViewPager(Context context){
        super(context);
    }
    public ChildViewPager(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return DISABLE&&super.onInterceptTouchEvent(arg0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        return DISABLE&&super.onTouchEvent(arg0);
    }
    }
