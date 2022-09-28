package com.example.grokart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class RegisterActivity extends AppCompatActivity {
    EditText et_username, et_password;
    Boolean isValid = false;
    JSONObject user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        TextView tv_appName = findViewById(R.id.tv_appTitle);

        //setting the style for the App Title
        Spannable groKart = new SpannableString(getString(R.string.groKart));
        groKart.setSpan(new ForegroundColorSpan(Color.GREEN), 0,3,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        groKart.setSpan(new ForegroundColorSpan(Color.RED),3,groKart.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tv_appName.setText(groKart);

        et_username =  findViewById(R.id.et_username);
        et_password =  findViewById(R.id.et_password);

        Button btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                checkInputs();

                //TODO send input to backend and proceed to Home page
                // with response as Intent Extra
                if(isValid) {


                }

            }
        });
    }


    private boolean checkInputs() {
        int invalidCounter = 0;
        if (et_username.length() == 0) {
            et_username.setError("Username is required");
            invalidCounter++;
        }
        if (et_password.length() == 0) {
            et_password.setError("Password is required");
            invalidCounter++;
        } else if (et_password.length() < 8) {
            et_password.setError("Password must be minimum 8 characters");
            invalidCounter++;
        }
        isValid = invalidCounter == 0;
        return isValid;
        // after all validation return true.
    }

    private JSONObject createUser(String username, String password){
        user = new JSONObject();
        try {
            user.put("username", username);
            user.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }



    public void sendToHome(View view) {
       // Intent intent = new Intent(this, HomeActivity.class);
        //startActivity(intent);
    }
}