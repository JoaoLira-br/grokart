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

import androidx.appcompat.app.AppCompatActivity;

import com.example.grokart.vRequests.PostRequest;
import com.example.grokart.utils.Const;
import com.example.grokart.vRequests.GetRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**@author Joao Victor Lira
 * */
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


        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);


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

                proceedRegister(v);

            }
        });
    }
    private void setPathAddress(){
        String username = et_username.getText().toString();
        String password = et_password.getText().toString();

        path = (Const.URL_SERVER_USERS + username + "/" + password).replaceAll("\\s", "");
        Log.d(TAG, "setPathAddress: "+path);

    }

    /**
     * @param v: the View clicked by the user
     * check if user input is correct and matches an existing user in the database, if yes proceeds to Main, otherwise stay in RegisterActivity
     * */
    private void proceedLogin(View v){

        if(checkInputs()) {
            setPathAddress();
            GetRequest getRequest = new GetRequest(path, TAG);
            Thread loginRequest = getRequest.createRequestThread();
            Thread loginResponse = getRequest.createResponseHandler(()->{
                int response = Integer.parseInt(getRequest.getResponseHM().get("privilege"));
                if(response  != -1){

                    sendToHomePage(v, et_username.getText().toString(), response);
                }else{
                    //TOAST MAKING THE APP CRASH
                    msgResponse.setText("Login Failed");
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
    /**
     * @param v: the View clicked by the user
     * check if user input is correct, if yes creates a new user in the DB and proceeds to Main, otherwise stay in RegisterActivity
     * */
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

    /**
     * @return true if user inputs correctly, i.e password.length longer than 7, false otherwise*/
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


    /**
     * @param view the view clicked by the user
     * @param userName is the user`s username input
     * @param privilege is 0 if the user is base user, 1 if store admin, 2 if app admin, -1 otherwise.
     * Allow proper home page redirection for different types of users
     * */
    public void sendToHomePage(View view, String userName, int privilege) {
        if(privilege == 0){

            GetRequest getDisplayName = new GetRequest(Const.URL_USER_INFO+userName+"/", TAG);
            getDisplayName.createRequestThread().start();
            getDisplayName.createResponseHandler(()->{
                String preferredStore = getDisplayName.getResponseHM().get("preferredStore");
                Log.d(TAG, "sendToHomePage: getDisplayName.getResponseHM().getPreferredStore"+getDisplayName.getResponseHM().get("preferredStore"));
                String displayName = getDisplayName.getResponseHM().get("displayName");
                Log.d(TAG, "sendToHomePage: displayName"+displayName);
                Intent intentBase = new Intent(RegisterActivity.this,MainActivity.class);
                intentBase.putExtra("userName", userName);
                intentBase.putExtra("displayName",displayName);
                intentBase.putExtra("preferredStore", preferredStore);
                startActivity(intentBase);

            }).start();

        }else if(privilege == 2 ){
            Intent intentAdmin = new Intent(RegisterActivity.this, AdminHomeActivity.class);
            startActivity(intentAdmin);
        }else if(privilege == 1){
            Intent intentStoreAdmin = new Intent(RegisterActivity.this, StoreAdminHomeActivity.class);
            startActivity(intentStoreAdmin);
        }

    }


}