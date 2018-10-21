package com.project.xetnghiem.models;

import com.google.gson.annotations.SerializedName;

public class AppointmentDetail {
    @SerializedName("SampleId")
    private int sampleId;
    @SerializedName("LabTestIds")
    private int labTestIds;
    @SerializedName("SampleName")
    private String sampleName;
    @SerializedName("StartTime")
    private String startTime;
    @SerializedName("GettingDate")
    private String gettingDate;
    @SerializedName("FinishTime")
    private String finishTime;
    private boolean isHeader = false;
    public AppointmentDetail(){}
    public AppointmentDetail(boolean isHeader, String startTime) {
        this.setHeader(isHeader);
        this.startTime = startTime;
    }


    public int getSampleId() {
        return sampleId;
    }

    public void setSampleId(int sampleId) {
        this.sampleId = sampleId;
    }

    public int getLabTestIds() {
        return labTestIds;
    }

    public void setLabTestIds(int labTestIds) {
        this.labTestIds = labTestIds;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    public String getGettingDate() {
        return gettingDate;
    }

    public void setGettingDate(String gettingDate) {
        this.gettingDate = gettingDate;
    }
}
