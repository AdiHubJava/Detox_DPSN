package com.example.detox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class Suggestions extends AppCompatActivity {
    String NameOrg, AgeOrg;
    int AgeInt;
    String UserDataFile = "Detox-CSV-File.csv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Call function to Read User Data file
        Context context = getApplicationContext();
        String TmpString = ReadUserDataFile(context);

        //Parse the read user data string into an array
        String[] UserDataArray = TmpString.split(",");
        NameOrg = UserDataArray[0];
        AgeOrg = UserDataArray[1];
        AgeInt = Integer.parseInt(AgeOrg);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);

        //Name and Age to be retried to make personalized suggestions like putting in age bracket and writing message with user name in it
        TextView tvsuggestion = findViewById(R.id.TvSuggesstions);
        TextView tvsuggest1 = findViewById(R.id.Tvsuggest1);
        TextView tvsuggest2 = findViewById(R.id.Tvsuggest2);
        TextView tvsuggest3 = findViewById(R.id.Tvsuggest3);
        TextView tvsuggest4 = findViewById(R.id.Tvsuggest4);
        TextView tvname = findViewById(R.id.TvName);
        tvsuggestion.setText("Tips and Tricks");

        //Space given for new line have tried other methods like /n --  not worked
        if (AgeInt <= 18) {
            tvname.setText("Dear " + NameOrg + ", since you are not yet an adult, here are some guidelines you should follow: ");
            tvsuggest1.setText("1. Restrict your screen time to no more than 4hrs a day ");
            tvsuggest2.setText("2. Take frequent breaks to socialize, exercise and be with your family. ");
            tvsuggest3.setText("3. Follow 20-20-20 rule, Every 20 minutes, look at something 20 feet away for 20 seconds.");
            tvsuggest4.setText("4. Play ‘Real’ games.");
        }

        if ((AgeInt > 18) && (AgeInt < 40)) {
            tvname.setText("Dear " + NameOrg + ", work and home life can be really engrossing. Don’t forget to take care of yourself:");
            tvsuggest1.setText("1. Take a break, set some alarms to remind yourself to take a breather and look away from the screen.");
            tvsuggest2.setText("2. Let your eyes move away from the screen every 30 minutes.");
            tvsuggest3.setText("3. Restrict your screen time beyond work.");
            tvsuggest4.setText("4. Stay active. Have more real-life social interactions");
        }

        if (AgeInt >= 40) {
            tvname.setText("Dear " + NameOrg + ", work can be really engrossing and stressful. Learn to take it easy:");
            tvsuggest1.setText("1. Take a break, set some alarms to remind yourself to take a breather and look away from the screen.");
            tvsuggest2.setText("2. Let your eyes move away from the screen every 30 minutes, do neck rolls. ");
            tvsuggest3.setText("3. Restrict your screen time beyond work.");
            tvsuggest4.setText("4. Stay active Have more real-life social interactions");
        }
    }

    //3 handlers for three button clicks which lead to setting, suggestions, MainActivity

    public void settings (View v){
        Intent settings = new Intent(Suggestions.this, Settings.class);
        startActivity(settings);
        finish();
    }

    public void suggestions (View v){
        Intent suggestions = new Intent(Suggestions.this, Suggestions.class);
        startActivity(suggestions);
        finish();
    }

    public void mainActivity (View v){
        Intent mainActivity = new Intent(Suggestions.this, MainActivity.class);
        startActivity(mainActivity);
        finish();
    }

    //Function for Reading the User Data CSV File
    public String ReadUserDataFile(Context context){
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


}
