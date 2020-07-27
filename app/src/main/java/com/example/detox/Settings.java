package com.example.detox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class Settings extends AppCompatActivity {
    // variables declared, values from file to be assigned
    String NameMod, AgeMod, EmailMod, ReminderMod, NameOrg, AgeOrg, EmailOrg, ReminderOrg;
    String UserDataFile = "Detox-CSV-File.csv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    //Three handlers for three button clicks which lead to setting, suggestions, MainActivity
    public void mainActivity(View v) {
        Intent mainActivity = new Intent(Settings.this, MainActivity.class);
        startActivity(mainActivity);
        finish();
    }

    public void suggestions(View v) {
        Intent suggestions = new Intent(Settings.this, Suggestions.class);
        startActivity(suggestions);
        finish();
    }

    public void settings(View v) {
        Intent settings = new Intent(Settings.this, Settings.class);
        startActivity(settings);
        finish();
    }

    // Method for Name switch on and off
    public void mthSwitchName(View view) {
        Switch swcName = findViewById(R.id.switchName);
        Switch swcAge = findViewById(R.id.switchAge);
        Switch swcEmail = findViewById(R.id.switchEmail);
        Switch swcReminder = findViewById(R.id.switchReminder);
        TextView tvNameText = findViewById(R.id.tvName);
        EditText etNameText = findViewById(R.id.etName);
        Button btnSubmit = findViewById(R.id.buttSubmit);

        if (swcName.isChecked()) {
            tvNameText.setVisibility(View.INVISIBLE);
            etNameText.setVisibility(View.VISIBLE);
            btnSubmit.setVisibility(View.VISIBLE);
        }else if (swcAge.isChecked() || swcEmail.isChecked() || swcReminder.isChecked()) {
            tvNameText.setVisibility(View.VISIBLE);
            etNameText.setVisibility(View.INVISIBLE);
            btnSubmit.setVisibility(View.VISIBLE);
        }
        else
        {
            tvNameText.setVisibility(View.VISIBLE);
            etNameText.setVisibility(View.INVISIBLE);
            btnSubmit.setVisibility(View.INVISIBLE);
        }
    }

    // Method for swcAge on and off
    public void mthSwitchAge(View view) {
        Switch swcName = findViewById(R.id.switchName);
        Switch swcAge = findViewById(R.id.switchAge);
        Switch swcEmail = findViewById(R.id.switchEmail);
        Switch swcReminder = findViewById(R.id.switchReminder);
        TextView tvAgeText = findViewById(R.id.tvAge);
        EditText etAgeText = findViewById(R.id.etAge);
        Button btnSubmit = findViewById(R.id.buttSubmit);

        if (swcAge.isChecked()) {
            tvAgeText.setVisibility(View.INVISIBLE);
            etAgeText.setVisibility(View.VISIBLE);
            btnSubmit.setVisibility(View.VISIBLE);
        }else if (swcName.isChecked() || swcEmail.isChecked() || swcReminder.isChecked()) {
            tvAgeText.setVisibility(View.VISIBLE);
            etAgeText.setVisibility(View.INVISIBLE);
            btnSubmit.setVisibility(View.VISIBLE);
        }
        else
        {
            tvAgeText.setVisibility(View.VISIBLE);
            etAgeText.setVisibility(View.INVISIBLE);
            btnSubmit.setVisibility(View.INVISIBLE);
        }
    }

    // Method for swcEmail on and off
    public void mthSwitchEmail(View view) {
        Switch swcName = findViewById(R.id.switchName);
        Switch swcAge = findViewById(R.id.switchAge);
        Switch swcEmail = findViewById(R.id.switchEmail);
        Switch swcReminder = findViewById(R.id.switchReminder);
        TextView tvAgeText = findViewById(R.id.tvAge);
        TextView tvEmailText = findViewById(R.id.tvEmail);
        EditText etAgeText = findViewById(R.id.etAge);
        EditText etEmailText = findViewById(R.id.etEmail);
        Button btnSubmit = findViewById(R.id.buttSubmit);

        if (swcEmail.isChecked()) {
            tvEmailText.setVisibility(View.INVISIBLE);
            etEmailText.setVisibility(View.VISIBLE);
            btnSubmit.setVisibility(View.VISIBLE);
        }else if (swcAge.isChecked() || swcName.isChecked() || swcReminder.isChecked()) {
            tvEmailText.setVisibility(View.VISIBLE);
            etEmailText.setVisibility(View.INVISIBLE);
            btnSubmit.setVisibility(View.VISIBLE);
        }else{
            tvEmailText.setVisibility(View.VISIBLE);
            etEmailText.setVisibility(View.INVISIBLE);
            btnSubmit.setVisibility(View.INVISIBLE);
        }
    }

    // Method for swcReminder on and off
    public void mthSwitchReminder(View view) {
        Switch swcName = findViewById(R.id.switchName);
        Switch swcAge = findViewById(R.id.switchAge);
        Switch swcEmail = findViewById(R.id.switchEmail);
        Switch swcReminder = findViewById(R.id.switchReminder);
        TextView tvReminderText = findViewById(R.id.tvReminder);
        EditText etReminderText = findViewById(R.id.etReminder);
        Button btnSubmit = findViewById(R.id.buttSubmit);

        if (swcReminder.isChecked()) {
            tvReminderText.setVisibility(View.INVISIBLE);
            etReminderText.setVisibility(View.VISIBLE);
            btnSubmit.setVisibility(View.VISIBLE);
        }else if (swcAge.isChecked() || swcName.isChecked() || swcEmail.isChecked()) {
            tvReminderText.setVisibility(View.VISIBLE);
            etReminderText.setVisibility(View.INVISIBLE);
            btnSubmit.setVisibility(View.VISIBLE);
        }else{
            tvReminderText.setVisibility(View.VISIBLE);
            etReminderText.setVisibility(View.INVISIBLE);
            btnSubmit.setVisibility(View.INVISIBLE);
        }
    }

    //method for on click of submit button
    public void mthButtSubmit(View v) throws IOException {
        Switch swcName = findViewById(R.id.switchName);
        Switch swcAge = findViewById(R.id.switchAge);
        Switch swcEmail = findViewById(R.id.switchEmail);
        Switch swcReminder = findViewById(R.id.switchReminder);

        //Call function to Read User Data file
        Context context = getApplicationContext();
        String TmpString = ReadUserDataFile(context);

        //Parse the read user data string into an array
        String[] UserDataArray = TmpString.split(",");
        NameOrg = UserDataArray[0];
        AgeOrg = UserDataArray[1];
        EmailOrg = UserDataArray[2];
        ReminderOrg = UserDataArray[3];

        NameMod = NameOrg;
        AgeMod = AgeOrg;
        EmailMod = EmailOrg;
        ReminderMod = ReminderOrg;

        // actions whens switch Name is on
        if (swcName.isChecked()) {
            EditText NameEt = findViewById(R.id.etName);

            NameMod = NameEt.getText().toString();
            NameEt.setVisibility(View.INVISIBLE);
            TextView tvn = findViewById(R.id.tvName);
            tvn.setVisibility(View.VISIBLE);
            Button btnSubmit = findViewById(R.id.buttSubmit);
            btnSubmit.setVisibility(View.INVISIBLE);
            swcName.setChecked(false);
            NameEt.setText("");

            if (NameMod.matches("")){
                NameMod = NameOrg;
            }
        }

        // actions whens switch Age is on
        if (swcAge.isChecked()) {
            EditText AgeEt = findViewById(R.id.etAge);

            AgeMod = AgeEt.getText().toString();
            AgeEt.setVisibility(View.INVISIBLE);
            TextView tva = findViewById(R.id.tvAge);
            tva.setVisibility(View.VISIBLE);
            Button btnSubmit = findViewById(R.id.buttSubmit);
            btnSubmit.setVisibility(View.INVISIBLE);
            swcAge.setChecked(false);
            AgeEt.setText("");

            if (AgeMod.matches("")){
                AgeMod = AgeOrg;
            }
        }

        // actions whens switch Email is on
        if (swcEmail.isChecked()) {
            EditText EmailEt = findViewById(R.id.etEmail);

            EmailMod = EmailEt.getText().toString();
            EmailEt.setVisibility(View.INVISIBLE);
            TextView tve = findViewById(R.id.tvEmail);
            tve.setVisibility(View.VISIBLE);
            Button btnSubmit = findViewById(R.id.buttSubmit);
            btnSubmit.setVisibility(View.INVISIBLE);
            swcEmail.setChecked(false);
            EmailEt.setText("");

            if (EmailMod.matches("")){
                EmailMod = EmailOrg;
            }
        }

        // actions whens switch Reminder is on
        if (swcReminder.isChecked()) {
            EditText ReminderEt = findViewById(R.id.etReminder);

            ReminderMod = ReminderEt.getText().toString();
            ReminderEt.setVisibility(View.INVISIBLE);
            TextView tvr = findViewById(R.id.tvReminder);
            tvr.setVisibility(View.VISIBLE);
            Button btnSubmit = findViewById(R.id.buttSubmit);
            btnSubmit.setVisibility(View.INVISIBLE);
            swcReminder.setChecked(false);
            ReminderEt.setText("");

            if (ReminderMod.matches("")){
                ReminderMod = ReminderOrg;
            }
        }

        //Prepare new text string with any changes made for writing in user data file
        String NewUserData = NameMod + "," + AgeMod + "," + EmailMod + "," + ReminderMod;

        //Write modified data in user data file: Detox-CSV-File.cs
        WriteUserDataFile(NewUserData);
    }

    //Function for Reading the User Data CSV File
    public String ReadUserDataFile(Context context) throws IOException {
        try {
            FileInputStream fis = context.openFileInput(UserDataFile);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader r = new BufferedReader(isr);

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            return "Error: File Not Found";
        } catch (UnsupportedEncodingException e) {
            return "Error: Unsupported Encoding";
        } catch (IOException e) {
            return "Error: IO Exception";
        }
    }

    // Write to Modify the User Data File
    public void WriteUserDataFile(String Arg1) {
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File directory = contextWrapper.getDir(getFilesDir().getName(), ContextWrapper.MODE_PRIVATE);
        File file = new File(directory, UserDataFile);
        String userData = Arg1;

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
