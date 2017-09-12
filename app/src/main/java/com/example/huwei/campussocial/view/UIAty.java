package com.example.huwei.campussocial.view;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.example.huwei.campussocial.Fragment.AssistantFragment;
import com.example.huwei.campussocial.Fragment.FriendFragment;
import com.example.huwei.campussocial.Fragment.MeFragment;
import com.example.huwei.campussocial.Fragment.SchoolFragment;
import com.example.huwei.campussocial.R;
import com.example.huwei.campussocial.Util.AutoUpdateService;
import com.example.huwei.campussocial.Util.DataCleanManager;
import com.example.huwei.campussocial.Util.HttpUtil;
import com.example.huwei.campussocial.Util.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UIAty extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
private TabLayout mTabLayout;
private ViewPager mViewPager;
private ImageButton imageButton;
private String[] mTitles = {"校 园","消 息","助 手","我 的"};
private int [] tabIcons = {R.drawable.campus,R.drawable.fri,R.drawable.set,R.drawable.me};
private int [] selecttabIcons = {R.drawable.selectcampus,R.drawable.selectfri,R.drawable.selectset,R.drawable.selectme};
private List<android.support.v4.app.Fragment> list;
private final static String url ="http://192.168.165.18:8088/Campus/index.php/Academic_exchange/Index/getUserInformation";
private PagerAdapter pagerAdapter;
private DrawerLayout mDrawerLayout;
    private TextView tv_nickname;
    private TextView tv_campus;
    private TextView tv_title;
    private String userphone;
    private LayoutInflater mInflater;
    private Button bt_choose;
    private ImageView UserIcon;
    private String[] items_list = {"拍摄","从相册中选择"};
    public static final int TAKE_PHOTO_ICON=1;
    public static final int CHOOSE_PHOTO_ICON=2;
    public static final int CHOOSE_PHOTO_SKIN=3;
    private Uri imageUri;
    private LinearLayout headerlayout;
    private View mNightView = null;
    private WindowManager mWindowManager = null;
    private Boolean isnight;
    private String realname;
    private String nickname;
    private String major;
    private String grade;
    private String institute;
    private String identify;
    private int campus_id;
    private int institute_id;
    private int major_id ;
    private  String iconStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uiaty);
        Intent intent = getIntent();
        userphone = intent.getStringExtra("userphone");
        if(userphone==null){
            userphone = " ";
            Toast.makeText(UIAty.this,"现在以游客的身份登录",Toast.LENGTH_SHORT).show();
            Log.i("userphone","empty");
        }
        Intent intent1 = new Intent(this, AutoUpdateService.class);
        intent1.putExtra("userphone","123");
        startService(intent1);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tv_title = (TextView) findViewById(R.id.title_tv);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.draw_layout);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        //初始化侧滑菜单用户信息
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navView.getHeaderView(0);
        tv_nickname = (TextView) headerView.findViewById(R.id.tv_nickname);
        tv_campus = (TextView) headerView.findViewById(R.id.tv_campus);
        UserIcon = (ImageView) headerView.findViewById(R.id.icon_image);
        headerlayout = (LinearLayout) headerView.findViewById(R.id.head_layout);
//        bt_choose = (Button) findViewById(R.id.bt_choose);
//        bt_choose.setOnClickListener(this);
        //初始化悬浮按钮和点击事件
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.bringToFront();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UIAty.this,ReleaseAty.class);
                intent.putExtra("userphone",userphone);
                startActivity(intent);
            }
        });
        //获取toolbar和设置侧滑菜单打开按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.nab);
        }
//        toolbar.setTitleMarginStart(310);

        toolbar.setTitle("");
        //设置侧滑菜单列表的点击事件
        navView.setCheckedItem(R.id.nav_personalMedal);
        isnight = false;
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_personalMedal:
                        AlertDialog.Builder al = new AlertDialog.Builder(UIAty.this);
                        al.setTitle("个人勋章");
                        al.setView(R.layout.good);
                        al.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                        AlertDialog dialog = al.create();
                        dialog.show();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_personalAccount:
                        mDrawerLayout.closeDrawers();
                        Intent intent1 = new Intent(UIAty.this,Personalaccount.class);
                            intent1.putExtra("realname",realname);
                            intent1.putExtra("nickname",nickname);
                            intent1.putExtra("campus_id",campus_id);
                            intent1.putExtra("institute_id",institute_id);
                            intent1.putExtra("major_id",realname);
                            intent1.putExtra("grade",grade);
                            intent1.putExtra("identify",identify);
                            startActivity(intent1);
                        break;
                    case R.id.nav_iconSetting:
                        showDialog();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_identitySetting:
                        Intent intent = new Intent(UIAty.this,PersonalaccountReset.class);
                        startActivity(intent);
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_skinSetting:
                        Intent i = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, CHOOSE_PHOTO_SKIN);
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_exit:
                        DataCleanManager.cleanApplicationData(UIAty.this);
                        startActivity(new Intent(UIAty.this,LoginAty.class));
                        UIAty.this.finish();
                    case R.id.nav_night:
                        if(isnight){
                            mWindowManager.removeView(mNightView);
                            isnight = false;
                        }else {
                            night();
                            isnight = true;
                        }
                        mDrawerLayout.closeDrawers();
                }
                return true;
            }
        });
        setText();
        //加载fragment
        list = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putString("userphone",userphone);
        bundle.putString("photo",iconStr);
        bundle.putString("nick_name",nickname);
        Fragment mtab01 = new SchoolFragment();
        Fragment mtab02 = new FriendFragment();
        Fragment mtab03 = new AssistantFragment();
        Fragment mtab04 = new MeFragment();
        mtab01.setArguments(bundle);
        mtab02.setArguments(bundle);
        mtab03.setArguments(bundle);
        mtab04.setArguments(bundle);
        list.add(mtab01);
        list.add(mtab02);
        list.add(mtab03);
        list.add(mtab04);
        //初始化设置TabLayout和Viewpager
        for(int i= 0;i<4;i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(mTitles[i]).setIcon(tabIcons[i]));
        }
         mTabLayout.getTabAt(0).setText(mTitles[0]).setIcon(selecttabIcons[0]);
        mTabLayout.setTabTextColors(Color.parseColor("#000000"),(Color.parseColor("#FF0000")));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                tv_title.setText(mTitles[tab.getPosition()]);
                mTabLayout.setTabTextColors(Color.parseColor("#000000"),(Color.parseColor("#FF0000")));
                mTabLayout.getTabAt(tab.getPosition()).setText(mTitles[tab.getPosition()]).setIcon(selecttabIcons[tab.getPosition()]);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                mTabLayout.getTabAt(tab.getPosition()).setText(mTitles[tab.getPosition()]).setIcon(tabIcons[tab.getPosition()]);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        };
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
    }

    private void night() {
        mNightView = new TextView(this);
        mNightView.setBackgroundColor(0xaa000000);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.TYPE_APPLICATION,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE|WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
        lp.gravity = Gravity.BOTTOM;
        lp.y = 10;
        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        mWindowManager.addView(mNightView,lp);
    }

    //获取用户数据（先在数据库获取，没有就请求服务器获取数据）
public void  setText() {
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    String userData = prefs.getString("userdata",null);
    final ProgressDialog progressDialog = new ProgressDialog(UIAty.this);
    progressDialog.setMessage("Loading...");
    progressDialog.setCancelable(true);
    progressDialog.show();
    if (userData==null) {
        HttpUtil.sendOkhttpRequest(url + "?phone=" + userphone, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Toast.makeText(UIAty.this, "网络不给力", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                progressDialog.dismiss();
                final String re = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(UIAty.this).edit();
                editor.putString("userdata",re);
                editor.apply();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(re);
                                String iconStr = jsonObject.getString("photo");
                                Log.i("icon",iconStr);
                                Glide.with(UIAty.this).load(iconStr).bitmapTransform(new CropCircleTransformation(UIAty.this)).crossFade(1000).into(UserIcon);
                                nickname = jsonObject.getString("nick_name");
                                realname = jsonObject.getString("real_name");
                                campus_id = jsonObject.getInt("campus_id");
                                institute_id = jsonObject.getInt("institute_id");
                                major_id = jsonObject.getInt("major_id");
                                grade = jsonObject.getString("grade");
                                identify =jsonObject.getString("identify");
                                tv_nickname.setText(nickname);
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
                                }
                                switch (institute_id) {
                                    case 0:
                                        institute = "信息学院";
                                        break;
                                    case 1:
                                        institute = "艺术学院";
                                        break;
                                    case 2:
                                        institute = "经贸学院";
                                        break;
                                }
                                switch (major_id) {
                                    case 0:
                                        major = "软件工程";
                                        break;
                                    case 1:
                                        major = "计算机科学与技术学院";
                                        break;
                                    case 2:
                                        major = "网络工程";
                                        break;
                                    case 3:
                                        major = "电子商务";
                                        break;
                                    case 4:
                                        major = "信息管理";
                                        break;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    });
            }
        });
    } else {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(userData);
            iconStr = jsonObject.getString("photo");
            Log.i("iconstr",iconStr);
            Glide.with(UIAty.this).load(iconStr).bitmapTransform(new CropCircleTransformation(UIAty.this)).crossFade(1000).into(UserIcon);
            nickname = jsonObject.getString("nick_name");
            realname = jsonObject.getString("real_name");
            tv_nickname.setText(nickname);
//            tv_grade.setText(jsonObject.getString("grade"));
            identify =jsonObject.getString("identify");
            campus_id = jsonObject.getInt("campus_id");
            institute_id = jsonObject.getInt("institute_id");
            major_id = jsonObject.getInt("major_id");
            grade = jsonObject.getString("grade");
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
            }
            switch (institute_id) {
                case 0:
                    institute = "信息学院";
                    break;
                case 1:
                    institute = "艺术学院";
                    break;
                case 2:
                    institute = "经贸学院";
                    break;
            }
            switch (major_id) {
                case 0:
                    major = "软件工程";
                    break;
                case 1:
                    major = "计算机科学与技术学院";
                    break;
                case 2:
                    major = "网络工程";
                    break;
                case 3:
                    major = "电子商务";
                    break;
                case 4:
                    major = "信息管理";
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }
    @Override
    public void onClick(View view) {
     switch (view.getId()){
         case R.id.bt_choose:
             break;
     }
    }
    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(UIAty.this);
        builder.setItems(items_list, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i){
                    case 0 :Takephoto();
                        break;
                    case 1: if(ContextCompat.checkSelfPermission(UIAty.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(UIAty.this,new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                    }else {
                        Album();
                    }
                        break;
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void Takephoto(){
        File outputImage = new File(getExternalCacheDir(),"output_image.jpg");
        try {
            if(outputImage.exists()){
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(Build.VERSION.SDK_INT>=24){
            imageUri = FileProvider.getUriForFile(UIAty.this,"com.example.huwei.campussocial.fileprovider",outputImage);
        }else{
            imageUri = Uri.fromFile(outputImage);
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,TAKE_PHOTO_ICON);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_PHOTO_ICON:
                if(resultCode==RESULT_OK&&data != null){
                    Bitmap bitmap = null;
                    try {
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
//                        picture.setImageBitmap(bitmap);
                    //    Glide.with(this).load(imageUri).animate(R.anim.scale).into(picture);
                    //高斯模糊:“23”：设置模糊度(在0.0到25.0之间)，默认”25";"4":图片缩放比例,默认“1”
//                    Glide.with(this).load(imageUri).crossFade(1000).animate(R.anim.alpha).bitmapTransform(new BlurTransformation(this,23,1)).into(UserIcon);
                    File file = Environment.getExternalStorageDirectory();
                    File fileAbs = new File(file,"updateIcon.jpg");
                    String filename = fileAbs.getAbsolutePath();
                    final ProgressDialog progressDialog = new ProgressDialog(UIAty.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.setCancelable(true);
                    progressDialog.show();
                    HttpUtil.updateIcon("http://192.168.165.18:8088/Campus/index.php/Academic_exchange/Index/modifyPhoto",userphone, filename, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                    Toast.makeText(UIAty.this, "网络不给力", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                         runOnUiThread(new Runnable() {
                             @Override
                             public void run() {
                                 progressDialog.dismiss();
                                 Toast.makeText(UIAty.this, "修改头像成功", Toast.LENGTH_SHORT).show();
                                 Glide.with(UIAty.this).load(imageUri).bitmapTransform(new CropCircleTransformation(UIAty.this)).crossFade(1000).into(UserIcon);
                             }
                         });
                        }
                    });
                }
                break;
            case CHOOSE_PHOTO_ICON:
                if(resultCode==RESULT_OK&&data != null){
                    if(Build.VERSION.SDK_INT>=19){
                        handleImageOnKitKat(data);
                    }else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            case CHOOSE_PHOTO_SKIN:
                if(resultCode==RESULT_OK&&data!=null) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                    BitmapDrawable bd = new BitmapDrawable(bitmap);
                    headerlayout.setBackground(bd);
                }
            default:break;
        }
    }
    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        displayImage(imagePath);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void handleImageOnKitKat(Intent data) {
        String imagePath=null;
        Uri uri = data.getData();
        if(DocumentsContract.isDocumentUri(this,uri)){
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content：//downloads/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);}
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            imagePath = getImagePath(uri,null);
        }else if("file".equalsIgnoreCase(uri.getScheme())){
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }
    private String getImagePath(Uri uri,String selection){
        String path = null;
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
    private void displayImage(final String imagePath){
        if(imagePath!=null){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            final ProgressDialog progressDialog = new ProgressDialog(UIAty.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(true);
            progressDialog.show();
            HttpUtil.updateIcon("http://192.168.165.18:8088/Campus/index.php/Academic_exchange/Index/modifyPhoto",userphone, imagePath, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            Toast.makeText(UIAty.this, "网络不给力", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            Toast.makeText(UIAty.this, "修改头像成功", Toast.LENGTH_SHORT).show();
                            Glide.with(UIAty.this).load(imagePath).bitmapTransform(new CropCircleTransformation(UIAty.this)).crossFade(1000).into(UserIcon);
                        }
                    });
                }
            });
        }
        //高斯模糊
//            Glide.with(this).load(imagePath).crossFade(1000)
//                    .bitmapTransform(new BlurTransformation(this,23,4))
//                    .listener(new RequestListener<String, GlideDrawable>() {
//                @Override
//                public boolean onException(Exception e, String s, Target<GlideDrawable> target, boolean b) {
//                    Toast.makeText(MainActivity.this,"error",Toast.LENGTH_LONG).show();
//                    return false;
//                }
//
//                @Override
//                public boolean onResourceReady(GlideDrawable glideDrawable, String s, Target<GlideDrawable> target, boolean b, boolean b1) {
//                    Toast.makeText(MainActivity.this,"success",Toast.LENGTH_LONG).show();
//                    return false;
//                }
//            }).into(picture);
        else {
            Toast.makeText(this,"failed to get image",Toast.LENGTH_LONG).show();
        }
    }

    public void Album(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO_ICON);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Album();
                }else {
                    Toast.makeText(this,"You denied the permission",Toast.LENGTH_LONG).show();
                }break;
            default:break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}

