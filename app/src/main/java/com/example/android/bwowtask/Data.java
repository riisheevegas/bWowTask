package com.example.android.bwowtask;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("heart-rate")
    @Expose
    private String heartRate;
    @SerializedName("sleep-time")
    @Expose
    private String sleepTime;
    @SerializedName("training-time")
    @Expose
    private String trainingTime;

    /**
     * No args constructor for use in serialization
     *
     */
    public Data() {
    }

    /**
     *
     * @param heartRate
     * @param trainingTime
     * @param sleepTime
     */
    public Data(String heartRate, String sleepTime, String trainingTime) {
        super();
        this.heartRate = heartRate;
        this.sleepTime = sleepTime;
        this.trainingTime = trainingTime;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    public String getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(String sleepTime) {
        this.sleepTime = sleepTime;
    }

    public String getTrainingTime() {
        return trainingTime;
    }

    public void setTrainingTime(String trainingTime) {
        this.trainingTime = trainingTime;
    }

}