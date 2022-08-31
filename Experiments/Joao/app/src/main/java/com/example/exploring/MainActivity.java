package com.example.exploring;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String TOAST_TITLE = "Enter the time please";
    public static final int DURATION = Toast.LENGTH_SHORT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText et_setHour =  findViewById(R.id.et_setHour);
        EditText et_setMinute =  findViewById(R.id.et_setMinute);
        Button btn_setAlarm =  findViewById(R.id.btn_setAlarm);
        Toast toast = Toast.makeText(this, TOAST_TITLE, DURATION);

        et_setMinute.setFilters(new InputFilter[] {
                new InputFilterMinMax("1","60")}
        );
        et_setHour.setFilters(new InputFilter[] {
                new InputFilterMinMax("1","12")}
        );

        btn_setAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                if(TextUtils.isEmpty(et_setHour.getText().toString()) || TextUtils.isEmpty(et_setMinute.getText().toString())) {
                    toast.show();
                }else{
                    int hour = Integer.parseInt(et_setHour.getText().toString());
                    int minute = Integer.parseInt(et_setMinute.getText().toString());
                    setAlarm("This is an alarm from Android", hour, minute);
                }
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

    //todo implement this method with intent
    public void setTimer(String message, int hour, int minutes){

    }
    //todo implement this method with intent
    public void sendMessage(View view){

    }
}