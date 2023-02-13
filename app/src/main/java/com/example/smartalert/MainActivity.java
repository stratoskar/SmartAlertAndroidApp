package com.example.smartalert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    // Login button
    public void login(View view){
        Intent intent = new Intent(this,User_Profile.class);
        startActivity(intent);
    }

    // Go to register page, if user does not have an account already
    public void go_to_register(View view){
        Intent intent = new Intent(this,Register.class);
        startActivity(intent);
    }
}