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
import com.example.grokart.utils.RecyclerItemClickListener;
import com.example.grokart.utils.ReportsListAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
* This class controls the app when the user wants to see their reports.
* @author Mattie McGovern
*/
public class ReportsActivity extends AppCompatActivity {

        RecyclerView reportsRV;
        ArrayList<String> reports;
        String username;
        ReportsListAdapter adapter;
        TextView msgReponse;
        Button createReportBtn;
        private HashMap<String, String> reportsHM;
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
            reportsHM = new HashMap<>();
            //sets up the report stuff
            reports = new ArrayList<String>();
            reportsRV = (RecyclerView) findViewById(R.id.rv_reports);
            getReports();
            adapter = new ReportsListAdapter(reports);
            reportsRV.setAdapter(adapter);
            reportsRV.setLayoutManager(new LinearLayoutManager(this));
            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
            reportsRV.addItemDecoration(itemDecoration);
            reportsRV.addOnItemTouchListener(
                    new RecyclerItemClickListener(this, reportsRV ,new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent intent = new Intent(ReportsActivity.this, ReportInfoActivity.class);
                            intent.putExtra("userName", username);
                            intent.putExtra("title", reports.get(position));
                            intent.putExtra("privilege", 0);
                            intent.putExtra("store", reportsHM.get(reports.get(position)));
                            startActivity(intent);
                        }

                        @Override
                        public void onLongItemClick(View view, int position) {
                            onItemClick(view, position);
                        }
                    })
            );
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

        /**
         * This method gets all previous reports from the backend.
         * It makes a get request that receives a JSONArray of the data,
         * then adds the title of the report to a string array.
        */
        private void getReports() {
            JsonArrayRequest req = new JsonArrayRequest(Const.URL_REPORTS + username,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d(TAG, response.toString());

                            if(response.length() == 0) {
                                msgReponse.setText("No reports");
                            }
                            for(int i = 0; i < response.length(); i++) {
                                try {
                                    String reportTitle = response.getJSONObject(i).get("reportTitle").toString();
                                    reports.add(reportTitle);
                                    String reportStore = response.getJSONObject(i).get("storeName").toString();
                                    reportsHM.put(reportTitle, reportStore);
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
                Intent intent = new Intent(ReportsActivity.this, SupportActivity.class);
                intent.putExtra("userName", username);
                intent.putExtra("privilege", 0);
                startActivity(intent);
                this.finish();
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

