package com.google.firebase.quickstart.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CallActivity extends DrawerActivity {

    Button savecall;
    TextView textcall;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.call);
        context = this;
        LinearLayout contentFrameLayout = (LinearLayout) findViewById(R.id.content); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_call, contentFrameLayout);
        super.setDrawer(false);


        savecall = findViewById(R.id.call_save);
        textcall = findViewById(R.id.call_text);


        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.call_preference_file_key), MODE_PRIVATE);
        String call_text = sharedPref.getString(getString(R.string.call_preference_key), "");

        textcall.setText(call_text);

        savecall.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                SharedPreferences sharedPref = getSharedPreferences(
                        getString(R.string.call_preference_file_key), MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.call_preference_key), textcall.getText().toString());
                editor.commit();
                Toast.makeText(context, getString(R.string.operation_done), Toast.LENGTH_LONG).show();


            }
        });


    }
}
