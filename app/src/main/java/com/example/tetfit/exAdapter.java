package com.example.tetfit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.text.InputType;
import android.util.Log;
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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class exAdapter extends ArrayAdapter<Ex_short> {
    public exAdapter(Context context, ArrayList<Ex_short> exercises){
        super(context, 0 , exercises);
    }

    public Ex_short ex;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ex = getItem(position);
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

                        Integer[] params = {rate_value};

                        new updateRating().execute(params);

                    }
                });
                builder.show();
            }
        });
        return convertView;
    }

    private class updateRating extends AsyncTask<Integer,Void,String> {

        String updateEndpoint = "http://18.188.175.235/exercises/updateRating";
        @Override
        protected String doInBackground(Integer... integers) {
            int newRating = integers[0];
            Log.e("UPDATERATING", "doInBackground: "+ex.get_title() );
            String jsonRequestString = String.format("{\"name\":\"%s\",\"newRating\":%d}",ex.get_title(),newRating);

            try {
                URL obj = new URL(updateEndpoint);

                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type","application/json");

                con.setDoOutput(true);
                OutputStream os = con.getOutputStream();
                byte[] input = jsonRequestString.getBytes("UTF-8");
                os.write(input,0,input.length);
                os.flush();
                os.close();

                int responseCode = con.getResponseCode();

                if(responseCode==HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader((con.getInputStream())));
                    String inputLine;

                    StringBuffer response = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    return response.toString();
                } else {
                    return null;
                }
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s==null) {
                Log.e("UPDATERATING", "onPostExecute: Error in Updating Rating");
            } else {
                Log.e("UPDATERATING", "onPostExecute: "+s );
            }
        }
    }

}
