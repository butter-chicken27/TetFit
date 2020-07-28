package com.example.tetfit;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    public static  final String titles_key = "com.example.android.tetfit.titles";
    public static  final String durations_key = "com.example.android.tetfit.durations";
    public static  final String length_key = "com.example.android.tetfit.length";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ArrayList<Exercise> workout = new ArrayList<Exercise>();
        final ExerciseAdapter adapter = new ExerciseAdapter(this, workout);
        final ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
        final Button b = findViewById(R.id.begin_workout);
        b.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                int size = workout.size();
                int number = 0;
                for(int i = 0; i < size; i++){
                    if(workout.get(i).getDuration() > 0)
                        number++;
                }
                String exercise_title[] = new String[number];
                int durations[] = new int[number];
                int index = 0;
                for(int i = 0; i < size; i++){
                    Exercise e = workout.get(i);
                    if(e.getDuration() > 0){
                        durations[index] = e.getDuration();
                        exercise_title[index] = e.getTitle();
                        index++;
                    }
                }
                Intent intent = new Intent(MainActivity.this, ExerciseTimer.class);
                intent.putExtra(length_key, number);
                intent.putExtra(titles_key, exercise_title);
                intent.putExtra(durations_key, durations);
                startActivity(intent);
            }
        });
    }
}
