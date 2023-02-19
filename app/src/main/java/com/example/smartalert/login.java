package com.example.smartalert;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationManager;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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

import android.Manifest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class login extends AppCompatActivity {
    EditText email;
    EditText password;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    double longitude;
    double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // start activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // see if location is requested.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // request location if not.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 582);
        } else {
            // otherwise set location.
            SetLocation();
        }

        // hide action support bar
        getSupportActionBar().hide();

        // set variables for the view
        email = findViewById(R.id.EditTextEmail);
        password = findViewById(R.id.EditTextPassword);
        progressBar = findViewById(R.id.ProgressBar_LOGIN);

        // FirebaseAuth singleton
        mAuth = FirebaseAuth.getInstance();
    }

    private void SetLocation()
    {
        // called only when the permission is granted.
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // don't get permission if it's not granted. (REQUIRED GTXM GIA JAVA)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }

        // get the last known location and store it for later.
        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (lastKnownLocation != null)
        {
            latitude = lastKnownLocation.getLatitude();
            longitude = lastKnownLocation.getLongitude();
            System.out.println(latitude + " " + longitude);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 582)
        {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                SetLocation();
            } else
            {
                finish();
            }
        }
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
        SmartAlertAPIHandler.getInstance(this).Login(email.getText().toString(), password.getText().toString(), latitude, longitude, this, progressBar);

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