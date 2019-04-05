package com.google.firebase.quickstart.database;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.quickstart.database.survey.StepperAdapter;
import com.google.firebase.quickstart.database.survey.SurveyViewModel;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

public class SurveyActivity extends DrawerActivity implements StepperLayout.StepperListener {

    private StepperLayout mStepperLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setDrawer(false);
        LinearLayout contentFrameLayout = (LinearLayout) findViewById(R.id.content); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_survey, contentFrameLayout);
        setTitle(R.string.survey);
        mStepperLayout = (StepperLayout) findViewById(R.id.stepperLayout);
        mStepperLayout.setAdapter(new StepperAdapter(getSupportFragmentManager(), this));
        mStepperLayout.setListener(this);
    }

    @Override
    public void onCompleted(View completeButton) {
        //Toast.makeText(this, "onCompleted!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,SurveyResultActivity.class);
        SurveyViewModel model = ViewModelProviders.of(this).get(SurveyViewModel.class);

        String sum = ""+model.getTotalPoint();
        intent.putExtra(getString(R.string.SURVEY_INTENT), sum);
        startActivity(intent);

    }

    @Override
    public void onError(VerificationError verificationError) {
        Toast.makeText(this, "onError! -> " + verificationError.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStepSelected(int newStepPosition) {

    }

    @Override
    public void onReturn() {
        finish();
    }
}