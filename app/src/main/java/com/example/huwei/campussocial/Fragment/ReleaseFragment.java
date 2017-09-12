package com.example.huwei.campussocial.Fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.huwei.campussocial.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HUWEI on 2017/5/2.
 */

public class ReleaseFragment extends Fragment implements View.OnClickListener{
    private String[] mTitles = {"发帖","发问"};
    private List<android.support.v4.app.Fragment> list;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.release,container,false);
        Button bt_posting = (Button) view.findViewById(R.id.bt_posting);
        Button bt_question= (Button) view.findViewById(R.id.bt_question);
        bt_posting.setOnClickListener(this);
        bt_question.setOnClickListener(this);
        replaceFragment(new Release_PostingFragment());
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_posting:
                replaceFragment(new Release_PostingFragment());
                break;
            case R.id.bt_question:
                replaceFragment(new Release_QuestionFragment());
                break;
        }
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.release_layout,fragment);
        transaction.commit();
    }
}
