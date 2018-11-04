package com.project.xetnghiem.models;

import com.google.gson.annotations.SerializedName;

public class Slot {
    @SerializedName("SlotId")
    private int slotId;
    @SerializedName("SampleGroupId")
    private int sampleGroupId;
    @SerializedName("StartTime")
    private String startTime;
    @SerializedName("FinishTime")
    private String finishTime;
    @SerializedName("Date")
    private String date;
    @SerializedName("Quantity")
    private int quantity;
    @SerializedName("RemainQuantity")
    private int remainQuantity;

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public int getSampleGroupId() {
        return sampleGroupId;
    }

    public void setSampleGroupId(int sampleGroupId) {
        this.sampleGroupId = sampleGroupId;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getRemainQuantity() {
        return remainQuantity;
    }

    public void setRemainQuantity(int remainQuantity) {
        this.remainQuantity = remainQuantity;
    }
}
