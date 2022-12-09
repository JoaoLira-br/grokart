package com.example.grokart;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

public class SupportActivity extends AppCompatActivity {
    Button btn_reports;
    Button btn_chat;
    Toolbar myToolbar;
    String username;
    int privilege;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        // gets the user info from the main activity that sent us to this page
        Intent intent = getIntent();
        username = intent.getStringExtra("userName");
        privilege = intent.getIntExtra("privilege", 0);
        // sets up stuff from xml file
        btn_chat = findViewById(R.id.btn_chat);
        btn_reports = findViewById(R.id.btn_reports);
        //adds in updated toolbar
        myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //button onClicks
        btn_reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(privilege == 1) {
                    Intent intent = new Intent(SupportActivity.this, StoreReportsActivity.class);
                    intent.putExtra("userName", username);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(SupportActivity.this, ReportsActivity.class);
                    intent.putExtra("userName", username);
                    startActivity(intent);
                }
            }
        });
        btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupportActivity.this, ChatActivity.class);
                intent.putExtra("userName", username);
                intent.putExtra("privilege", privilege);
                startActivity(intent);
            }
        });
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
            Intent intent;
            if(privilege == 1) {
                intent = new Intent(SupportActivity.this, StoreAdminHomeActivity.class);
            }
            else {
                intent = new Intent(SupportActivity.this, MainActivity.class);
            }
            intent.putExtra("userName", username);
            startActivity(intent);
            this.finish();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
