package com.example.detox;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    boolean mTimerRunning;
    long mStartTimeInMillis;
    long mTimeLeftInMillis;
    long mTimeRemaining;
    long mEndTime;
    String NameVal;
    String EmailVal;
    String ReminderVal;
    CountDownTimer mCountDownTimer;
    TextView mTextViewCountDown;    String UserDataFile = "Detox-CSV-File.csv";

    //Code to know if Main Activity is visible or not
    private static boolean activityVisible;
    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextViewCountDown = findViewById(R.id.text_view_countdown);

        // Read User Data file to get Reminder Value - Call function to Read User Data file
        Context myContext = getApplicationContext();
        String TmpString = ReadUserDataFile(myContext);

        //Parse the read user data string into an array
        String[] myDataArray = TmpString.split(",");
        NameVal = myDataArray[0];
        EmailVal = myDataArray[2];
        ReminderVal = myDataArray[3];

        Float ReminderFlt = Float.parseFloat(ReminderVal);
        long ReminderLongMin = (long) (ReminderFlt*1);

        long millisInput = ReminderLongMin * 60000;
        if (mTimerRunning) {
            //updateCountDownText();
        } else {
            setTime(millisInput);
            startTimer();
        }
    }

    // Three Intent handlers for three button clicks which lead to setting, suggestions, MainActivity
    public void Settings(View v) {
        pauseTimer();
        activityPaused();

        Intent settings = new Intent(MainActivity.this, Settings.class);
        startActivity(settings);
        finish();
    }

    public void Suggestions(View v) {
        pauseTimer();
        activityPaused();

        Intent suggestions = new Intent(MainActivity.this, Suggestions.class);
        startActivity(suggestions);
        finish();
    }

    public void MainActivity(View v) {
        Intent mainActivity = new Intent(MainActivity.this, MainActivity.class);
        startActivity(mainActivity);
        finish();
    }

    //Function for Reading the User Data CSV File
    public String ReadUserDataFile(Context context) {
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

    // Timer Function Code
    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("startTimeInMillis", mStartTimeInMillis);
        editor.putLong("millisLeft", mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);
        editor.apply();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        startTimer();
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        mStartTimeInMillis = prefs.getLong("startTimeInMillis", 600000);
        mTimeLeftInMillis = prefs.getLong("millisLeft", mStartTimeInMillis);
        mTimerRunning = prefs.getBoolean("timerRunning", false);
        updateCountDownText();
        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();
            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0;
                mTimerRunning = false;
                updateCountDownText();
            } else {
                startTimer();
            }
        }
    }

    private void setTime(long milliseconds) {
        mStartTimeInMillis = milliseconds;
        resetTimer();
    }

    private void resetTimer() {
        mTimeLeftInMillis = mStartTimeInMillis;
        updateCountDownText();
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
    }

    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
            }
        }.start();
        mTimerRunning = true;
    }

    private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted;

        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        }
        mTextViewCountDown.setText(timeLeftFormatted);

        //Send Email and Android Notification to User and Reset Timer
        if ((hours == 0) && (minutes == 0) && (seconds == 0)) {
            //sendEmail();  //To be used after finding a suitable SMTP server

            //Refresh the page and restart the timer
            //Intent mainActivity = new Intent(MainActivity.this, MainActivity.class);
            //startActivity(mainActivity);
            //finish();
            resetTimer();

            // Show an alert dialog
            if (activityVisible = true){
                ShowMessageBox("Detox Reminder", "Hello " + NameVal + ", It is time to take a 15-20 minutes break !!! Press 'Resume' after you return from break.");
            }
        }
    }

    private void ShowMessageBox(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNeutralButton("Resume", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Refresh the page and restart the timer
                Intent mainActivity = new Intent(MainActivity.this, MainActivity.class);
                startActivity(mainActivity);
                finish();
                resetTimer();
            }
        });
        builder.show();
    }

    private void sendEmail() {
        //Getting content for email
        String email = EmailVal;
        String subject = "Detox Reminder: Stop & Relax";
        String message = "Hello " + NameVal + ", It's time to stop and relax for 15-20 minutes, if you are working.";

        //Creating SendMail object
        SendMail sm = new SendMail(this, email, subject, message);

        //Executing sendmail to send email
        sm.execute();
    }
}


