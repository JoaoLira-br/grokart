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

public class ReportsListAdapter extends RecyclerView.Adapter<ReportsListAdapter.ViewHolder>{
        // Provide a direct reference to each of the views within a data item
        // Used to cache the views within the item layout for fast access
        public class ViewHolder extends RecyclerView.ViewHolder {
            // Your holder should contain a member variable
            // for any view that will be set as you render a row
            public TextView reportName;
            public ImageButton reportInfoBtn;

            // We also create a constructor that accepts the entire item row
            // and does the view lookups to find each subview
            public ViewHolder(View itemView) {
                // Stores the itemView in a public final member variable that can be used
                // to access the context from any ViewHolder instance.
                super(itemView);

                reportName = (TextView) itemView.findViewById(R.id.tv_report_name);
                reportInfoBtn = (ImageButton) itemView.findViewById(R.id.ib_report_info);
            }
        }
        // Store a member variable for the reports
        private List<String> userReports;

        // Pass in the contact array into the constructor
        public ReportsListAdapter(List<String> reports) {
            userReports = reports;
        }

        // Usually involves inflating a layout from XML and returning the holder
        @Override
        public ReportsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            // Inflate the custom layout
            View contactView = inflater.inflate(R.layout.item_report, parent, false);

            // Return a new holder instance
            ReportsListAdapter.ViewHolder viewHolder = new ReportsListAdapter.ViewHolder(contactView);
            return viewHolder;
        }


    // Involves populating data into the item through holder
        @Override
        public void onBindViewHolder(ReportsListAdapter.ViewHolder holder, int position) {
            // Get the data model based on position
            String reportName = userReports.get(position);

            // Set item views based on your views and data model
            TextView textView = holder.reportName;
            textView.setText(reportName);
            ImageButton reportInfoBtn = holder.reportInfoBtn;
        }

        // Returns the total count of items in the list
        @Override
        public int getItemCount() {
            return userReports.size();
        }
}

