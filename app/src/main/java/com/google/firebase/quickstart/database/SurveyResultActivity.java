package com.google.firebase.quickstart.database;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.quickstart.database.survey.SurveyViewModel;

public class SurveyResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_result);
        Intent intent = getIntent();
        String message = intent.getStringExtra(getString(R.string.SURVEY_INTENT));
        int sum = Integer.parseInt(message);
       // Toast.makeText(this, "TOTAL "+sum,Toast.LENGTH_SHORT ).show();

        Button button = findViewById(R.id.button4);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        TextView result_header = findViewById(R.id.textView33);
        TextView result_text = findViewById(R.id.textView34);
        Button result_button = findViewById(R.id.button4);

        if(sum<21){
            result_header.setText(R.string.survey_result_1);
            result_header.setTextColor(getResources().getColor(R.color.fui_bgPhone));
            result_text.setText(R.string.survey_result_1_text);
        }
        else if(sum>=21 && sum <=32){
            result_header.setText(R.string.survey_result_2);
            result_header.setTextColor(getResources().getColor(R.color.secondary_text));
            result_text.setText(R.string.survey_result_2_text);
        }
        else if(sum>32){
            result_header.setText(R.string.survey_result_3);
            result_header.setTextColor(getResources().getColor(R.color.fui_bgEmail));
            result_text.setText(R.string.survey_result_3_text);
        }
    }
}
