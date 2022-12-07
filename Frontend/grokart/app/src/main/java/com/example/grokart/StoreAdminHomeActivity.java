package com.example.grokart;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class StoreAdminHomeActivity extends AppCompatActivity {
    private Button btn_storeItems;
    private Button btn_support;
    private TextView tv_title;
    private TextView tv_welcome;
    private Toolbar myToolbar;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_admin_home);
        final Intent intentBase = getIntent();
        username = intentBase.getStringExtra("userName");

        tv_title = findViewById(R.id.tv_store_admin_home_title);
        tv_welcome = findViewById(R.id.tv_main_welcome2);
        btn_support = findViewById(R.id.btn_support);
        btn_storeItems = findViewById(R.id.btn_storeItems);

        //setting the style for the App Title
        Spannable groKart = new SpannableString(getString(R.string.groKart));
        groKart.setSpan(new ForegroundColorSpan(Color.GREEN), 0,3,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        groKart.setSpan(new ForegroundColorSpan(Color.RED),3,groKart.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tv_title.setText(groKart);


        btn_storeItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                //TODO code button functionality
            }
        });
        btn_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Intent intent = new Intent(StoreAdminHomeActivity.this, SupportActivity.class);
                intent.putExtra("userName", username);
                intent.putExtra("privilege", 0);
                startActivity(intent);
                finish();
            }
        });
    }
}
