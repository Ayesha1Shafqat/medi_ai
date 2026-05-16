package com.example.medi_ai;

public class DailyHealthModel {

    private String bp;

    private String water;

    private String symptoms;

    private String date;

    // EMPTY CONSTRUCTOR

    public DailyHealthModel() {
    }

    // CONSTRUCTOR

    public DailyHealthModel(String bp,
                            String water,
                            String symptoms,
                            String date) {

        this.bp = bp;

        this.water = water;

        this.symptoms = symptoms;

        this.date = date;
    }

    // GETTERS & SETTERS

    public String getBp() {
        return bp;
    }

    public void setBp(String bp) {
        this.bp = bp;
    }

    public String getWater() {
        return water;
    }

    public void setWater(String water) {
        this.water = water;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
