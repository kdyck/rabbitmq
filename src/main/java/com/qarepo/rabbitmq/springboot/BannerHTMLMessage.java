package com.qarepo.rabbitmq.springboot;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class BannerHTMLMessage implements Serializable {
    private String jobNumber;
    private String jobName;
    private String previewUrl;

    public BannerHTMLMessage() {
        this.jobNumber = "AALB2986152";
        this.jobName = "FAKEJOB 1361570770 HTML5 Banners";
        this.previewUrl = "http://zpreview.ztrac.com/clients/583f191395dbd78ca1a2dae63a87acdc";
    }

    public BannerHTMLMessage(
            @JsonProperty String jobNumber,
            @JsonProperty String jobName,
            @JsonProperty String previewUrl) {
        this.jobNumber = jobNumber;
        this.jobName = jobName;
        this.previewUrl = previewUrl;
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

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    @Override
    public String toString() {
        return "QueueMessage{" +
                "jobNumber='" + jobNumber + '\'' +
                ", jobName='" + jobName + '\'' +
                ", previewUrl='" + previewUrl + '\'' +
                '}';
    }
}
