package com.example.huwei.campussocial.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.huwei.campussocial.R;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

/**
 * Created by HUWEI on 2017/5/21.
 */

public class Friend_FriendFragment extends Fragment {
    private Button mButton_Friend;
    private Button mButton_Customer;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friend_friendfragment,container,false);
        //打开聊天列表
     if (RongIM.getInstance() != null){
     RongIM.getInstance().startConversationList(getContext() );
     }
        return view;
    }
}
