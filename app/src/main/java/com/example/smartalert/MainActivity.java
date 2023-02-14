package com.example.smartalert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
{
    EditText email;
    EditText password;
    RequestQueue requestQueue;

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
        // URL ENDPOINT. Here is all the backend magic. This is where we retrieve all users and alerts.
        String url = "http://192.168.1.92:3000/auth/login";

        // This string request sends a POST request to the endpoint.
        // It also handles the Requests accordingly. When the credentials are correct, it goes to the next Activity. When they aren't it shows an error message.
        StringRequest request = new StringRequest(Request.Method.POST, url,

                // this listener handles successful (HTTP 200) requests.
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        // it creates a JSON object and retrieves the AUTH_TOKEN generated from the API. It is then used to log in to the user's data.
                        try
                        {
                            // the response object has the token (like this : {"auth_token": "hasdjhf..."}) and through the JSONObject we handle it.
                            JSONObject jsonResponse = new JSONObject(response);
                            String token = jsonResponse.getString("auth_token");
                            User_Profile.setAuth_token(token);

                            // at this point, everything has worked correctly. We save the AUTH_TOKEN and then show the other activity
                            Intent intent = new Intent(MainActivity.this, User_Profile.class);
                            startActivity(intent);
                            finish();
                        }
                        catch (Exception e)
                        {
                            // otherwise there is some error. Show it.
                            e.printStackTrace();
                        }
                    }
                },

                // this is for when the credentials are wrong (or something else, like internet connection not established).
                error -> Toast.makeText(this, "Error, Wrong Credentials.", Toast.LENGTH_LONG).show())
        {
            // and these are the Params. Params are responsible to pass the data through the POST request.
            // our data here is only the e-mail and the password.
            @Override
            protected HashMap<String, String> getParams()
            {
                HashMap<String, String> params = new HashMap<>();
                params.put("email", email.getText().toString());
                params.put("password", password.getText().toString());
                return params;
            }
        };

        // send the request. Await for the listeners.
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    // Go to register page, if user does not have an account already
    public void go_to_register(View view)
    {
        Intent intent = new Intent(this,Register.class);
        startActivity(intent);
    }
}