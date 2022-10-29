package com.example.grokart.Requests;

import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import static java.lang.Thread.sleep;

import android.content.Intent;

public interface RequestITF {

    public Thread createRequestThread();

    public void storeOnHash(JSONObject response);
}
