package com.example.grokart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.grokart.Requests.PostRequest;
import com.example.grokart.app.AppController;
import com.example.grokart.utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
/**
* This class controls the app when a user wants to make a new report.
 * @author Mattie McGovern
*/
public class NewReportActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
        private EditText et_title, et_description;
        private final String TAG = com.example.grokart.NewReportActivity.class.getSimpleName();
        private TextView msgResponse;
        private String username = "";
        private Toolbar myToolbar;
        // These tags will be used to cancel the requests
        private final String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";
        private String tag_string_req = "string_req";
        private ArrayList<String> storesArray;
        private Spinner storesMenu;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // sets the view to the right layout
            setContentView(R.layout.activity_new_report);
            // gets the user info from the main activity that sent us to this page
            Intent intent = getIntent();
            username = intent.getStringExtra("userName");
            //adds in updated toolbar
            myToolbar = findViewById(R.id.toolbar);
            setSupportActionBar(myToolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            // sets the text and button objects here to their matched pair in the layout
            et_title = findViewById(R.id.et_title);
            et_description = findViewById(R.id.et_description);
            msgResponse = findViewById(R.id.msgResponse);
            //sets up the stores menu stuff
            storesMenu = findViewById(R.id.spinner);
            storesArray = new ArrayList<String>();
            storesArray.add("Select store");
            getStores();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, storesArray);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            storesMenu.setAdapter(adapter);

            Button btn_createReport = findViewById(R.id.btn_createReport);
            btn_createReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    msgResponse.setText("");
                    sendReport();
                }
            });
        }
        /**
        * This method sends the user report to the backend using a post request.
         */
    private void sendReport() {
        if(checkInputs()) {
            JSONObject report = createReport();
            PostRequest postRequest = new PostRequest(Const.URL_REPORTS, TAG, report);
            Thread reportRequest = postRequest.getRequestThread();
            Thread reportResponse = postRequest.createResponseHandler(()->{
                String response = String.valueOf(postRequest.getResponseHM().get("message"));
                msgResponse.setText(response);
            });

            reportRequest.start();
            try {
                reportRequest.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            reportResponse.start();
        }
    }
    /**
    * This method checks that all of the report fields have been appropriately filled out.
     * If so, it returns true. If not, it returns false and prompts the user to correct their inputs.
     * @return boolean that is true if all inputs are valid and false otherwise.
    */
    private boolean checkInputs(){
        int invalidCounter = 0;
        if (et_title.length() == 0) {
            et_title.setError("Title is required");
            invalidCounter++;
        }
        if (storesMenu.getSelectedItem().toString().equals("Select store")) {
            msgResponse.setText("Please select a store");
            invalidCounter++;
        } if (et_description.length() == 0) {
            et_description.setError("Description is required");
            invalidCounter++;
        }
        return invalidCounter == 0;
        // after all validation return true.
    }

    /**
    * This method takes the user inputs and creates a JSONObject holding that data
    * @return JSONObject holding the report data
    */
    private JSONObject createReport(){
        JSONObject report = new JSONObject();
        try {
            report.put("reportTitle", et_title.getText().toString());
            report.put("storeName", storesMenu.getSelectedItem());
            report.put("description", et_description.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return report;
    }

    /**
     * This method gets all available store names from the backend.
     * It uses a JSONArray request and then puts the names of each store into a string array.
     */
    private void getStores() {
            JsonArrayRequest req = new JsonArrayRequest(Const.URL_SERVER_STORES,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d(TAG, response.toString());
                            for(int i = 0; i < response.length(); i++) {
                                try {
                                    storesArray.add(response.getJSONObject(i).get("storeName").toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
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
                Intent intent = new Intent(com.example.grokart.NewReportActivity.this, ReportsActivity.class);
                intent.putExtra("userName", username);
                startActivity(intent);
                this.finish();
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            // On selecting a spinner item
            String item;
            item = parent.getItemAtPosition(position).toString();
            storesMenu.setPrompt("Select store");
            // Showing selected spinner item
            Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        }

        public void onNothingSelected(AdapterView<?> arg0) {}
}
