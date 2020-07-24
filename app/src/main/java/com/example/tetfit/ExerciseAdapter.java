package com.example.tetfit;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

class ExerciseAdapter implements ListAdapter{
    ArrayList<Exercise> arrayList;
    Context context;
    public ExerciseAdapter(Context context, ArrayList<Exercise> arrayList) {
        this.arrayList=arrayList;
        this.context=context;
    }
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }
    @Override
    public boolean isEnabled(int position) {
        return true;
    }
    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
    }
    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getViewTypeCount() {
        return arrayList.size();
    }
    @Override
    public boolean isEmpty() {
        return false;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        final Exercise e = arrayList.get(position);
        if(convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.list_exercise, null);
            final View current = convertView;
            final TextView t = convertView.findViewById(R.id.duration);
            Button b1 = convertView.findViewById(R.id.increment);
            Button b2 = convertView.findViewById(R.id.decrement);
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    current.setBackgroundColor(Color.rgb(47,47,47));
                    TextView stats = current.findViewById(R.id.status);
                    stats.setVisibility(View.INVISIBLE);
                    int x = Integer.parseInt(String.valueOf(t.getText()));
                    x += 5;
                    e.increaseDuration();
                    t.setText(Integer.toString(x));
                }
            });
            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int x = Integer.parseInt(String.valueOf(t.getText()));
                    x -= 5;
                    if(x > 0) {
                        t.setText(Integer.toString(x));
                        e.decreaseDuration();
                    }
                    else{
                        current.setBackgroundColor(Color.RED);
                        TextView stats = current.findViewById(R.id.status);
                        stats.setVisibility(View.VISIBLE);
                        t.setText("0");
                        if(e.getDuration() != 0)
                        e.decreaseDuration();
                    }
                }
            });
            TextView title = convertView.findViewById(R.id.header);
            title.setText(e.getTitle());
            TextView d = convertView.findViewById(R.id.duration);
            d.setText(Integer.toString(e.getDuration()));
        }
        return convertView;
    }
}