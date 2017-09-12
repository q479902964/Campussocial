package com.example.huwei.campussocial.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.huwei.campussocial.R;

public class Personalaccount extends AppCompatActivity {
private TextView tv_nickname;
    private TextView tv_realname;
    private TextView tv_campus;
    private TextView tv_institute;
    private TextView tv_major;
    private TextView tv_grade;
    private TextView tv_identity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalaccount);
        Intent intent = this.getIntent();
        String nickname = intent.getStringExtra("nickname");
        String realname = intent.getStringExtra("realname");
        String grade = intent.getStringExtra("grade");
        String identity = intent.getStringExtra("identify");
        int campus_id = intent.getIntExtra("campus_id",0);
        int institute_id = intent.getIntExtra("institute_id",0);
        int major_id = intent.getIntExtra("major_id",0);
        tv_campus = (TextView) findViewById(R.id.tv_campus);
        tv_institute = (TextView) findViewById(R.id.tv_institute);
        tv_major = (TextView) findViewById(R.id.tv_major);
        tv_grade = (TextView) findViewById(R.id.tv_grade);
        tv_identity = (TextView) findViewById(R.id.tv_identity);
        tv_realname = (TextView) findViewById(R.id.tv_realname);
        tv_nickname = (TextView) findViewById(R.id.tv_nickname);
        switch (campus_id) {
            case 0:
                tv_campus.setText("广东外语外贸大学");
                break;
            case 1:
                tv_campus.setText("广东工业大学");
                break;
            case 2:
                tv_campus.setText("华南理工大学");
                break;
            default:
                tv_campus.setText("");
                break;
        }
        switch (institute_id) {
            case 0:
                tv_institute.setText("信息学院");
                break;
            case 1:
                tv_institute.setText("艺术学院");
                break;
            case 2:
                tv_institute.setText("经贸学院") ;
                break;
            default:
                tv_campus.setText("");
                break;
        }
        switch (major_id) {
            case 0:
                tv_major.setText("软件工程") ;
                break;
            case 1:
                tv_major.setText("计算机科学与技术学院");
                break;
            case 2:
                tv_major.setText("网络工程");
                break;
            case 3:
                tv_major.setText("电子商务");
                break;
            case 4:
                tv_major.setText("信息管理");
                break;
            default:
                tv_campus.setText("");
                break;
        }
        tv_nickname.setText(nickname);
        tv_realname.setText(realname);
        tv_grade.setText(grade);
        tv_identity.setText(identity);
    }
}
