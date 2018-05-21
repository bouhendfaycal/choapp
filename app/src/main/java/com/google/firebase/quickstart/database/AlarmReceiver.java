package com.google.firebase.quickstart.database;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.quickstart.database.models.Alarm;

public class AlarmReceiver extends BroadcastReceiver implements View.OnTouchListener, View.OnClickListener {

    private View topLeftView;

    private Button overlayedButton;
    private float offsetX;
    private float offsetY;
    private int originalXPos;
    private int originalYPos;
    private boolean moving;
    private WindowManager wm;
    private View alarm_layout;
    private Context MyContext;
    private Ringtone ringtone;

    @Override
    public void onReceive(final Context context, Intent intent) {
        MyContext = context;
        //AlarmActivity.getTextView2().setText("Enough Rest. Do Work Now!");
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(context, uri);
        ringtone.play();

        int Alarm_Id = intent.getIntExtra("Alarm_Id", 0);

        Alarm alarm = new Alarm(context, Alarm_Id);


        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);


        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        alarm_layout = li.inflate(R.layout.receiver_alarm, null);


        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT);


        wm.addView(alarm_layout, params);


        Button clickButton = alarm_layout.findViewById(R.id.alarm_button);
        TextView textview = alarm_layout.findViewById(R.id.textview_alarm_title);


        textview.setText(alarm.Title);
        clickButton.setOnClickListener(this);



        /*Intent i = new Intent(context, AlarmReceiverActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);*/


    }

    @Override
    public void onClick(View view) {
        wm.removeView(alarm_layout);
        ringtone.stop();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        return false;
    }
}