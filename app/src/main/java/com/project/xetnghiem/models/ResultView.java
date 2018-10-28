package com.project.xetnghiem.models;

public class ResultView {
    private boolean isHeader;
    private String name;
    private String value;
    private String lowNormalHigh;
    private String normalRange;
    private String unit;

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLowNormalHigh() {
        return lowNormalHigh;
    }

    public void setLowNormalHigh(String lowNormalHigh) {
        this.lowNormalHigh = lowNormalHigh;
    }

    public String getNormalRange() {
        return normalRange;
    }

    public void setNormalRange(String normalRange) {
        this.normalRange = normalRange;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
