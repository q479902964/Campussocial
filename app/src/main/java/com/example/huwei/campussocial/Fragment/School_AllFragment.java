package com.example.huwei.campussocial.Fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huwei.campussocial.Adapter.PostingsAdapter;
import com.example.huwei.campussocial.bean.Postings;
import com.example.huwei.campussocial.R;
import com.example.huwei.campussocial.Util.HttpUtil;
import com.example.huwei.campussocial.Util.Utility;
import com.example.huwei.campussocial.view.PostingsAty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by HUWEI on 2017/5/1.
 */

public class School_AllFragment extends Fragment {
    private ListView listView;
    private static final int LEVEL_Campus = 0;
    private static final int LEVEL_institute = 1;
    private static final int LEVEL_Classification = 2;
    private final static String url ="http://192.168.165.18:8088/Campus/index.php/Academic_exchange/Index/getAllPost";
    private Button bt_choose;
    private List<Postings> postingslist;
    private int campusId;
    private int instituteId;
    private int classificationId;
    private String[] campuslist = {"广东外语外贸大学","广东工业大学","华南理工大学"};
    private String[] institutelist = {"信息学院","经贸学院","艺术学院"};
    private String[] classificationlist={"学术","生活","全部"};
    public SwipeRefreshLayout swipeRefreshLayout;
    private TextView tv_title;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        db = Connector.getDatabase();
        View view = inflater.inflate(R.layout.school_allfragment,container,false);
        listView = (ListView) view.findViewById(R.id.lv_school_all);
        bt_choose= (Button) view.findViewById(R.id.bt_choose);
        tv_title= (TextView) view.findViewById(R.id.title_text);
        swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.swipe_all);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                inial();
            }
        });
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String postinginfo = prefs.getString("all_posting",null);
        final String userphone = getArguments().getString("userphone");
        final String photo = getArguments().getString("photo");
        final String nickname = getArguments().getString("nick_name");
        postingslist = new ArrayList<>();
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
                    intent.putExtra("nick_name",nickname);
                    startActivity(intent);
                }
            }
        });
        if(postinginfo!=null){
            postingslist= Utility.handlePostingResponse(postinginfo);
            PostingsAdapter postingsAdapter = new PostingsAdapter(getContext(),R.layout.me_list,postingslist);
            listView.setAdapter(postingsAdapter);
        }else {
            inial();
        }
        bt_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCampus();
            }
        });
        return view;
    }
    private void inial() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
        progressDialog.show();
        HttpUtil.sendOkhttpRequest(url + "?campus_id=" + campusId + "&institute=" + instituteId + "&type=" + 2, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                Toast.makeText(getContext(),"网络不给力啊",Toast.LENGTH_SHORT).show();
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                });
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String re= response.body().string();
                postingslist = Utility.handlePostingResponse(re);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        if(postingslist!=null){
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
                            editor.putString("all_posting",re);
                            editor.apply();
                            PostingsAdapter postingsAdapter = new PostingsAdapter(getContext(),R.layout.me_list,postingslist);
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
    public void showInstitute(){
        AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
        ad.setTitle("学院选择");
        ad.setItems(institutelist, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                instituteId = i;
                showClassification();
            }
        });
        AlertDialog dialog = ad.create();
        dialog.show();
    }
    public void showClassification(){
        AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
        ad.setTitle("帖型选择");
        ad.setItems(classificationlist, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                classificationId = i;
                Log.d("id:",campusId+"/"+instituteId+"/"+classificationId+"/");
                tv_title.setText(classificationlist[i]);
                HttpUtil.sendOkhttpRequest(url + "?campus_id=" + campusId + "&institute=" + instituteId + "&type=" + classificationId, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getContext(),"网络不给力啊",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        final String re= response.body().string();
                        postingslist = new ArrayList<>();
                        postingslist.clear();
                        postingslist = Utility.handlePostingResponse(re);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(postingslist!=null){
                                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
                                    editor.putString("question_posting",re);
                                    editor.apply();
                                    PostingsAdapter postingsAdapter = new PostingsAdapter(getContext(),R.layout.me_list,postingslist);
                                    listView.setAdapter(postingsAdapter);
                                }else {
                                    Toast.makeText(getContext(),"获取帖子失败",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        });
        AlertDialog dialog = ad.create();
        dialog.show();
    }
    public void showCampus(){
        AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
        ad.setTitle("大学选择");
        ad.setItems(campuslist, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                campusId = i;
                showInstitute();
            }
        });
        AlertDialog dialog = ad.create();
        dialog.show();
    }
}
