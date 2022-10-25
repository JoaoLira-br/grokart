package com.example.grokart.Requests;

import org.json.JSONObject;

import static java.lang.Thread.sleep;

import android.content.Intent;

public interface RequestITF {
public void makeRequest();
public void storeOnIntent(JSONObject response);
}
