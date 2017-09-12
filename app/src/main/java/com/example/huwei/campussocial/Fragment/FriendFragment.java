package com.example.huwei.campussocial.Fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.huwei.campussocial.Adapter.MessageAdapter;
import com.example.huwei.campussocial.Adapter.PostingsAdapter;
import com.example.huwei.campussocial.R;
import com.example.huwei.campussocial.Util.HttpUtil;
import com.example.huwei.campussocial.Util.Utility;import com.example.huwei.campussocial.bean.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by HUWEI on 2017/5/2.
 */

public class FriendFragment extends Fragment implements View.OnClickListener{
    private ListView listView;
    public SwipeRefreshLayout swipeRefreshLayout;
    public String new_message;
    private final static String url ="http://192.168.165.18:8088/Campus/index.php/Academic_exchange/Index/getAllPost";
    private List<com.example.huwei.campussocial.bean.Message> messagelist = new ArrayList<>();
    private List<Message> new_messagelist = new ArrayList<>();
    private MessageAdapter messageAdapter;
    private String userphone;
    private String message;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friend,container,false);
        userphone = getArguments().getString("userphone");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        new_message= prefs.getString("new_message",null);
        message = prefs.getString("message",null);
        listView = (ListView) view.findViewById(R.id.lv_message_all);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_all);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showmeassage();
            }
        });
        if(message!=null){
            messagelist= Utility.handleChatResponse(message);
            messageAdapter = new MessageAdapter(getContext(),R.layout.message,messagelist);
            listView.setAdapter(messageAdapter);
        }else {
           showmeassage();
        }
            inial();
        return view;
    }

    private void showmeassage() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
        progressDialog.show();
        HttpUtil.getMessage(url, userphone, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(),"网络不给力啊",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String re= response.body().string();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messagelist= Utility.handleChatResponse(message);
                        messageAdapter = new MessageAdapter(getContext(),R.layout.message,messagelist);
                        listView.setAdapter(messageAdapter);
                        progressDialog.dismiss();
                    }
                });

            }
        });
    }

    @Override
    public void onClick(View view) {
    }
    public void inial(){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
        progressDialog.show();
        if(new_message!=null) {
            new_messagelist = Utility.handleChatResponse(new_message);
            messagelist.addAll(new_messagelist);
            messageAdapter.notifyDataSetChanged();
            listView.setAdapter(messageAdapter);
            progressDialog.dismiss();
        }
    }
}
