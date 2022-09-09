package com.example.exploring;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnItemSelectedListener {
    public static final String TOAST_TITLE = "Enter the time please";
    public static final int DURATION = Toast.LENGTH_SHORT;
    public static final String EXTRA_MESSAGE = "com.example.exploring.MESSAGE";
    private static final String TAG = "MyActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        EditText et_setHour = findViewById(R.id.et_setHour);
        EditText et_setMinute = findViewById(R.id.et_setMinute);
        Button btn_setAlarm = findViewById(R.id.btn_setAlarm);
        EditText et_enterMessage = findViewById(R.id.et_enterMessage);
        Button btn_sendMessage = findViewById(R.id.btn_sendMessage);
        TextView tv_response = findViewById(R.id.tv_response);
        Intent intent = getIntent();
        String response = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        //setting the dropdown list for choosing colors
        Spinner spr_chooseColor = findViewById(R.id.spr_chooseColor);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.color_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spr_chooseColor.setAdapter(adapter);

        et_setMinute.setFilters(new InputFilter[]{
                new InputFilterMinMax("1", "60")}
        );
        et_setHour.setFilters(new InputFilter[]{
                new InputFilterMinMax("1", "12")}
        );

        btn_setAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(et_setHour.getText().toString()) || TextUtils.isEmpty(et_setMinute.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Please enter a valid time", Toast.LENGTH_SHORT).show();
                } else {
                    int hour = Integer.parseInt(et_setHour.getText().toString());
                    int minute = Integer.parseInt(et_setMinute.getText().toString());
                    setAlarm("This is an alarm from Android", hour, minute);
                }
            }
        });
        btn_sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = et_enterMessage.getText().toString();
                if (TextUtils.isEmpty(message)) {
                    Toast.makeText(MainActivity.this, "Enter a message", Toast.LENGTH_SHORT).show();
                } else {
                    sendMessage(view, message);

                }
            }
        });
        spr_chooseColor.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            switch(position){
                case 0:
                    btn_sendMessage.setBackgroundColor(getResources().getColor(R.color.blue));
                    btn_setAlarm.setBackgroundColor(getResources().getColor(R.color.blue));
                    break;
                case 1:
                    btn_sendMessage.setBackgroundColor(getResources().getColor(R.color.red));
                    btn_setAlarm.setBackgroundColor(getResources().getColor(R.color.red));
                    break;
                case 2:
                    btn_sendMessage.setBackgroundColor(getResources().getColor(R.color.pink));
                    btn_setAlarm.setBackgroundColor(getResources().getColor(R.color.pink));
                    break;
                case 3:
                    btn_sendMessage.setBackgroundColor(getResources().getColor(R.color.yellow));
                    btn_setAlarm.setBackgroundColor(getResources().getColor(R.color.yellow));
                    
                    break;
            }

        }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            String title = String.valueOf(R.string.spinnerTitle);
                Log.d(TAG, title);
                spr_chooseColor.setPrompt(title);
            }
        });

    }






    public void setAlarm(String message, int hour, int minutes){
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
        intent.putExtra(AlarmClock.EXTRA_MESSAGE, message);
        intent.putExtra(AlarmClock.EXTRA_MINUTES, minutes);
        intent.putExtra(AlarmClock.EXTRA_HOUR, hour);
        intent.putExtra(AlarmClock.EXTRA_VIBRATE, true);
        startActivity(intent);

    }
//    public void setActivityBackgroundColor(int color) {
//        View view = this.getWindow().getDecorView();
//        view.setBackgroundColor(color);
//
//    }

    //todo implement this method with intent
    public void setTimer(String message, int hour, int minutes){
        Intent intent = new Intent(AlarmClock.ACTION_SET_TIMER);
        intent.putExtra(AlarmClock.EXTRA_MESSAGE, message);
        intent.putExtra(AlarmClock.EXTRA_MINUTES, minutes);
    }
    public void sendMessage(View view, String message){
        Intent intent = new Intent(MainActivity.this, DisplayMessageActivity.class);
        //todo USAR EXTRA_MESSAGE no MAIN
        intent.putExtra(MainActivity.EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    //todo implement this method with intent
//    public void sendMessage(View view, String message, Context from, Class sendTo){
//        Intent intent = new Intent(from, sendTo);
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);
//    }

//    public void sendMessageThenBack(View view, String message){
//        Intent intent = new Intent(MainActivity.this, DisplayMessageActivity.class);
//        //todo USAR EXTRA_MESSAGE no MAIN
//        intent.putExtra(MainActivity.EXTRA_MESSAGE, message);
//        startActivityFor(intent);
//        startActivityForResult(intent, 2);
//
//    }
}