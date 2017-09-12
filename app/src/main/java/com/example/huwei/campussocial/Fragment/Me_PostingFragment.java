package com.example.huwei.campussocial.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.huwei.campussocial.Adapter.PostingsAdapter;
import com.example.huwei.campussocial.R;
import com.example.huwei.campussocial.Util.HttpUtil;
import com.example.huwei.campussocial.Util.Utility;
import com.example.huwei.campussocial.bean.Postings;
import com.example.huwei.campussocial.view.PostingsAty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by HUWEI on 2017/5/4.
 */

public class Me_PostingFragment extends Fragment {
    private ListView listView;
    private List<Postings> PostingsList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private final static String url ="http://192.168.165.18:8088/Campus/index.php/Academic_exchange/Index/getMyPost";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me_posting,container,false);
        final String userphone = getArguments().getString("userphone");
        final String photo = getArguments().getString("photo");
        listView = (ListView) view.findViewById(R.id.lv_posting);
        swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.swipe_post);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestposting(userphone);
            }
        });
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String postinginfo = prefs.getString("me_posting",null);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object obj=view.getTag();
                if(obj!=null){
                    String id=obj.toString();
                    Intent intent = new Intent(getActivity(), PostingsAty.class);
                    intent.putExtra("key", id);
                    intent.putExtra("userphone",userphone);
                    intent.putExtra("photo",photo);
                    startActivity(intent);
                }
            }
        });
        if(postinginfo!=null){
            PostingsList = Utility.handlePostingResponse(postinginfo);
            PostingsAdapter postingsAdapter = new PostingsAdapter(getContext(),R.layout.me_list,PostingsList);
            listView.setAdapter(postingsAdapter);
        }else {
        requestposting(userphone);
        }
        return view;
    }
    public void requestposting(String phone){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
        progressDialog.show();
        HttpUtil.sendOkhttpRequest(url+ "?phone="+phone , new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
             getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(),"网络不给力啊",Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String re = response.body().string();
                PostingsList = Utility.handlePostingResponse(re);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        if(PostingsList!=null){
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
                            editor.putString("me_posting",re);
                            editor.apply();
                            PostingsAdapter postingsAdapter = new PostingsAdapter(getContext(),R.layout.me_list,PostingsList);
                            listView.setAdapter(postingsAdapter);
                        }else {
                            Toast.makeText(getContext(),"获取帖子失败",Toast.LENGTH_SHORT).show();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }
}
