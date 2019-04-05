package com.google.firebase.quickstart.database.survey;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.quickstart.database.R;
import com.stepstone.stepper.VerificationError;

public class StepFragment extends Fragment {
    /*TODO :
     * form validation and error output with toast
     * get the selected value from radiobutton group
     * */

    private boolean valid;
    private SurveyViewModel model;
    private int total_points;


    private void recursiveVerify(ViewGroup v){
        int childCount = v.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View element = v.getChildAt(i);

             if (element instanceof RadioGroup) {
                if (((RadioGroup)element).getCheckedRadioButtonId() == -1)
                {
                    valid = false;
                }
            }else if (element instanceof ViewGroup){
                recursiveVerify((ViewGroup) element);
            }
        }
    }

    protected boolean verifyForm(ViewGroup v){
        valid = true;
        recursiveVerify(v);
        return valid;
    }

    protected VerificationError validateForm(ViewGroup v) {
        //return null if the user can go to the next step, create a new VerificationError instance otherwise
        boolean isValid = verifyForm(v);
        if(!isValid) {
            return new VerificationError(getString(R.string.step_validation_error));
        }else{
            model = ViewModelProviders.of(getActivity()).get(SurveyViewModel.class);
            total_points = 0;
            recursiveupdatePoints(v);
            //Toast.makeText(getActivity(), "points "+total_points,Toast.LENGTH_SHORT).show();
            return null;
        }
    }



    private void recursiveupdatePoints(ViewGroup v){
        int childCount = v.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View element = v.getChildAt(i);

            if (element instanceof RadioGroup) {
                    int question_index = Integer.parseInt(element.getTag().toString());
                    int selectedId = ((RadioGroup)element).getCheckedRadioButtonId();
                    // find the radiobutton by returned id
                    RadioButton selectedRadioButton = (RadioButton) v.findViewById(selectedId);
                    int points = Integer.parseInt((String)selectedRadioButton.getTag());
                    model.setValue(question_index,points);
                    total_points+= points;
            }else if (element instanceof ViewGroup){
                recursiveupdatePoints((ViewGroup) element);
            }
        }
    }
}
