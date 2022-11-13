package com.example.grokart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.grokart.app.AppController;
import com.example.grokart.utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import android.widget.Spinner;
import android.widget.Toast;

/**
* This class controls the app when a user wants to edit their profile.
* It allows them to change their display name, their email, and their preferred store.
* @author Mattie McGovern
*/

public class EditProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText et_name, et_email;
    private final String TAG = EditProfileActivity.class.getSimpleName();
    private TextView msgResponse;
    private String username = "";
    private Toolbar myToolbar;
    // These tags will be used to cancel the requests
    private final String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";
    private String tag_string_req = "string_req";
    private ArrayList<String> storesArray;
    private Spinner storesMenu;
    String item = null;
    private String displayName, preferredStore;
    final int DISPLAYNAMEINT = 0;
    final int EMAILADDINT = 1;
    final int PREFERREDSTOREINT = 2;
    final String SUCCESSMSG = "{\"message\":\"success\"}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // sets the view to the right layout
        setContentView(R.layout.activity_edit_profile);
        // gets the user info from the main activity that sent us to this page
        Intent intent = getIntent();
        username = intent.getStringExtra("userName");
        displayName = intent.getStringExtra("displayName");
        preferredStore = intent.getStringExtra("preferredStore");
        //adds in updated toolbar
        myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // sets the text and button objects here to their matched pair in the layout
        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        msgResponse = findViewById(R.id.msgResponse);
        storesMenu = findViewById(R.id.spinner);
        storesArray = new ArrayList<String>();
        storesArray.add("Select preferred store");
        getStores();
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, storesArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        storesMenu.setAdapter(adapter);

        Button btn_editProfile = findViewById(R.id.btn_editProfile);

        btn_editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msgResponse.setText("");
                String path;
                if(!et_name.getText().toString().equals("")) {
                    msgResponse.append(" Name ");
                    path = Const.URL_UPDATE_NAME + username + "/" + et_name.getText().toString() + "/";
                    makeStringReq(path);
                }
                if(!et_email.getText().toString().equals("") ){
                    msgResponse.append(" Email ");
                    path = Const.URL_UPDATE_EMAIL + username + "/" + et_email.getText().toString() + "/";
                    makeStringReq(path);
                }
                if(!storesMenu.getSelectedItem().toString().equals("Select preferred store")) {
                    msgResponse.append(" Preferred store ");
                    path = Const.URL_UPDATE_PREFERRED_STORE + username + "/" + storesMenu.getSelectedItem().toString() + "/";
                    makeStringReq(path);
                }
            }
        });
    }
    /**
    * This method gets all available store names from the backend.
    * It uses a JSONArray request and then puts the names of each store into a string array.
    */
    private void getStores() {
        JsonArrayRequest req = new JsonArrayRequest(Const.URL_SERVER_STORES,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        for(int i = 0; i < response.length(); i++) {
                            try {
                                storesArray.add(response.getJSONObject(i).get("storeName").toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req,
                tag_json_arry);
    }

    /**
    * This method is necessary when creating a toolbar for the edit profile page.
    * It uses the menu layout stored in res/menu/menu_back_to_main.xml
    */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_back_to_main, menu);
        return true;
    }

    /**
    * This method checks to see if any of the toolbar options were selected,
    * and then performs the appropriate action.
     * @param item is the selected MenuItem
    */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // If back button clicked
        if (item.getItemId() == android.R.id.home) {// Start home intent and finish this intent
            Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
            intent.putExtra("userName", username);
            intent.putExtra("displayName", displayName);
            intent.putExtra("preferredStore", preferredStore);
            startActivity(intent);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        if(position == 0) {
            item = null;
        }
        else {
            item = parent.getItemAtPosition(position).toString();
        }
        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {}

    /**
     * This method makes a string put request to the backend to update an aspect of the user profile.
     * @param path is the url that the string request uses
     */
    private void makeStringReq(String path) {
        StringRequest strReq = new StringRequest(Request.Method.PUT, path, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                if(response.toString().equals(SUCCESSMSG)) {
                    msgResponse.append("updated.");
                }
                else {
                    msgResponse.append("failed to update.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
            @Override
            protected Map<String, String> getParams()
            {
                return new HashMap<String, String>();
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}