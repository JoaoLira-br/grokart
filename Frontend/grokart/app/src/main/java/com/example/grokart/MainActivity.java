package com.example.grokart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.grokart.vRequests.GetRequest;
import com.example.grokart.utils.Const;
import com.example.grokart.utils.KartItemModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
/*
* @author Joao Victor Lira
* */
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
        private Button btn_createNewList, btn_viewListHistory, btn_viewReports;
        private Toolbar myToolbar;
        private String userName, displayName, preferredStore;
        private static final String TAG = CreateNewListActivity.class.getSimpleName();


    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Intent intentBase = getIntent();


        userName = intentBase.getStringExtra("userName");


        tv_welcomeUser =  findViewById(R.id.tv_main_welcome);
        tv_welcomeUser.append(" " + userName + "!");
        tv_appName = findViewById(R.id.tv_main_appTitle);
        btn_createNewList =  findViewById(R.id.btn_main_createNewList);
        btn_viewListHistory = findViewById(R.id.btn_main_viewListHistory);
        btn_viewReports = findViewById(R.id.btn_view_reports);
        //setting the style for the App Title
        Spannable groKart = new SpannableString(getString(R.string.groKart));
        groKart.setSpan(new ForegroundColorSpan(Color.GREEN), 0,3,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        groKart.setSpan(new ForegroundColorSpan(Color.RED),3,groKart.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tv_appName.setText(groKart);




        btn_createNewList.setOnClickListener(this);
        btn_viewListHistory.setOnClickListener(this);
        btn_viewReports.setOnClickListener(this);

        //adds in updated toolbar
        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_main_createNewList:
              sendCreateNewList(v);
                break;
            case R.id.btn_main_viewListHistory:
                intent = new Intent(MainActivity.this, ViewPreviousListsActivity.class);
                intent.putExtra("userName", userName);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_view_reports:
                intent = new Intent(MainActivity.this, ReportsActivity.class);
                intent.putExtra("userName", userName);

                startActivity(intent);
                finish();
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

    /**
     * @param v: the view which the user has clicked
     * sends the user to CreateNewList page, along with the user`s username and
     * preferred store, which is then used to populate the recycler view of the next page
     * */
    public void sendCreateNewList(View v){

        Intent intentCreateNewList = new Intent(MainActivity.this, CreateNewListActivity.class);
        String path = Const.URL_SAMPLE_READ_USER_GET+userName+"/preferredStore/";
        Log.d(TAG, "sendCreateNewList: path"+ path);
        Log.d(TAG, "sendCreateNewList: path: "+ path);
        GetRequest getPreferredStore = new GetRequest(path,TAG);
        //TODO:  HANDLE PATH TO JSON INSTEAD OF STRING
        getPreferredStore.createRequestThread().start();

        getPreferredStore.createResponseHandler(()->{
            preferredStore = getPreferredStore.getResponseHM().get("preferredStore");
            Log.d(TAG, "sendCreateNewList: getPreferredStore.getResponseString(): "+ getPreferredStore.getResponseString());
            intentCreateNewList.putExtra("username", userName);
            intentCreateNewList.putExtra("preferredStore", preferredStore);
            Log.d(TAG, "sendCreateNewList: preferredStore"+ preferredStore);
            startActivity(intentCreateNewList);

        }).start();


//        intentCreateNewList.putExtra("username", userName);
//        intentCreateNewList.putExtra("storeItems",storeItems );

    }
}