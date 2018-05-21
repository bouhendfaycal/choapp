package com.google.firebase.quickstart.database;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.quickstart.database.models.Alarm;


public class AlarmActivity extends AppCompatActivity {
    private int timeHour;
    private int timeMinute;
    private TextView alarm_time;
    private RadioGroup week_days;
    private Alarm MyAlarm;
    private AlarmActivity context;
    private RadioGroup toggleGroup;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        context = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        Intent intent = getIntent();
        String message = intent.getStringExtra(getString(R.string.ALARM_EXTRA_MESSAGE));
        int i = Integer.parseInt(message);
        if (i == 12548964) {// case new alarm to create
            MyAlarm = new Alarm(this, i, true);
        } else {
            MyAlarm = new Alarm(this, i);
        }


        toggleGroup = ((RadioGroup) findViewById(R.id.toggleGroup));

        for (int j = 0; j < toggleGroup.getChildCount(); j++) {
            final ToggleButton view = (ToggleButton) toggleGroup.getChildAt(j);
            view.setChecked(MyAlarm.days[j]);
        }

        timeHour = MyAlarm.timeHour;
        timeMinute = MyAlarm.timeMinute;


        alarm_time = (TextView) findViewById(R.id.alarm_text_time);
        alarm_time.setText(MyAlarm.timeHour + ":" + MyAlarm.timeMinute);

        week_days = findViewById(R.id.toggleGroup);

        Button button_edit = (Button) findViewById(R.id.alarm_edit);
        Button button_delete = (Button) findViewById(R.id.alarm_delete);
        Button button_save = (Button) findViewById(R.id.alarm_save);
        final EditText alarmEditText = findViewById(R.id.alarmEditText);

        alarmEditText.setText(MyAlarm.Title);

        OnClickListener edit_alarm_listener = new OnClickListener() {
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt(MyConstants.HOUR, timeHour);
                bundle.putInt(MyConstants.MINUTE, timeMinute);
                MyDialogFragment fragment = new MyDialogFragment(new MyHandler());
                fragment.setArguments(bundle);
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(fragment, MyConstants.TIME_PICKER);
                transaction.commit();
            }
        };
        OnClickListener save_alarm_listener = new OnClickListener() {
            public void onClick(View view) {
                if (TextUtils.isEmpty(alarmEditText.getText().toString())) {
                    alarmEditText.setError("Enter Your Name");
                    alarmEditText.requestFocus();
                    return;
                } else {
                    MyAlarm.Title = alarmEditText.getText().toString();
                    MyAlarm.Save();
                    finish();
                }

            }
        };
        OnClickListener delete_alarm_listener = new OnClickListener() {
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle(getString(R.string.alarm_delete_tite))
                        .setMessage(getString(R.string.alarm_delete_message))
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        if (!MyAlarm.Delete()) {
                                            Toast.makeText(context, "Enregistrer d'abord", Toast.LENGTH_LONG).show();
                                        } else {
                                            finish();
                                        }
                                    }
                                }
                        )
                        .setNegativeButton(android.R.string.no, null).show();


            }
        };

        button_edit.setOnClickListener(edit_alarm_listener);
        button_delete.setOnClickListener(delete_alarm_listener);
        button_save.setOnClickListener(save_alarm_listener);
    }

    public void onToggle(View view) {
        ToggleButton v = (ToggleButton) view;
        ((RadioGroup) view.getParent()).check(view.getId());
        int indexOfMyView = ((RadioGroup) view.getParent()).indexOfChild(view);
        MyAlarm.days[indexOfMyView] = v.isChecked();
        Log.v("alarm", "" + indexOfMyView);
        // app specific stuff ..
    }


    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            MyAlarm.timeHour = bundle.getInt(MyConstants.HOUR);
            MyAlarm.timeMinute = bundle.getInt(MyConstants.MINUTE);
            alarm_time.setText(MyAlarm.timeHour + ":" + MyAlarm.timeMinute);
        }
    }
}