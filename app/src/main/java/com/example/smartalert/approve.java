package com.example.smartalert;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.smartalert.R;
import com.example.smartalert.SmartAlert.SmartAlertAPIHandler;
import com.google.firebase.auth.FirebaseAuth;

public class approve extends AppCompatActivity
{
    Button buttonApprove;
    String Description;
    String Type;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // start activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.approve);

        // set the button.
        buttonApprove = findViewById(R.id.button_approve);
        ProgressBar progressBar = findViewById(R.id.progressBar);

        buttonApprove = findViewById(R.id.button_approve);

        // set the shared preferences.
        SharedPreferences SP = getSharedPreferences("AlertData", MODE_PRIVATE);
        Description = SP.getString("Description", "");
        Type = SP.getString("Type", "");

        LinearLayout layout = findViewById(R.id.LinearLayout_approve_cards);

       SmartAlertAPIHandler.getInstance(this).ConfirmCloseEvents(progressBar, getLayoutInflater(), layout, this);
    }

    public void SendAlert(View v)
    {
        SmartAlertAPIHandler.getInstance(this).SendUnapprovedAlert(Type, Description);
    }
}

