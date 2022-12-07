package com.example.grokart.vRequests;

import org.json.JSONObject;

import static java.lang.Thread.sleep;

/**@author  Joao Victor Lira
 * Interface for implementing common request methods
 * */
public interface RequestITF {

    public Thread createRequestThread();

    public void storeOnHash(JSONObject response);
}
