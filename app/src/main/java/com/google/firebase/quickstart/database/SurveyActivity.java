package com.google.firebase.quickstart.database;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.quickstart.database.survey.StepperAdapter;
import com.stepstone.stepper.StepperLayout;

public class SurveyActivity extends AppCompatActivity {

    private StepperLayout mStepperLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        mStepperLayout = (StepperLayout) findViewById(R.id.stepperLayout);
        mStepperLayout.setAdapter(new StepperAdapter(getSupportFragmentManager(), this));
    }
}