package com.example.tetfit;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    public static  final String titles_key = "com.example.android.tetfit.titles";
    public static  final String durations_key = "com.example.android.tetfit.durations";
    public static  final String length_key = "com.example.android.tetfit.length";

    private ArrayList<Exercise> workout = new ArrayList<Exercise>();
    private ExerciseAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list);

        new getExercises(this).execute();

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

    private class getExercises extends AsyncTask<Void,Void, JSONArray> {
        private String apiEndpoint = "http://ec2-3-18-111-115.us-east-2.compute.amazonaws.com/exercises";
        private Context mContext;
        public getExercises(Context activityContext) {
            mContext = activityContext;
        }

        @Override
        protected JSONArray doInBackground(Void... voids) {
            try {
                URL obj = new URL(apiEndpoint);
                HttpURLConnection con = (HttpURLConnection)obj.openConnection();

                //Set the required headers
                con.setRequestMethod("GET");

                //Initiates the connection
                int responseCode = con.getResponseCode();

                if(responseCode==HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader((con.getInputStream())));
                    String inputLine;

                    //Read the response from the server
                    StringBuffer response = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    if(response.toString().equals("Error on Server")) {
                        Log.e("MAINACTIVITY", "Server Error");
                        return null;
                    }

                    JSONArray exerciseList = new JSONArray(response.toString());
                    return exerciseList;
                } else {
                    Log.e("MAINACTIVITY", "Server Connection Failed");
                    return null;
                }

            } catch(Exception e) {
                Log.e("MAINACTIVITY", "doInBackground: ",e );
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONArray exerciseList) {
            super.onPostExecute(exerciseList);
            if(exerciseList==null) {
                Toast.makeText(MainActivity.this, "Could not Load Exercise List from Server", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    JSONObject currentExercise;
                    for (int i = 0; i < exerciseList.length(); i++) {

                        currentExercise = exerciseList.getJSONObject(i);

                        Exercise newExercise = new Exercise(currentExercise.getString("name"),
                                currentExercise.getString("bodyPart"),
                                currentExercise.getString("description"),
                                currentExercise.getString("ytUrl"),
                                currentExercise.getDouble("rating"),
                                currentExercise.getInt("intensity"));

                        workout.add(newExercise);

                    }
                    adapter = new ExerciseAdapter(mContext,workout);
                    listView.setAdapter(adapter);

                } catch(JSONException e) {
                    Log.e("MAINACTIVITY", "Error in parsing JSON Objects from Array",e);
                }
            }

        }
    }
}
