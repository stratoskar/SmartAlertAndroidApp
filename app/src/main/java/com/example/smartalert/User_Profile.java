package com.example.smartalert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class User_Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
    }

    // View alerts button
    public void view_alerts(View view){
        Intent intent = new Intent(this,Summary.class);
        startActivity(intent);
    }

    // Report a case button
    public void report_alert(View view){
        Intent intent = new Intent(this,Report_Alert.class);
        startActivity(intent);
    }
}
