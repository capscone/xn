package com.project.xetnghiem.models;

import com.google.gson.annotations.SerializedName;

public class LabTest {
    @SerializedName("LabTestId")
    private int labTestId;
    @SerializedName("LabTestName")
    private String labTestName;
    @SerializedName("Description")
    private String description;
    @SerializedName("Price")
    private int price;


    public int getLabTestId() {
        return labTestId;
    }

    public void setLabTestId(int labTestId) {
        this.labTestId = labTestId;
    }

    public String getLabTestName() {
        return labTestName;
    }

    public void setLabTestName(String labTestName) {
        this.labTestName = labTestName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
