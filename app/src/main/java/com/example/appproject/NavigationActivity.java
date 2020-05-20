package com.example.appproject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    PersonDBHelper pHelper;
    MessageDBHelper mHelper;
    ArrayList<Message> messageArrayList;

    CustomCursorAdapter adapter;
    Cursor mCursor;
    String mid, mpw, mname;
    String sid, stitle, scontents;
    LinearLayout firstContainer, fragmentContainer;
    Fragment writeFragment, sendFragment, modifyFragment, readFragment;

    boolean checkIdCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        firstContainer = findViewById(R.id.firstContainer);
        fragmentContainer = findViewById(R.id.fragmentContainer);
        writeFragment = new WriteMessageFragment();
        sendFragment = new SendMessageFragment();
        modifyFragment = new ModifyFragment();
        readFragment = new ReadMessageFragment();

        pHelper = new PersonDBHelper(this);
        mHelper = new MessageDBHelper(this);
        Person person = (Person) getIntent().getSerializableExtra("PersonIn");
        final String userID = person.getId();
        mCursor = mHelper.getAllContacts(userID);


        final ListView messageList = (ListView) findViewById(R.id.messageList);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                messageArrayList = new ArrayList<Message>();
                while (mCursor.moveToNext()) {
                    Message message = new Message(userID, mCursor.getString(2), mCursor.getString(3), mCursor.getString(4), mCursor.getString(5));
                    messageArrayList.add(message);
                }
                adapter = new CustomCursorAdapter(NavigationActivity.this, mCursor);
                messageList.setAdapter(adapter);
            }
        });

        messageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                firstContainer.setVisibility(View.GONE);
                Bundle readDataBundle = new Bundle();
                readDataBundle.putString("sanded", messageArrayList.get(position).getmailid());
                readDataBundle.putString("received", userID);
                readDataBundle.putString("titled", messageArrayList.get(position).getmailTitle());
                readDataBundle.putString("contained", messageArrayList.get(position).getmailContains());
                readDataBundle.putString("date", messageArrayList.get(position).getdate());
                readFragment.setArguments(readDataBundle);


                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, readFragment);
                fragmentTransaction.commit();
            }
        });
    }
    public void setActionBarTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.navigation, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// Handle action bar item clicks here. The action bar will
// automatically handle clicks on the Home/Up button, so long
// as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
// Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_write) {
            firstContainer.setVisibility(View.GONE);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, writeFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_receive) {
            if (firstContainer.getVisibility() == View.GONE) {
                firstContainer.setVisibility(View.VISIBLE);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, sendFragment);
                fragmentTransaction.commit();
            }

        } else if (id == R.id.nav_modify) {
            Person person = (Person) getIntent().getSerializableExtra("PersonIn");
            Bundle modifyBundle = new Bundle();
            modifyBundle.putString("mid", person.getId());
            modifyBundle.putString("mpw", person.getpw());
            modifyBundle.putString("mname", person.getname());
            modifyFragment.setArguments(modifyBundle);

            firstContainer.setVisibility(View.GONE);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, modifyFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("KILL_APP", true);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClickModify() {
        Person person;
        person = new Person(mid, mpw, mname);
        pHelper.updatePerson(person);

        if (firstContainer.getVisibility() == View.GONE) {
            firstContainer.setVisibility(View.VISIBLE);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, sendFragment);
            fragmentTransaction.commit();
        }

        Toast.makeText(NavigationActivity.this, "변경 완료 " + "ID : " + mid + " Password : " + mpw + " Name : " + mname, Toast.LENGTH_SHORT).show();
    }

    public void onClickSend() { // 메세지 작성 화면에서 보내기 버튼을 누를 때 실행되는 버튼 이벤트
        long now = System.currentTimeMillis();
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date mDate = new Date(now);
        String getTime = simpleDate.format(mDate);

        Person person = (Person) getIntent().getSerializableExtra("PersonIn");
        String userId = person.getId();
        SQLiteDatabase db;
        db = pHelper.getReadableDatabase();
        Cursor pCursor;
        pCursor = db.rawQuery("SELECT id FROM person", null);

        String Result = "";
        checkIdCode = false; // DB에 있는 아이디와 일치하면 true  / 다르면 false 반환;

        while (pCursor.moveToNext()) {
            String checkid = pCursor.getString(0);
            Result = checkid;

            if (sid.equals(Result)) {
                checkIdCode = true;
                break;
            }
        }
        if (!TextUtils.isEmpty(sid) && !TextUtils.isEmpty(stitle) && !TextUtils.isEmpty(scontents) && checkIdCode == true) {
            Message message;
            message = new Message(sid, userId, stitle, scontents, getTime);
            mHelper.addMessage(message);
            adapter.changeCursor(mHelper.getAllContacts(userId));

            Bundle checkBundle = new Bundle();
            checkBundle.putInt("checkSend", 1); // 1 이면 메세지가 잘 저장 되었다는 것을 SendMessageFragment에게 알려줌 -> 메세지를 전송 후 TextView를 null로 만들어주기 위해
            writeFragment.setArguments(checkBundle);

            if (firstContainer.getVisibility() == View.GONE) {
                firstContainer.setVisibility(View.VISIBLE);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, sendFragment);
                fragmentTransaction.commit();
            }
            Toast.makeText(NavigationActivity.this, "메세지 전송 완료!", Toast.LENGTH_SHORT).show();
        } else if (checkIdCode == false) {
            Toast.makeText(NavigationActivity.this, "받는 사람을 확인해 주세요.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(sid)) {
            Toast.makeText(NavigationActivity.this, "받는 사람을 입력해 주세요.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(stitle)) {
            Toast.makeText(NavigationActivity.this, "제목을 입력해 주세요.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(scontents)) {
            Toast.makeText(NavigationActivity.this, "내용을 입력해 주세요.", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public void onClickCancel() { // 메세지 작성 화면에서 취소 버튼 누를 시 실행되는 버튼 이벤트
        if (firstContainer.getVisibility() == View.GONE) {
            firstContainer.setVisibility(View.VISIBLE);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, sendFragment);
            fragmentTransaction.commit();
        }
    }

}