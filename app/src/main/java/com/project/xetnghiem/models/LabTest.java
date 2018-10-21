package com.project.xetnghiem.models;

import com.google.gson.annotations.SerializedName;

public class LabTest {
    @SerializedName("LabTestId")
    private int labTestId;
    private String sampleName;
    @SerializedName("LabTestName")
    private String labTestName;
    @SerializedName("Description")
    private String description;
    @SerializedName("Price")
    private int price;
    private boolean isChecked;
    private boolean isHeader = false;
private int sampleId;
    public LabTest(boolean isHeader, String sampleName) {
        this.isHeader = isHeader;
        this.sampleName = sampleName;
    }

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

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getSampleId() {
        return sampleId;
    }

    public void setSampleId(int sampleId) {
        this.sampleId = sampleId;
    }
}
