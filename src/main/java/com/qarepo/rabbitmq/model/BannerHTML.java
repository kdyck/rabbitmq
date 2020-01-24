package com.qarepo.rabbitmq.model;

import java.io.Serializable;

public class BannerHTML implements Serializable {
    private String jobNumber;
    private String jobName;
    private String previewURL;

    public BannerHTML() {
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getPreviewURL() {
        return previewURL;
    }

    public void setPreviewURL(String previewURL) {
        this.previewURL = previewURL;
    }

    public String toJson() {
        return "{\n" +
                "\"jobNumber\":" + "\"" + jobNumber + "\"," +
                "\"jobName\":" + "\"" + jobName + "\"," +
                "\"previewURL\":" + "\"" + previewURL + "\"" +
                "}";
    }

    @Override
    public String toString() {
        return "Banner{" +
                "jobNumber='" + jobNumber + '\'' +
                ", jobName='" + jobName + '\'' +
                ", previewURL='" + previewURL + '\'' +
                '}';
    }
}
