package com.example.smartalert;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.smartalert.R;
import com.google.firebase.auth.FirebaseAuth;

public class approve extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // start activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.approve);
    }
}

