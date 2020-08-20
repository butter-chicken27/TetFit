package com.example.tetfit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class exAdapter extends ArrayAdapter<Ex_short> {
    public exAdapter(Context context, ArrayList<Ex_short> exercises){
        super(context, 0 , exercises);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Ex_short ex = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.exec_customise, parent, false);
        }
        TextView title = convertView.findViewById(R.id.title_exercise);
        title.setText(ex.get_title());
        final TextView duration = convertView.findViewById(R.id.duration);
        duration.setText(Integer.toString(ex.get_duration()));
        Button b1 = convertView.findViewById(R.id.increment);
        Button b2 = convertView.findViewById(R.id.decrement);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ex.increase();
                duration.setText(Integer.toString(ex.get_duration()));
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ex.decrease();
                duration.setText(Integer.toString(ex.get_duration()));
            }
        });
        ImageView rate = convertView.findViewById(R.id.rating_launcher);
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Rate Exercise");
                final EditText input = new EditText(getContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setHint("Enter rating here");
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String rating = input.getText().toString();
                        int rate_value = Integer.parseInt(rating);
                        String title = ex.get_title();
                    }
                });
                builder.show();
            }
        });
        return convertView;
    }
}
