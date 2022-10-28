package com.example.grokart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

public class BottomNavigationBar extends AppCompatActivity {
    ImageButton btn_home;
    ImageButton btn_search;
    ImageButton btn_list;


    public BottomNavigationBar(){
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_navigation_bar);
        btn_home = findViewById(R.id.btn_home);
        btn_search = findViewById(R.id.btn_search);
        btn_list = findViewById(R.id.btn_list);

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BottomNavigationBar.this, SocialActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BottomNavigationBar.this, SearchUsersActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BottomNavigationBar.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
