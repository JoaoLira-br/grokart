
# Android Volley Tutorial

This repository contains example projects that can be used for learning.

It has the image request, string request, and json request calls.
### Image request
Download an image

### String request
StringRequest can be used to fetch any kind of string data. The response can be json, xml, html,text.

### Json request
Use JsonObjectRequest if you are expecting json object in the response.

### Pre-requisites

0. Add dependency - implementation 'com.android.volley:volley:1.1.1'
1. Add Internet permission in  AndroidManifest.xml - `<uses-permission android:name="android.permission.INTERNET" />`
2. Java version 1.8, API level 26 (recommended)
3. Allow clear text traffic - `<application android:usesCleartextTraffic="true" />`

### To Run the project 
1. Lanuch project using a virtual device with API level 26

### Example requests
1. JSON Object Request - expect response to be in JSON format: 
    1. Client: new JsonObjectRequest(Method,  url, null, new Response.Listener<JSONObject>() {
         protected Map<String, String> getParams() {
            return a Hashmap that contains key-value pairs sent to server
        }
    })
    2. Server:     JavaEntityA methodName(@RequestBody JavaEntityB instance){ return JavaEntityA}

2. JSON Object Request - send a JSON Object to server: 
    1. JSON object: 
        JSONObject jsonObject = new JSONObject();
        //input your API parameters
        jsonObject.put("name","imajsonobj");
        jsonObject.put("emailId","this_is_pwd");
    2. Client: new JsonObjectRequest(Method, url, jsonObject, new Response.Listener<JSONObject>() {})
    3. Server:     JavaEntityA methodName(@RequestBody JavaEntityB instance){ return JavaEntityA}
3. JSON Array Request - expect response to be in the format of list of JSON objects: 
    1. Client: new JsonArrayRequest(Method,  url, null, new Response.Listener<JSONArray>() {
         protected Map<String, String> getParams() {
            return a Hashmap that contains key-value pairs sent to server
        }
    })
    2. Server:     List<JavaEntityA> methodName(@RequestBody JavaEntityB instance){ return List<JavaEntityA>}
4. StringRequest - send a string to server: 
    1. Client: new StringRequest(Method, url/{id}, new Response.Listener<String>()
    2. Server: String method(@PathVariable int id){}

5. ImageRequest - fetch an image
    1. Client: URL should be pointed to an image
    2. approach 1 - https://android--examples.blogspot.com/2017/02/android-volley-image-request-example.html
    3. approach 2 - 
    Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Entry entry = cache.get(Const.URL_IMAGE);
        if(entry != null){
            // handle  entry.data, converting it to bitmap. 
        }



#

### Note :
### 1. /api/{id} can be combined with JSON request, the corresponding mapping needs to be `JavaEntityA method(@PathVariable int id, @RequestBody JavaEntityB request)`



