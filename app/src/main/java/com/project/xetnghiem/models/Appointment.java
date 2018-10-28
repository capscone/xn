package com.project.xetnghiem.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Appointment {
    @SerializedName("AppointmentCode")
    private String appointmentCode;
    @SerializedName("SampleGettingDtos")
    private List<AppointmentDetail> listApptDetail;


    public List<AppointmentDetail> getListApptDetail() {
        return listApptDetail;
    }

    public void setListApptDetail(List<AppointmentDetail> listApptDetail) {
        this.listApptDetail = listApptDetail;
    }

    public String getAppointmentCode() {
        return appointmentCode;
    }

    public void setAppointmentCode(String appointmentCode) {
        this.appointmentCode = appointmentCode;
    }
}
