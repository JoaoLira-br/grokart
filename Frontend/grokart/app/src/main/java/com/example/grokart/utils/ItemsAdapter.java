package com.example.grokart.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder> {
    private Context ct;
    private ArrayList<KartItemModel> itemSet;

    public ItemsAdapter(Context ct, ArrayList<KartItemModel> itemSet) {
        this.ct = ct;
        this.itemSet = itemSet;
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ItemsViewHolder extends RecyclerView.ViewHolder{
        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
