package com.example.smartalert;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class fragment_report extends Fragment
{

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
        ConstraintLayout layout = (ConstraintLayout) view;

        radioGroup = layout.findViewById(R.id.radio_radioGroup);
        description = layout.findViewById(R.id.editText_enter_description);

       return view;
    }

    // check if a radio button value is selected by the user or not
    public String takeRadioButtonValue() {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = radioButton.findViewById(selectedId);
        if (selectedId == -1) {
            return "Nothing Selected";
        }
        else
        {
            return "OK";
        }
    }

    // this method is called as soon as the button is clicked by the user
    public void checkIfEveryFieldIsFilled(LayoutInflater inflater, ViewGroup container)
    {
        String radioValue = takeRadioButtonValue();
        String descriptionValue = description.getText().toString();
        if (descriptionValue == "" || radioValue == "Nothing Selected")
        {
            View view = inflater.inflate(R.layout.fragment_approve, container, false);
            //return View;
        }
        else
        {
            // show message to user
        }
    }

}