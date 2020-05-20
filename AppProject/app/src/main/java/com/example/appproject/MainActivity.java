package com.example.appproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final static int REQCODE_ACTEDIT = 1001; //메인 액티비티 코드
    TextView idText;
    TextView pwText;
    String id, pw;
    String Cid, Cpass, Cname;

    PersonDBHelper pHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar mainac = getSupportActionBar();
        mainac.hide();

        idText = (TextView) findViewById(R.id.idEdit);
        pwText = (TextView) findViewById(R.id.pwEdit);

        pHelper = new PersonDBHelper(this);
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        SharedPreferences pref = getSharedPreferences("IdPass", MODE_PRIVATE);

        String prefid = pref.getString("Name", "");
        String prefpw = pref.getString("Password", "");
        Boolean chk = pref.getBoolean("check", false);
        checkBox.setChecked(chk);

        if (checkBox.isChecked()) {
            if (!prefid.equals("")) {
                idText.setText(prefid);
            }
            if (!prefpw.equals("")) {
                pwText.setText(prefpw);
            }
        } else if (!checkBox.isChecked()) {
            idText.setText(null);
            pwText.setText(null);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        boolean killFlag = intent.getBooleanExtra("KILL_APP", false);
        if (killFlag == true) {
            finish();
        }
    }

    public void mOnClickLogin(View v) {
        id = idText.getText().toString();
        pw = pwText.getText().toString();
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        SQLiteDatabase db;
        db = pHelper.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("SELECT id, pw, name FROM person", null);

        if (TextUtils.isEmpty(id)) {
            Toast.makeText(MainActivity.this, "ID를 입력하세요.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(pw)) {
            Toast.makeText(MainActivity.this, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                Cid = cursor.getString(0);
                Cpass = cursor.getString(1);
                Cname = cursor.getString(2);

                if (id.equals(Cid) && pw.equals(Cpass)) {

                    SharedPreferences pref = getSharedPreferences("IdPass", MODE_PRIVATE);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putString("Name", Cid);
                    edit.putString("Password", Cpass);
                    edit.putBoolean("check", checkBox.isChecked());
                    edit.commit();

                    Intent navigationIntent = new Intent(MainActivity.this, NavigationActivity.class);
                    navigationIntent.putExtra("PersonIn", new Person(Cid, Cpass, Cname));
                    startActivityForResult(navigationIntent, REQCODE_ACTEDIT);
                    Toast.makeText(MainActivity.this, Cname + "님 환영합니다.", Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    Toast.makeText(MainActivity.this, "정확한 정보를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        }
        cursor.close();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQCODE_ACTEDIT:
                if (resultCode == RESULT_OK) {
                    Person person = (Person) data.getSerializableExtra("PersonOut");
                }
                break;
        }
    }

    public void mOnClickSign(View v) {
        Intent signIntent = new Intent(this, signActivity.class);
        startActivityForResult(signIntent, REQCODE_ACTEDIT); //startActivityforresult함수로 변경하기
    }

}