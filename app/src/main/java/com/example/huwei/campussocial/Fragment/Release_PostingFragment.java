package com.example.huwei.campussocial.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.huwei.campussocial.R;
import com.example.huwei.campussocial.Util.HttpUtil;
import com.example.huwei.campussocial.view.UIAty;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by HUWEI on 2017/5/2.
 */

public class Release_PostingFragment extends Fragment{
    private Button bt_posting;
    private EditText editText;
    private CheckBox checkBox;
    private EditText et_title;
    private Spinner  spinner_campus;
    private Spinner  spinner_institutes;
    private Spinner  spinner_major;
    private int campus_id;
    private int institute_id;
    private int major_id;
    private ProgressDialog progressDialog;
    private final static String url ="http://192.168.165.18:8088/Campus/index.php/Academic_exchange/Index/post";
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.release_posting,container,false);
        bt_posting = (Button) view.findViewById(R.id.bt_posting);
        editText = (EditText) view.findViewById(R.id.et_content);
        checkBox = (CheckBox) view.findViewById(R.id.checkbox);
        et_title = (EditText) view.findViewById(R.id.et_title);
        spinner_major = (Spinner) view.findViewById(R.id.spinner_major);
        spinner_campus = (Spinner)view. findViewById(R.id.spinner_campus);
        spinner_institutes = (Spinner)view.findViewById(R.id.spinner_institutes);
        progressDialog = new ProgressDialog(getActivity());
        spinner_campus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0: campus_id = 0;
                        break;
                    case 1: campus_id = 1;
                        break;
                    case 2: campus_id = 2;
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner_institutes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0: institute_id = 0;
                        break;
                    case 1: institute_id = 1;
                        break;
                    case 2:institute_id = 2;
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner_major.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0: major_id = 0;
                        break;
                    case 1: major_id = 1;
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        final String userphone = getArguments().getString("userphone");
        bt_posting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean check = checkBox.isClickable();
                String content = editText.getText().toString().trim();
                String title = et_title.getText().toString();
                Log.i("id","campus_id=" + campus_id +"/institute_id="+institute_id + "/major_id = " + major_id);
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(true);
                progressDialog.show();
                if(check){
                    HttpUtil.postPostings(url,userphone, content, title,campus_id,institute_id,major_id,1,0,0, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                Toast.makeText(getContext(),"网络不给力啊",Toast.LENGTH_SHORT).show();
                            }
                        });
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                    Toast.makeText(getContext(),"发帖成功",Toast.LENGTH_SHORT).show();
                                    editText.setText("");
                                    startActivity(new Intent(getActivity(), UIAty.class));
                                    getActivity().finish();
                                    editText.setText("");
                                }
                            });
                        }
                    });

                }else {
                    HttpUtil.postPostings(url,userphone,content,title,campus_id,institute_id,major_id,0,0,0, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(),"网络不给力啊",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(),"发帖成功",Toast.LENGTH_SHORT).show();
                                    editText.setText("");
                                    startActivity(new Intent(getActivity(), UIAty.class));
                                    getActivity().finish();
                                }
                            });
                        }
                    });
                }
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        progressDialog.dismiss();
    }
}
