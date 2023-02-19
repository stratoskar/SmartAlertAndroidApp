package com.example.smartalert;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class fragment_report extends Fragment
{
    private Button button;
    private EditText description;
    private RadioButton radioButton;
    private RadioGroup radioGroup;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        ScrollView layout = (ScrollView) view;

        // default views. From these views we want their values.
        radioGroup = layout.findViewById(R.id.radio_radioGroup);
        description = layout.findViewById(R.id.editText_enter_description);
        button = layout.findViewById(R.id.button_report);

        // code for when the button is pressed
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // the button is available to be pressed only when all values are not empty.
                if (!checkIfEveryFieldIsFilled())
                    return;

                // store to shared preferences the variables.
                SharedPreferences SP = getContext().getSharedPreferences("AlertData", Context.MODE_PRIVATE);
                SharedPreferences.Editor SpEditor = SP.edit();

                SpEditor.putString("Description", description.getText().toString());
                SpEditor.putString("Type", takeRadioButtonValue());
                SpEditor.apply();

                // start new activity.
                Intent intent = new Intent(getContext(), approve.class);
                startActivity(intent);
            }
        });

       return view;
    }

    // check if a radio button value is selected by the user or not
    public String takeRadioButtonValue()
    {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = radioGroup.findViewById(selectedId);

        if (selectedId == -1)
            return "Nothing Selected";

        return radioButton.getText().toString();
    }

    // this method is called as soon as the button is clicked by the user
    public boolean checkIfEveryFieldIsFilled()
    {
        if (description.getText().toString().trim().equals(""))
        {
            description.setError("Description cannot be empty.");
            return false;
        }

        if (takeRadioButtonValue().equals("Nothing Selected"))
        {
            Toast.makeText(getContext(), "Please select an incident.", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

}