package com.example.grokart;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        // gets the user info from the main activity that sent us to this page
        Intent intent = getIntent();
        username = intent.getStringExtra("userName");
        username = "mrm";
        // sets up stuff from xml file
        btn_chat = findViewById(R.id.btn_chat);
        btn_reports = findViewById(R.id.btn_reports);
        //adds in updated toolbar
        myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    /*
     * This method is similar to an onClickListener.
     * It checks to see if any of the toolbar options were selected,
     * and then performs the appropriate action
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //todo set back button to correct location

        // If back button clicked
        if (item.getItemId() == android.R.id.home) {// Start home intent and finish this intent
            Intent intent = new Intent(SupportActivity.this, MainActivity.class);
            intent.putExtra("userName", username);
            startActivity(intent);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
