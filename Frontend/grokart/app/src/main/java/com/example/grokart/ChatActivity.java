package com.example.grokart;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.grokart.app.AppController;
import com.example.grokart.utils.BaseMessage;
import com.example.grokart.utils.Const;
import com.example.grokart.utils.MessageListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Objects;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import org.*;



public class ChatActivity extends AppCompatActivity {
    private RecyclerView mMessageRecycler;
    private EditText et_message;
    private Button btn_send;
    private MessageListAdapter mMessageAdapter;
    private String username;
    private Toolbar myToolbar;
    private ArrayList<BaseMessage> messageList;
    private final String TAG = com.example.grokart.ChatActivity.class.getSimpleName();
    private final String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req", tag_string_req = "string_req";

    private WebSocketClient cc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        // gets the user info from the main activity that sent us to this page
        Intent intent = getIntent();
        username = intent.getStringExtra("userName");
        // sets up stuff from xml file
        et_message = findViewById(R.id.edit_message);
        btn_send = findViewById(R.id.button_send);
        //adds in updated toolbar
        myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // sets the message recycler stuff
        messageList = new ArrayList<>();


        // set up websocket connection
        Draft[] drafts = {
                new Draft_6455()
        };

        /**
         * If running this on an android device, make sure it is on the same network as your
         * computer, and change the ip address to that of your computer.
         * If running on the emulator, you can use localhost.
         */
        String w = Const.WS_PATH + username;

        try {
            Log.d("Socket:", "Trying socket");
            cc = new WebSocketClient(new URI(w), (Draft) drafts[0]) {
                @Override
                public void onMessage(String message) {
                    Log.d("", "run() returned: " + message);
                    String sender;
                    if(message.contains(username)) {
                        sender = username;
                    }
                    else {
                        sender = "other";
                    }
                    String messageText = message;
                    String date;
                    String time;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd");
                        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                        date = dateFormatter.format(LocalDate.now());
                        time = timeFormatter.format(LocalTime.now());
                    }
                    else {
                        date = "00/00";
                        time = "00:00";
                    }
                    BaseMessage BMmessage = new BaseMessage(sender, messageText, date, time);
                    messageList.add(BMmessage);
                    mMessageAdapter.notifyItemInserted(messageList.size()-1);
                    mMessageRecycler.smoothScrollToPosition(messageList.size()-1);
                }

                @Override
                public void onOpen(ServerHandshake handshake) {
                    Log.d("OPEN", "run() returned: " + "is connecting");
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("CLOSE", "onClose() returned: " + reason);
                }

                @Override
                public void onError(Exception e) {
                    Log.d("Exception:", e.toString());
                }
            };
        } catch (URISyntaxException e) {
            Log.d("Exception:", e.getMessage().toString());
            e.printStackTrace();
        }
        cc.connect();

        mMessageRecycler = (RecyclerView) findViewById(R.id.recycler_chat);
        mMessageAdapter = new MessageListAdapter(username, this, messageList);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMessageRecycler.setAdapter(mMessageAdapter);

        // sending messages
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cc.send(et_message.getText().toString());
                et_message.setText("");

            }
        });
    }

    /*
     * This method is similar to an onClickListener.
     * It checks to see if any of the toolbar options were selected,
     * and then performs the appropriate action
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // If back button clicked
        if (item.getItemId() == android.R.id.home) {// Start home intent and finish this intent
            Intent intent = new Intent(ChatActivity.this, SupportActivity.class);
            intent.putExtra("userName", username);
            startActivity(intent);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}