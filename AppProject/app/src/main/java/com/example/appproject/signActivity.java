package com.example.appproject;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class signActivity extends AppCompatActivity {


    final static int REQCODE_ACTEDIT = 1003; // 회원 등록 화면 코드

    TextView idText, pwText, nameText;
    String id, pw, name;
    boolean checkIdCode = true; // false :  아이디가 중복  / true: 회원가입 가능

    PersonDBHelper pHelper;
    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);

        ActionBar signac = getSupportActionBar();
        signac.hide();

        idText = (TextView) findViewById(R.id.idEdit);
        pwText = (TextView) findViewById(R.id.pwEdit);
        nameText = (TextView) findViewById(R.id.nameEdit);

        pHelper = new PersonDBHelper(this);
        cursor = pHelper.getAllContacts();

    }

    public void onClickCheck(View v) {
        id = idText.getText().toString();
        pw = pwText.getText().toString();
        name = nameText.getText().toString();

        SQLiteDatabase db;
        db = pHelper.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("SELECT id FROM person", null);

        String Result = "";
        checkIdCode = true;

        while (cursor.moveToNext()) {
            String checkid = cursor.getString(0);
            Result = checkid;

            if (id.equals(Result)) {
                checkIdCode = false;
                break;
            }
        }
        if (!TextUtils.isEmpty(id) && checkIdCode == true) {
            Toast.makeText(signActivity.this, "사용하실 수 있습니다.", Toast.LENGTH_SHORT).show();
        } else if (id.length() == 0) {
            Toast.makeText(signActivity.this, "아이디를 입력해 주세요.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(signActivity.this, "이미 존재하는 ID입니다.", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }

    public void onClickAdd(View v) {
        Person person;
        id = idText.getText().toString();
        pw = pwText.getText().toString();
        name = nameText.getText().toString();

        if (TextUtils.isEmpty(id)) {
            Toast.makeText(signActivity.this, "아이디를 정확히 입력해주세요", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(pw)) {
            Toast.makeText(signActivity.this, "비밀번호를 정확히 입력해주세요.", Toast.LENGTH_SHORT).show();
        } else if (name.length() < 2) {
            Toast.makeText(signActivity.this, "이름을 정확히 입력해주세요.", Toast.LENGTH_SHORT).show();
        } else if (checkIdCode == false) {
            Toast.makeText(signActivity.this, "다른 아이디를 입력해 주세요.", Toast.LENGTH_SHORT).show();
        } else {
            person = new Person(id, pw, name);
            pHelper.addPerson(person);
            finish();
            Toast.makeText(signActivity.this, name + "님의 회원가입을 축하합니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
