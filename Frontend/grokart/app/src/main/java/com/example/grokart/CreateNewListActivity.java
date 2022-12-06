package com.example.grokart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grokart.utils.OnItemsClickListener;
import com.example.grokart.vRequests.*;
import com.example.grokart.utils.Const;
import com.example.grokart.utils.ItemsAdapter;
import com.example.grokart.utils.KartItemModel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**@author Joao Victor Lira*/
public class CreateNewListActivity extends AppCompatActivity {
    private String path, preferredStore, userName, kartName;
    private Button btn_viewStoreItems;
    private ImageButton imgBtn_viewKart;
    private EditText et_nameYourKart;
    private TextView tv_totalPrice;
    private double totalPrice;
    private int numberItems;
    private RecyclerView itemsRecyclerView;
    private ItemsAdapter itemsAdapter;
    private ArrayList<KartItemModel> storeItems, userKartItems;
    private final String TAG = CreateNewListActivity.class.getSimpleName();
    private final Context ct = CreateNewListActivity.this;

    private Toolbar myToolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_list);
        final Intent intentCreateNewList = getIntent();
        preferredStore = intentCreateNewList.getStringExtra("preferredStore");
        Log.d(TAG, "onCreate: preferredStore" + preferredStore);
        userName = intentCreateNewList.getStringExtra("username");
        storeItems = new ArrayList<>();
        userKartItems = new ArrayList<>();
        btn_viewStoreItems = findViewById(R.id.btn_viewStoreItems);
        itemsRecyclerView = findViewById(R.id.rv_storeItems);
        imgBtn_viewKart = findViewById(R.id.imgBtn_viewKart);
        et_nameYourKart = findViewById(R.id.et_nameYourKart);
        tv_totalPrice = findViewById(R.id.tv_totalPrice);
        totalPrice = 0.0;
        tv_totalPrice.append(" " + totalPrice);


        Log.d(TAG, "onCreate - storeItems"+ storeItems);

        Log.d(TAG, "Before populateRows: storeItems "+ storeItems);
        populateRows("item", preferredStore);
        Log.d(TAG, "After populateRows: storeItems "+ storeItems);

        //adds in updated toolbar
        myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        /**on click fills the fills the recycler views with the items from the ArrayList storeItems by attaching an adapter with kartItemModels*/
        btn_viewStoreItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemsAdapter = new ItemsAdapter(ct, getStoreItems(), TAG);
                Log.d(TAG, "onClick: getStoreItems(): "+getStoreItems());
                itemsRecyclerView.setAdapter(itemsAdapter);
                itemsRecyclerView.setLayoutManager(new LinearLayoutManager(ct));
//                OnItemsClickListener listenerOnPlus = new OnItemsClickListener() {
//                    @Override
//                    public void onItemClick(KartItemModel kartItemModel) {
//
//                    }
//                };
//                OnItemsClickListener listenerOnMinus = new OnItemsClickListener() {
//                    @Override
//                    public void onItemClick(KartItemModel kartItemModel) {
//
//                    }
//                };
                itemsAdapter.setWhenClickListener(new OnItemsClickListener() {
                    @Override
                    public void onItemClick(KartItemModel kartItemModel) {
                        totalPrice += Double.parseDouble(kartItemModel.getItemPrice().substring(2));
                        tv_totalPrice.setText("$ " + Double.toString(totalPrice));

                    }
                }, new OnItemsClickListener() {
                    @Override
                    public void onItemClick(KartItemModel kartItemModel) {
                        totalPrice -= Double.parseDouble(kartItemModel.getItemPrice().substring(2));
                        tv_totalPrice.setText("$ " + Double.toString(totalPrice));


                    }
                });


            }
        });
        imgBtn_viewKart.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(et_nameYourKart.getText().toString().isEmpty()){
                    et_nameYourKart.setError("Enter a name for your kart");
                }else{
                    kartName = et_nameYourKart.getText().toString();
                    fillUserKart();
                    navigateToViewList();
                }
            }
        });
    }



    public ArrayList<KartItemModel> getStoreItems() {
        return storeItems;
    }
    public void fillUserKart(){
        for (int i = 0; i < itemsRecyclerView.getChildCount(); i++) {
            ItemsAdapter.ItemViewHolder holder = (ItemsAdapter.ItemViewHolder) itemsRecyclerView.findViewHolderForAdapterPosition(i);
            assert holder != null;
            if(holder.getQuantityToBuy()>0){
                userKartItems.add(getItemInfo(holder));
                numberItems++;
            }
        }

        Log.d(TAG, "imgBtn_viewKart onClick: "+userKartItems);
    }
    public void navigateToViewList(){

        Intent intent = new Intent(this, ViewListDetailsActivity.class);
        intent.putParcelableArrayListExtra("kartItems", userKartItems);
        intent.putExtra("kartName", kartName);
        intent.putExtra("kartPrice", Double.toString(totalPrice));
        intent.putExtra("numberOfItems", Integer.toString(numberItems));
//        intent.putExtra("BUNDLE",args);
        startActivity(intent);

////         TODO: send intents through a bundle and using serializable to retrieve it later
//         intent.putExtra("kartName",et_nameYourKart.getText().toString());
//         intent.putExtra("kartItems",userKartItems );
//         startActivity(intent);
    }
    public KartItemModel getItemInfo(ItemsAdapter.ItemViewHolder holder){
        String quantityToBuy = String.valueOf(holder.getQuantityToBuy());
        String itemName = holder.getItemName();
        String price = holder.getPrice().substring(2);
        String maxQuantity = holder.getMaxQuantity();
        return new KartItemModel(itemName, price, quantityToBuy, maxQuantity);
    }


    /**@param objectToStore is the name of the objects which the response sends. The name is of the developer`s choice.
     *The name will reflect later when the developer decides to make Models out of it
     * @param preferredStore is the user`s preferred store, used to properly populate the recycler view`s rows with the items from the respective store
     * it fills the ArrayList storeItems which is then used to fill the recycler view
     *                     */
    public void populateRows(String objectToStore, String preferredStore) {
        GetRequest getStoreItems;
        if(preferredStore == null){
            getStoreItems = new GetRequest(Const.URL_STORE_ITEMS, TAG);
            Log.d(TAG, "populateRows: parei no null");
        }else{
            getStoreItems = new GetRequest(Const.URL_STORE_ITEMS+preferredStore, TAG);
            Log.d(TAG, "populateRows: path "+Const.URL_STORE_ITEMS+preferredStore);
            Log.d(TAG, "populateRows: parei no else not null");
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





