package com.example.tetfit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

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
        Toast t = Toast.makeText(this, first + " " + second + " " + third, Toast.LENGTH_LONG);
        t.show();
        double count = 0;
        if(!(first.equals("")))
            count++;
        if(!(second.equals("")))
            count++;
        if(!(third.equals("")))
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
        TextView views[] = new TextView[6];
        views[0] = findViewById(R.id.Chest);
        views[1] = findViewById(R.id.legs);
        views[2] = findViewById(R.id.Glutes);
        views[3] = findViewById(R.id.arm_1);
        views[4] = findViewById(R.id.arm_2);
        views[5] = findViewById(R.id.abs);
        double red_cut = 2 * count;
        double orange_cut = count;
        for(int i = 0; i < 3; i++){
            if(total[i] > red_cut)
                views[i].setBackgroundColor(ContextCompat.getColor(this, R.color.body_red));
            else if(total[i] > orange_cut)
                views[i].setBackgroundColor(ContextCompat.getColor(this, R.color.body_orange));
            else
                views[i].setBackgroundColor(ContextCompat.getColor(this, R.color.body_green));
        }
        if(total[3] > red_cut) {
            views[3].setBackgroundColor(ContextCompat.getColor(this, R.color.body_red));
            views[4].setBackgroundColor(ContextCompat.getColor(this, R.color.body_red));
        }
        else if(total[3] > orange_cut) {
            views[3].setBackgroundColor(ContextCompat.getColor(this, R.color.body_orange));
            views[4].setBackgroundColor(ContextCompat.getColor(this, R.color.body_orange));
        }
        else {
            views[3].setBackgroundColor(ContextCompat.getColor(this, R.color.body_green));
            views[4].setBackgroundColor(ContextCompat.getColor(this, R.color.body_green));
        }
        if(total[4] > red_cut)
            views[5].setBackgroundColor(ContextCompat.getColor(this, R.color.body_red));
        else if(total[4] > red_cut)
            views[5].setBackgroundColor(ContextCompat.getColor(this, R.color.body_orange));
        else
            views[5].setBackgroundColor(ContextCompat.getColor(this, R.color.body_green));
    }
}
