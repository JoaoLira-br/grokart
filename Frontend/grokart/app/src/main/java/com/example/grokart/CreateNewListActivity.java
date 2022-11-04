package com.example.grokart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grokart.Requests.*;
import com.example.grokart.utils.Const;
import com.example.grokart.utils.ItemsAdapter;
import com.example.grokart.utils.KartItemModel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class CreateNewListActivity extends AppCompatActivity {
    private String path;
    private Button btn_viewStoreItems;
    private EditText et_search;
    private RecyclerView itemsRecyclerView;
    private ItemsAdapter itemsAdapter;
    private ArrayList<KartItemModel> storeItems;
    private final String TAG = CreateNewListActivity.class.getSimpleName();
    private final Context ct = CreateNewListActivity.this;
    private final Intent intent = getIntent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_list);
        storeItems = new ArrayList<>();
        btn_viewStoreItems = findViewById(R.id.btn_viewStoreItems);
        Log.d(TAG, "onCreate - storeItems"+ storeItems);
        itemsRecyclerView = findViewById(R.id.rv_storeItems);
        et_search = findViewById(R.id.et_searchBar);
        populateRows("item");
        btn_viewStoreItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemsAdapter = new ItemsAdapter(ct, getStoreItems(), TAG);
                itemsRecyclerView.setAdapter(itemsAdapter);
                itemsRecyclerView.setLayoutManager(new LinearLayoutManager(ct));
            }
        });


        et_search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    itemsAdapter = new ItemsAdapter(ct, getStoreItems(), TAG);
                itemsRecyclerView.setAdapter(itemsAdapter);
                 itemsRecyclerView.setLayoutManager(new LinearLayoutManager(ct));
                 return true;
                }
                return false;
            }
        });

    }

    public void setPath() {

        path = et_search.getText().toString();
    }

    public ArrayList<KartItemModel> getStoreItems() {
        return storeItems;
    }

    public void populateRows(String objectToStore) {

        GetRequest getStoreItems = new GetRequest(Const.URL_WALMART_ITEMS, TAG);
        Thread arrayRequest = getStoreItems.createArrayRequestThread(objectToStore);
        Thread handleResponse = getStoreItems.createResponseHandler(() -> {
            HashMap<String, JSONObject> storeItemsHash = getStoreItems.getResponseArrayHM();
            Log.d(TAG, "InHandleResponseget - StoreItems.getResponseArrayHM(); "+ storeItemsHash);
            Log.d(TAG, "InHandleResponse - storeItemsHash.size() "+storeItemsHash.size());
            Log.d(TAG, "InHandleResponse - storeItemsHash.get(\"item1\").entrySet()"+storeItemsHash.get("item1").keys());
            JSONObject itemHash;
            String key;
            String itemName = null;
            String itemPrice = null;
            String maxQuantity = null;
            int i = 0;
            int itemIndex = i;
            while(i < storeItemsHash.size() ){
                itemIndex = i+1;
                key = objectToStore+itemIndex;
                Log.d(TAG, "InHandleResponse - itemOffset "+itemIndex);
                itemHash = storeItemsHash.get(key);
                try {
                    itemName = itemHash.get("name").toString();
                    itemPrice = itemHash.get("price").toString();
                    Log.d(TAG, "populateRows:itemHash.get(\"quantity\"): "+itemHash.get("quantity"));
                    maxQuantity = itemHash.get("quantity").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                storeItems.add(new KartItemModel(itemName, itemPrice, "0", maxQuantity));
                i++;
            }
        });
        arrayRequest.start();
        try {
            arrayRequest.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        handleResponse.start();

    }
        }





