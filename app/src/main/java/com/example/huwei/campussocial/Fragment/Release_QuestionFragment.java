package com.example.huwei.campussocial.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class Release_QuestionFragment extends Fragment {
    private Button bt_question;
    private EditText editText;
    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private EditText et_reward;
    private Spinner spinner_campus;
    private Spinner  spinner_institutes;
    private Spinner  spinner_major;
    private EditText et_title;
    private int campus_id;
    private int institute_id;
    private int major_id;
    private ProgressDialog progressDialog;
    private final static String url ="http://192.168.165.18:8088/Campus/index.php/Academic_exchange/Index/post";
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.release_question,container,false);
        bt_question = (Button) view.findViewById(R.id.bt_question);
        editText = (EditText) view.findViewById(R.id.et_content);
        checkBox1= (CheckBox) view.findViewById(R.id.checkbox1);
        checkBox2= (CheckBox) view.findViewById(R.id.checkbox2);
        et_reward = (EditText) view.findViewById(R.id.et_reward);
        spinner_major = (Spinner) view.findViewById(R.id.spinner_major);
        spinner_campus = (Spinner)view. findViewById(R.id.spinner_campus);
        spinner_institutes = (Spinner)view.findViewById(R.id.spinner_institutes);
        et_title = (EditText) view.findViewById(R.id.et_title);
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
        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox2.isChecked()){
                    et_reward.setVisibility(View.VISIBLE);
                }else {
                    et_reward.setVisibility(View.GONE);
                }
            }
        });
        final String userphone = getArguments().getString("userphone");
//        Log.i("userphone",userphone);
        bt_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean check1 = checkBox1.isClickable();
                boolean check2 = checkBox2.isClickable();
                String content = editText.getText().toString().trim();
                String title = et_title.getText().toString();
                if(check1){
                    progressDialog.setMessage("Loading...");
                    progressDialog.setCancelable(true);
                    progressDialog.show();
                    HttpUtil.postPostings(url,userphone,content,title,campus_id,institute_id,major_id,1,0,1,new Callback() {
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
                        int money= Integer.parseInt(et_reward.getText().toString());

                    if(check2){
                        HttpUtil.postPostings(url,userphone,content,title,campus_id,institute_id,major_id,0,money,1 ,new Callback() {
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
                    }else {
                        HttpUtil.postPostings(url,userphone,content,title,campus_id,institute_id,major_id,0,0,1, new Callback() {
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
