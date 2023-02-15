package com.example.smartalert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class User_Profile extends AppCompatActivity
{
    private static String auth_token;

    public static void setAuth_token(String token)
    {
        auth_token = token;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        System.out.println(auth_token);
    }

    // Report a case button
    public void report_alert(View view)
    {
        Intent intent = new Intent(this,Report_Alert.class);
        startActivity(intent);
    }
}
