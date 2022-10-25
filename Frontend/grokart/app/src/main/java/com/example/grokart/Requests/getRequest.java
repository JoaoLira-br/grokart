package com.example.grokart.Requests;

import android.content.Intent;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.grokart.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class getRequest implements RequestITF {
    private String path;
    private JSONObject user;
    private HashMap<String, String> responseHM;
    private final String tag_json_obj = "jobj_req";
    private final String tag_json_arry = "jarray_req";
    private String TAG; //adf
    public getRequest(String path, JSONObject user, Intent intent, String TAG) {
        this.path = path;
        this.user = user;
        this.TAG = TAG;
        this.responseHM = new HashMap<>();
    }

    public HashMap<String, String> getResponseHM() {
        return responseHM;
    }

    public String getUrl() {
        return path;
    }

    public JSONObject getUser() {
        return user;
    }

    public void setUrl(String path) {
        this.path = path;
    }

    public void setUser(JSONObject user) {
        this.user = user;
    }

    @Override
    public void makeRequest() {
  JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,path,null,
          new Response.Listener<JSONObject>() {
              @Override
              public void onResponse(JSONObject response) {
                      storeOnIntent(response);
              }
          }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
          VolleyLog.d(TAG, "Unfortunately we got an error");
          VolleyLog.d(TAG, "Error: " + error.getMessage());
      }
  }) {
      /**
       * Passing some request headers
       * */
      @Override
      public Map<String, String> getHeaders() throws AuthFailureError {
          HashMap<String, String> headers = new HashMap<>();
          headers.put("Content-Type", "application/json");
          return headers;
      }

      @Override
      protected Map<String, String> getParams() {
          Map<String, String> params = new HashMap<>();
//                params.put("name", "Androidhive");
//                params.put("email", "abc@androidhive.info");
//                params.put("pass", "password123");

          return params;
      }
  };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq,
                tag_json_obj);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
    }

    @Override
    public void storeOnIntent(JSONObject response) {

        Iterator<?> keys = response.keys();

        while( keys.hasNext() ){
            String key = (String)keys.next();
            try {
                String value = response.getString(key);
                responseHM.put(key, value);
            }catch (JSONException e){
                e.printStackTrace();
            }


        }
    }
}
