package com.project.xetnghiem.models;

import com.google.gson.annotations.SerializedName;

public class Appointment {
    @SerializedName("StartTime")
    private String startTime;
    @SerializedName("SampleType")
    private String sampleType;
    @SerializedName("FinishTime")
    private String finishTime;

    public Appointment() {
    }

    public Appointment(String startTime, String sampleType, String finishTime) {
        this.startTime = startTime;
        this.sampleType = sampleType;
        this.finishTime = finishTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }
}
