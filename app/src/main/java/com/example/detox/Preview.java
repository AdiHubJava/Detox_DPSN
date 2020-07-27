package com.example.detox;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class Preview extends AppCompatActivity {
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        //declaring handler for preview screen

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                //making string for shared prefrences
                final String PREFS_NAME = "MyPrefsFile";

                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

                if (settings.getBoolean("my_first_time", true)) {

                    Intent intent=new Intent(Preview.this,Login.class);
                    startActivity(intent);
                    finish();

                    settings.edit().putBoolean("my_first_time", false).commit();
                }
                else {
                    //changing activities after completion
                    Intent intent=new Intent(Preview.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                //delay for splash screen
            }
        },3000);

    }
}