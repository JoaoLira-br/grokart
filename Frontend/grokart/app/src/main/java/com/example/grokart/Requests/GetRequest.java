package com.example.grokart.Requests;

import android.content.Intent;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.grokart.RegisterActivity;
import com.example.grokart.Responses.ResponseHandlerITF;
import com.example.grokart.app.AppController;
import com.example.grokart.utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GetRequest implements RequestITF {
    private String path;
    private final HashMap<String, String> responseHM;
    private final HashMap<String, HashMap> responseArrayHM;
    private final String tag_json_obj = "jobj_req";
    private final String tag_json_arry = "jarray_req";
    private String TAG;
    private Thread reqThread;
    private JSONObject response;

    public GetRequest(String path, String TAG) {
        this.path = path;
        this.responseHM = new HashMap<>();
        this.responseArrayHM = new HashMap<>();
        this.TAG = TAG;
//
    }

    public HashMap<String, String> getResponseHM() {
        return responseHM;
    }

    public HashMap<String, HashMap> getResponseArrayHM() {
        return responseArrayHM;
    }

    public String getPath() {
        return path;
    }

    public void setUrl(String path) {
        this.path = path;
    }

    public Thread getRequestThread() {
        return reqThread;
    }

    //TODO: remove this function
    @Override
    public Thread createRequestThread() {
       this.reqThread = new Thread( new Runnable() {
            @Override
            public void run() {
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,path,null,
                        new Response.Listener<JSONObject>()  {
                            @Override
                            public void onResponse(JSONObject response) {
                                storeOnHash(response);
                                Log.d(TAG, "onResponse: " + getResponseHM());
                            }
//                        hideProgressDialog();
                        }
                        , new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        VolleyLog.d("Volley Error:", "Unfortunately we got an error");
                        VolleyLog.d("Volley Error:", "Error: " + error.getMessage());
//                hideProgressDialog();

                    }
                }) {

                    /**
                     * Passing some request headers
                     * */
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json");
                        return headers;
                    }

                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
//                params.put("name", "Androidhive");
//                params.put("email", "abc@androidhive.info");
//                params.put("pass", "password123");

                        return params;
                    }
                };

                // Adding request to request queue
                AppController.getInstance().addToRequestQueue(jsonObjReq,
                        tag_json_obj);
            }
        });
       return this.reqThread;
        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
    };

    public Thread createArrayRequestThread(String objectToStore){
        this.reqThread = new Thread(new Runnable() {
            @Override
            public void run() {
                JsonArrayRequest req = new JsonArrayRequest(path,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                            storeOnArrayHash(response, objectToStore);

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
//                hideProgressDialog();
                    }
                });

                // Adding request to request queue
                AppController.getInstance().addToRequestQueue(req,
                        tag_json_arry);

                // Cancelling request
                // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_arry);
            }
        });
        return reqThread;
    };

    public Thread createResponseHandler(ResponseHandlerITF hdlResponse){
        return new Thread(new Runnable(){

            @Override
            public void run() {
                //Waits for the Server Response
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                hdlResponse.handleResponseFunction();

            }
        });
    }


    //TODO: identify problem: null HASH MAP response in GET response
    @Override
    public void storeOnHash(JSONObject response) {

        Iterator<?> keys = response.keys();

        while (keys.hasNext()) {
            String key = (String) keys.next();
            try {
                String value = response.getString(key);
                responseHM.put(key, value);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    //item1, item2, item3, ...
    public void storeOnArrayHash(JSONArray response, String nameOfObject){
        int i = 0;
        while(i < response.length()){
            try {
                storeOnHash(response.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            responseArrayHM.put(nameOfObject+ Integer.toString(i+1), getResponseHM());
            getResponseHM().clear();
            i++;
        }
    }


}