package com.example.tetfit;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.net.Uri;
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
                    int x = Integer.parseInt(String.valueOf(t.getText()));
                    x += 1;
                    e.increaseDuration();
                    t.setText(Integer.toString(x));
                }
            });
            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int x = Integer.parseInt(String.valueOf(t.getText()));
                    x -= 1;
                    if(x > 0) {
                        t.setText(Integer.toString(x));
                        e.decreaseDuration();
                    }
                    else{
                        current.setBackgroundColor(Color.RED);
                        t.setText("0");
                        if(e.getDuration() != 0)
                        e.decreaseDuration();
                    }
                }
            });
            Button des = convertView.findViewById(R.id.description);
            des.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast toast = Toast.makeText(context, e.get_description(), Toast.LENGTH_LONG);
                    toast.show();
                }
            });
            ImageView yt = convertView.findViewById(R.id.yt_link);
            yt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(e.get_Yt()));
                    try{
                        context.startActivity(webIntent);
                    }
                    catch (ActivityNotFoundException ex){}
                }
            });
            TextView r = convertView.findViewById(R.id.rating);
            r.setText(Double.toString(e.get_rating()));
            TextView title = convertView.findViewById(R.id.header);
            title.setText(e.getTitle());
            TextView d = convertView.findViewById(R.id.duration);
            d.setText(Integer.toString(e.getDuration()));
        }
        return convertView;
    }
}