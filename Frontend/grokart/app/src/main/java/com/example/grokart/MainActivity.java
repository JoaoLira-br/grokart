package com.example.grokart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
//    private EditText et_username, et_password;
//
//    private JSONObject user;
//    private TextView msgResponse, tv_JSONOutput, tv_appName;
//    private Button btn_register, btn_login;
//    private final String TAG = RegisterActivity.class.getSimpleName();
//    // These tags will be used to cancel the requests
//    private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";
        private TextView tv_welcomeUser, tv_appName;
        private Button btn_createNewList, btn_viewListHistory;
        private ImageButton btn_menu;



//    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String userName = intent.getStringExtra("userName");

        tv_welcomeUser =  findViewById(R.id.tv_main_welcome);
        tv_appName = findViewById(R.id.tv_main_appTitle);
        btn_createNewList =  findViewById(R.id.btn_main_createNewList);
        btn_viewListHistory = findViewById(R.id.btn_main_viewListHistory);
        btn_menu = findViewById(R.id.btn_main_menu);
        //setting the style for the App Title
        Spannable groKart = new SpannableString(getString(R.string.groKart));
        groKart.setSpan(new ForegroundColorSpan(Color.GREEN), 0,3,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        groKart.setSpan(new ForegroundColorSpan(Color.RED),3,groKart.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tv_appName.setText(groKart);
        tv_welcomeUser.append(" " + userName + "!");


        btn_menu.setOnClickListener(this);
        btn_createNewList.setOnClickListener(this);
        btn_viewListHistory.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_main_menu:
                Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_main_createNewList:
                //do something
                break;
            case R.id.btn_main_viewListHistory:
                //do something
                break;
            default:
                break;
        }
    }
}