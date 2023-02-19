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
        // fragment alerts show
        View view = inflater.inflate(R.layout.fragment_alerts, container, false);

        // relative layout and linear layouts from the children views
        RelativeLayout layout = (RelativeLayout) view;
        LinearLayout layout1 = layout.findViewById(R.id.LinearLayout_SCRLVIEW);

        // progress bar set to visible because it's loading
        ProgressBar progressBar = layout.findViewById(R.id.ProgressBar_CARD);
        progressBar.setVisibility(View.VISIBLE);

        // load all alerts
        SmartAlertAPIHandler.getInstance(this.getContext()).GetEventsBasedOnDangerUser(layout1, getLayoutInflater(), progressBar, getActivity());

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
}