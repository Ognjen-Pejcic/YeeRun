package com.example.yeerun;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class Rout implements Serializable {
    private String name;
    private double length;
    private String time;
    private double startX;
    private double startY;
    private double endX;
    private double endY;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public Rout(String name, double length, String time) {
        this.name = name;
        this.length = length;
        this.time = time;
    }

    public Rout(String name, double length, String time, double startX, double startY, double endX, double endY) {
        this.name = name;
        this.length = length;
        this.time = time;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

}
