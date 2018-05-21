package com.google.firebase.quickstart.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class NotesActivity extends DrawerActivity {

    Button savenote;
    TextView textnote;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.note);
        context = this;
        LinearLayout contentFrameLayout = (LinearLayout) findViewById(R.id.content); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_notes, contentFrameLayout);
        super.setDrawer(false);


        savenote = findViewById(R.id.note_save);
        textnote = findViewById(R.id.note_text);


        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.note_preference_file_key), MODE_PRIVATE);
        String note_text = sharedPref.getString(getString(R.string.note_preference_key), "");

        textnote.setText(note_text);

        savenote.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                SharedPreferences sharedPref = getSharedPreferences(
                        getString(R.string.note_preference_file_key), MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.note_preference_key), textnote.getText().toString());
                editor.commit();
                Toast.makeText(context, getString(R.string.operation_done), Toast.LENGTH_LONG).show();


            }
        });


    }
}
