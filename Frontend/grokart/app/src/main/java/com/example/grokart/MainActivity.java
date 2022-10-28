package com.example.grokart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
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
        private Toolbar myToolbar;
        private String userName;


//    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");

        tv_welcomeUser =  findViewById(R.id.tv_main_welcome);
        tv_appName = findViewById(R.id.tv_main_appTitle);
        btn_createNewList =  findViewById(R.id.btn_main_createNewList);
        btn_viewListHistory = findViewById(R.id.btn_main_viewListHistory);
        //setting the style for the App Title
        Spannable groKart = new SpannableString(getString(R.string.groKart));
        groKart.setSpan(new ForegroundColorSpan(Color.GREEN), 0,3,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        groKart.setSpan(new ForegroundColorSpan(Color.RED),3,groKart.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tv_appName.setText(groKart);
        tv_welcomeUser.append(" " + userName + "!");

        btn_createNewList.setOnClickListener(this);
        btn_viewListHistory.setOnClickListener(this);

        //adds in updated toolbar
        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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

    //adds in methods for toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
            intent.putExtra("userName", userName);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}