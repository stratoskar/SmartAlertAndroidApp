package com.example.smartalert;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.LocaleListCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.smartalert.SmartAlert.SmartAlertAPIHandler;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity
{
    EditText email;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        email = findViewById(R.id.EditTextEmail);
        password = findViewById(R.id.EditTextPassword);
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
        SmartAlertAPIHandler.getInstance(this).Login(email.getText().toString(), password.getText().toString(), this);
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


    // Go to register page, if user does not have an account already
    public void go_to_register(View view)
    {
        Intent intent = new Intent(this,Register.class);
        startActivity(intent);
    }

    // test code (to be deleted)
    public void hidden(View view)
    {
        Intent intent = new Intent(this,test.class);
        startActivity(intent);
    }
}