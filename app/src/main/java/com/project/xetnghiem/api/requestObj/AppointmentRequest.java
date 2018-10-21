package com.project.xetnghiem.api.requestObj;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AppointmentRequest implements Serializable {

    @SerializedName("PatientId")
    private int patientId;
    @SerializedName("SampleGettingDtos")
    private List<SampleGettingDtos> list;

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public List<SampleGettingDtos> getList() {
        return list;
    }

    public void setList(List<SampleGettingDtos> list) {
        this.list = list;
    }

    public static class SampleGettingDtos {
        @SerializedName("SampleId")
        private int sampleId;
        @SerializedName("LabTestIds")
        private List<Integer> labTestIds;
        @SerializedName("StartTime")
        private String startTime;
        @SerializedName("FinishTime")
        private String finishTime;

        public int getSampleId() {
            return sampleId;
        }

        public void setSampleId(int sampleId) {
            this.sampleId = sampleId;
        }

        public List<Integer> getLabTestIds() {
            return labTestIds;
        }

        public void setLabTestIds(List<Integer> labTestIds) {
            this.labTestIds = labTestIds;
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
    }
}
