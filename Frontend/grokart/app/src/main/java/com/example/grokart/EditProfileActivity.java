package com.example.grokart;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    private JSONObject user;
    private String TAG = RegisterActivity.class.getSimpleName();
    // These tags will be used to cancel the requests
    private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        et_name =  findViewById(R.id.et_name);
        et_email =  findViewById(R.id.et_email);
        et_phone =  findViewById(R.id.et_phone);
        et_preferredStore =  findViewById(R.id.et_preferredStore);

        Button btn_editProfile = findViewById(R.id.btn_editProfile);
        btn_editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //gets the current user hopefully
                makeJsonObjReq();
                //update user profile
                String name = et_name.getText().toString();
                String email = et_email.getText().toString();
                String phone = et_phone.getText().toString();
                String store = et_preferredStore.getText().toString();
                updateUser(name,email,phone,store);
                makeJsonObjReq(user);
                //ToDO: If successful, change pages
            }
        });
    }
    //TODO figure out if this is actually how you get the user object

    //TODO error: ullPointerException: Attempt to invoke virtual method
    // 'void com.example.grokart.app.AppController.addToRequestQueue(com.android.volley.Request, java.lang.String)'
    // on a null object reference
    private void makeJsonObjReq() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Const.URL_USER, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        user = response;
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                    }
        }){

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
//                params.put("name", "Androidhive");
//                params.put("email", "abc@androidhive.info");
//                params.put("pass", "password123");

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq,
                tag_json_obj);
        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
    }
    //TODO check if this is how you update a JSON Object
    private void updateUser(String name, String email, String phone, String store){
        try {
            user.put("name", name);
            user.put("email", email);
            user.put("phone", phone);
            user.put("preferredStore", store);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //TODO is this actually how I would send the updated info?
    private void makeJsonObjReq(JSONObject profile) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                Const.URL_UPDATE_USER, profile,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
//                        hideProgressDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
//                hideProgressDialog();
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
            //TODO are these necessary?
            //If so, do I need to include the username and password?
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                try {
                    params.put("name", user.get("name").toString());
                    params.put("email", user.get("email").toString());
                    params.put("phone", user.get("phone").toString());
                    params.put("preferredStore", user.get("preferredStore").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq,
                tag_json_obj);
        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
    }
}
