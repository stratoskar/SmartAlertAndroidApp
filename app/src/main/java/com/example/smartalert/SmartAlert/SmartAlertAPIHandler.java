package com.example.smartalert.SmartAlert;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.smartalert.MainActivity;
import com.example.smartalert.User_Profile;

import org.json.JSONObject;

import java.util.HashMap;

public class SmartAlertAPIHandler
{
    private static final String URL = "http://192.168.1.92:3000";
    private String _token;
    private static SmartAlertAPIHandler instance;
    private final RequestQueue requestQueue;

    private SmartAlertAPIHandler(Context context)
    {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized SmartAlertAPIHandler getInstance(Context context)
    {
        if (instance == null)
            instance = new SmartAlertAPIHandler(context);

        return instance;
    }

    public void Login(String email, String password, Activity activity)
    {
        // URL ENDPOINT. Here is all the backend magic. This is where we retrieve all users and alerts.
        String url = URL + "/auth/login";

        // This string request sends a POST request to the endpoint.
        // It also handles the Requests accordingly. When the credentials are correct, it goes to the next Activity. When they aren't it shows an error message.
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    // this function is called when a successful response is returned (HTTP 200).
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            // Save the token somewhere safe.
                            JSONObject jsonResponse = new JSONObject(response);
                            _token = jsonResponse.getString("auth_token");

                            // and start the new activity.
                            Intent intent = new Intent(activity, User_Profile.class);
                            activity.startActivity(intent);
                            activity.finish();
                        }
                        catch (Exception e)
                        {
                            // this is when something unexpected goes wrong. (E.g. the activity doesn't exist.)
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                // individual wrong cases.
                switch (error.networkResponse.statusCode)
                {
                    // 401 means that there are wrong credentials given.
                    case 401:
                        Toast.makeText(activity, "Wrong E-Mail and/or Password.", Toast.LENGTH_LONG).show();
                        break;
                    // 500 code means internal server error.
                    case 500:
                        Toast.makeText(activity, "An internal server error has occurred. Please try again.", Toast.LENGTH_LONG).show();
                        break;
                    // we have no idea what else is bound to be wrong.
                    default:
                        System.out.println(error.toString());
                        Toast.makeText(activity, "An unexpected error occurred.", Toast.LENGTH_LONG).show();
                }
            }
        })
        {
            // and these are the Params. Params are responsible to pass the data through the POST request.
            // our data here is only the e-mail and the password.
            @Override
            protected HashMap<String, String> getParams()
            {
                HashMap<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        // send the request. Await for the listeners.
        requestQueue.add(request);
    }
}
