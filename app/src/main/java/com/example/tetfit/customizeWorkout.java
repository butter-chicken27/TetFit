package com.example.tetfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class customizeWorkout extends AppCompatActivity {
    public static final String length_key = "com.example.android.tetfit.exec.length";
    public static final String title_key = "com.example.android.tetfit.exec.titles";
    public static final String times_key = "com.example.android.tetfit.exec.times";
    public static final String sum_key = "com.example.android.tetfit.exec.summary";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_workout);
        final ArrayList<Ex_short> exercises = new ArrayList<Ex_short>();
        Intent intent = getIntent();
        String[] titles;
        int[] counts;
        final String [] body_parts;
        final int[] intensities;
        final int length;
        length = intent.getIntExtra(MainActivity.length_key, -2);
        counts = intent.getIntArrayExtra(MainActivity.counts_key);
        titles = intent.getStringArrayExtra(MainActivity.titles_key);
        body_parts = intent.getStringArrayExtra(MainActivity.body_key);
        intensities = intent.getIntArrayExtra(MainActivity.intensity_key);
        int total = 0;
        for(int i = 0; i < length; i++){
            total += counts[i];
        }
        while(total > 0){
            for(int i = 0; i < length; i++){
                if(counts[i] > 0){
                    total--;
                    counts[i]--;
                    Ex_short e = new Ex_short(titles[i], 30);
                    exercises.add(e);
                }
            }
        }
        exAdapter adapter = new exAdapter(this, exercises);
        ListView listView = findViewById(R.id.exec_list);
        listView.setAdapter(adapter);
        Button b1 = findViewById(R.id.launch);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int l = exercises.size();
                int count = 0;
                for(int i = 0; i < l; i++){
                    if(exercises.get(i).get_duration() > 0)
                        count++;
                }
                String titles[] = new String[count];
                int durations[] = new int[count];
                int index = 0;
                for(int i = 0; i < l; i++){
                    Ex_short e = exercises.get(i);
                    if(e.get_duration() > 0){
                        titles[index] = e.get_title();
                        durations[index] = e.get_duration();
                        index++;
                    }
                }
                /*Part Indices
                0) Chest/Back
                1) Hamstrings/Calves
                2) Glutes
                3) Triceps/Biceps
                4) Abs
                */
                double load[] = new double[5];
                for(int i =0 ;i < 5; i++)
                    load[i] = 0;
                for(int i = 0; i < length; i++){
                    String x = body_parts[i];
                    if(x.equals("Chest") || x.equals("Back")){
                        load[0] += durations[i] * intensities[i];
                    }
                    else if(x.equals("Hamstrings") || x.equals("Calves")){
                        load[1] += durations[i] * intensities[i];
                    }
                    else if(x.equals("Glutes")){
                        load[2] += durations[i] * intensities[i];
                    }
                    else if(x.equals("Triceps")){
                        load[3] += durations[i] * intensities[i];
                    }
                    else if(x.equals("Abs")){
                        load[4] += durations[i] * intensities[i];
                    }
                }
                double max = 0;
                for(int i = 0; i < 5; i++){
                    if(max < load[i])
                        max = load[i];
                }
                for(int i = 0; i < 5; i++){
                    load[i] /= max;
                }
                String summary = "";
                for(int i = 0; i < 5; i++){
                    if(load[i] >= 0.7)
                        summary += "R";
                    else if (load[i] >= 0.4)
                        summary += "O";
                    else
                        summary += "G";
                }
                Intent next = new Intent(customizeWorkout.this, ExerciseTimer.class);
                next.putExtra(length_key, count);
                next.putExtra(times_key, durations);
                next.putExtra(title_key, titles);
                next.putExtra(sum_key, summary);
                startActivity(next);
            }
        });
    }
}
