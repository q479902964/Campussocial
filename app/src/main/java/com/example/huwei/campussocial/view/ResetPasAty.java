package com.example.huwei.campussocial.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.huwei.campussocial.R;
import com.example.huwei.campussocial.SQL.UserDataManager;
import com.example.huwei.campussocial.Util.HttpUtil;
import com.example.huwei.campussocial.bean.LocalUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ResetPasAty extends AppCompatActivity {
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    private Button button1;
    private Button button2;
    private final static String url ="http://192.168.165.18:8088/Campus/index.php/Academic_exchange/Index/modifyPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pas);
        editText1 = (EditText) findViewById(R.id.Et1);
        editText2 = (EditText) findViewById(R.id.Et2);
        editText3 = (EditText) findViewById(R.id.Et3);
        editText4 = (EditText) findViewById(R.id.Et4);
        button1 = (Button) findViewById(R.id.bt1);
        button2 = (Button) findViewById(R.id.bt2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResetPasAty.this,LoginAty.class));
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rsetpas();
            }
        });
    }

    private void rsetpas() {
        final String name = editText1.getText().toString().trim();
        String oldpassword = editText2.getText().toString().trim();
        final String newpassword = editText3.getText().toString().trim();
        final String newcheckpassword = editText4.getText().toString().trim();
        if(TextUtils.isEmpty(name)){
            Toast.makeText(ResetPasAty.this,"用户名不可以为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(oldpassword)){
            Toast.makeText(ResetPasAty.this,"旧密码不可以为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(newpassword)){
            Toast.makeText(ResetPasAty.this,"新密码不可以为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(newpassword.equals(newcheckpassword)==false){
            Toast.makeText(ResetPasAty.this,"两次密码输入不相同",Toast.LENGTH_SHORT).show();
            return;
        }
        Log.i("phone",name+"/"+oldpassword+"/"+newpassword);
        final ProgressDialog progressDialog = new ProgressDialog(ResetPasAty.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
        progressDialog.show();
          HttpUtil.sendOkhttpRequest(url + "?phone=" + name + "&password=" + oldpassword + "&new_password=" + newpassword, new Callback() {
              @Override
              public void onFailure(Call call, IOException e) {
                 runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         progressDialog.dismiss();
                         Toast.makeText(ResetPasAty.this, "网络不给力", Toast.LENGTH_SHORT).show();
                     }
                 });
              }
              @Override
              public void onResponse(Call call, Response response) throws IOException {
                  final String re = response.body().string();
                  try {
                      JSONObject jsonObject = new JSONObject(re);
                      final int num  = jsonObject.getInt("feedback");
                      Log.i("num", String.valueOf(num));
                      runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              progressDialog.dismiss();
                              switch (num){
                                  case -1:
                                      Toast.makeText(ResetPasAty.this,"该用户不存在",Toast.LENGTH_SHORT).show();
                                      break;
                                  case 0:
                                      Toast.makeText(ResetPasAty.this,"原密码错误",Toast.LENGTH_SHORT).show();
                                      break;
                                  case 1:
                                          Toast.makeText(ResetPasAty.this, "修改成功",Toast.LENGTH_SHORT).show();
                                          Intent intent_Register_to_Login = new Intent(ResetPasAty.this,LoginAty.class) ;    //切换User Activity至Login Activity
                                          startActivity(intent_Register_to_Login);
                                          finish();
                              }
                          }
                      });
                  } catch (JSONException e) {
                      e.printStackTrace();
                  }

              }
          });
        }
}
