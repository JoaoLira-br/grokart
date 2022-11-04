package com.example.grokart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;


public class CreateNewListActivity extends AppCompatActivity {
    private String path, preferredStore, userName;
    private Button btn_viewStoreItems;
    private EditText et_search;
    private RecyclerView itemsRecyclerView;
    private ItemsAdapter itemsAdapter;
    private ArrayList<KartItemModel> storeItems;
    private final String TAG = CreateNewListActivity.class.getSimpleName();
    private final Context ct = CreateNewListActivity.this;

    private Toolbar myToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_list);
        final Intent intentCreateNewList = getIntent();
        preferredStore = intentCreateNewList.getStringExtra("preferredStore");
        userName = intentCreateNewList.getStringExtra("username");
        storeItems = new ArrayList<>();
        btn_viewStoreItems = findViewById(R.id.btn_viewStoreItems);
        Log.d(TAG, "onCreate - storeItems"+ storeItems);
        itemsRecyclerView = findViewById(R.id.rv_storeItems);
        et_search = findViewById(R.id.et_searchBar);
        populateRows("item", preferredStore);

        //adds in updated toolbar
        myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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

    public void populateRows(String objectToStore, String preferredStore) {
        GetRequest getStoreItems;
        if(preferredStore.equals("null")|| preferredStore == null){
            getStoreItems = new GetRequest(Const.URL_STORE_ITEMS, TAG);
        }else{
            getStoreItems = new GetRequest(Const.URL_STORE_ITEMS+preferredStore, TAG);
        }
        Thread arrayRequest = getStoreItems.createArrayRequestThread(objectToStore);
        Thread handleResponse = getStoreItems.createResponseHandler(() -> {
            HashMap<String, JSONObject> storeItemsHash = getStoreItems.getResponseArrayHM();
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

    /*
     * This method is necessary when creating a toolbar for the edit profile page.
     * It uses the menu layout stored in res/menu/menu_back_to_main.xml
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_back_to_main, menu);
        return true;
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
            Intent intent = new Intent(CreateNewListActivity.this, MainActivity.class);
            intent.putExtra("userName", userName);
            startActivity(intent);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}





