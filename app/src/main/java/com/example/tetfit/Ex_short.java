package com.example.tetfit;

public class Ex_short {
    private String title;
    private int duration;
    public  Ex_short(String t, int d){
        title = t;
        duration = d;
    }
    public String get_title(){
        return title;
    }
    public int get_duration(){
        return duration;
    }
    public void increase(){
        duration += 5;
    }
    public void decrease(){
        if(duration > 0)
            duration -=5;
    }
}
