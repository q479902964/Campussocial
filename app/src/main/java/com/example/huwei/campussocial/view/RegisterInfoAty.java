package com.example.huwei.campussocial.view;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Contacts;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.huwei.campussocial.R;
import com.example.huwei.campussocial.SQL.UserDataManager;
import com.example.huwei.campussocial.Util.HttpUtil;
import com.example.huwei.campussocial.bean.LocalUser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegisterInfoAty extends AppCompatActivity implements View.OnClickListener{
    private EditText Et_realname;
    private EditText Et_identity;
    private EditText Et_nickname;
    private Spinner  spinner_campus;
    private Spinner  spinner_institutes;
    private Spinner  spinner_major;
    private EditText Et_grade;
    private Button bt1;
    private Button bt2;
    private Button bt_updateicon;
    private ImageView UserIcon;
    private AlertDialog alertDialog;
    private final static String url ="http://192.168.165.18:8088/Campus/index.php/Academic_exchange/Index/userInformationRegistration";
    private String[] items_list = {"拍摄","从相册中选择"};
    private Uri imageUri;
    private LocalUser localUser = new LocalUser();
        public static final int TAKE_PHOTO=1;
    public static final int CHOOSE_PHOTO=2;
    private int campus_id;
    private int institude_id;
    private int major_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_info_aty);
        Et_realname = (EditText) findViewById(R.id.Et_realname);
        Et_nickname = (EditText) findViewById(R.id.Et_nickname);
        Et_identity = (EditText) findViewById(R.id.Et_identity);
        Et_grade = (EditText) findViewById(R.id.Et_grade);
        spinner_major = (Spinner) findViewById(R.id.spinner_major);
        spinner_campus = (Spinner) findViewById(R.id.spinner_campus);
        spinner_institutes = (Spinner) findViewById(R.id.spinner_institutes);
        bt1 = (Button) findViewById(R.id.bt1);
        bt_updateicon = (Button) findViewById(R.id.bt_updateicon);
        UserIcon = (ImageView) findViewById(R.id.iv_usericon);
        bt_updateicon.setOnClickListener(this);
        bt1.setOnClickListener(this);
        Intent  intent = getIntent();
        localUser = (LocalUser) intent.getSerializableExtra("user");
        spinner_campus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0: localUser.setCampus_id(0);
                        campus_id = 0;
                        break;
                    case 1: localUser.setCampus_id(2);
                        campus_id = 1;
                        break;
                    case 2: localUser.setCampus_id(1);
                        campus_id = 2;
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
                    case 0: localUser.setCampus_id(0);
                        institude_id = 0;
                        break;
                    case 1: localUser.setCampus_id(1);
                        institude_id= 1;
                        break;
                    case 2: localUser.setCampus_id(2);
                        institude_id= 2;
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
                    case 0: localUser.setMajor_id(0);
                        major_id=0;
                        break;
                    case 1: localUser.setMajor_id(1);
                        major_id=1;
                        break;
                    case 2: localUser.setMajor_id(2);
                        major_id=2;
                        break;
                    case 3: localUser.setMajor_id(3);
                        major_id=3;
                        break;
                    case 4: localUser.setMajor_id(4);
                        major_id=4;
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt1:
                if(TextUtils.isEmpty(Et_realname.getText().toString().trim())){
                    Toast.makeText(RegisterInfoAty.this,"实名不可以为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(Et_nickname.getText().toString().trim())){
                    Toast.makeText(RegisterInfoAty.this,"昵称不可以为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(Et_identity.getText().toString().trim())){
                    Toast.makeText(RegisterInfoAty.this,"身份不可以为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(Et_grade.getText().toString().trim())){
                    Toast.makeText(RegisterInfoAty.this,"年级不可以为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                localUser.setCampus_id(campus_id);
                localUser.setInstitute_id(institude_id);
                localUser.setMajor_id(major_id);
                localUser.setRealName(Et_realname.getText().toString().trim());
                localUser.setNickName(Et_nickname.getText().toString().trim());
                localUser.setIdentity(Et_identity.getText().toString().trim());
                localUser.setGrade(Et_grade.getText().toString().trim());
                final String phonenum =localUser.getUserPhone();
                final ProgressDialog progressDialog = new ProgressDialog(RegisterInfoAty.this);
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(true);
                progressDialog.show();
                HttpUtil.updateUserData(url, localUser, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                Toast.makeText(RegisterInfoAty.this,"网络不给力啊",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String re = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                Toast.makeText(RegisterInfoAty.this,"注册成功",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterInfoAty.this, LoginAty.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                });
                break;
            case R.id.bt_updateicon:
                showDialog();
                break;
        }
    }
    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterInfoAty.this);
        builder.setItems(items_list, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i){
                    case 0 :Takephoto();
                        break;
                    case 1: if(ContextCompat.checkSelfPermission(RegisterInfoAty.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(RegisterInfoAty.this,new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
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
            imageUri = FileProvider.getUriForFile(RegisterInfoAty.this,"com.example.huwei.campussocial.fileprovider",outputImage);
        }else{
            imageUri = Uri.fromFile(outputImage);
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,TAKE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                if(resultCode==RESULT_OK){
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
                    Glide.with(this).load(imageUri).bitmapTransform(new CropCircleTransformation(this)).crossFade(1000).into(UserIcon);
                    File file = Environment.getExternalStorageDirectory();
                    File fileAbs = new File(file,"output_image.jpg");
                    String filename = fileAbs.getAbsolutePath();
                    localUser.setUserIcon(filename);
                }
                break;
            case CHOOSE_PHOTO:
                if(resultCode==RESULT_OK){
                    if(Build.VERSION.SDK_INT>=19){
                        handleImageOnKitKat(data);
                    }else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
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
    private void displayImage(String imagePath){
        if(imagePath!=null){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
//            picture.setImageBitmap(bitmap);
//            Glide.with(this).load(imagePath).animate(R.anim.alpha).listener(new RequestListener<String, GlideDrawable>() {
//                @Override
//                public boolean onException(Exception e, String s, Target<GlideDrawable> target, boolean b) {
//                    Toast.makeText(UserUIAty.this,"error",Toast.LENGTH_LONG).show();
//                    return false;
//                }
//
//                @Override
//                public boolean onResourceReady(GlideDrawable glideDrawable, String s, Target<GlideDrawable> target, boolean b, boolean b1) {
//                    Toast.makeText(UserUIAty.this,"success",Toast.LENGTH_LONG).show();
//                    return false;
//                }
//            }).into(UserIcon);
            Glide.with(this).load(imagePath).bitmapTransform(new CropCircleTransformation(this)).crossFade(1000).into(UserIcon);
            localUser.setUserIcon(imagePath);
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
        startActivityForResult(intent,CHOOSE_PHOTO);
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
}
