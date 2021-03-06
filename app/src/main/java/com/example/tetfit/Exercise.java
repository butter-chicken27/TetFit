package com.example.tetfit;

public class Exercise {
    private String title, body_part, description, Yt_URL;
    private double rating;
    private int intensity;
    private int image_ID = NO_IMAGE_PROVIDED;
    private int duration = 0;
    public static final int NO_IMAGE_PROVIDED = -1;

    public Exercise(String t, String b, String d, String y, double r, int i){
        title = t;
        body_part = b;
        description = d;
        Yt_URL = y;
        rating = r;
        intensity = i;
    }
    public Boolean hasImage(){
        return  image_ID != NO_IMAGE_PROVIDED;
    }

    public int getDuration() {
        return duration;
    }

    public int getImage_ID() {
        return image_ID;
    }

    public String getTitle() {
        return title;
    }

    public void increaseDuration() {
        duration += 1;
    }

    public void decreaseDuration(){
        if(duration != 0)
        duration -= 1;
    }
    public String get_description(){
        return description;
    }
    public String get_Yt(){
        return Yt_URL;
    }
    public double get_rating(){
        return rating;
    }
    public String getBody_part(){
        return body_part;
    }
    public int getIntensity(){
        return intensity;
    }
}
