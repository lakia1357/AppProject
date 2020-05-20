package com.example.appproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;

public class MessageDBHelper extends SQLiteOpenHelper implements Serializable {

    public MessageDBHelper(Context context) { // 데이터베이스 생성
        // 데이터베이스 이름 : MessageInfo
        super(context, "MessageInfo.db", null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { // 테이블 생성
        // 테이블 이름 : message
        // 칼럼 값 : _id  / id TEXT / title TEXT / contains TEXT / date TEXT
        db.execSQL("CREATE TABLE message ( _id INTEGER PRIMARY KEY AUTOINCREMENT, userId TEXT, id TEXT, title TEXT, contains TEXT, date TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS message");
        onCreate(db);
    }

    public void deleteAllContents() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("message", null, null);
        db.close();
    }

    public void addMessage(Message message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userId", message.getUserId());
        values.put("id", message.getmailid());
        values.put("title", message.getmailTitle());
        values.put("contains", message.getmailContains());
        values.put("date", message.getdate());
        db.insert("message", null, values);
        db.close();
    }

    public Cursor getAllContacts(String userID) {
        String selectQuery = "SELECT _id, userId, id, title, contains, date FROM message WHERE userId=?";
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, new String[]{userID});
    }
}