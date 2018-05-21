package com.google.firebase.quickstart.database.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.quickstart.database.R;
import com.google.firebase.quickstart.database.models.Alarm;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;


public class AlarmsFragment extends Fragment {
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    private RecyclerView.Adapter mAdapter;
    private List<Alarm> Alarms;


    private List<Alarm> AlarmList() {
        Gson gson = new Gson();


        SharedPreferences sharedPref = getContext().getSharedPreferences(
                getContext().getString(R.string.alarm_preference_file_key), Context.MODE_PRIVATE);

        String alarms_array_json = sharedPref.getString(getContext().getString(R.string.alarm_preference_key), null);

        Type type = new TypeToken<List<Alarm>>() {
        }.getType();
        List<Alarm> alarms_array = gson.fromJson(alarms_array_json, type);
        return alarms_array;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        Alarms = AlarmList();
        View rootView = inflater.inflate(R.layout.fragment_all_alarms, container, false);


        mRecycler = rootView.findViewById(R.id.all_alarms);
        mRecycler.setHasFixedSize(true);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mManager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(mManager);

        mAdapter = new RecyclerView.Adapter<CustomViewHolder>() {
            @Override
            public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_alarm
                        , viewGroup, false);
                return new CustomViewHolder(view);
            }

            @Override
            public void onBindViewHolder(CustomViewHolder viewHolder, int i) {
                viewHolder.noticeSubject.setText(Alarms.get(i).getAlarm_Id());
            }

            @Override
            public int getItemCount() {
                return Alarms.size();
            }

        };


    }

    private class CustomViewHolder extends RecyclerView.ViewHolder {

        public TextView noticeSubject;

        public CustomViewHolder(View itemView) {
            super(itemView);

            noticeSubject = (TextView) itemView.findViewById(R.id.alarm_title);
        }
    }

}
