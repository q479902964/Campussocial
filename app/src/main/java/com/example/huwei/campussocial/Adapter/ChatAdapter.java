package com.example.huwei.campussocial.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huwei.campussocial.R;
import com.example.huwei.campussocial.bean.Chat;

import java.util.List;

/**
 * Created by HUWEI on 2017/5/2.
 */

public class ChatAdapter extends ArrayAdapter<Chat> {
    int resourceID;
    public ChatAdapter(Context context, int textViewResourceId, List<Chat> objects) {
        super(context, textViewResourceId, objects);
        resourceID = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Chat chat = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceID,parent,false);
        ImageView FriendIcon = (ImageView) view.findViewById(R.id.FriIcon);
        TextView  FriendName = (TextView) view.findViewById(R.id.FriName);
        TextView  ChatContent = (TextView) view.findViewById(R.id.ChatContent);
        TextView  ChatTime = (TextView) view.findViewById(R.id.ChatTime);
        FriendIcon.setImageResource(chat.getFriendIcon());
        FriendName.setText(chat.getFriendName());
        ChatContent.setText(chat.getChatContent());
        ChatTime.setText(chat.getChatTime());
        return view;
    }
}
