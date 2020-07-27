package com.example.detox;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Login extends AppCompatActivity {

    // Variable Declarations for taking Credentials
    String Name, Age, Email;
    String UserDataFile = "Detox-CSV-File.csv";
    String DefaultTime = "120";             // Minutes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    //Handler for Button Click for Submit
    public void ButtonclickSubmit(View v) {

        //Read Values of various user credentials
        EditText name = findViewById(R.id.et1);
        Name = name.getText().toString();

        EditText age = findViewById(R.id.et2);
        Age = age.getText().toString();

        EditText email = findViewById(R.id.et3);
        Email = email.getText().toString();

        //Call function to write Name, Age and Email in a file
        WriteUserDataFile(Name, Age, Email);

        Button button = findViewById(R.id.Button);
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void WriteUserDataFile(String Arg1, String Arg2, String Arg3) {
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File directory = contextWrapper.getDir(getFilesDir().getName(), ContextWrapper.MODE_PRIVATE);
        File file = new File(directory, UserDataFile);
        String userData = Arg1 + "," + Arg2 + "," + Arg3 + "," + DefaultTime;

        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(UserDataFile, Context.MODE_PRIVATE);
            outputStream.write(userData.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
