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

import com.example.smartalert.SmartAlert.SmartAlertAPIHandler;

public class fragment_alerts extends Fragment
{
    ViewGroup container;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        this.container = container;
        return inflater.inflate(R.layout.fragment_alerts, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        SmartAlertAPIHandler.getInstance(this.getContext()).GetEventsBasedOnDangerUser();
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