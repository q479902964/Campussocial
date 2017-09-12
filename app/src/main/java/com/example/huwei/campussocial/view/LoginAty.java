package com.example.huwei.campussocial.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.huwei.campussocial.App;
import com.example.huwei.campussocial.MainActivity;
import com.example.huwei.campussocial.R;
import com.example.huwei.campussocial.SQL.UserDataManager;
import com.example.huwei.campussocial.Util.HttpUtil;
import com.example.huwei.campussocial.bean.LocalUser;
import com.example.huwei.campussocial.bean.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginAty extends AppCompatActivity implements View.OnClickListener{
private EditText editText1;
private EditText editText2;
private Button bt1;
private Button bt2;
private Button bt3;
private Button bt4;
    private final static String url ="http://192.168.165.18:8088/Campus/index.php/Academic_exchange/Index/validate";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_aty);
        editText1 = (EditText) findViewById(R.id.Et1);
        editText2 = (EditText) findViewById(R.id.Et2);
        bt1 = (Button) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);
        bt3 = (Button) findViewById(R.id.bt_reset);
        bt4 = (Button) findViewById(R.id.bt_vistor);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt1:
                login();
                break;
            case R.id.bt2:
                startActivity(new Intent(LoginAty.this,RegisterAty.class));
                finish();
                break;
            case R.id.bt_reset:
                Intent intent = new Intent(LoginAty.this,ResetPasAty.class);
                startActivity(intent);
                finish();
                break;
            case R.id.bt_vistor:
                Intent intent1  = new Intent(LoginAty.this,UIAty.class);
                startActivity(intent1);
                finish();
            default:
                break;
        }
    }

   public void login(){
       final String phone = editText1.getText().toString().trim();
       String password = editText2.getText().toString().trim();
       if(TextUtils.isEmpty(phone)){
          Toast.makeText(LoginAty.this,"手机号不可以为空",Toast.LENGTH_SHORT).show();
           return;
       }
       if(TextUtils.isEmpty(editText1.getText().toString().trim())){
           Toast.makeText(LoginAty.this,"密码不可以为空",Toast.LENGTH_SHORT).show();
           return;
       }
       final ProgressDialog progressDialog = new ProgressDialog(LoginAty.this);
       progressDialog.setMessage("Loading...");
       progressDialog.setCancelable(true);
       progressDialog.show();
       HttpUtil.sendOkhttpRequest(url +"?phone="+phone + "&password="+password, new Callback() {
           @Override
           public void onFailure(Call call, IOException e) {
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       progressDialog.dismiss();
                       Toast.makeText(LoginAty.this, "网络不给力", Toast.LENGTH_SHORT).show();
                   }
               });
           }
           @Override
           public void onResponse(Call call, Response response) throws IOException {
           progressDialog.dismiss();
           final String re = response.body().string();
           runOnUiThread(new Runnable() {
               @Override
               public void run() {
                   try {
                       JSONObject jsonObject = new JSONObject(re);
                       int num =jsonObject.getInt("feedback");
                       if(num==-1){
                           Toast.makeText(LoginAty.this,"该用户不存在",Toast.LENGTH_SHORT).show();
                       }else {
                           SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(LoginAty.this);
                           SharedPreferences.Editor editor =sp.edit();
                           editor.putString("userphone",phone);
                           editor.putBoolean("login",true);
                           editor.apply();
                           Intent intent = new Intent(LoginAty.this, UIAty.class);
                           intent.putExtra("userphone", phone);
                           startActivity(intent);
                           if(phone=="13611484297"){
                               connect("Fq5D4YZerdVAk6mihy9sGSMEcxJztQGOq1G82o69++BWpTXY0QR2XwKkXnW/k5/eHc+p2gutLP1pZtW1eAPlBmdq7QEGfhzT");
                               Toast.makeText(LoginAty.this,"connect"+phone,Toast.LENGTH_SHORT).show();
                           }else{
                               connect("0AJsg/RVErNZSw5ySm7+0OE5wjGb2hT//QEmkmz5BCDFfAlUqErTSVIkYstd6SFEp/yNd4MotgpJyF3KQ64e+RzS9qxQA60C");
                               Toast.makeText(LoginAty.this,"connect"+phone,Toast.LENGTH_SHORT).show();
                           }
                           finish();
                       }
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
               }
           });
           }
       });
   }
    private void connect(String token) {

        if (getApplicationInfo().packageName.equals(App.getCurProcessName(getApplicationContext()))) {

            /**
             * IMKit SDK调用第二步,建立与服务器的连接
             */
            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
                 */
                @Override
                public void onTokenIncorrect() {

                    Log.d("LoginActivity", "--onTokenIncorrect");
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token
                 */
                @Override
                public void onSuccess(String userid) {
//                    Toast.makeText(getApplicationContext(), "connect server success 10086", Toast.LENGTH_SHORT).show();
                    Log.d("LoginActivity", "--onSuccess" + userid);
                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 *                  http://www.rongcloud.cn/docs/android.html#常见错误码
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                    Log.d("LoginActivity", "--onError" + errorCode);
                }
            });
        }
    }
}
