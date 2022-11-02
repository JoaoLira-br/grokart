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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
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
/*
* Reminder: to get store name, use
*       stores.getJSONObject(indexNum).get("storeName").toString()
*/

public class EditProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText et_name, et_email;
    private final String TAG = EditProfileActivity.class.getSimpleName();
    private TextView msgResponse;
    private String username = "";
    private JSONObject user;
    private Toolbar myToolbar;
    // These tags will be used to cancel the requests
    private final String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";
    private ArrayList<String> storesArray;
    private Spinner storesMenu;
    String item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // sets the view to the right layout
        setContentView(R.layout.activity_edit_profile);
        // gets the user info from the main activity that sent us to this page
        Intent intent = getIntent();
        username = intent.getStringExtra("userName");
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

        /*
        * This sets what happens if the edit profile button is clicked.
        * When clicked, it calls the jsonUpdateUser method,
        * which updates the info kept on the user in the backend.
        */
        btn_editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = jsonGetUser();
            }
        });
    }

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

    /*
     * This method gets the current user logged in by using the username passed by the intent
     * */
    private JSONObject jsonGetUser() {
        String path = Const.URL_SAMPLE_READ_USER_GET + username;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, path, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        user = response;
                        try {
                            jsonUpdateUser(user);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
//        {
//            @Override
//            public Map<String, String> getHeaders() {
//                HashMap<String, String> headers = new HashMap<>();
//                headers.put("Content-Type", "application/json");
//                return headers;
//            }
//            @Override
//            protected Map<String, String> getParams() {
//                return new HashMap<>();
//            }
//        };
        AppController.getInstance().addToRequestQueue(request, tag_json_obj);
        return user;
    }

    /*
    * This method sends a Json object put request
    * to update the user info in the backend.
    */
    private void jsonUpdateUser(JSONObject curUser) throws JSONException {
        String path = Const.URL_SERVER_USERS + username;
        path = path.replaceAll(" ", "%20");
        curUser.put("displayName", et_name.getText().toString());
        curUser.put("emailAdd", et_email.getText().toString());
        curUser.put("preferredStore",item);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, path, curUser,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        msgResponse.setText("Updated successfully");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                msgResponse.setText("Failed to update");
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
            @Override
            protected Map<String, String> getParams() {
                return new HashMap<>();
            }
        };
        AppController.getInstance().addToRequestQueue(request, tag_json_obj);
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
        // If back button clicked
        if (item.getItemId() == android.R.id.home) {// Start home intent and finish this intent
            Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
            startActivity(intent);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        //TODO, if pos 0, then null
        if(position == 0) {
            item = null;
        }
        else {
            item = parent.getItemAtPosition(position).toString();
        }
        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
}