package com.project.xetnghiem.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Appointment {
    @SerializedName("AppointmentCode")
    private int id;
    @SerializedName("SampleGettingDtos")
    private List<AppointmentDetail> listApptDetail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<AppointmentDetail> getListApptDetail() {
        return listApptDetail;
    }

    public void setListApptDetail(List<AppointmentDetail> listApptDetail) {
        this.listApptDetail = listApptDetail;
    }
}
