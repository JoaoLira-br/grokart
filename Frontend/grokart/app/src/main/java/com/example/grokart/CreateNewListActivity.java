package com.example.grokart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grokart.Requests.*;
import com.example.grokart.utils.Const;
import com.example.grokart.utils.KartItemModel;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class CreateNewListActivity extends AppCompatActivity {
    private String path;
    private EditText et_search;
    private ArrayList<KartItemModel> storeItems;
    private final String TAG = CreateNewListActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_list);
        et_search = findViewById(R.id.et_searchBar);

        et_search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER) {
                    if(et_search.getText().toString().isEmpty()){
                        return false;
                    }else{

                        return true;
                    }
                }
                return false;
            }
        });
        
    }
    public void setPath(){

        path = et_search.getText().toString();
    }
    public void populateRows(){

        //TODO: Conferir com o Charlie o Path correto de store items
        GetRequest getStoreItems = new GetRequest(Const.URL_STORE_ITEMS, TAG);
        getStoreItems.createArrayRequestThread("item");
        HashMap<String,HashMap> storeItems = getStoreItems.getResponseArrayHM();
        HashMap itemHash;
        String key;
        int i = 1;
        while(i < storeItems.size() + 1 ){
            itemHash = storeItems.get("item"+i);
                //TODO: perguntar Charlie qual e o formato de cada item em JSON
//                String itemName = itemHash.get("name");
//                String itemPrice = itemHash.get("price");
//                storeItems.add(new KartItemModel(itemName, itemPrice, "0"));
            }
            i++;
        }

    }

