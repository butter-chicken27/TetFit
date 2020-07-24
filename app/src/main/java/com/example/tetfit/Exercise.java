package com.example.tetfit;

public class Exercise {
    private String title;
    private int image_ID = NO_IMAGE_PROVIDED;
    private int duration;
    public static final int NO_IMAGE_PROVIDED = -1;

    public Exercise(String t, int i, int d){
        title = t;
        image_ID = i;
        duration = d;
    }
    public  Exercise(String t, int d){
        title = t;
        duration = d;
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
        duration += 5;
    }

    public void decreaseDuration(){
        if(duration != 0)
        duration -= 5;
    }
}
