package com.example.tetfit;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

class ExerciseAdapter implements ListAdapter{
    ArrayList<Exercise> arrayList;
    Context context;
    Exercise e;
    public ExerciseAdapter(Context context, ArrayList<Exercise> arrayList) {
        this.arrayList=arrayList;
        this.context=context;
    }
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }
    @Override
    public boolean isEnabled(int position) {
        return true;
    }
    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
    }
    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getViewTypeCount() {
        return arrayList.size();
    }
    @Override
    public boolean isEmpty() {
        return false;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        e = arrayList.get(position);
        if(convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.list_exercise, null);
            final View current = convertView;
            final TextView t = convertView.findViewById(R.id.duration);
            Button b1 = convertView.findViewById(R.id.increment);
            Button b2 = convertView.findViewById(R.id.decrement);
            final ImageView bodyImage = convertView.findViewById(R.id.body_image);
            String body_part =  e.getBody_part();
            if(body_part.equals("Chest")){
                bodyImage.setBackgroundResource(R.drawable.chest);
            }
            else if(body_part.equals("Back")){
                bodyImage.setBackgroundResource(R.drawable.back);
            }
            else if(body_part.equals("Hamstrings")){
                bodyImage.setBackgroundResource(R.drawable.thigh);
            }
            else if(body_part.equals("Triceps")){
                bodyImage.setBackgroundResource(R.drawable.arm);
            }
            else if(body_part.equals("Calves")){
                bodyImage.setBackgroundResource(R.drawable.calf);
            }
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    current.setBackgroundColor(Color.rgb(47,47,47));
                    int x = Integer.parseInt(String.valueOf(t.getText()));
                    x += 1;
                    e.increaseDuration();
                    t.setText(Integer.toString(x));
                }
            });
            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int x = Integer.parseInt(String.valueOf(t.getText()));
                    x -= 1;
                    if(x > 0) {
                        t.setText(Integer.toString(x));
                        e.decreaseDuration();
                    }
                    else{
                        current.setBackgroundColor(Color.RED);
                        t.setText("0");
                        if(e.getDuration() != 0)
                        e.decreaseDuration();
                    }
                }
            });
            Button des = convertView.findViewById(R.id.description);
            des.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast toast = Toast.makeText(context, e.get_description(), Toast.LENGTH_LONG);
                    toast.show();
                }
            });
            ImageView yt = convertView.findViewById(R.id.yt_link);
            yt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(e.get_Yt()));
                    try{
                        context.startActivity(webIntent);
                    }
                    catch (ActivityNotFoundException ex){}
                }
            });
            TextView r = convertView.findViewById(R.id.rating);
            r.setText(Double.toString(e.get_rating()));
            TextView title = convertView.findViewById(R.id.header);
            title.setText(e.getTitle());
            TextView d = convertView.findViewById(R.id.duration);
            d.setText(Integer.toString(e.getDuration()));
            final ImageView rate = convertView.findViewById(R.id.rate);
            rate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Rate Exercise");
                    final EditText input = new EditText(context);
                    input.setInputType(InputType.TYPE_CLASS_NUMBER);
                    builder.setView(input);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String rating = input.getText().toString();
                            int rate_value = Integer.parseInt(rating);

                            Integer[] queryInput = {rate_value};

                            new updateRating().execute(queryInput);
                        }
                    });
                    builder.show();
                }
            });
        }
        return convertView;
    }

    private class updateRating extends AsyncTask<Integer,Void,String> {

        String updateEndpoint = "http://18.188.175.235/exercises/updateRating";

        @Override
        protected String doInBackground(Integer... integers) {
            int newRating = integers[0];
            String jsonRequestString = String.format("{\"name\":\"%s\",\"newRating\":\"%d\"}",e.getTitle(),newRating);

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
            }
        }
    }
}