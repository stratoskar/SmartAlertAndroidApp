package com.example.smartalert;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smartalert.SmartAlert.SmartAlertAPIHandler;

import org.w3c.dom.Text;

import java.util.Objects;

public class fragment_alerts extends Fragment
{
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_alerts, container, false);

        RelativeLayout layout = (RelativeLayout) view;
        LinearLayout layout1 = layout.findViewById(R.id.LinearLayout_SCRLVIEW);

        ProgressBar progressBar = layout.findViewById(R.id.ProgressBar_CARD);
        progressBar.setVisibility(View.VISIBLE);

        SmartAlertAPIHandler.getInstance(this.getContext()).GetEventsBasedOnDangerUser(layout1, getLayoutInflater(), progressBar);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
}