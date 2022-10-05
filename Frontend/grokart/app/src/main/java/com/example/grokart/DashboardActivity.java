package com.example.grokart;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grokart.R;
import com.example.grokart.RegisterActivity;

//TODO actually make this a dashboard page
// this is just a temporary dashboard page
public class DashboardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }
}