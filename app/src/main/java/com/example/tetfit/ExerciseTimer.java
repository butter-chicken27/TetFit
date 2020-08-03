package com.example.tetfit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import  java.util.concurrent.TimeUnit;

public class ExerciseTimer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_timer);
        TextView header = findViewById(R.id.exec_title);
        final TextView time = findViewById(R.id.timer);
        Intent intent = getIntent();
        int size = intent.getIntExtra(customizeWorkout.length_key, -2);
        int durations[];
        String titles[];
        durations = intent.getIntArrayExtra(customizeWorkout.times_key);
        titles = intent.getStringArrayExtra(customizeWorkout.title_key);
        play_exercise(durations, titles, size, header, time, 0);
    }

    void play_exercise(final int[] durations, final String[] titles, int size, final TextView title, final TextView time, int index){
        final int s = size;
        final int i = index;
        title.setText(titles[index]);
        final TextView timer = time;
        new CountDownTimer(durations[index] * 1000, 1000){
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(String.valueOf(millisUntilFinished / 1000 + 1));
            }
            @Override
            public void onFinish() {
                if(i != s - 1){
                    play_exercise(durations, titles, s, title, time, i + 1);
                }
                else{
                }
            }
        }.start();
    }
}
