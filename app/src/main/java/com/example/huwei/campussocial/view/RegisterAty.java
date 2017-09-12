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
import com.example.huwei.campussocial.Util.HttpUtil;
import com.example.huwei.campussocial.bean.LocalUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegisterAty extends AppCompatActivity implements View.OnClickListener{
    private EditText editText2;
    private EditText editText3;
    private Button button1;
    private Button button2;
    private String phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editText2 = (EditText) findViewById(R.id.Et2);
        editText3 = (EditText) findViewById(R.id.Et3);
        button1 = (Button) findViewById(R.id.bt1);
        button2 = (Button) findViewById(R.id.bt2);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        RegisterPage registerPage = new RegisterPage();
        //回调函数
        registerPage.setRegisterCallback(new EventHandler()
        {
            public void afterEvent(int event, int result, Object data)
            {
                // 解析结果
                if (result == SMSSDK.RESULT_COMPLETE)
                {   HashMap<String,Object> maps = (HashMap<String, Object>) data;
                    String country = (String) maps.get("country");
                    String UserPhone = (String) maps.get("phone");
                    phoneNumber = UserPhone;
                    //提交验证码成功
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE)
                    {
                    }
                    //提交验证码成功，此时已经验证成功了
                    else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE)
                    {
                    }
                }

            }
        });
        registerPage.show(RegisterAty.this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt1:
                register();
                break;
            case R.id.bt2:
                Intent intent_Register_to_Login = new Intent(RegisterAty.this,LoginAty.class) ;
                startActivity(intent_Register_to_Login);
                finish();
                break;
            default:
                break;
        }
    }
    public void register(){
        final String password = editText2.getText().toString().trim();
        String Checkpassword = editText3.getText().toString().trim();
        if(TextUtils.isEmpty(password)){
            Toast.makeText(RegisterAty.this,"密码不可以为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(Checkpassword)){
            Toast.makeText(RegisterAty.this,"再次输入的密码不可以为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.equals(Checkpassword)==false){
            Toast.makeText(RegisterAty.this,"两次密码输入不正确",Toast.LENGTH_SHORT).show();
            return;
        }
        final ProgressDialog progressDialog = new ProgressDialog(RegisterAty.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
        progressDialog.show();
        HttpUtil.sendOkhttpRequest("http://192.168.165.18:8088/Campus/index.php/Academic_exchange/Index/ExitPhone"+ "?phone="+phoneNumber, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterAty.this,"网络不给力啊",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        String re = null;
                        try {
                            Log.i("url","http://192.168.165.18:8088/Campus/index.php/Academic_exchange/Index/userInformationRegistration"+ "?phone="+phoneNumber);
                            re = response.body().string();
                            Log.i("response", re);
                            JSONObject jsonObject = new JSONObject(re);
                            int num = jsonObject.getInt("feedback");
                            if(num==-1){
                                Toast.makeText(RegisterAty.this,"该用户已注册,返回码为"+num,Toast.LENGTH_SHORT).show();
                            }else {
                                final LocalUser mLocalUser = new LocalUser();
                                mLocalUser.setUserPassword(password);
                                mLocalUser.setUserPhone(phoneNumber);
                                Intent intent = new Intent(RegisterAty.this, RegisterInfoAty.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("user", mLocalUser);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });

            }
        });
    }


}
