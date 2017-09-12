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
import com.example.huwei.campussocial.bean.Postings;

import java.util.List;

/**
 * Created by HUWEI on 2017/5/5.
 */

public class PostingsAdapter extends ArrayAdapter<Postings> {
    int resourceid;

    public PostingsAdapter(Context context, int textViewResourceId, List<Postings> objects) {
        super(context, textViewResourceId, objects);
        resourceid = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Postings postings = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(resourceid,parent,false);

        ImageView UserIcon = (ImageView) convertView.findViewById(R.id.usericon);
        TextView UserName = (TextView) convertView.findViewById(R.id.username);
        TextView Content = (TextView) convertView.findViewById(R.id.content);
        TextView  Title = (TextView) convertView.findViewById(R.id.title);
        TextView  Time = (TextView) convertView.findViewById(R.id.time);
        TextView  CommentNum = (TextView) convertView.findViewById(R.id.commentnum);
        TextView  Thumbsup = (TextView) convertView.findViewById(R.id.thumbsup);
        TextView  Thumbsdown = (TextView) convertView.findViewById(R.id.thumbsdown);

        convertView.setTag(postings.getID());
        String usericon = postings.getUserIcon();
        Glide.with(getContext()).load(usericon).into(UserIcon);
        UserName.setText(postings.getUserName());
        Content.setText(postings.getContent());
        Time.setText(postings.getTime());
        CommentNum.setText(String.valueOf(postings.getCommentNum()));
        Thumbsup.setText(String.valueOf(postings.getThumbsupNum()));
        Thumbsdown.setText(String.valueOf(postings.getThumbsdownNum()));
        Title.setText(postings.getTitle());
        return convertView;
    }
}
