package com.example.grokart;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class SearchUsersActivity extends AppCompatActivity {
    TextView tvAddFriends;
    EditText etSearchUsername;
    ImageButton btnSearchUsers;
    RecyclerView rvProfiles;
    BottomNavigationBar navBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_users);
            tvAddFriends = findViewById(R.id.tv_add_friends);
            etSearchUsername = findViewById(R.id.et_search_username);
            btnSearchUsers = findViewById(R.id.btn_search_user);
            rvProfiles = findViewById(R.id.RVProfiles);
            navBar = new BottomNavigationBar();
    }
}
