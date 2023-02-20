package com.example.smartalert;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.smartalert.SmartAlert.SmartAlertAPIHandler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class Register extends AppCompatActivity
{
    EditText email;
    EditText password;
    EditText name;
    ProgressBar progressBar;
    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // initialize app.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        // hide action support bar
        getSupportActionBar().hide();

        // see if location is requested.
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // request location if not.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 582);
        } else {
            // otherwise set location.
            SetLocation();
        }

        // email, name and password values
        email = findViewById(R.id.EditTextEmail_REGISTER);
        password = findViewById(R.id.EditTextPassword_REGISTER);
        name = findViewById(R.id.EditTextFullName_REGISTER);
        progressBar = findViewById(R.id.ProgressBar_REGISTER);
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

    // Register button
    public void register(View view)
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

        // and the fullname.
        if (name.getText().toString().trim().isEmpty())
        {
            name.setError(getString(R.string.warning_name));
            skip = true;
        }

        // and if at least one of them is empty, don't continue.
        if (skip) return;

        SmartAlertAPIHandler.getInstance(this).Signup(email.getText().toString(), password.getText().toString(), name.getText().toString(), latitude, longitude, this, progressBar);

        // Set the progress bar to be active.
        progressBar.setVisibility(View.VISIBLE);
    }

    // Go to login page, if user has an account already
    public void go_to_login(View view)
    {
        Intent intent = new Intent(this,login.class);
        startActivity(intent);
    }

    public void ChangeLocale(View view)
    {
        Locale locale = new Locale((view.getId() == R.id.ImageViewEN_REGISTER) ? "en" : "el");
        Locale.setDefault(locale);

        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();

        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        recreate();
    }
}