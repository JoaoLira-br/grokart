package com.example.grokart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.grokart.app.AppController;
import com.example.grokart.utils.Const;
import com.example.grokart.utils.ReportsListAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Objects;

public class ReportsActivity extends AppCompatActivity {

        RecyclerView reportsRV;
        ArrayList<String> reports;
        String username;
        ReportsListAdapter adapter;
        TextView msgReponse;
        Button createReportBtn;
        private Toolbar myToolbar;
        private final String TAG = com.example.grokart.ReportsActivity.class.getSimpleName();
        private final String tag_json_arry = "jarray_req";
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_reports);
            // gets the user info from the main activity that sent us to this page
            Intent intent = getIntent();
            username = intent.getStringExtra("userName");
            //adds in updated toolbar
            myToolbar = findViewById(R.id.toolbar);
            setSupportActionBar(myToolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            //sets up the report stuff
            reports = new ArrayList<String>();
            reportsRV = (RecyclerView) findViewById(R.id.rv_reports);
            getReports();
            adapter = new ReportsListAdapter(reports);
            reportsRV.setAdapter(adapter);
            reportsRV.setLayoutManager(new LinearLayoutManager(this));
            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
            reportsRV.addItemDecoration(itemDecoration);
            msgReponse = findViewById(R.id.reportsMsgResponse);
            createReportBtn = findViewById(R.id.btn_new_report);
            createReportBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ReportsActivity.this, NewReportActivity.class);
                    intent.putExtra("userName", username);
                    startActivity(intent);
                    finish();
                }
            });
        }

        private void getReports() {
            JsonArrayRequest req = new JsonArrayRequest(Const.URL_REPORTS,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d(TAG, response.toString());
                            if(response.length() == 0) {
                                msgReponse.setText("No reports");
                            }
                            for(int i = 0; i < response.length(); i++) {
                                try {
                                    reports.add(response.getJSONObject(i).get("reportTitle").toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                }
            });
            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(req,
                    tag_json_arry);
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
                Intent intent = new Intent(ReportsActivity.this, MainActivity.class);
                intent.putExtra("userName", username);
                startActivity(intent);
                this.finish();
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

    }

