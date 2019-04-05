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
import android.widget.TextView;

import com.google.firebase.quickstart.database.models.Alarm;
import com.google.firebase.quickstart.database.models.Entry;

import java.util.List;

public class EntryAdapter extends  RecyclerView.Adapter<EntryAdapter.ViewHolder> {
    private Context context;
    private List<Entry> mDataset;
    private String[] entry_categories;

    public EntryAdapter(List<Entry> myDataset, Context c) {
        mDataset = myDataset;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        entry_categories = context.getResources().getStringArray(R.array.category);
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_entry, parent, false);
        EntryAdapter.ViewHolder viewHolder = new EntryAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,final int position) {
        viewHolder.header_date.setText(mDataset.get(position).getTime());
        viewHolder.header_creneau.setText(entry_categories[mDataset.get(position).getCreneau()]);
        viewHolder.glycemie.setText(mDataset.get(position).getTauxGlycemie()+"");
        viewHolder.pression.setText(mDataset.get(position).getPressionArterielle()+"");
        viewHolder.acetone.setText(mDataset.get(position).getAcetone()+"");

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EntryActivity.class);
                long i = mDataset.get(position).getBaseObjId();
                intent.putExtra(context.getString(R.string.ENTRY_EXTRA_MESSAGE), (int) i);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView header_date;
        public TextView header_creneau;
        public TextView glycemie;
        public TextView pression;
        public TextView acetone;
        //public ConstraintLayout Layout;

        public ViewHolder(View v) {
            super(v);
          //  Layout = (ConstraintLayout) itemView.findViewById(R.id.alarm_item_layout);
            header_date = itemView.findViewById(R.id.entry_item_date);
            header_creneau = itemView.findViewById(R.id.entry_item_creneau);
            glycemie= itemView.findViewById(R.id.entry_item_glycemie);
            pression = itemView.findViewById(R.id.entry_item_pression);
            acetone = itemView.findViewById(R.id.entry_item_acetone);
        }
    }
}
