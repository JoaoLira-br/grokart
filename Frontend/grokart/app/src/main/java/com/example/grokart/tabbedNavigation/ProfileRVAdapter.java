package com.example.grokart.tabbedNavigation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grokart.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileRVAdapter extends RecyclerView.Adapter<ProfileRVAdapter.ViewHolder> {
    // creating variables for context and array list.
    private Context context;
    private ArrayList<JSONObject> profilesArrayList;

    // creating a constructor
    public ProfileRVAdapter(Context context, ArrayList<JSONObject> profilesArrayList) {
        this.context = context;
        this.profilesArrayList = profilesArrayList;
    }

    @NonNull
    @Override
    public ProfileRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        return new ProfileRVAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.profile_rv_item, parent, false));

    }

    // below method is use for filtering data in our array list
    public void filterList(ArrayList<JSONObject> filterlist) {
        // on below line we are passing filtered
        // array list in our original array list
        profilesArrayList = filterlist;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileRVAdapter.ViewHolder holder, int position) {
        // getting data from array list in our modal.
        JSONObject profile = profilesArrayList.get(position);
        // on below line we are setting data to our text view.
        try {
            holder.contactTV.setText(profile.get("UserName").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // on below line we are adding on click listener to our item of recycler view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are opening a new activity and passing data to it.
                Intent i = new Intent(context, SearchFragment.class);
                try {
                    i.putExtra("name", profile.get("username").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // on below line we are starting a new activity,
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return profilesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // on below line creating a variable
        // for our image view and text view.
        private ImageView contactIV;
        private TextView contactTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our image view and text view.
            contactIV = itemView.findViewById(R.id.IVContact);
            contactTV = itemView.findViewById(R.id.TVContactName);
        }
    }

}
