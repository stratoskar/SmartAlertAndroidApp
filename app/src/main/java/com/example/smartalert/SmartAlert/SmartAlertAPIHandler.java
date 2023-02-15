package com.example.smartalert.SmartAlert;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.smartalert.R;
import com.example.smartalert.ViewAlerts;

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

    /**
     <h1>Class Singleton</h1>
     Ensures that only one instance is created and used throughout the entire app.

     @param context Activity's context to create a RequestQueue.
     @return A {@link SmartAlertAPIHandler} object, which contains all the useful functions.
     */
    public static synchronized SmartAlertAPIHandler getInstance(Context context)
    {
        if (instance == null)
            instance = new SmartAlertAPIHandler(context);

        return instance;
    }

    private void AccountHandle(boolean Login, Activity activity, String... parameters)
    {
        // URL ENDPOINT. Here is all the backend magic. This is where we retrieve all users and alerts.
        String url = URL + (Login? "/auth/login" : "/signup");

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
                            Intent intent = new Intent(activity, ViewAlerts.class);
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
                        Toast.makeText(activity, activity.getString(R.string.toast_wrong_credentials), Toast.LENGTH_LONG).show();
                        break;
                    // 422 means that the email uniqueness is not covered.
                    case 422:
                        Toast.makeText(activity, activity.getString(R.string.toast_email_already_taken), Toast.LENGTH_LONG).show();
                        break;
                    // 500 code means internal server error.
                    case 500:
                        Toast.makeText(activity, activity.getString(R.string.toast_internal_server_error), Toast.LENGTH_LONG).show();
                        break;
                    // we have no idea what else is bound to be wrong.
                    default:
                        System.out.println(error.toString());
                        Toast.makeText(activity, activity.getString(R.string.toast_unexpected_error), Toast.LENGTH_LONG).show();
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
                params.put("email", parameters[0]);
                params.put("password", parameters[1]);

                if (!Login)
                {
                    params.put("name", parameters[2]);
                    params.put("password_confirmation", parameters[1]);
                }

                return params;
            }
        };

        // send the request. Await for the listeners.
        requestQueue.add(request);
    }

    /**
     * <h1>Authenticates the User.</h1>
     *
     * When provided with the correct credentials, redirects the user to their home menu.
     * Authenticates using standard authentication in Firebase.
     *
     * @param email An E-Mail is unique and is provided by the User through {@link android.widget.EditText}.
     * @param password A password is provided by the User through {@link android.widget.EditText} and securely stored in the database.
     * @param activity The {@link Context} that called this function.
     */
    public void Login(String email, String password, Activity activity)
    {
        AccountHandle(true, activity, email, password);
    }

    /**
     * <h1>Creates a User Account</h1>
     *
     * This method is responsible for creating Users and storing them into the Database. When successfully creating an account,
     * the User automatically gets redirected to their Home Menu.
     *
     * @param email All Users have a unique E-Mail and get authenticated using that. <b>E-Mails are unique and cannot be duplicate.</b>
     * @param password A password provided by the User, that is safely stored.
     * @param name The User's Full Name.
     * @param activity The {@link Context} that called this function.
     */
    public void Signup(String email, String password, String name, Activity activity)
    {
        AccountHandle(false, activity, email, password, name);
    }
}
