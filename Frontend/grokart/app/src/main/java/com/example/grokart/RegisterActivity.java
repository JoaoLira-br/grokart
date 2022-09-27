package com.example.grokart;

import androidx.appcompat.app.AppCompatActivity;

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

public class RegisterActivity extends AppCompatActivity {

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

        EditText et_username =  findViewById(R.id.et_username);
        EditText et_password =  findViewById(R.id.et_password);

        Button btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {

            //TODO make sure password and username are filled properly
            @Override
            public void onClick(View view) {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();

                //TODO send input to backend and proceed to Home page
            }
        });
    }
}