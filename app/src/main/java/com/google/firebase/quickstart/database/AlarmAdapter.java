package com.google.firebase.quickstart.database;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.quickstart.database.models.Alarm;

import java.net.ConnectException;
import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolder> {
    private List<Alarm> mDataset;
    private Context context;

    public AlarmAdapter(List<Alarm> myDataset, Context c) {
        //context = c;
        mDataset = myDataset;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_alarm, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        viewHolder.mTextView.setText(mDataset.get(position).getTime());
        viewHolder.mtTextView.setText(mDataset.get(position).Title);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context," position is ",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, AlarmActivity.class);
                //EditText editText = (EditText) findViewById(R.id.editText);
                //String message = editText.getText().toString();
                intent.putExtra(context.getString(R.string.ALARM_EXTRA_MESSAGE), mDataset.get(position).getAlarm_Id());
                context.startActivity(intent);
                Log.d("alarm_debug", "clicked");
            }
        });
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public TextView mtTextView;
        public ConstraintLayout Layout;

        public ViewHolder(View v) {
            super(v);
            Layout = (ConstraintLayout) itemView.findViewById(R.id.alarm_item_layout);
            mTextView = (TextView) itemView.findViewById(R.id.alarm_time);
            mtTextView = (TextView) itemView.findViewById(R.id.alarm_title);
        }
    }
}
