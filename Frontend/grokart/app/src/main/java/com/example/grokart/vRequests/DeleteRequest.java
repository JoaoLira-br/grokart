package com.example.grokart.vRequests;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.grokart.vResponses.ResponseHandlerITF;
import com.example.grokart.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**@author Joao Victor Lira
 * */
public class DeleteRequest implements RequestITF{
    private String path;
    private final HashMap<String, String> responseHM;
    private final String tag_json_obj = "jobj_req";
    private final String tag_json_arry = "jarray_req";
    private String TAG;
    private Thread reqThread;
    private JSONObject response;

    public DeleteRequest(String path, String TAG) {
        this.path = path;
        this.responseHM = new HashMap<>();
        this.TAG = TAG;
//
    }
    /**@return The Hashmap with the response from the volleyResquests
     * */
    public HashMap<String, String> getResponseHM() {
        return responseHM;
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


    /** @return A thread that runs the volley deleteRequest. */
    @Override
    public Thread createRequestThread() {
        this.reqThread = new Thread( new Runnable() {
            @Override
            public void run() {
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.DELETE,path,null,
                        new Response.Listener<JSONObject>()  {
                            @Override
                            public void onResponse(JSONObject response) {
                                storeOnHash(response);
                                Log.d(TAG, "onResponse: "+getResponseHM());
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
        return reqThread;
        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
    };

    /**@param hdlResponse the lambda function or interface implementation which handles the response
     * @return a thread that runs the response handler after 500ms (enough so that the response is received by the client)*/
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



    /**@param response JSON object sent by the server
     *  stores response in a Hashmap
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
}
