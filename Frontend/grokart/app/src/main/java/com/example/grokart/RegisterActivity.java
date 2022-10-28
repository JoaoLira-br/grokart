package com.example.grokart;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.grokart.Requests.PostRequest;
import com.example.grokart.app.AppController;
import com.example.grokart.utils.Const;
import com.example.grokart.Requests.GetRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private EditText et_username, et_password;
    private static String jsonResponse;
    private JSONObject user;
    private TextView msgResponse, tv_JSONOutput, tv_appName;
    private Button btn_register, btn_login;
    private final String TAG = RegisterActivity.class.getSimpleName();
    // These tags will be used to cancel the requests
    private final String tag_json_obj = "jobj_req";
    private final String tag_json_arry = "jarray_req";
    private String path;
    private final String loginFailedMsg= "Login Failed";






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        tv_appName = findViewById(R.id.tv_appTitle);
        btn_login =  findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        msgResponse = findViewById(R.id.tv_msgResponse);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        //setting the style for the App Title
        Spannable groKart = new SpannableString(getString(R.string.groKart));
        groKart.setSpan(new ForegroundColorSpan(Color.GREEN), 0,3,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        groKart.setSpan(new ForegroundColorSpan(Color.RED),3,groKart.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tv_appName.setText(groKart);

        /* OBS: All url endpoints must have no whitespace
        * */

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceedLogin(v);
            }

        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO have REGISTER button send input to backend and proceed to Home page
                // with response as Intent Extra
              proceedRegister(v);

            }
        });

    }
    private void setPathAddress(){
         String username = et_username.getText().toString();
         String password = et_password.getText().toString();
        path = (Const.URL_SERVER_USERS + username + "/" + password).replaceAll("\\s", "");

    }
    private void proceedLogin(View v){
        if(checkInputs()) {
            setPathAddress();
            GetRequest getRequest = new GetRequest(path, TAG);
            Thread loginRequest = getRequest.createRequestThread();
            Thread loginResponse = getRequest.createResponseHandler(()->{
                String response = String.valueOf(getRequest.getResponseHM().get("message"));
                if(response.equals("success")){
                    sendToHomePage(v, et_username.getText().toString(), 0);
                }else{
                    //TOAST MAKING THE APP CRASH
                    msgResponse.setText(response);
                }
            });

            loginRequest.start();
            try {
                loginRequest.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
           loginResponse.start();
        }
    };
    private void proceedRegister(View v){
        if(checkInputs()) {
           user = createUser();
            PostRequest postRequest = new PostRequest(Const.URL_SAMPLE_CREATE_USER_POST, TAG, user);
            Thread registerRequest = postRequest.getRequestThread();
            Thread registerResponse = postRequest.createResponseHandler(()->{
                String response = String.valueOf(postRequest.getResponseHM().get("message"));
                if(response.equals("success")){
                    sendToHomePage(v, et_username.getText().toString(), 0);
                }else{
                    //TOAST MAKING THE APP CRASH
                    msgResponse.setText(response);
                }
            });

            registerRequest.start();
            try {
                registerRequest.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
           registerResponse.start();
        }
    };

    private Boolean checkInputs() {
        int invalidCounter = 0;
        if (et_username.length() == 0) {
            et_username.setError("Username is required");
            invalidCounter++;
        }
        if (et_password.length() == 0) {
            et_password.setError("Password is required");
            invalidCounter++;
        } else if (et_password.length() < 7) {
            et_password.setError("Password must be minimum 8 characters");
            invalidCounter++;
        }
        return invalidCounter == 0;
        // after all validation return true.
    }

    private JSONObject createUser(){
        user = new JSONObject();
        try {
            user.put("userName", et_username.getText().toString());
            user.put("password", et_password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void sendToHomePage(View view, String userName,int privilege) {
        if(privilege == 0){
            Intent intentBase = new Intent(RegisterActivity.this,MainActivity.class);
            intentBase.putExtra("userName", userName);
            startActivity(intentBase);
        }else if(privilege == 1){
            Intent intentAdmin = new Intent(RegisterActivity.this, AdminHomeActivity.class);
            startActivity(intentAdmin);
        }else{
            //TODO if user is store admin send him to store admin home page
        }
    }

    private void jsonObjPostReq(JSONObject user) {
//        showProgressDialog();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,Const.URL_SAMPLE_CREATE_USER_POST,user,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        jsonResponse = response.toString();
                        Log.d(TAG, jsonResponse);
                        Log.d(TAG, "gotcha!");
//                        msgResponse.setText(response.toString());
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
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/json");
//                return headers;
//            }

//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
////                params.put("name", "Androidhive");
////                params.put("email", "abc@androidhive.info");
////                params.put("pass", "password123");
//
//                return params;
//            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq,
                tag_json_obj);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
    }

//    /**
//     * Making json array request
//     * */
    private void makeJsonArrayReq() {
//        showProgressDialog();
        JsonArrayRequest req = new JsonArrayRequest(Const.URL_SERVER_USERS,
                    new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, Const.URL_SERVER_USERS);
                        Log.d(TAG, response.toString());
//                        msgResponse.setText(response.toString());
//                        hideProgressDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
//                hideProgressDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req,
                tag_json_arry);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_arry);
    }
}