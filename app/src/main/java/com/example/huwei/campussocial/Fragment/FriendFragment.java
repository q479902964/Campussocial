package com.example.huwei.campussocial.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.huwei.campussocial.Adapter.MessageAdapter;
import com.example.huwei.campussocial.Adapter.PostingsAdapter;
import com.example.huwei.campussocial.MainActivity;
import com.example.huwei.campussocial.R;
import com.example.huwei.campussocial.Util.AutoUpdateService;
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
    private List<Message> messagelist= new ArrayList<>();
    private List<Message> new_messagelist = new ArrayList<>();
    private MessageAdapter messageAdapter;
    private String userphone;
    private String message;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friend,container,false);
        userphone = getArguments().getString("userphone");
        SharedPreferences prefs = getContext().getSharedPreferences("new", Context.MODE_MULTI_PROCESS);
        new_message= prefs.getString("new_message",null);
        handler.postDelayed(task,5000);
//        Log.i("new_message",new_message);
//        message = prefs.getString("message"," [\n" +
//                "        {\n" +
//                "            \"title\": \"Google\",\n" +
//                "            \"time\": \"18:20\",\n" +
//                "            \"content\": \"你好\",\n" +
//                "            \"usericon\": \"http://bpic.588ku.com/back_pic/02/67/58/81578e331cc7693.jpg\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "            \"title\": \"lj\",\n" +
//                "            \"time\": \"18:20\",\n" +
//                "            \"content\": \"你好\",\n" +
//                "            \"usericon\": \"http://bpic.588ku.com/back_pic/02/67/58/81578e331cc7693.jpg\"\n" +
//                "        }\n" +
//                "    ]\n");
        message = prefs.getString("message",null);
//        Log.i("message",message);
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
        HttpUtil.getMessage(url, userphone, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(),"网络不给力啊",Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String re= response.body().string();
                messagelist = new ArrayList<>();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messagelist= Utility.handleChatResponse(message);
                        messageAdapter = new MessageAdapter(getContext(),R.layout.message,messagelist);
                        listView.setAdapter(messageAdapter);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

            }
        });
    }

    @Override
    public void onClick(View view) {
    }
    public void inial(){
//        Log.i("new_message_inial",new_message);
        if(new_message!=null) {
            new_messagelist = Utility.handleChatResponse(new_message);
            messagelist.addAll(new_messagelist);
             messageAdapter.notifyDataSetChanged();
            listView.setAdapter(messageAdapter);
            Toast.makeText(getContext(),"有新消息",Toast.LENGTH_LONG).show();
        }
    }
    private Handler handler = new Handler();

    private Runnable task =new Runnable() {
        public void run() {
            // TODOAuto-generated method stub
            handler.postDelayed(this,5*1000);//设置延迟时间，此处是5秒
            //需要执行的代码
            SharedPreferences prefs = getContext().getSharedPreferences("new", Context.MODE_MULTI_PROCESS);
            new_message= prefs.getString("new_message",null);
//            Log.i("new_",new_message);
            if(new_message!=null) {
                new_messagelist = Utility.handleChatResponse(new_message);
                messagelist.addAll(new_messagelist);
                messageAdapter.notifyDataSetChanged();
                listView.setAdapter(messageAdapter);
                Toast.makeText(getContext(),"有新消息",Toast.LENGTH_LONG).show();
            }
        }
    };
}
