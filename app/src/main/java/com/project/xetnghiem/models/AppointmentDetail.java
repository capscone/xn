package com.project.xetnghiem.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AppointmentDetail {

    @SerializedName("SampleId")
    private int sampleId;
    @SerializedName("LabTestIds")
    private List<Integer> labTestIds;
    @SerializedName("SampleName")
    private String sampleName;
    @SerializedName("StartTime")
    private String startTime;
    @SerializedName("GettingDate")
    private String gettingDate;
    @SerializedName("FinishTime")
    private String finishTime;
    private String appointmentCode;
    private boolean isHeader = false;

    public AppointmentDetail(){}
    public AppointmentDetail(boolean isHeader, String appointmentCode) {
        this.setHeader(isHeader);
        this.appointmentCode = appointmentCode;
    }


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

    public String getAppointmentCode() {
        return appointmentCode;
    }

    public void setAppointmentCode(String appointmentCode) {
        this.appointmentCode = appointmentCode;
    }

    public List<Integer> getLabTestIds() {
        return labTestIds;
    }

    public void setLabTestIds(List<Integer> labTestIds) {
        this.labTestIds = labTestIds;
    }
}
