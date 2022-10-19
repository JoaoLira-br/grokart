package com.example.grokart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
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

import java.util.HashMap;
import java.util.Map;




public class AdminHomeActivity extends AppCompatActivity {
    private final String tag_json_obj = "jobj_req";
    private final String tag_json_arry = "jarray_req";
    private static Button viewAppActivity;
    private static String jsonResponse;
    private TextView usersCount, storesCount;
    private final String TAG = AdminHomeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        usersCount = findViewById(R.id.tv_userCount);
        storesCount = findViewById(R.id.tv_storeCount);
        viewAppActivity = findViewById(R.id.btn_viewAppActivity);

        viewAppActivity.setOnClickListener(new View.OnClickListener() {

                                               @Override
                                               public void onClick(View v) {
                                                   //TODO change path to array stores endpoint
                                                   JsonArrayRequest jsonArrReqStores = new JsonArrayRequest(Const.URL_SERVER_STORES,
                                                           new Response.Listener<JSONArray>() {
                                                               @Override
                                                               public void onResponse(JSONArray response) {
                                                                   jsonResponse = Integer.toString(response.length());
                                                                   storesCount.setText(jsonResponse);
//

                                                               }
                                                           }, new Response.ErrorListener() {
                                                       @Override
                                                       public void onErrorResponse(VolleyError error) {
                                                           VolleyLog.d(TAG, "Error: " + error.getMessage());
//
                                                       }
                                                   });
                                                   AppController.getInstance().addToRequestQueue(jsonArrReqStores,
                                                           tag_json_arry);

                                                   // Cancelling request
                                                   // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
                                                   JsonArrayRequest jsonArrReq = new JsonArrayRequest(Const.URL_SERVER_USERS,
                                                           new Response.Listener<JSONArray>() {
                                                               @Override
                                                               public void onResponse(JSONArray response) {
                                                                  jsonResponse = Integer.toString(response.length());
                                                                   Log.d(TAG, "onResponse: "+jsonResponse);
                                                                    usersCount.setText(jsonResponse);
//                        msgResponse.setText(response.toString());
//                        hideProgressDialog();
                                                               }
                                                           }, new Response.ErrorListener() {
                                                       @Override
                                                       public void onErrorResponse(VolleyError error) {
                                                           VolleyLog.d(TAG, "Error: " + error.getMessage());
//                hideProgressDialog();
                                                       }
                                                   });
                                                   AppController.getInstance().addToRequestQueue(jsonArrReq,
                                                           tag_json_arry);
                                               }
                                           });


                                               //TODO uncomment and add appropriate path for fetching stores count

                                               // Cancelling request
                                               // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
//        JsonObjectRequest jsonObjReq_storeCount = new JsonObjectRequest(Request.Method.GET,Const.URL_SERVER_USERS,null,
//                new Response.Listener<JSONObject>()  {
//                    @Override
//                    public void onResponse(JSONObject response) {
////                                        setSuccessfulLoginCheck(true);
////                                        AppController.users.put(et_username.toString(), response);
//                        jsonResponse = Integer.toString(response.length());
//
//                        try {
//                            Log.d(TAG, response.get("message").toString());
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                        usersCount.setText(jsonResponse);
//
//                    }
////                        hideProgressDialog();
//                }
//                , new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                VolleyLog.d(TAG, "Unfortunately we got an error");
//                VolleyLog.d(TAG, "Error: " + error.getMessage());
////                hideProgressDialog();
//
//            }
//
//        });
//        AppController.getInstance().addToRequestQueue(jsonObjReq_storeCount,
//                tag_json_obj);


                                                    }

    }



