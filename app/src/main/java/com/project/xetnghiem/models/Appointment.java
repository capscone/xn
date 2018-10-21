package com.project.xetnghiem.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Appointment {
    @SerializedName("AppointmentCode")
    private String id;
    @SerializedName("SampleGettingDtos")
    private List<AppointmentDetail> listApptDetail;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<AppointmentDetail> getListApptDetail() {
        return listApptDetail;
    }

    public void setListApptDetail(List<AppointmentDetail> listApptDetail) {
        this.listApptDetail = listApptDetail;
    }
}
