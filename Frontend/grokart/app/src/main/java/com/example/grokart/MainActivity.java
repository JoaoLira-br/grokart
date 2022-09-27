package com.example.grokart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_login = findViewById(R.id.btn_loginPage);
        Button btn_register = findViewById(R.id.btn_register);

        //TODO Send user to register page
        btn_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                sendPage(view);
            }
        });
        //TODO Send user to register page
        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                sendPage(view);
            }
        });
    }

    public void sendPage(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        //Intent intent2 = new Intent(this, LoginActivity.class);
        switch (view.getId()) {
            case R.id.btn_register:
                startActivity(intent);
                break;
            case R.id.btn_loginPage:
            break;
        }

    }
}