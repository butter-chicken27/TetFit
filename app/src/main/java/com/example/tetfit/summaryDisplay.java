package com.example.tetfit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class summaryDisplay extends AppCompatActivity {
    public SharedPreferences mSharedPref;
    public String first, second,third;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_display);
        mSharedPref = getSharedPreferences(ExerciseTimer.sharedPrefFile, MODE_PRIVATE);
        first = mSharedPref.getString(ExerciseTimer.first_key, "");
        second = mSharedPref.getString(ExerciseTimer.second_key, "");
        third = mSharedPref.getString(ExerciseTimer.third_key, "");
        double count = 0;
        if(first.equals(""))
            count++;
        if(second.equals(""))
            count++;
        if(third.equals(""))
            count++;
        double total[] = new double[5];
        if(first.length() == 5){
            for(int i = 0; i < 5; i++){
                char c = first.charAt(i);
                if(c == 'R')
                    total[i] += 3;
                else if(c == 'O')
                    total[i] += 2;
                else
                    total[i] += 1;
            }
        }
        if(second.length() == 5){
            for(int i = 0; i < 5; i++){
                char c = second.charAt(i);
                if(c == 'R')
                    total[i] += 3;
                else if(c == 'O')
                    total[i] += 2;
                else
                    total[i] += 1;
            }
        }
        if(third.length() == 5){
            for(int i = 0; i < 5; i++){
                char c = third.charAt(i);
                if(c == 'R')
                    total[i] += 3;
                else if(c == 'O')
                    total[i] += 2;
                else
                    total[i] += 1;
            }
        }
        double cut_off = count * 3;
        for(int i = 0; i < 5; i++)
            total[i] /= cut_off;
        TextView views[] = new TextView[6];
        views[0] = findViewById(R.id.Chest);
        views[1] = findViewById(R.id.legs);
        views[2] = findViewById(R.id.Glutes);
        views[3] = findViewById(R.id.arm_1);
        views[4] = findViewById(R.id.arm_2);
        views[5] = findViewById(R.id.abs);
        for(int i = 0; i < 5; i++){
            if(i < 3){
                if(total[i] >= 2 / 3)
                    views[i].setBackgroundColor(ContextCompat.getColor(this, R.color.body_red));
                else if(total[i] > 1 / 3)
                    views[i].setBackgroundColor(ContextCompat.getColor(this, R.color.body_orange));
                else
                    views[i].setBackgroundColor(ContextCompat.getColor(this, R.color.body_green));
            }
            else if(i == 3){
                if(total[i] >= 2 / 3) {
                    views[i].setBackgroundColor(ContextCompat.getColor(this, R.color.body_red));
                    views[i + 1].setBackgroundColor(ContextCompat.getColor(this, R.color.body_red));
                }
                else if(total[i] > 1 / 3) {
                    views[i].setBackgroundColor(ContextCompat.getColor(this, R.color.body_orange));
                    views[i + 1].setBackgroundColor(ContextCompat.getColor(this, R.color.body_orange));
                }
                else {
                    views[i].setBackgroundColor(ContextCompat.getColor(this, R.color.body_green));
                    views[i + 1].setBackgroundColor(ContextCompat.getColor(this, R.color.body_green));
                }
            }
            else{
                if(total[i] >= 2 / 3) {
                    views[i + 1].setBackgroundColor(ContextCompat.getColor(this, R.color.body_red));
                }
                else if(total[i] > 1 / 3) {
                    views[i + 1].setBackgroundColor(ContextCompat.getColor(this, R.color.body_orange));
                }
                else {
                    views[i + 1].setBackgroundColor(ContextCompat.getColor(this, R.color.body_green));
                }
            }
        }
    }
}
