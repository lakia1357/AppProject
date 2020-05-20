package com.example.appproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.Serializable;

public class PersonDBHelper extends SQLiteOpenHelper implements Serializable {

    public PersonDBHelper(Context context) { // 데이터베이스 생성
        // 데이터베이스 이름 : PersonInfo
        super(context, "PersonInfo.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { // 테이블 생성
        // 테이블 이름 : person
        // 칼럼 값 : _id  / id TEXT / pw TEXT / name TEXT
        db.execSQL("CREATE TABLE person ( _id INTEGER PRIMARY KEY AUTOINCREMENT, id TEXT, pw TEXT, name TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS person");
        onCreate(db);
    }

    public void deleteAllContents() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("person", null, null);
        db.close();
    }

    public void addPerson(Person person) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", person.getId());
        values.put("pw", person.getpw());
        values.put("name", person.getname());
        db.insert("person", null, values);
        db.close();
    }

    public void  updatePerson(Person person){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("pw", person.getpw());
        values.put("name", person.getname());
        db.update("person", values, "id=?", new String[]{person.getId()});
        db.close();
    }

    public Cursor getAllContacts() {
        String selectQuery = "SELECT _id, id, pw, name FROM person";
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, null);
    }
}
