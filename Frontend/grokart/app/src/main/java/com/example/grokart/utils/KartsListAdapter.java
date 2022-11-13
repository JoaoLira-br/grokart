package com.example.grokart.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.grokart.*;

import java.util.List;

public class KartsListAdapter extends RecyclerView.Adapter<KartsListAdapter.ViewHolder>{
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView kartName;
        public ImageButton kartInfoBtn;
        public ImageButton editKartBtn;
        public ImageButton deleteKartBtn;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            kartName = (TextView) itemView.findViewById(R.id.tv_kart_name);
            kartInfoBtn = (ImageButton) itemView.findViewById(R.id.ib_kart_info);
            editKartBtn = (ImageButton) itemView.findViewById(R.id.ib_edit_kart);
            deleteKartBtn = (ImageButton) itemView.findViewById(R.id.ib_delete_kart);
        }
    }
    // Store a member variable for the contacts
    private List<String> userKarts;

    // Pass in the contact array into the constructor
    public KartsListAdapter(List<String> karts) {
        userKarts = karts;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public KartsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_kart, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(KartsListAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        String kartName = userKarts.get(position);

        // Set item views based on your views and data model
        TextView textView = holder.kartName;
        textView.setText(kartName);
        ImageButton kartInfoBtn = holder.kartInfoBtn;
        ImageButton editKartBtn = holder.editKartBtn;
        ImageButton deleteKartBtn = holder.deleteKartBtn;
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return userKarts.size();
    }
}
