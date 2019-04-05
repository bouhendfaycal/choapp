package com.google.firebase.quickstart.database.survey;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

public class StepperAdapter extends AbstractFragmentStepAdapter {
    private static final String CURRENT_STEP_POSITION_KEY = "messageResourceId";

    public StepperAdapter(FragmentManager fm, Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {
        StepFragment_1 step = new StepFragment_1();
        Bundle b = new Bundle();
        b.putInt(CURRENT_STEP_POSITION_KEY, position);
        switch (position){
            case 0:
                final StepFragment_1 step1 = new StepFragment_1();
                Bundle b1 = new Bundle();
                b1.putInt(CURRENT_STEP_POSITION_KEY, position);
                step1.setArguments(b1);
                return step1;
            case 1:
                final StepFragment_2 step2 = new StepFragment_2();
                Bundle b2 = new Bundle();
                b2.putInt(CURRENT_STEP_POSITION_KEY, position);
                step2.setArguments(b2);
                return step2;
            case 2:
                final StepFragment_3 step3 = new StepFragment_3();
                Bundle b3 = new Bundle();
                b3.putInt(CURRENT_STEP_POSITION_KEY, position);
                step3.setArguments(b3);
                return step3;
            case 3:
                final StepFragment_4 step4 = new StepFragment_4();
                Bundle b4 = new Bundle();
                b4.putInt(CURRENT_STEP_POSITION_KEY, position);
                step4.setArguments(b4);
                return step4;
            case 4:
                final StepFragment_5 step5 = new StepFragment_5();
                Bundle b5 = new Bundle();
                b5.putInt(CURRENT_STEP_POSITION_KEY, position);
                step5.setArguments(b5);
                return step5;
            case 5:
                final StepFragment_6 step6 = new StepFragment_6();
                Bundle b6 = new Bundle();
                b6.putInt(CURRENT_STEP_POSITION_KEY, position);
                step6.setArguments(b6);
                return step6;
            case 6:
                final StepFragment_7 step7 = new StepFragment_7();
                Bundle b7 = new Bundle();
                b7.putInt(CURRENT_STEP_POSITION_KEY, position);
                step7.setArguments(b7);
                return step7;
            case 7:
                final StepFragment_8 step8 = new StepFragment_8();
                Bundle b8 = new Bundle();
                b8.putInt(CURRENT_STEP_POSITION_KEY, position);
                step8.setArguments(b8);
                return step8;
        }
        step.setArguments(b);
        return step;
    }

    @Override
    public int getCount() {
        return 8;
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position) {
        //Override this method to set Step title for the Tabs, not necessary for other stepper types
        return new StepViewModel.Builder(context)
                //.setTitle("title") //can be a CharSequence instead
                .create();
    }
}