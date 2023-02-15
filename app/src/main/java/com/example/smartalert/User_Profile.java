package com.example.smartalert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.smartalert.SmartAlert.SmartAlertAPIHandler;

public class User_Profile extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);


    }

    // Report a case button
    public void report_alert(View view)
    {
        Intent intent = new Intent(this,Report_Alert.class);
        startActivity(intent);
    }
}
