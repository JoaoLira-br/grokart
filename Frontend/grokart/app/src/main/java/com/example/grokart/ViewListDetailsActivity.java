package com.example.grokart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.grokart.utils.ItemsAdapter;
import com.example.grokart.utils.KartItemModel;
import com.example.grokart.utils.OnItemsClickListener;
import com.example.grokart.vRequests.PostRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewListDetailsActivity extends AppCompatActivity {
    private String kartName;
    private ArrayList<KartItemModel> kartItems;
    private Double kartPrice;
    private int numberOfItems; //number of items
    private JSONArray savedKartItems; //final kart items to be saved in the backend
    private JSONObject savedKart; //final kart to be saved
    private final String TAG = ViewListDetailsActivity.class.getSimpleName();
    private ImageButton btnSaveKart;
    private RecyclerView rvKartItems;
    private TextView tvKartName, tvTotalPrice, tvTotalItems;
    private final Context ct = ViewListDetailsActivity.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list_details);
        Intent intent = getIntent();
        btnSaveKart = findViewById(R.id.imgBtn_saveKart);
        rvKartItems = findViewById(R.id.rv_kartItems);
        tvKartName = findViewById(R.id.tv_kart_name);
        tvTotalPrice = findViewById(R.id.tv_finalPrice);
        tvTotalItems = findViewById(R.id.tv_totalItems);
        kartItems = intent.getParcelableArrayListExtra("kartItems");

        kartName = intent.getStringExtra("kartName");
        kartPrice = Double.parseDouble(intent.getStringExtra("kartPrice"));
        numberOfItems = Integer.parseInt(intent.getStringExtra("numberOfItems"));
        Log.d(TAG, "onCreate: "+ kartPrice + " "+ numberOfItems);
        savedKart = new JSONObject();
        savedKartItems = new JSONArray();
        tvKartName.setText(kartName);
        tvTotalPrice.append(" " + Double.toString(kartPrice));
        tvTotalItems.append(" " + Integer.toString(numberOfItems));
        Log.d(TAG, "onCreate: kartItems: " + kartItems + " " + kartName);

        setupRV();

       btnSaveKart.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               try {
                   fillUserKart();
                   saveKart();
               } catch (JSONException e) {
                   e.printStackTrace();
               }
               Log.d(TAG, "onClick: savedKart: "+ savedKart);
           }
       });

//        Bundle args = intent.getBundleExtra("BUNDLE");
//        ArrayList<KartItemModel> kartItems = (ArrayList<KartItemModel>) args.getSerializable("kartItems");
//        kartName = args.getString("kartName");

//        kartName = intent.getStringExtra("kartName");
//        kartItems = (ArrayList<KartItemModel>) intent.getSerializableExtra("kartItems");

    }
    public void fillUserKart() throws JSONException {

        savedKart.put("kartName", kartName);
        savedKart.put("kartPrice", kartPrice);


        for (int i = 0; i < rvKartItems.getChildCount(); i++) {
            ItemsAdapter.ItemViewHolder holder = (ItemsAdapter.ItemViewHolder) rvKartItems.findViewHolderForAdapterPosition(i);

            assert holder != null;
            if(holder.getQuantityToBuy()>0){
                Log.d(TAG, "fillUserKart: getItemInfo: "+ getItemInfo(holder));
               savedKartItems.put(getItemInfo(holder));
                Log.d(TAG, "fillUserKart: savedKartItems: "+savedKartItems);

            }
        }
        savedKart.put("kartItems", savedKartItems);

        Log.d(TAG, "imgBtn_viewKart onClick: " + rvKartItems);
    }
    public JSONObject getItemInfo(ItemsAdapter.ItemViewHolder holder) throws JSONException {
        String quantityToBuy = String.valueOf(holder.getQuantityToBuy());
        String itemName = holder.getItemName();
        String price = holder.getPrice().substring(2);
        Log.d(TAG, "getItemInfo: price: " + price);
        String maxQuantity = holder.getMaxQuantity();
        String jsonString = String.format("{\"itemName\":%1$s,\"price\":%2$s,\"quantityToBuy\":%3$s,\"maxQuantity\":%4$s}", itemName, price, quantityToBuy, maxQuantity);
        Log.d(TAG, "getItemInfo: json:"+ jsonString);
        return new JSONObject(jsonString);
    }
    public void setupRV(){
        ItemsAdapter itemsAdapter = new ItemsAdapter(ct, kartItems, TAG);
        rvKartItems.setAdapter(itemsAdapter);
        rvKartItems.setLayoutManager(new LinearLayoutManager(ct));
        itemsAdapter.setWhenClickListener(new OnItemsClickListener() {
            @Override
            public void onItemClick(KartItemModel kartItemModel) {
                kartPrice += Double.parseDouble(kartItemModel.getItemPrice().substring(2));
                tvTotalPrice.setText(String.format("Total Price: $ %1$s", kartPrice));

            }
        }, new OnItemsClickListener() {
            @Override
            public void onItemClick(KartItemModel kartItemModel) {
                kartPrice -= Double.parseDouble(kartItemModel.getItemPrice().substring(2));
                tvTotalPrice.setText(String.format("Total Price: $ %1$s", kartPrice));
            }
        });
    }
    public void saveKart(){
        //TODO: change path to appropriate endpoint
        PostRequest pr = new PostRequest("somepath",TAG, savedKart);
        pr.getRequestThread().start();
        pr.createResponseHandler(()->{
            pr.getResponseHM().get("response");
            //TODO: if response successful send user back to main page
        }).start();
    }
}