package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;

import com.example.myapplication.model.UserData;

import java.util.ArrayList;
import java.util.List;

public class UserDataDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "userdata.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USERDATA = "userdata";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_AVATAR = "avatar";
    private static final String COLUMN_BIRTHDAY = "birthday";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_USERDATA + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_EMAIL + " TEXT, " +
                    COLUMN_AVATAR + " TEXT, " +
                    COLUMN_BIRTHDAY + " DATE);";

    public UserDataDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(TABLE_CREATE);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERDATA);
        onCreate(db);
    }

    public void insertUserData(String name, String email, String birthday, String avatar) {
        SQLiteDatabase db = this.getWritableDatabase();
        String insertQuery = "INSERT INTO " + TABLE_USERDATA + " (" + COLUMN_NAME + ", " + COLUMN_EMAIL + ", " + COLUMN_AVATAR + ", " + COLUMN_BIRTHDAY + ") VALUES (?, ?, ?, ?)";
        SQLiteStatement statement = db.compileStatement(insertQuery);
        statement.bindString(1, name);
        statement.bindString(2, email);
        statement.bindString(3, avatar);
        statement.bindString(4, birthday);
        statement.executeInsert();
        db.close();
    }

    public List<UserData> getAllUserData() {
        List<UserData> userDataList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERDATA, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                UserData userData = new UserData();
                userData.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                userData.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
                userData.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)));
                userData.setAvatar(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AVATAR)));
                userData.setBirthday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BIRTHDAY)));
                userDataList.add(userData);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return userDataList;
    }
}