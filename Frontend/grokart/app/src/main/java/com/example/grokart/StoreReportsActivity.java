package com.example.grokart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.grokart.app.AppController;
import com.example.grokart.utils.Const;
import com.example.grokart.utils.RecyclerItemClickListener;
import com.example.grokart.utils.ReportsListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class StoreReportsActivity extends AppCompatActivity {
    private TextView tv_title;
    private TextView msgResponse;
    private RecyclerView reportsRV;
    ArrayList<String> reports;
    ReportsListAdapter adapter;
    private Toolbar myToolbar;
    private final String TAG = com.example.grokart.ReportsActivity.class.getSimpleName();
    private final String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";
    private String store;
    private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_reports);
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
        reportsRV.addOnItemTouchListener(
                new RecyclerItemClickListener(this, reportsRV ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(StoreReportsActivity.this, ReportInfoActivity.class);
                        intent.putExtra("userName", username);
                        intent.putExtra("store", store);
                        intent.putExtra("title", reports.get(position));
                        intent.putExtra("privilege", 1);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        onItemClick(view, position);
                    }
                })
        );

        adapter = new ReportsListAdapter(reports);
        reportsRV.setAdapter(adapter);
        reportsRV.setLayoutManager(new LinearLayoutManager(this));
        getStore();
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        reportsRV.addItemDecoration(itemDecoration);
        msgResponse = findViewById(R.id.reportsMsgResponse);

    }

    protected void getStore() {
        JsonObjectRequest req = new JsonObjectRequest(Const.URL_USER_INFO + username,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        System.out.println(response.toString());
                        try {
                            store = response.getString("preferredStore").toString();
                            getReports();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req,
                tag_json_obj);
        System.out.println(store);

    }

    /**
     * This method gets all previous reports from the backend.
     * It makes a get request that receives a JSONArray of the data,
     * then adds the title of the report to a string array.
     */
    private void getReports() {
        String url = Const.URL_REPORTS +"store/" + store;
        System.out.println(url);
        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        if(response.length() == 0) {
                            msgResponse.setText("No reports");
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
            Intent intent = new Intent(StoreReportsActivity.this, StoreAdminHomeActivity.class);
            intent.putExtra("userName", username);
            startActivity(intent);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
