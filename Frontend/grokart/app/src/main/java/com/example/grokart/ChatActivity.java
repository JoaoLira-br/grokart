package com.example.grokart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.grokart.app.AppController;
import com.example.grokart.utils.BaseMessage;
import com.example.grokart.utils.Const;
import com.example.grokart.utils.MessageListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private String username;
    private Toolbar myToolbar;
    private ArrayList<BaseMessage> messageList;
    private final String TAG = com.example.grokart.ChatActivity.class.getSimpleName();
    private final String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req", tag_string_req = "string_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        // gets the user info from the main activity that sent us to this page
        Intent intent = getIntent();
        //username = intent.getStringExtra("userName");
        //TODO switch username back
        username = "mrm";
        //adds in updated toolbar
        myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // sets the message recycler stuff
        ArrayList<BaseMessage> messageList = new ArrayList<BaseMessage>();
        getMessages();
        mMessageRecycler = (RecyclerView) findViewById(R.id.recycler_gchat);
        mMessageAdapter = new MessageListAdapter(username, this, messageList);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMessageRecycler.setAdapter(mMessageAdapter);
    }

    /**
     * This method gets all previous reports from the backend.
     * It makes a get request that receives a JSONArray of the data,
     * then adds the title of the report to a string array.
     */
    private void getMessages() {
        //todo change the url
        String url = "https://eb90d981-fc0b-42ec-ad43-3cfec437d3ed.mock.pstmn.io/message";
        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        if(response.length() != 0) {
                        for(int i = 0; i < response.length(); i++) {
                            System.out.println(response.toString());
                            try {
                                //todo change to match backend code
                                JSONObject tempMessage = response.getJSONObject(i);
                                String sender = tempMessage.get("sendingUser").toString();
                                String messageText = tempMessage.get("message").toString();
                                String date = tempMessage.get("date").toString();
                                String time = tempMessage.get("time").toString();
                                BaseMessage message = new BaseMessage(sender, messageText, date, time);
                                messageList.add(message);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        mMessageAdapter.notifyDataSetChanged();
                    }}
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                System.out.println(error.getMessage());
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req,
                tag_json_arry);
    }

    /*
     * This method is necessary when creating a toolbar for the edit profile page.
     * It uses the menu layout stored in res/menu/menu_back_to_main.xml
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_back_to_main, menu);
        return true;
    }
    /*
     * This method is similar to an onClickListener.
     * It checks to see if any of the toolbar options were selected,
     * and then performs the appropriate action
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //todo set back button to correct location

        // If back button clicked
        if (item.getItemId() == android.R.id.home) {// Start home intent and finish this intent
            Intent intent = new Intent(ChatActivity.this, StoreAdminHomeActivity.class);
            intent.putExtra("userName", username);
            startActivity(intent);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}