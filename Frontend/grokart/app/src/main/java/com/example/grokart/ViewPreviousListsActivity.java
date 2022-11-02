package com.example.grokart;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grokart.utils.KartsListAdapter;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ViewPreviousListsActivity extends AppCompatActivity {
    RecyclerView kartRV;
    ArrayList<String> karts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_previous_lists);
        karts = new ArrayList<String>();
        kartRV = (RecyclerView) findViewById(R.id.rv_karts_list);
        getKarts();
        KartsListAdapter adapter = new KartsListAdapter(karts);
        kartRV.setAdapter(adapter);
        kartRV.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        kartRV.addItemDecoration(itemDecoration);
    }
    private void getKarts() {
        //TODO get actual karts from backend
        karts.add("Temp list 1");
        karts.add("Temp list 2");
        karts.add("Temp list 3");
    }
}
