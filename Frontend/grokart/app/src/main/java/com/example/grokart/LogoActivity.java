package com.example.grokart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class LogoActivity extends AppCompatActivity {

    Timer timer = new Timer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                Intent intent = new Intent(LogoActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}