package com.example.grokart.utils;

import android.content.ClipData;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grokart.R;

import java.util.ArrayList;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder> {
    private Context ct;
    private ArrayList<KartItemModel> itemSet;
    private String TAG;

    public ItemsAdapter(Context ct, ArrayList<KartItemModel> itemSet, String TAG) {
        this.ct = ct;
        this.itemSet = itemSet;
        this.TAG = TAG;
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(ct);
        View view = layoutInflater.inflate(R.layout.card_item_layout, parent, false);
        ItemsViewHolder itemRow = new ItemsViewHolder(view);
        int firstClick = 0;
        Log.d(TAG, "onCreateViewHolder: "+itemRow.maxQuantity.getText().toString());

        itemRow.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    itemRow.setQuantityAvailable(Integer.parseInt(itemRow.maxQuantity.getText().toString()));

                itemRow.incrementQuantity();
                itemRow.updateQuantityToBuy();
                Log.d(TAG, "onClick: "+ itemRow.maxQuantity);

            }
        });
        itemRow.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemRow.decrementQuantity();
                itemRow.updateQuantityToBuy();
            }
        });
        return new ItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {
        holder.name.setText(itemSet.get(position).getItemName());
        holder.price.setText(itemSet.get(position).getItemPrice());
        holder.quantityToBuy.setText(itemSet.get(position).getQuantityToBuy());
        holder.maxQuantity.setText(itemSet.get(position).getMaxQuantity());
    }

    @Override
    public int getItemCount() {
        return itemSet.size();
    }

    public class ItemsViewHolder extends RecyclerView.ViewHolder{
        TextView name, price, quantityToBuy,maxQuantity;
        ImageButton minus, add;
        Integer quantity, quantityAvailable;

        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);

            quantity = 0;
            quantityAvailable = 0;
            maxQuantity = itemView.findViewById(R.id.tv_quantityAvailable);

            name = itemView.findViewById(R.id.tv_itemName);
            price = itemView.findViewById(R.id.tv_price);
            quantityToBuy = itemView.findViewById(R.id.tv_quantity);
            minus = itemView.findViewById(R.id.btn_minus_sign);
            add = itemView.findViewById(R.id.btn_plus_sign);
        }

        public void setQuantityAvailable(Integer quantityAvailable) {
            this.quantityAvailable = quantityAvailable;
        }

        public void updateQuantityToBuy() {
            this.quantityToBuy.setText(this.quantity.toString());
        }
        public void incrementQuantity(){
            if(quantity < quantityAvailable){
                quantity++;
            }
        }
        public void decrementQuantity(){
            if(quantity > 0){
                quantity--;
            }
        }
    }
}
