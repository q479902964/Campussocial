package com.example.huwei.campussocial.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.huwei.campussocial.bean.Campus;
import com.example.huwei.campussocial.bean.LocalUser;
import com.example.huwei.campussocial.bean.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HUWEI on 2017/4/23.
 */

public class UserDataManager {
//    private static final String TAG = "UserDataManager";
//    private static final String DB_NAME = "user_data";
//    private static final String TABLE_NAME = "users";
//    private static final String TABLE_NAME1 = "posting";
//    public static final String ID = "_id";
//    public static final String USER_NAME = "user_name";
//    public static final String USER_PWD = "user_pwd";
//    public static final String USER_PHONE = "user_phone";
//    public static final String USER_ICON= "user_icon";
//    public static final String USER_LOCATION = "user_location";
//    public static final String USER_TIME= "user_time";
//    public static final String USER_CONTENT = "user_content";
//    private static final int DB_VERSION = 2;
//    private Context mContext = null;
//
//    //创建用户book表
////    private static final String DB_CREATE = "create table " + TABLE_NAME + " ("
////            + ID + " integer primary key," + USER_NAME + " varchar,"
////            + USER_PWD + " varchar," + USER_PHONE + " varchar" + ");";
//
//    public static final String DB_CREATE = "create table " + TABLE_NAME +
//            "(id integer primary key autoincrement," +
//            "userphone text," +
//            "realname text," +
//            "password text," +
//            "identity text," +
//            "major_id integer," +
//            "grade text," +
//            "usericon text," +
//            "institute_id integer," +
//            "campus_id integer," +
//            "nickname text)";
//    private static final String DB_CREATE1 = "create table " + TABLE_NAME1 + " ("
//            + ID + " integer primary key," + USER_NAME + " varchar,"
//            + USER_ICON + " blob," + USER_LOCATION + " varchar," + USER_CONTENT + " varchar,"+ USER_TIME + " varchar" + ");";
////    public static final String DB_CREATE_Campus = "create table if not exists " + "Campus" +
////            "(id integer primary key autoincrement," +
////            "campusName text," +
////            "campusCode integer)";
////    public static final String DB_CREATE_Institute = "create table if not exists " + "Institute" +
////            "(id integer primary key autoincrement," +
////            "instituteName text," +
////            "instituteCode integer," +
////            "CampusId integer)";
////    public static final String DB_CREATE_Classification = "create table if not exists " + "Classification" +
////            "(id integer primary key autoincrement," +
////            "ClassificationName text," +
////            "weatherId text," +
////            "InstituteId integer)";
//
//    private SQLiteDatabase mSQLiteDatabase = null;
//    private DataBaseManagementHelper mDatabaseHelper = null;
//
//    //DataBaseManagementHelper继承自SQLiteOpenHelper
//    private static class DataBaseManagementHelper extends SQLiteOpenHelper {
//
//        DataBaseManagementHelper(Context context) {
//            super(context, DB_NAME, null, DB_VERSION);
//        }
//
//        @Override
//        public void onCreate(SQLiteDatabase db) {
//            Log.i(TAG,"db.getVersion()="+db.getVersion());
//            db.execSQL(DB_CREATE);
//            db.execSQL(DB_CREATE1);
////            db.execSQL(DB_CREATE_Campus);
////            db.execSQL(DB_CREATE_Institute);
////            db.execSQL(DB_CREATE_Classification);
//            Log.i(TAG, "db.execSQL(DB_CREATE)");
//            Log.i(TAG, "db.execSQL(DB_CREATE1)");
////            Log.i(TAG, "db.execSQL(DB_CREATE_Campus)");
////            Log.i(TAG, "db.execSQL(DB_CREATE_Institute)");
////            Log.i(TAG, "db.execSQL(DB_CREATE_Classification)");
//            Log.e(TAG, DB_CREATE);
//        }
//
//        @Override
//        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            Log.i(TAG, "DataBaseManagementHelper onUpgrade");
//            db.execSQL("drop table if exists" + DB_CREATE);
//            db.execSQL("drop table if exists" + DB_CREATE1);
//            onCreate(db);
//        }
//    }
//
//    public UserDataManager(Context context) {
//        mContext = context;
//        Log.i(TAG, "UserDataManager construction!");
//    }
//    //打开数据库
//    public void openDataBase() throws SQLException {
//       mDatabaseHelper = new DataBaseManagementHelper(mContext);
//        mSQLiteDatabase = mDatabaseHelper.getWritableDatabase();
//    }
//
//    //关闭数据库
//    public void closeDataBase() throws SQLException {
//        mDatabaseHelper.close();
//    }
//    //添加新用户
//    public void  insertUserData(LocalUser localUserData) {
//        String userphone = localUserData.getUserPhone();
//        String realname = localUserData.getRealName();
//        String nickname = localUserData.getNickName();
//        String grade = localUserData.getGrade();
//        String identity = localUserData.getIdentity();
//        String usericon = localUserData.getUserIcon();
//        int campus_id = localUserData.getCampus_id();
//        int institute_id = localUserData.getInstitute_id();
//        int major_id = localUserData.getMajor_id();
//        ContentValues values = new ContentValues();
//        values.put("userphone", userphone);
//        values.put("realname" ,realname);
//        values.put("nickname",nickname);
//        values.put("major",major_id);
//        values.put("grade",grade);
//        values.put("identity",identity);
//        values.put("usericon",usericon);
//        values.put("campus_id",campus_id);
//        values.put("institute_id",institute_id);
//        mSQLiteDatabase.insert(TABLE_NAME, ID, values);
////        return mSQLiteDatabase.insert(TABLE_NAME, ID, values);
//    }
////    public void  insertPostingData(Posting postinginfoData) {
////        String userName= postinginfoData.getUserName();
////        String userIcon= postinginfoData.getUserIcon();
////        String location= postinginfoData.getLocation();
////        String time =postinginfoData.getTime();
////        String content = postinginfoData.getContent();
////        ContentValues values = new ContentValues();
////        values.put(USER_NAME, userName);
////        values.put(USER_ICON, userIcon);
////        values.put(USER_CONTENT,content);
////        values.put(USER_TIME,time);
////        values.put(USER_LOCATION,location);
////        mSQLiteDatabase.insert(TABLE_NAME1, ID, values);
//////        return mSQLiteDatabase.insert(TABLE_NAME, ID, values);
////    }
//    //更新用户信息，如修改密码
//    public boolean updateUserPsw(LocalUser localUserData) {
//        //int id = localUserData.getUserId();
//        String userPhone = localUserData.getUserPhone();
//        String userPwd = localUserData.getUserPassword();
//        ContentValues values = new ContentValues();
////values.put(USER_NAME, userName);
//        values.put(USER_PWD, userPwd);
//        String where = USER_NAME + "=" + "=\"" + userPhone + "\"";
//        return mSQLiteDatabase.update(TABLE_NAME, values, where, null) > 0;
////return mSQLiteDatabase.update(TABLE_NAME, values, ID + "=" + id, null) > 0;
//    }
////    public boolean updateUserIsLogin(String phone) {
////        ContentValues values = new ContentValues();
//////values.put(USER_NAME, userName);
////        values.put("login", 0);
////        String where = "userphone" + "=" + "=\"" + phone + "\"";
////        return mSQLiteDatabase.update(TABLE_NAME, values, where, null) > 0;
//////return mSQLiteDatabase.update(TABLE_NAME, values, ID + "=" + id, null) > 0;
////    }
////    public Cursor fetchlogin(){
////        Cursor mCursor = mSQLiteDatabase.query(false, TABLE_NAME, null, "login"
////                + "=" + 1, null, null, null, null, null);
////        if (mCursor != null) {
////            mCursor.moveToFirst();
////        }
////        return mCursor;
////    }
//    public Cursor fetchUserData(String phone) throws SQLException {
//        Cursor mCursor = mSQLiteDatabase.query(false, TABLE_NAME, null, "userphone"
//                + "=" + phone, null, null, null, null, null);
//        if (mCursor != null) {
//            mCursor.moveToFirst();
//        }
//        return mCursor;
//    }
//    //
//    public Cursor fetchAllUserDatas() {
//        return mSQLiteDatabase.query(TABLE_NAME, null, null, null, null, null,
//                null);
//    }
//    //根据id删除用户
//    public boolean deleteUserData(int id) {
//        return mSQLiteDatabase.delete(TABLE_NAME, ID + "=" + id, null) > 0;
//    }
//    //根据用户名注销
//    public boolean deleteUserDatabyname(String name) {
//        return mSQLiteDatabase.delete(TABLE_NAME, USER_NAME + "=" + name, null) > 0;
//    }
//    //删除所有用户
//    public boolean deleteAllUserDatas() {
//        return mSQLiteDatabase.delete(TABLE_NAME, null, null) > 0;
//    }
//
//    //
//    public String getStringByColumnName(String columnName, String name) {
//        Cursor mCursor = fetchUserData(name);
//        int columnIndex = mCursor.getColumnIndex(columnName);
//        String columnValue = mCursor.getString(columnIndex);
//        mCursor.close();
//        return columnValue;
//    }
//    //
//    public boolean updateUserDataById(String columnName, int id,
//                                      String columnValue) {
//        ContentValues values = new ContentValues();
//        values.put(columnName, columnValue);
//        return mSQLiteDatabase.update(TABLE_NAME, values, ID + "=" + id, null) > 0;
//    }
//    //根据用户名找用户，可以判断注册时用户名是否已经存在
//    public Cursor findUserByName(String userphone){
//        Log.i(TAG,"findUserByPhone , userphone="+userphone);
//        Cursor mCursor=mSQLiteDatabase.query(TABLE_NAME, null, "userphone"+"=?", new String[]{userphone}, null, null, null);
//        return mCursor;
//    }
//    //根据用户名和密码找用户，用于登录
//    public int findUserByNameAndPwd(String userName,String pwd){
//        Log.i(TAG,"findUserByNameAndPwd");
//        int result=0;
//        Cursor mCursor=mSQLiteDatabase.query(TABLE_NAME, null, USER_NAME+"=?"+" and "+USER_PWD+"=?",
//                new String[]{userName,pwd}, null, null, null);
//        if(mCursor!=null){
//            result=mCursor.getCount();
//            mCursor.close();
//            Log.i(TAG,"findUserByNameAndPwd , result="+result);
//        }
//        return result;
//    }
}
