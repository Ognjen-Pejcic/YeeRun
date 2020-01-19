package com.example.yeerun;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class Rout implements Serializable {
    private String name;
    private double length;
    private String time;
    private LatLng start;
    private LatLng end;

    public LatLng getStart() {
        return start;
    }

    public void setStart(LatLng start) {
        this.start = start;
    }

    public LatLng getEnd() {
        return end;
    }

    public void setEnd(LatLng end) {
        this.end = end;
    }

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

    public Rout(String name, double length, String time, LatLng start, LatLng end) {
        this.name = name;
        this.length = length;
        this.time = time;
        this.start = start;
        this.end = end;
    }

}
