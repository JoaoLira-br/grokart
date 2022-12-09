package com.example.grokart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.grokart.utils.Const;
import com.example.grokart.vRequests.GetRequest;
import com.example.grokart.vRequests.PostRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateStoreAdmin extends AppCompatActivity {
    private EditText etStoreName, etUsername, etPassword;
    private Button btnRegister;
    private String path;
    private final String TAG = CreateStoreAdmin.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_store_admin);
        etStoreName = findViewById(R.id.et_storeName);
        etUsername = findViewById(R.id.et_storeAdmin_username);
        etPassword = findViewById(R.id.et_storeAdmin_password);
        btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInputs()){
                    setPathAddress();
                    Log.d(TAG, "onClick: path"+ path);
//                    Log.d(TAG, "onClick: json"+ createJson());
                    PostRequest pr = new PostRequest(path, TAG, null);
                    pr.createRequestThread().start();
                    pr.createResponseHandler(()->{
                        Log.d(TAG, "onClick: getResponseHM"+ pr.getResponseHM());
                        startActivity(new Intent(CreateStoreAdmin.this, AdminHomeActivity.class));
                    });
                }
            }
        });
    }
    public void navigateBack(){

    }
    /**
     * @return true if user inputs correctly, i.e password.length longer than 7, false otherwise*/
    private Boolean checkInputs() {
        int invalidCounter = 0;
        if(etStoreName.length() == 0){
            etStoreName.setError("Storename is required");
            invalidCounter++;
        }
        if (etUsername.length() == 0) {
            etUsername.setError("Username is required");
            invalidCounter++;
        }
        if (etPassword.length() == 0) {
            etPassword.setError("Password is required");
            invalidCounter++;
        } else if (etPassword.length() < 7) {
            etPassword.setError("Password must be minimum 8 characters");
            invalidCounter++;
        }
        return invalidCounter == 0;
        // after all validation return true.
    }
    //{storeName}/{username}/{password}
    private void setPathAddress(){
        String storeName =  etStoreName.getText().toString();
        String username =  etUsername.getText().toString();
        String password =  etPassword.getText().toString();

        path = String.format("%1$s/%2$s/%3$s/%4$s", Const.URL_SAMPLE_CREATE_STORE_ADMIN, storeName, username, password );
    }
    private JSONObject createJson(){
        String storeName =  etStoreName.getText().toString();
        String username =  etUsername.getText().toString();
        String password =  etPassword.getText().toString();
        String json = String.format("{\"storeName:\"%1$s, \"username:\"%2$s, \"password:\"%3$s;}");
        Log.d(TAG, "createJson: json"+ json);
        JSONObject jsonStoreAdmin = null;
        try {
            jsonStoreAdmin = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    return jsonStoreAdmin;
    }
}