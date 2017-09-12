package com.example.huwei.campussocial.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.huwei.campussocial.R;
import com.example.huwei.campussocial.Util.HttpUtil;
import com.example.huwei.campussocial.bean.LocalUser;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PersonalaccountReset extends AppCompatActivity implements View.OnClickListener {
    private EditText Et_realname;
    private EditText Et_identity;
    private EditText Et_nickname;
    private Spinner spinner_campus;
    private Spinner  spinner_institutes;
    private Spinner  spinner_major;
    private EditText Et_grade;
    private Button bt1;
    private Button bt2;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalaccountreset);
        Et_realname = (EditText) findViewById(R.id.Et_realname);
        Et_nickname = (EditText) findViewById(R.id.Et_nickname);
        Et_identity = (EditText) findViewById(R.id.Et_identity);
        Et_grade = (EditText) findViewById(R.id.Et_grade);
        spinner_major = (Spinner) findViewById(R.id.spinner_major);
        spinner_campus = (Spinner) findViewById(R.id.spinner_campus);
        spinner_institutes = (Spinner) findViewById(R.id.spinner_institutes);
        bt1 = (Button) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt1:
                LocalUser localUser =new LocalUser();
                if(TextUtils.isEmpty(Et_realname.getText().toString().trim())){
                    Toast.makeText(PersonalaccountReset.this,"实名不可以为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(Et_nickname.getText().toString().trim())){
                    Toast.makeText(PersonalaccountReset.this,"昵称不可以为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(Et_identity.getText().toString().trim())){
                    Toast.makeText(PersonalaccountReset.this,"身份不可以为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(Et_grade.getText().toString().trim())){
                    Toast.makeText(PersonalaccountReset.this,"年级不可以为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                localUser.setCampus_id(spinner_campus.getSelectedItemPosition());
                localUser.setInstitute_id(spinner_institutes.getSelectedItemPosition());
                localUser.setMajor_id(spinner_major.getSelectedItemPosition());
                localUser.setCampus_id(spinner_campus.getSelectedItemPosition());
                localUser.setRealName(Et_realname.getText().toString().trim());
                localUser.setNickName(Et_nickname.getText().toString().trim());
                localUser.setIdentity(Et_identity.getText().toString().trim());
                localUser.setGrade(Et_grade.getText().toString().trim());
                String address = "http://192.168.165.24:8088/Test/test.php";
                progressDialog = new ProgressDialog(PersonalaccountReset.this);
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(true);
                progressDialog.show();
                HttpUtil.updateUserData(address, localUser, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                Toast.makeText(PersonalaccountReset.this,"网络不给力啊",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                Toast.makeText(PersonalaccountReset.this,"修改个人信息成功",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(PersonalaccountReset.this,UIAty.class));
                            }
                        });
                    }
                });
                break;
            case R.id.bt2:
                startActivity(new Intent(PersonalaccountReset.this,UIAty.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog!=null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
}
