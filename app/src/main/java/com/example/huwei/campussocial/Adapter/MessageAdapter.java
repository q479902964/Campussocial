package com.example.huwei.campussocial.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.huwei.campussocial.R;
import com.example.huwei.campussocial.bean.Message;
import com.example.huwei.campussocial.bean.Postings;

import java.util.List;

/**
 * Created by HUWEI on 2017/9/11.
 */

public class MessageAdapter  extends ArrayAdapter<Message> {
    int resourceid;
    public MessageAdapter(Context context, int textViewResourceId, List<Message> objects) {
        super(context, textViewResourceId, objects);
        resourceid = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message mes = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(resourceid,parent,false);
        ImageView UserIcon = (ImageView) convertView.findViewById(R.id.usericon);
        TextView Content = (TextView) convertView.findViewById(R.id.message_content);
        TextView  Title = (TextView) convertView.findViewById(R.id.message_title);
        TextView  Time = (TextView) convertView.findViewById(R.id.message_time);

        String usericon = mes.getUsericon();
        Glide.with(getContext()).load(usericon).into(UserIcon);
        Content.setText(mes.getContent());
        Time.setText(mes.getTime());
        Title.setText(mes.getTitle());
        return convertView;
    }
}
