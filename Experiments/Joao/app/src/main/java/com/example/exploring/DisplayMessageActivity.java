package com.example.exploring;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayMessageActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Button btn_sendResponse =  findViewById(R.id.btn_sendResponse);
        EditText et_enterResponse =  findViewById(R.id.et_enterResponse);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);


        TextView tv_receivedText =  findViewById(R.id.tv_receivedText);
        tv_receivedText.setText("The other Activity sent us this message: " + message);


        btn_sendResponse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String message =  et_enterResponse.getText().toString();
                        if (TextUtils.isEmpty(message)) {
                            Toast.makeText(DisplayMessageActivity.this, "Enter a response please", Toast.LENGTH_SHORT).show();
                        }else{
                           sendMessage(view, message);

                        }
                    }
                    });

    }
    public void sendMessage(View view, String message){
        Intent intent = new Intent(this, MainActivity.class);
        //todo USAR EXTRA_MESSAGE no MAIN
        intent.putExtra(MainActivity.EXTRA_MESSAGE, message);
        startActivity(intent);
    }





}