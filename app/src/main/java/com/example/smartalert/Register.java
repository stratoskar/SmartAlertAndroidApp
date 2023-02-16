package com.example.smartalert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smartalert.SmartAlert.SmartAlertAPIHandler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity
{
    EditText email;
    EditText password;
    EditText name;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // initialize app.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        // hide action support bar
        getSupportActionBar().hide();

        // email, name and password values
        email = findViewById(R.id.EditTextEmail_REGISTER);
        password = findViewById(R.id.EditTextPassword_REGISTER);
        name = findViewById(R.id.EditTextFullName_REGISTER);

        // firebase auth singleton
        mAuth = FirebaseAuth.getInstance();
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

        SmartAlertAPIHandler.getInstance(this).Signup(email.getText().toString(), password.getText().toString(), name.getText().toString(), this);
    }

    // Go to login page, if user has an account already
    public void go_to_login(View view)
    {
        Intent intent = new Intent(this,login.class);
        startActivity(intent);
    }
}