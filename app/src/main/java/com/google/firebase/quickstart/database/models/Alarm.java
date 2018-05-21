package com.google.firebase.quickstart.database.models;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.quickstart.database.AlarmActivity;
import com.google.firebase.quickstart.database.AlarmReceiver;
import com.google.firebase.quickstart.database.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Alarm {

    @Expose
    private int Alarm_Id;
    @Expose
    public int timeHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    @Expose
    public int timeMinute = Calendar.getInstance().get(Calendar.MINUTE);
    @Expose
    public String Title;
    private Intent alarmIntent;
    private PendingIntent alarmPendingIntent;
    private Context context;
    private boolean IsNew;
    @Expose
    public boolean[] days;


    public Alarm() {
    }

    public Alarm(Context ctx) {
        this(ctx, 0, true);
    }

    public Alarm(Context ctx, int alarmId) {
        this(ctx, alarmId, false);
    }


    public Alarm(Context ctx, int alarmId, boolean isNew) {
        this.IsNew = isNew;
        this.context = ctx;
        days = new boolean[7];

        if (IsNew) {
            Alarm_Id = createID();
            for (int i = 0; i < 7; i++) {
                days[i] = false;
            }
            Arrays.fill(days, false);
        } else {
            Alarm_Id = alarmId;
            FromSaved();
        }
        alarmIntent = new Intent(context, AlarmReceiver.class);
        alarmIntent.putExtra("Alarm_Id", Alarm_Id);
        alarmPendingIntent = PendingIntent.getBroadcast(context, Alarm_Id, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void Set() {
        /*Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, timeHour);
        calendar.set(Calendar.MINUTE, timeMinute);
        calendar.set(Calendar.SECOND, 0);



        if (System.currentTimeMillis() > calendar.getTimeInMillis()) {
            //calendar.add(Calendar.DATE, 1);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY * 7, alarmPendingIntent);
        }
        else
            Toast.makeText(context , "KITKAT not supported", Toast.LENGTH_LONG).show();*/

        for (int i = 0; i < 7; i++) {
            if (days[i]) {
                scheduleAlarm(i + 1);
            }
        }
    }

    private void scheduleAlarm(int dayOfWeek) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        calendar.set(Calendar.HOUR_OF_DAY, timeHour);
        calendar.set(Calendar.MINUTE, timeMinute);
        calendar.set(Calendar.SECOND, 0);

        // Check we aren't setting it in the past which would trigger it to fire instantly
        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 7);
        }
        // Set this to whatever you were planning to do at the given time
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, alarmPendingIntent);
    }


    private void Cancel() {
        alarmPendingIntent.cancel();
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(alarmPendingIntent);
    }

    public void Save() {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.alarm_preference_file_key), context.MODE_PRIVATE);
        String alarms_array_json = sharedPref.getString(context.getString(R.string.alarm_preference_key), "");
        Type type = new TypeToken<List<Alarm>>() {
        }.getType();
        List<Alarm> alarms_array;
        alarms_array = gson.fromJson(alarms_array_json, type);
        if (alarms_array == null) {

            alarms_array = new ArrayList<Alarm>();
        }
        if (IsNew) {
            alarms_array.add(this);
        } else {
            int array_size = alarms_array.size();
            for (int i = 0; i < array_size; i++) {
                if (alarms_array.get(i).getAlarmId() == Alarm_Id) {
                    alarms_array.set(i, this);
                    Log.d("alarm_to_save" + timeHour + ":" + timeMinute, "" + Alarm_Id);
                    IsNew = false;
                }
            }
        }
        String json = gson.toJson(alarms_array);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.alarm_preference_key), json);
        editor.commit();
        Toast.makeText(context, context.getString(R.string.operation_done), Toast.LENGTH_LONG).show();
        Set();
    }

    public boolean Delete() {
        Cancel();
        Gson gson = new Gson();
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.alarm_preference_file_key), Context.MODE_PRIVATE);
        String alarms_array_json = sharedPref.getString(context.getString(R.string.alarm_preference_key), "");
        Type type = new TypeToken<List<Alarm>>() {
        }.getType();
        List<Alarm> alarms_array = gson.fromJson(alarms_array_json, type);
        int array_size = alarms_array.size();
        for (int i = 0; i < array_size; i++) {
            if (alarms_array.get(i).getAlarmId() == Alarm_Id) {

                alarms_array.remove(i);
                String json = gson.toJson(alarms_array);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(context.getString(R.string.alarm_preference_key), json);
                editor.commit();
                return true;
            }
        }
        return false;
    }

    private boolean FromSaved() {
        Gson gson = new Gson();
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.alarm_preference_file_key), Context.MODE_PRIVATE);
        String alarms_array_json = sharedPref.getString(context.getString(R.string.alarm_preference_key), "");

        Type type = new TypeToken<List<Alarm>>() {
        }.getType();
        List<Alarm> alarms_array = gson.fromJson(alarms_array_json, type);

        int array_size = alarms_array.size();
        for (int i = 0; i < array_size; i++) {
            if (alarms_array.get(i).getAlarmId() == Alarm_Id) {

                timeHour = alarms_array.get(i).timeHour;
                timeMinute = alarms_array.get(i).timeMinute;
                days = alarms_array.get(i).days;
                Title = alarms_array.get(i).Title;
                return true;
            }
        }
        Toast.makeText(context, "Alarm not found", Toast.LENGTH_LONG).show();

        return false;
    }

    public int createID() {
        Date now = new Date();
        int id = Integer.parseInt(new SimpleDateFormat("ddHHmmss", Locale.US).format(now));
        return id;
    }

    public String getAlarm_Id() {
        String id = Integer.toString(Alarm_Id);
        return id;
    }

    public int getAlarmId() {
        return Alarm_Id;
    }

    public String getTime() {
        return timeHour + " : " + timeMinute;
    }

}
