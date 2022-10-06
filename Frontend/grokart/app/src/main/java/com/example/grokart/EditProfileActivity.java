package com.example.grokart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.grokart.app.AppController;
import com.example.grokart.utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {
    private EditText et_name, et_email, et_phone, et_preferredStore;
    private final String TAG = RegisterActivity.class.getSimpleName();
    private TextView msgResponse;
    private String username = "";
    private JSONObject user;
    // These tags will be used to cancel the requests
    private final String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        et_phone = findViewById(R.id.et_phone);
        et_preferredStore = findViewById(R.id.et_preferredStore);
        msgResponse = (TextView) findViewById(R.id.msgResponse);

        Button btn_editProfile = findViewById(R.id.btn_editProfile);

        jsonGetUser();

        btn_editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //jsonTest();
                try {
                    jsonUpdateUser();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    private void jsonGetUser() {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Const.URL_USER, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            user = response;
                            username = response.getString("username");
                            msgResponse.setText("hello " + username);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        AppController.getInstance().addToRequestQueue(request,
                tag_json_obj);
    }
    private void jsonUpdateUser() throws JSONException {
        user.put("name", et_name.getText().toString());
        user.put("email", et_email.getText().toString());
        user.put("phone", et_phone.getText().toString());
        user.put("preferredStore", et_preferredStore.getText().toString());

        String url = Const.URL_USER + "/" + username;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, user,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            msgResponse.setText(response.getString("message"));
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
//                params.put("name", et_name.getText().toString());
//                params.put("email", et_email.getText().toString());
//                params.put("phone", et_phone.getText().toString());
//                params.put("preferredStore", et_preferredStore.getText().toString());

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(request,
                tag_json_obj);
    }
}