package com.google.firebase.quickstart.database;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.quickstart.database.models.Alarm;
import com.google.firebase.quickstart.database.models.Entry;

import org.litepal.crud.DataSupport;

import java.util.Calendar;

public class EntryActivity extends AppCompatActivity {

    private int minute;
    private int hour;
    private int day;
    private int month;
    private int year ;
    private Entry entry;
    private Spinner entry_creneau;
    private EditText entry_glycemie;
    private Button entry_save;
    private EditText entry_pression;
    private EditText entry_note;
    private EditText entry_acetone;
    private Context context;
    private Button entry_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        setContentView(R.layout.activity_entry);



        Intent intent = getIntent();
        int i = intent.getIntExtra(getString(R.string.ENTRY_EXTRA_MESSAGE),1);
        if (i == 12548964) {// case new alarm to create
            entry = new Entry();
        } else {
            entry = DataSupport.find(Entry.class,i);
        }



        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,R.array.category, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        final TextView entry_time = findViewById(R.id.entry_time);
        final TextView entry_date = findViewById(R.id.entry_date);

        entry_creneau = findViewById(R.id.entry_creneau);
        entry_glycemie = findViewById(R.id.entry_glycemie);
        entry_pression = findViewById(R.id.entry_pression);
        entry_acetone = findViewById(R.id.entry_acetone);
        entry_note = findViewById(R.id.entry_note);
        entry_save = findViewById(R.id.entry_save);
        entry_delete = findViewById(R.id.entry_delete);

        //initialising Entry time
        minute = entry.getmMinute();
        hour = entry.getmHour();
        day = entry.getmDay();
        month = entry.getmMonth();
        year=entry.getmYear();

        //initialising Entry form
        entry_time.setText(hour + ":" + minute);
        entry_date.setText(year + "-" + month  + "-" + day);
        entry_creneau.setAdapter(spinnerAdapter);
        entry_creneau.setSelection(entry.getCreneau(),true);// return entry position from spinner selection array
        entry_glycemie.setText(String.valueOf(entry.getTauxGlycemie()));
        entry_pression.setText(String.valueOf(entry.getPressionArterielle()));
        entry_acetone.setText(String.valueOf(entry.getAcetone()));
        entry_note.setText(String.valueOf(entry.getNote()));

        //region Time and Date picker listeners
        entry_time.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(EntryActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minutes) {
                                entry_time.setText(hourOfDay + ":" + minutes);
                                hour = hourOfDay;
                                minute = minutes;

                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        entry_date.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(EntryActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        /*      Your code   to get date and time    */
                        selectedmonth = selectedmonth + 1;
                        entry_date.setText(selectedyear + "-" + selectedmonth  + "-" + selectedday);
                        year =selectedyear;
                        month = selectedmonth;
                        day = selectedday;
                    }
                }, year, month, day);
                mDatePicker.show();
            }
        });
        //endregion

        //region Save button click

        entry_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entry.setDateTime(year,month,day,hour,minute);
                entry.setTauxGlycemie(Float.valueOf(entry_glycemie.getText().toString()));
                entry.setPressionArterielle(Float.valueOf(entry_pression.getText().toString()));
                entry.setAcetone(Float.valueOf(entry_acetone.getText().toString()));
                entry.setNote(entry_note.getText().toString());
                entry.setCreneau(entry_creneau.getSelectedItemPosition());

                entry.save();
                Toast.makeText(context, context.getString(R.string.operation_done), Toast.LENGTH_LONG).show();
                finish();
            }
        });

        entry_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle(getString(R.string.alarm_delete_tite))
                        .setMessage(getString(R.string.entry_delete_message))
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        if (!entry.isSaved()) {
                                            Toast.makeText(context, "Enregistrer d'abord", Toast.LENGTH_LONG).show();
                                        } else {
                                            entry.delete();
                                            finish();
                                        }
                                    }
                                }
                        )
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });
        //endregion

    }
}
