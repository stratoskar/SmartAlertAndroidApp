package com.example.smartalert;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class fragment_alerts extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alerts, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /* Adds a new card to the screen
    public addCard()
    {
        View view = getLayoutInflater().inflate(R.layout.card,null);
        TextView text = view.findViewById(R.id.textView);
        ImageView image = view.findViewById(R.id.imageView);
        Button accept_button = view.findViewById(R.id.Accept_Button);
        Button decline_button = view.findViewById(R.id.Decline_Button);
    }
    */
}