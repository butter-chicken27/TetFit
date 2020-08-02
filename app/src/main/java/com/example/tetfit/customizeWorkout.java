package com.example.tetfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class customizeWorkout extends AppCompatActivity {
    public static final String length_key = "com.example.android.tetfit.exec.length";
    public static final String title_key = "com.example.android.tetfit.exec.titles";
    public static final String times_key = "com.example.android.tetfit.exec.times";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_workout);
        final ArrayList<Ex_short> exercises = new ArrayList<Ex_short>();
        Intent intent = getIntent();
        String[] titles;
        int[] counts;
        int length;
        length = intent.getIntExtra(MainActivity.length_key, -2);
        counts = intent.getIntArrayExtra(MainActivity.counts_key);
        titles = intent.getStringArrayExtra(MainActivity.titles_key);
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
                Intent intent = new Intent(customizeWorkout.this, ExerciseTimer.class);
                intent.putExtra(length_key, count);
                intent.putExtra(times_key, durations);
                intent.putExtra(title_key, titles);
                startActivity(intent);
            }
        });
    }
}
