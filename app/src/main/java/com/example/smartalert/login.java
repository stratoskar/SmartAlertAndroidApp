package com.example.smartalert;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.smartalert.SmartAlert.SmartAlertAPIHandler;

import java.util.Locale;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.firebase.ui.auth.util.ExtraConstants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class login extends AppCompatActivity
{
    EditText email;
    EditText password;
    ProgressBar progressBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // start activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // set variables for the view
        email = findViewById(R.id.EditTextEmail);
        password = findViewById(R.id.EditTextPassword);
        progressBar = findViewById(R.id.ProgressBar_LOGIN);

        // FirebaseAuth singleton
        mAuth = FirebaseAuth.getInstance();
    }

    // Login button
    public void Login(View view)
    {

        boolean skip = false;

        // the email field is required.
        if (email.getText().toString().trim().isEmpty())
        {
            email.setError(getString(R.string.warning_email));
            skip = true;
        }

        // so is the password.
        if (password.getText().toString().trim().isEmpty())
        {
            password.setError(getString(R.string.warning_password));
            skip = true;
        }

        // and if at least one of them is empty, don't continue.
        if (skip) return;

        // otherwise try to login.
        SmartAlertAPIHandler.getInstance(this).Login(email.getText().toString(), password.getText().toString(), this, progressBar);

        // Set the progress bar to be active.
        progressBar.setVisibility(View.VISIBLE);
    }


    // Go to register page, if user does not have an account already
    public void go_to_register(View view)
    {
        Intent intent = new Intent(this,Register.class);
        startActivity(intent);
    }

    public void ChangeLocale(View view)
    {
        Locale locale = new Locale((view.getId() == R.id.ImageViewEN_LOGIN) ? "en" : "el");
        Locale.setDefault(locale);

        Resources resources = getApplicationContext().getResources();
        Configuration configuration = resources.getConfiguration();

        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        recreate();
        System.out.println(locale.toString());
        System.out.println(getString(R.string.warning_password));
    }



}