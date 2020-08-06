package com.example.tetfit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import  java.util.concurrent.TimeUnit;

public class ExerciseTimer extends AppCompatActivity {
    public SharedPreferences mSharedPref;
    public static final String sharedPrefFile = "com.example.android.TetFit.data_store";
    public static final String count_key = "com.example.android.TetFit.count";
    public static final String first_key = "com.example.android.TetFit.first";
    public static final String second_key = "com.example.android.TetFit.second";
    public static final String third_key = "com.example.android.TetFit.third";
    public String first, second, third;
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
        mSharedPref = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        first = mSharedPref.getString(first_key, "");
        second = mSharedPref.getString(second_key, "");
        third = mSharedPref.getString(third_key, "");
        third = second;
        second = first;
        first = intent.getStringExtra(customizeWorkout.sum_key);
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putString(first_key, first);
        editor.putString(second_key, second);
        editor.putString(third_key, third);
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
