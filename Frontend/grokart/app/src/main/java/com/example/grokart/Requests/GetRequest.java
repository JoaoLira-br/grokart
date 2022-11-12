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

/**@author Joao Victor Lira
 * */
public class GetRequest implements RequestITF {
    private String path;
    private final HashMap<String, String> responseHM;
    private final HashMap<String, JSONObject> responseArrayHM;
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

/**@return The Hashmap with the response from the volleyResquests
 * */
    public HashMap<String, String> getResponseHM() {
        return responseHM;
    }

/**@return Hashmap with the response from the volleyArrayRequest*/
    public HashMap<String, JSONObject> getResponseArrayHM() {
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


    /** @return A thread that runs the volley getRequest. */
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

    /**@param objectToStore is the name of the objects the response sends. The name is of the developer`s choice.
     *The name will reflect later when the developer decides to make Models out of it
     *
     * @return A thread that runs the volley Array request*/
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

    /**@param hdlResponse the lambda function or interface implementation which handles the response
     * @return a thread that runs the response handler after 500ms (enough so that the response is received by the client)*/
    public Thread createResponseHandler(ResponseHandlerITF hdlResponse){
        return new Thread(new Runnable(){

            @Override
            public void run() {
                //Waits for the Server Response
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                hdlResponse.handleResponseFunction();

            }
        });
    }



    /**@param response JSON object sent by the server
     *   stores response in a Hashmap
     * */
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

    /**@param nameOfObject is the name of the objects the response sends. The name is of the developer`s choice.
     *The name will reflect later when the developer decides to make Models out of it
     * @param response: the JSONArray response sent by the server
     *
     * */
    public void storeOnArrayHash(JSONArray response, String nameOfObject){
        JSONObject itemHash = new JSONObject();
        String key;
        int i = 0;
        while(i < response.length()){
            try {
                Log.d(TAG, "GetRequest/StoreOnArrayHash - jsonObjectFromResponse "+response.getJSONObject(i).toString());
                itemHash = response.getJSONObject(i);
                Log.d(TAG, "GetRequest/StoreOnArrayHash - itemHash " + itemHash);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            key = nameOfObject + (i + 1);
            Log.d(TAG, "GetRequest/StoreOnArrayHash - key" + " "+ key);
            Log.d(TAG, "GetRequest/StoreOnArrayHash - getResponseHM(): "+getResponseHM());
            responseArrayHM.put(key, itemHash);
            Log.d(TAG, "GetRequest/StoreOnArrayHash - getResponseArrayHM()" + getResponseArrayHM());

            i++;
        }
        Log.d(TAG, "GetRequest/StoreOnArrayHash - HashMapAfterStoreOnArrayHash: "+getResponseArrayHM());
    }


}