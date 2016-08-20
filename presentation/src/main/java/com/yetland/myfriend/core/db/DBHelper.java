package com.yetland.myfriend.core.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.yetland.myfriend.model.UserModel;

public class DBHelper extends SQLiteOpenHelper {

    public static final String TAG = "DBHelper";

    private Context context;

    public DBHelper(Context context, String name, CursorFactory factory,
                    int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    public DBHelper(Context context) {
        super(context, DBString.DB_NAME, null, DBString.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "create table if not exists user(" +
                "table_id integer primary key autoincrement," +
                UserModel.USER_ID + " int not null," +
                UserModel.REGISTER_TIME + " text not null," +
                UserModel.PHONE_NUMBER + " text not null," +
                UserModel.PASSWORD + " text not null," +
                UserModel.EMAIL + "text," +
                UserModel.STATUS + "text," +
                UserModel.NICK + " text," +
                UserModel.SEX + " varchar(10)," +
                UserModel.SIGN + " text," +
                UserModel.SCHOOL_NAME + "text," +
                UserModel.GRADUATE_YEAR + "text," +
                UserModel.CLASS_NAME + "text"
                + ")";

        db.execSQL(sql);
        Log.e(TAG, "onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        //		db.execSQL("DROP TABLE IF EXISTS user");
        //		db.execSQL("DROP TABLE IF EXISTS goods");
        onCreate(db);
        Log.e(TAG, "onUpgrade");
    }

    public boolean deleteDatabase() {
        return context.deleteDatabase(DBString.DB_NAME);
    }
}
