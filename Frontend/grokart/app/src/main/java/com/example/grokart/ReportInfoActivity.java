package com.example.grokart;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.grokart.app.AppController;
import com.example.grokart.utils.Const;
import com.example.grokart.vRequests.DeleteRequest;
import com.example.grokart.vRequests.PostRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ReportInfoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    // initializing class fields
    private TextView tv_page_title;
    private TextView msgResponse;
    private TextView tv_report_title_heading;
    private TextView tv_report_title;
    private TextView tv_status_heading;
    private ImageView iv_status;
    private TextView tv_description_heading;
    private TextView tv_description;
    private TextView tv_comment_heading;
    private TextView et_comment;
    private Button btn_action;
    private Toolbar myToolbar;
    private String username;
    private String reportName;
    private String storeName;
    private int privilege;
    private JSONObject report;
    private ArrayList<String> statusArray;
    private Spinner statusMenu;
    String item = null;
    private final String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req", tag_string_req = "string_req";
    private final String TAG = ReportInfoActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_info);

        // getting the intent info sent from the previous page
        Intent intent = getIntent();
        username = intent.getStringExtra("userName");
        reportName = intent.getStringExtra("title");
        storeName = intent.getStringExtra("store");
        privilege = intent.getIntExtra("privilege",0);

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
        tv_description_heading = findViewById(R.id.tv_report_description_heading);
        tv_description = findViewById(R.id.tv_report_description);
        tv_comment_heading = findViewById(R.id.tv_comment_heading);
        et_comment = findViewById(R.id.et_comment);
        btn_action = findViewById(R.id.btn_action);
        statusMenu = findViewById(R.id.spinner2);

        // sets up the status menu
        statusArray = new ArrayList<String>();
        statusArray.add("In progress");
        statusArray.add("Complete");
        statusArray.add("Declined");
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, statusArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusMenu.setAdapter(adapter);
        statusMenu.setOnItemSelectedListener(this);

        // gets the report info
        getReport();
        tv_report_title.setText(reportName);

        // sets up the page depending on the privilege of the user
        if(privilege == 1){ // store admin
            statusMenu.setEnabled(true);
            et_comment.setEnabled(true);
            btn_action.setText(R.string.update);
            btn_action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String path = Const.URL_REPORTS + tv_report_title.getText() + "/" + storeName + "/status/" + statusMenu.getSelectedItem().toString();;
                    makeStringReq(path);
                    String commentPath = Const.URL_REPORTS + tv_report_title.getText() + "/" + storeName + "/comments/" + et_comment.getText();
                    makeStringReq(commentPath);
                }
            });
        }
        else { // base user
            statusMenu.setEnabled(false);
            et_comment.setEnabled(false);
            btn_action.setText(R.string.delete);
            Context context = this;
            btn_action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setCancelable(true);
                    builder.setTitle("Delete report");
                    builder.setMessage("Are you sure?");
                    builder.setPositiveButton("Confirm",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    String path = Const.URL_REPORTS + reportName+ "/" + storeName;
                                    System.out.println(path);
                                    deleteReport(path);

                                }
                            });
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //do nothing
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        }
    }
    protected void getReport() {
        JsonObjectRequest req = new JsonObjectRequest(Const.URL_REPORTS +reportName+"/"+storeName,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        report = response;
                        try {
                            String status = report.getString("reportStatus").toString();
                            updateStatusImage(status);
                            tv_description.setText(report.getString("description").toString());
                            et_comment.setText(report.getString("comments").toString());
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
    private void updateStatusImage(String status) {
        if(status.equals(statusArray.get(0))) {
            statusMenu.setSelection(0);
            iv_status.setImageResource(R.drawable.ic_pending);
        }
        else if(status.equals(statusArray.get(1))) {
            statusMenu.setSelection(1);
            iv_status.setImageResource(R.drawable.ic_complete);
        }
        else {
            statusMenu.setSelection(2);
            iv_status.setImageResource(R.drawable.ic_canceled);
        }
    }

    private void makeStringReq(String path) {
        StringRequest strReq = new StringRequest(Request.Method.PUT, path, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                msgResponse.setText("Update successful.");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
            @Override
            protected Map<String, String> getParams()
            {
                return new HashMap<String, String>();
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void deleteReport(String path) {
        StringRequest deleteReport = new StringRequest(Request.Method.DELETE, path, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                msgResponse.setText(response);
                System.out.println(response);
                Intent intent = new Intent(ReportInfoActivity.this, ReportsActivity.class);
                intent.putExtra("userName", username);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                System.out.println(error.getMessage());
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(deleteReport, tag_string_req);
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
            if(privilege == 1) {
                Intent intent = new Intent(ReportInfoActivity.this, StoreReportsActivity.class);
                intent.putExtra("userName", username);
                startActivity(intent);
                this.finish();
                return true;
            }
            else {
                Intent intent = new Intent(ReportInfoActivity.this, ReportsActivity.class);
                intent.putExtra("userName", username);
                startActivity(intent);
                this.finish();
                return true;
            }

        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * This method sets what happens when an item is selected in the status dropdown.
     * Item is set to whatever was selected.
     * @param parent the adapter view where the selection happened
     * @param view the view within the adapter view where the selection happened
     * @param position the position of the selected item in the array
     * @param id the row of the selected item
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = parent.getItemAtPosition(position).toString();
        updateStatusImage(statusArray.get(position));

    }

    public void onNothingSelected(AdapterView<?> arg0) {}
}
