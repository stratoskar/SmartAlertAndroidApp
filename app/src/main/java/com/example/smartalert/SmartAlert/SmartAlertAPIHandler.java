package com.example.smartalert.SmartAlert;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.smartalert.R;
import com.example.smartalert.ViewAlerts;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * <h1>Handles all API Requests.</h1>
 *
 * All the magic for logging Users in and out, retrieving alerts and handling settings are done here.
 * This class sends GET and POST http requests to the registered {@link FirebaseAuth} API and to another
 * SmartAlert API made by the team.
 */
public class SmartAlertAPIHandler
{
    private static final String URL = "https://us-central1-smartalertapi.cloudfunctions.net/api/";
    private String _token;
    private static SmartAlertAPIHandler instance;
    private final RequestQueue requestQueue;
    private final FirebaseAuth mAuth;
    private FirebaseUser User;

    private SmartAlertAPIHandler(Context context)
    {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        mAuth = FirebaseAuth.getInstance();
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

    private void SetFirstNameAndLastName(String firstname, String lastname)
    {
        // URL ENDPOINT. Here is all the backend magic. This is where we retrieve all users and alerts.
        String url = URL + "users";

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
                            System.out.println("Successfully set values firstName and lastName for currently logged on User.");
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

                if (error == null)
                {
                    return;
                }

                error.printStackTrace();
            }
        })
        {
            // These are the Headers.
            // Headers help authenticate users through tokens without having to type their password every single time.
            @Override
            public HashMap<String, String> getHeaders()
            {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + _token);
                headers.put("Content-Type", "application/json");
                return headers;
            }

            // The backend module gets responses with JSON Body
            // our previously set body here is set.
            @Override
            public byte[] getBody()
            {
                String body = String.format("{\"firstName\": \"%s\", \"lastName\": \"%s\"}", firstname, lastname);

                try
                {
                    return body == null ? null : body.getBytes("utf-8");
                }
                catch (UnsupportedEncodingException e)
                {
                    return null;
                }
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
    public void Login(String email, String password, Activity activity, ProgressBar progressBar)
    {
        // sign in the user using email and password.
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity, task ->
                {
                    // if the user successfully logs in
                    if (task.isSuccessful())
                    {
                        // save the User object.
                        User = mAuth.getCurrentUser();

                        System.out.println(User.getUid());
                        // get the user's token.
                        User.getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<GetTokenResult> task)
                            {
                                _token = task.getResult().getToken();
                                System.out.println(_token);
                            }
                        });

                        // redirect to the new Activity.
                        Intent intent = new Intent(activity, ViewAlerts.class);
                        activity.startActivity(intent);
                        activity.finish();
                    }

                    task.addOnFailureListener(new OnFailureListener()
                    {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            if (e.getClass() == FirebaseAuthInvalidCredentialsException.class)
                            {
                                Toast.makeText(activity, activity.getString(R.string.toast_wrong_credentials), Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                    // get the progressbar to be gone
                    progressBar.setVisibility(View.GONE);
                });
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
        // using the default firebase auth method, sign the user up.
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity, task ->
        {
            // if the user successfully signs up.
            if (task.isSuccessful())
            {
                // save the user.
                User = mAuth.getCurrentUser();

                // get the user's token.
                User.getIdToken(false).addOnCompleteListener(new OnCompleteListener<GetTokenResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<GetTokenResult> task)
                    {
                        // save the token.
                        _token = task.getResult().getToken();

                        System.out.println(_token);

                        // get the user's name
                        String firstname = name.split(" ")[0];
                        String lastname = name.split(" ")[1];

                        // and set it
                        SetFirstNameAndLastName(firstname, lastname);
                    }
                });


                // start the new activity.
                Intent intent = new Intent(activity, ViewAlerts.class);
                activity.startActivity(intent);
                activity.finish();
            }
            else
            {
                // notify the user.
                Toast.makeText(activity, activity.getString(R.string.toast_wrong_credentials), Toast.LENGTH_LONG).show();
            }
        });
    }
}
