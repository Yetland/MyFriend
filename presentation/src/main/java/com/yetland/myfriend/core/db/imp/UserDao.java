package com.yetland.myfriend.core.db.imp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.yetland.myfriend.core.db.DBHelper;
import com.yetland.myfriend.model.UserModel;
import com.yetland.myfriend.util.CustomUtils;
import com.yetland.myfriend.util.SharedPreferenceUtil;

public class UserDao {

    private static final String TAG = "UserDao";
    private DBHelper mDbHelper;
    private SQLiteDatabase db;
    private String userRaw[];
    private Context context;

    public UserDao(Context context) {
        this.context = context;
        mDbHelper = new DBHelper(context);
        db = mDbHelper.getWritableDatabase();
        userRaw = new String[]{UserModel.USER_ID,
                UserModel.PHONE_NUMBER,
                UserModel.PASSWORD,
                UserModel.REGISTER_TIME,
                UserModel.EMAIL,
                UserModel.STATUS,
                UserModel.NICK,
                UserModel.SEX,
                UserModel.SIGN,
                UserModel.SCHOOL_NAME,
                UserModel.GRADUATE_YEAR,
                UserModel.CLASS_NAME
        };
    }

    // 添加用户信息到数据库
    public Long insertIntoUser(UserModel user) {
        if (user == null) {
            Log.e(TAG, "insertIntoUser:" + "null");
            return (long) 0;
        } else {
            Cursor cursor = findUser(user.getPhoneNumber());
            if (cursor.moveToNext()) {
                Log.e(TAG, "insertIntoUser:" + "用户已存在");
                return (long) 1;
            } else {
                ContentValues contentValues = new ContentValues();
                String userId = String.valueOf(user.getId());
                contentValues.put(UserModel.USER_ID, userId);
                contentValues.put(UserModel.PHONE_NUMBER, user.getPhoneNumber());
                contentValues.put(UserModel.PASSWORD, user.getPassword());
                contentValues.put(UserModel.REGISTER_TIME, user.getRegisterTime());
                contentValues.put(UserModel.EMAIL, user.getEmail());
                contentValues.put(UserModel.STATUS, user.getUserStatus());
                contentValues.put(UserModel.NICK, user.getNick());
                contentValues.put(UserModel.SEX, user.getSex());
                contentValues.put(UserModel.SIGN, user.getSign());
                contentValues.put(UserModel.SCHOOL_NAME, user.getSchoolName());
                contentValues.put(UserModel.GRADUATE_YEAR, user.getGraduateYear());
                contentValues.put(UserModel.CLASS_NAME, user.getClassName());
                return db.insert("user", "id", contentValues);
            }
        }
    }

    // 检测是否已存在用户
    public Cursor findUser(String phoneNumber) {
        String sql = "select * from user where phoneNumber = ?";
        return db.rawQuery(sql, new String[]{phoneNumber});
    }

    // 删除用户信息
    public int deleteUser(String phoneNumber) {
        return db.delete("user", "phoneNumber = " + phoneNumber, null);
    }

    // 修改用户信息
    public void updateUser(UserModel user) {
        ContentValues contentValues = new ContentValues();
        String userId = String.valueOf(user.getId());
        contentValues.put(UserModel.USER_ID, userId);
        contentValues.put(UserModel.PHONE_NUMBER, user.getPhoneNumber());
        contentValues.put(UserModel.PASSWORD, user.getPassword());
        contentValues.put(UserModel.REGISTER_TIME, user.getRegisterTime());
        contentValues.put(UserModel.EMAIL, user.getEmail());
        contentValues.put(UserModel.STATUS, user.getUserStatus());
        contentValues.put(UserModel.NICK, user.getNick());
        contentValues.put(UserModel.SEX, user.getSex());
        contentValues.put(UserModel.SIGN, user.getSign());
        contentValues.put(UserModel.SCHOOL_NAME, user.getSchoolName());
        contentValues.put(UserModel.GRADUATE_YEAR, user.getGraduateYear());
        contentValues.put(UserModel.CLASS_NAME, user.getClassName());

        SharedPreferenceUtil.saveObject(context,CustomUtils.key.USER_INFO,user, CustomUtils.fileName.USER);

        db.update("user", contentValues, "id=" + user.getId(), null);
    }

    public UserModel queryUser(int userId) {

        //			table：表名。相当于select语句from关键字后面的部分。如果是多表联合查询，可以用逗号将两个表名分开。
        //			columns：要查询出来的列名。相当于select语句select关键字后面的部分。
        //			selection：查询条件子句，相当于select语句where关键字后面的部分，在条件子句允许使用占位符“?”
        //			selectionArgs：对应于selection语句中占位符的值，值在数组中的位置与占位符在语句中的位置必须一致，否则就会有异常。
        //			groupBy：相当于select语句group by关键字后面的部分
        //			having：相当于select语句having关键字后面的部分
        //			orderBy：相当于select语句order by关键字后面的部分，如：personid desc, age asc;
        //			 limit：指定偏移量和获取的记录数，相当于select语句limit关键字后面的部分。
        Cursor cursor = db.query("user", userRaw, UserModel.USER_ID + "=" + userId, null, null, null, null);
        Log.e("User遍历结果长度:", cursor.getCount() + "");
        if (cursor.moveToFirst()) {
            UserModel user = new UserModel();
            user.setId(cursor.getInt(cursor.getColumnIndex(UserModel.USER_ID)));
            user.setPhoneNumber(cursor.getString(cursor.getColumnIndex(UserModel.PHONE_NUMBER)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(UserModel.PASSWORD)));
            user.setRegisterTime(cursor.getString(cursor.getColumnIndex(UserModel.REGISTER_TIME)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(UserModel.EMAIL)));
            user.setUserStatus(cursor.getString(cursor.getColumnIndex(UserModel.STATUS)));
            user.setNick(cursor.getString(cursor.getColumnIndex(UserModel.NICK)));
            user.setSex(cursor.getString(cursor.getColumnIndex(UserModel.SEX)));
            user.setSign(cursor.getString(cursor.getColumnIndex(UserModel.SIGN)));
            user.setSchoolName(cursor.getString(cursor.getColumnIndex(UserModel.SCHOOL_NAME)));
            user.setGraduateYear(cursor.getString(cursor.getColumnIndex(UserModel.GRADUATE_YEAR)));
            user.setClassName(cursor.getString(cursor.getColumnIndex(UserModel.CLASS_NAME)));
            return user;
        }
        return null;
    }
}
