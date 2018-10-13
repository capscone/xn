package com.project.xetnghiem.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SampleDto {
    @SerializedName("SampleId")
    private int sampleId;
    @SerializedName("SampleName")
    private String sampleName;
    @SerializedName("LabTests")
    private List<LabTest> labTests;
    @SerializedName("SampleDuration")
    private int sampleDuration;
    @SerializedName("OpenTime")
    private float openTime;
    @SerializedName("CloseTime")
    private float closeTime;


    public int getSampleId() {
        return sampleId;
    }

    public void setSampleId(int sampleId) {
        this.sampleId = sampleId;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public List<LabTest> getLabTests() {
        return labTests;
    }

    public void setLabTests(List<LabTest> labTests) {
        this.labTests = labTests;
    }

    public int getSampleDuration() {
        return sampleDuration;
    }

    public void setSampleDuration(int sampleDuration) {
        this.sampleDuration = sampleDuration;
    }

    public float getOpenTime() {
        return openTime;
    }

    public void setOpenTime(float openTime) {
        this.openTime = openTime;
    }

    public float getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(float closeTime) {
        this.closeTime = closeTime;
    }
}
