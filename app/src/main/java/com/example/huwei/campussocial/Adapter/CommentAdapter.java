package com.example.huwei.campussocial.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.huwei.campussocial.R;
import com.example.huwei.campussocial.bean.Comment;
import com.example.huwei.campussocial.bean.Postings;

import java.util.List;

/**
 * Created by HUWEI on 2017/5/29.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private List<Comment> mmsgList;
    private Context mcontext;
    public CommentAdapter(List<Comment> msgList,Context context){
        mmsgList = msgList;
        mcontext = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_list,parent,false);
        return new CommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Comment comment = mmsgList.get(position);
        String usericon = comment.getUserIcon();

        holder.itemView.setTag(comment.getID());
        Glide.with(mcontext).load(usericon).into(holder.UserIcon);
        holder.UserName.setText(comment.getUserName());
        holder .Content.setText(comment.getContent());
        holder.Time.setText(comment.getTime());
    }

    @Override
    public int getItemCount() {
        return mmsgList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView UserIcon;
        TextView UserName;
        TextView Content;
        TextView  Time;
        public ViewHolder(View itemView) {
            super(itemView);
        UserIcon= (ImageView) itemView.findViewById(R.id.usericon);
        UserName = (TextView) itemView.findViewById(R.id.username);
        Content = (TextView) itemView.findViewById(R.id.content);
        Time = (TextView) itemView.findViewById(R.id.time);
        }
    }
}
