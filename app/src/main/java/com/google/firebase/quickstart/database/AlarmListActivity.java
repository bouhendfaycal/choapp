package com.google.firebase.quickstart.database;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.quickstart.database.models.Alarm;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AlarmListActivity extends DrawerActivity {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private AlarmAdapter mAdapter;
    private FloatingActionButton fab;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_alarm_list__);

        LinearLayout contentFrameLayout = (LinearLayout) findViewById(R.id.content); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_alarm_list__, contentFrameLayout);

        context = this;
        super.setDrawer(false);

        setTitle(R.string.alarm);


        mRecyclerView = (RecyclerView) findViewById(R.id.all_alarms_recyclerview);

        fab = findViewById(R.id.fab_new_alarm);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AlarmActivity.class);
                //EditText editText = (EditText) findViewById(R.id.editText);
                //String message = editText.getText().toString();
                intent.putExtra(context.getString(R.string.ALARM_EXTRA_MESSAGE), "12548964");
                context.startActivity(intent);
            }
        });

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new AlarmAdapter(AlarmList(), this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private List<Alarm> AlarmList() {
        Gson gson = new Gson();
        SharedPreferences sharedPref = this.getSharedPreferences(
                this.getString(R.string.alarm_preference_file_key), Context.MODE_PRIVATE);
        String alarms_array_json = sharedPref.getString(this.getString(R.string.alarm_preference_key), "");

        Type type = new TypeToken<List<Alarm>>() {
        }.getType();
        List<Alarm> alarms_array;

        alarms_array = gson.fromJson(alarms_array_json, type);
        if (alarms_array == null) {

            alarms_array = new ArrayList<Alarm>();
        }
        return alarms_array;
    }

    @Override
    public void onResume() {
        super.onResume();
        // fetch updated data
        mAdapter = new AlarmAdapter(AlarmList(), this);
        mRecyclerView.setAdapter(mAdapter);
    }


}
