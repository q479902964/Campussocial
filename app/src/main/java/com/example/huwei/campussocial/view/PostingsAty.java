package com.example.huwei.campussocial.view;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.huwei.campussocial.Adapter.CommentAdapter;
import com.example.huwei.campussocial.Adapter.PostingsAdapter;
import com.example.huwei.campussocial.R;
import com.example.huwei.campussocial.Util.HttpUtil;
import com.example.huwei.campussocial.bean.Comment;
import com.example.huwei.campussocial.bean.Postings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PostingsAty extends AppCompatActivity implements View.OnClickListener{
    private final static String url ="http://192.168.165.18:8088/Campus/index.php/Academic_exchange/Index/getPostById";
    private final static String url1 ="http://192.168.165.18:8088/Campus/index.php/Academic_exchange/Index/AddComment";
    private ImageView UserIcon;
    private TextView UserName;
    private TextView Content;
    private TextView Title;
    private TextView Time;
    private TextView CommentNum;
    private TextView Thumbsup;
    private TextView Thumbsdown;
    private Button bt_comment;
    private Button bt_thumbup;
    private Button bt_thumbdown;
    private Button bt_reward;
    private ImageButton bt_back;
    private LinearLayout linearLayout;
    private RecyclerView recycleView;
    private List<Comment> CommentList = new ArrayList<>();
    private CommentAdapter commentAdapter;
    private  String key;
    private String userphone;
    private String photo;
    private String nick_name;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postings);
        UserIcon = (ImageView) findViewById(R.id.usericon);
        UserName = (TextView) findViewById(R.id.username);
        Content = (TextView) findViewById(R.id.content);
        Title = (TextView) findViewById(R.id.title);
        Time = (TextView) findViewById(R.id.time);
        CommentNum = (TextView)findViewById(R.id.commentnum);
        Thumbsup = (TextView) findViewById(R.id.thumbsup);
        Thumbsdown = (TextView)findViewById(R.id.thumbsdown);
        bt_comment= (Button) findViewById(R.id.bt_comment);
        bt_thumbup = (Button) findViewById(R.id.bt_thumbup);
        bt_thumbdown= (Button) findViewById(R.id.bt_thumbdown);
        bt_reward= (Button) findViewById(R.id.bt_reward);
        bt_back = (ImageButton) findViewById(R.id.bt_back);
        linearLayout = (LinearLayout) findViewById(R.id.activity_postings);
        recycleView = (RecyclerView) findViewById(R.id.lv_comment);
        swipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.swipe_comment);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        bt_comment.setOnClickListener(this);
        bt_thumbup.setOnClickListener(this);
        bt_thumbdown.setOnClickListener(this);
        bt_reward.setOnClickListener(this);
        bt_back.setOnClickListener(this);
        Intent i=getIntent();
        key = i.getStringExtra("key");
        userphone = i.getStringExtra("userphone");
        photo = i.getStringExtra("photo");
        nick_name=i.getStringExtra("nick_name");
        Log.i("url",url+"?post_id="+key);
        view(key);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
            view(key);
            }
        });
    }

    private void view(String id) {
        HttpUtil.sendOkhttpRequest(url+"?post_id="+id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(PostingsAty.this,"网络不给力啊",Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
            final String re = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject data=new JSONObject(re);
                            String userName=data.getString("nick_name");
                            String userIcon=data.getString("photo");
                            String time=data.getString("release_time");
                            String content=data.getString("content");
                            String title = data.getString("title");
                            int commentnum=data.getInt("comment_num");
                            int thumbsup=data.getInt("number_point");
                            int thumbsdown = data.getInt("number_step");
                            JSONArray jsonArray1 = data.getJSONArray("comment_list");
                            for(int j = 0;j<jsonArray1.length();j++){
                                JSONObject jsonObject1 = jsonArray1.getJSONObject(j);
                                String comment_photo=jsonObject1.getString("photo");
                                String comment_nick_name=jsonObject1.getString("nick_name");
                                String comment_release_time = jsonObject1.getString("release_time");
                                String comment_content=jsonObject1.getString("content");
                                Comment comment = new Comment();
                                comment.setUserName(comment_nick_name);
                                comment.setUserIcon(comment_photo);
                                comment.setTime(comment_release_time);
                                comment.setContent(comment_content);
                                CommentList.add(comment);
                                Log.i("comment_content",comment_content);
                            }
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PostingsAty.this);
                            recycleView.setLayoutManager(linearLayoutManager);
                            commentAdapter = new CommentAdapter(CommentList,getApplicationContext());
                            Log.i("commentlist",CommentList.toString());
                            recycleView.setAdapter(commentAdapter);
                            Glide.with(PostingsAty.this).load(userIcon).into(UserIcon);
                            UserName.setText(userName);
                            Content.setText(content);
                            Time.setText(time);
                            CommentNum.setText(String.valueOf(commentnum));
                            Thumbsup.setText(String.valueOf(thumbsup));
                            Thumbsdown.setText(String.valueOf(thumbsdown));
                            Title.setText(title);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_comment:
                View view1 = LayoutInflater.from(this).inflate(R.layout.ed_comment,null);
                Button bt = (Button) view1.findViewById(R.id.bt_posting);
                final EditText editText = (EditText) view1.findViewById(R.id.et);
                AlertDialog.Builder alg = new AlertDialog.Builder(this);
                alg.setView(view1);
                final AlertDialog dialog = alg.create();
                Window window = dialog.getWindow();
                window.setGravity(Gravity.BOTTOM);
                dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                dialog.show();
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                     final String comment1 = editText.getText().toString();
                     HttpUtil.postingcomment(url1,key, comment1,userphone,0, new Callback() {
                         @Override
                         public void onFailure(Call call, IOException e) {
                             runOnUiThread(new Runnable() {
                                 @Override
                                 public void run() {
                                     Toast.makeText(PostingsAty.this,"发布失败",Toast.LENGTH_SHORT).show();
                                 }
                             });
                         }

                         @Override
                         public void onResponse(Call call, Response response) throws IOException {
                          runOnUiThread(new Runnable() {
                              @Override
                              public void run() {
                                  Comment comment = new Comment();
                                  comment.setUserIcon(photo);
                                  comment.setUserName(nick_name);
                                  comment.setContent(comment1);
                                  comment.setTime("1:30");
                                  CommentList.add(comment);
                                  commentAdapter.notifyItemInserted(CommentList.size()-1);
                                  recycleView.scrollToPosition(CommentList.size()-1);
                              }
                          });
                         }
                     });
                     dialog.dismiss();
                    }
                });
                break;
            case R.id.bt_thumbup:
                HttpUtil.sendOkhttpRequest("http://192.168.165.18:8088/Campus/index.php/Academic_exchange/Index/AddPoint?post_id=" + key, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(PostingsAty.this,"网络不给力啊",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                bt_thumbup.setEnabled(false);
                            }
                        });
                    }
                });
                break;
            case R.id.bt_thumbdown:
                HttpUtil.sendOkhttpRequest("http://192.168.165.18:8088/Campus/index.php/Academic_exchange/Index/AddStep?post_id=" + key, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(PostingsAty.this,"网络不给力啊",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                bt_thumbdown.setEnabled(false);
                            }
                        });
                    }
                });
                break;
            case R.id.bt_reward:
                View view2 = LayoutInflater.from(this).inflate(R.layout.ed_comment,null);
                Button bt1 = (Button) view2.findViewById(R.id.bt_posting);
                EditText et = (EditText) view2.findViewById(R.id.et);
                et.setHint("打赏一下嘛大爷");
                bt1.setText("打赏");
                AlertDialog.Builder alg1 = new AlertDialog.Builder(this);
                alg1.setView(view2);
                final AlertDialog dialog1 = alg1.create();
                Window window1 = dialog1.getWindow();
                window1.setGravity(Gravity.BOTTOM);
                dialog1.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                dialog1.show();
                bt1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog1.dismiss();
                    }
                });
                break;
            case R.id.bt_back:
                PostingsAty.this.finish();
                break;
        }
    }
}
