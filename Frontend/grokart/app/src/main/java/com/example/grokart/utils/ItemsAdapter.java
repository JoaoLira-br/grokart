package com.example.grokart.utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grokart.*;


import java.util.ArrayList;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {
    private Context ct;
    private ArrayList<KartItemModel> itemSet;
    private String TAG;
    private OnItemsClickListener listenerPlus;
    private OnItemsClickListener listenerMinus;


    public ItemsAdapter(Context ct, ArrayList<KartItemModel> itemSet, String TAG) {
        this.ct = ct;
        this.itemSet = itemSet;
        this.TAG = TAG;

    }

    public void setWhenClickListener(OnItemsClickListener listenerPlus, OnItemsClickListener listenerMinus){
        this.listenerPlus = listenerPlus;
        this.listenerMinus = listenerMinus;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(ct);
        View view = layoutInflater.inflate(R.layout.card_item_layout, parent, false);
//        ItemViewHolder itemRow = new ItemViewHolder(view);
        int firstClick = 0;
//        itemRow.btn_plusSign.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.setQuantityAvailable(Integer.parseInt(holder.tv_maxQuantity.getText().toString()));
//                holder.incrementQuantity();
//                holder.updateQuantityToBuy();
//                Log.d(TAG, "onClick: "+ holder.tv_maxQuantity);
//
//            }
//        });
//        itemRow.btn_minusSign.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.decrementQuantity();
//                holder.updateQuantityToBuy();
//            }
//        });
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        final KartItemModel kartItemModel = itemSet.get(position);
        holder.tv_itemName.setText(kartItemModel.getItemName());
        holder.tv_price.setText(kartItemModel.getItemPrice());
        holder.tv_quantityToBuy.setText(kartItemModel.getQuantityToBuy());
        holder.tv_maxQuantity.setText(kartItemModel.getMaxQuantity());
        holder.setQuantity(holder, Integer.parseInt(kartItemModel.getQuantityToBuy()) );
        Log.d(TAG, "onBindViewHolder: holder.getQuantityToBuy()" + holder.getQuantityToBuy());
        holder.btn_plusSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.isWithinLimit(kartItemModel, v)){
                    holder.incrementQuantity(holder);
                    if(listenerPlus != null){
                        listenerPlus.onItemClick(kartItemModel);
                    }
                };

             
            }
        });
        holder.btn_minusSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.isWithinLimit(kartItemModel, v)){
                holder.decrementQuantity(holder);
                    if(listenerMinus != null){
                        listenerMinus.onItemClick(kartItemModel);
                    }
                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return itemSet.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView tv_itemName, tv_price, tv_quantityToBuy, tv_maxQuantity;
        ImageButton btn_minusSign, btn_plusSign;
        Integer quantity, quantityAvailable;
        Boolean wasZero = true;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

//            quantity = Integer.parseInt(tv_quantityToBuy.getText().toString());

            tv_maxQuantity = itemView.findViewById(R.id.tv_quantityAvailable);

            tv_itemName = itemView.findViewById(R.id.tv_itemName);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_quantityToBuy = itemView.findViewById(R.id.tv_quantityToBuy);
            btn_minusSign = itemView.findViewById(R.id.btn_minus_sign);
            btn_plusSign = itemView.findViewById(R.id.btn_plus_sign);

            //TODO: Possibly redundant code, remove it afterwards
            if(tv_quantityToBuy.getText().toString().equals("")){
                quantity = 0;
                Log.d(TAG, "ItemViewHolder: quantity = 0");
            }else{
                quantity = Integer.parseInt(tv_quantityToBuy.getText().toString());
                Log.d(TAG, "ItemViewHolder: quantity = outra coisa "+quantity);
            }
            //
        }
        public int getQuantityToBuy(){ return Integer.parseInt(this.tv_quantityToBuy.getText().toString());}

        public boolean isWithinLimit(KartItemModel kartItemModel, View v){
            Log.d(TAG, "isWithinLimit: quantity" + quantity);
            Boolean isWithinLimit = false;
            int maxItemQuantity = Integer.parseInt(kartItemModel.getMaxQuantity());
            Log.d(TAG, "isWithinLimit: v.getId(): "+ v.getId() + " "+R.id.btn_minus_sign + " "+ R.id.btn_plus_sign);
            switch(v.getId()){
                case R.id.btn_minus_sign:
                    if(quantity > 0){
                        isWithinLimit = true;
                    };
                    break;
                case R.id.btn_plus_sign:
                    if(quantity < maxItemQuantity){
                        isWithinLimit = true;
                    }
                    break;
            }
            return isWithinLimit;
        }
        public void setQuantity(ItemViewHolder holder, int quantity){
            holder.quantity = quantity;
        }
        public void incrementQuantity(ItemViewHolder holder){
            if(quantity == 0){
                wasZero = true;
            }
                quantity++;
                holder.tv_quantityToBuy.setText(this.quantity.toString());

        }
        public void decrementQuantity(ItemViewHolder holder){
                quantity--;
                if(quantity==-1){
                    quantity=0;
                }
                holder.tv_quantityToBuy.setText(this.quantity.toString());

        }
        public String getItemName(){
            return tv_itemName.getText().toString();
        }

        public String getPrice() {
            return tv_price.getText().toString();
        }

        public String getMaxQuantity() {
            return tv_maxQuantity.getText().toString();
        }
    }
}
