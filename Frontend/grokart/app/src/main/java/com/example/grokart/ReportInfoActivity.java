package com.example.grokart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.grokart.app.AppController;
import com.example.grokart.utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class ReportInfoActivity extends AppCompatActivity {
    private TextView tv_page_title;
    private TextView msgResponse;
    private TextView tv_report_title_heading;
    private TextView tv_report_title;
    private TextView tv_status_heading;
    private TextView tv_status;
    private ImageView iv_status;
    private TextView tv_description_heading;
    private TextView tv_description;
    private TextView tv_message_heading;
    private TextView tv_message;
    private Toolbar myToolbar;
    private String username;
    private String reportName;
    private String storeName;
    private JSONObject report;
    private final String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";
    private final String TAG = ReportInfoActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_info);
        // gets the user info from the main activity that sent us to this page
        Intent intent = getIntent();
        username = intent.getStringExtra("userName");
        reportName = intent.getStringExtra("reportName");
        storeName = intent.getStringExtra("storeName");
        //adds in updated toolbar
        myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // sets the text and button objects here to their matched pair in the layout
        tv_page_title = findViewById(R.id.tv_reports_page_title);
        msgResponse = findViewById(R.id.reportsMsgResponse);
        tv_report_title_heading = findViewById(R.id.tv_report_title_heading);
        tv_report_title = findViewById(R.id.tv_report_title);
        tv_status_heading = findViewById(R.id.tv_report_status_heading);
        iv_status = findViewById(R.id.imageView);
        tv_status = findViewById(R.id.tv_report_status);
        tv_description_heading = findViewById(R.id.tv_report_description_heading);
        tv_description = findViewById(R.id.tv_report_description);
        tv_message_heading = findViewById(R.id.tv_report_message_heading);
        tv_message = findViewById(R.id.tv_report_message);
        getReport();
        tv_report_title.setText(reportName);


    }
    protected void getReport() {
        JsonObjectRequest req = new JsonObjectRequest(Const.URL_REPORTS +reportName+"/"+storeName,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        report = response;
                        try {
                            tv_status.setText(report.getString("reportStatus").toString());
                            tv_description.setText(report.getString("description").toString());
                            tv_message.setText(report.getString("comments").toString());
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
    }
    /**
     * This method is necessary when creating a toolbar for the edit profile page.
     * It uses the menu layout stored in res/menu/menu_back_to_main.xml
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_back_to_main, menu);
        return true;
    }

    /**
     * This method checks to see if any of the toolbar options were selected,
     * and then performs the appropriate action.
     * @param item is the selected MenuItem
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // If back button clicked
        if (item.getItemId() == android.R.id.home) {// Start home intent and finish this intent
            Intent intent = new Intent(ReportInfoActivity.this, StoreReportsActivity.class);
            intent.putExtra("userName", username);
            startActivity(intent);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
