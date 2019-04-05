package com.google.firebase.quickstart.database.survey;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.quickstart.database.R;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import java.util.ArrayList;
import java.util.List;

public class StepFragment_7 extends Fragment implements Step {

    private List<CheckBox> checkBoxs;
    private boolean valid;
    ViewGroup viewGroup;
    RadioGroup group1;
    RadioGroup group2;
    private SurveyViewModel model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.step_7, container, false);
        viewGroup = (ViewGroup) v;


        //initialize your UI
        checkBoxs = new ArrayList<CheckBox>();

        checkBoxs.add((CheckBox) v.findViewById(R.id.step_7_checkBox1));
        checkBoxs.add((CheckBox) v.findViewById(R.id.step_7_checkBox2));
        checkBoxs.add((CheckBox) v.findViewById(R.id.step_7_checkBox3));
        checkBoxs.add((CheckBox) v.findViewById(R.id.step_7_checkBox4));
        checkBoxs.add((CheckBox) v.findViewById(R.id.step_7_checkBox5));
        checkBoxs.add((CheckBox) v.findViewById(R.id.step_7_checkBox6));

        group1 = v.findViewById(R.id.radioGroup7);
        group2 = v.findViewById(R.id.radioGroup8);

        model = ViewModelProviders.of(getActivity()).get(SurveyViewModel.class);


        return v;
    }

    @Override
    public VerificationError verifyStep() {
        //return null if the user can go to the next step, create a new VerificationError instance otherwise
        valid = false;
        int points = 0;
        int mSize = checkBoxs.size();

        int total1 =0,total2 =0;

        for(int i = 0; i < mSize; i++){
            CheckBox element = checkBoxs.get(i);
            if(element.isChecked()) {
                valid = true;
                total1 += Integer.parseInt((String) element.getTag());
            }
        }
        if(valid){
            if(group1.getCheckedRadioButtonId() != -1 && group2.getCheckedRadioButtonId() != -1 ){
                int selectedId = group1.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = (RadioButton) viewGroup.findViewById(selectedId);
                int point1 = Integer.parseInt((String)selectedRadioButton.getTag());
                selectedId = group2.getCheckedRadioButtonId();
                selectedRadioButton = (RadioButton) viewGroup.findViewById(selectedId);
                int point2 = Integer.parseInt((String)selectedRadioButton.getTag());

                total2 += Math.max(point1, point2);
            }else {
                valid = false;
            }
        }

        if(!valid) {
            return new VerificationError(getString(R.string.step_validation_error));
        }else{
            model.setValue(10,total1);
            model.setValue(11,total1);
            points = total1 + total2;
            Toast.makeText(getActivity(), "points "+points,Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    @Override
    public void onSelected() {
        //update UI when selected



    }

    @Override
    public void onError(@NonNull VerificationError error) {
        //handle error inside of the fragment, e.g. show error on EditText
    }

}