package com.baeldung.paimon;

public class Metric {
    private String deviceId;
    private String metricsName;
    private double metricsValue;
    private String source;
    private String state = "active";
    private String createTime;
    private String createdBy;

    public Metric() {
    }

    public Metric(String deviceId, String metricsName, double metricsValue, String source, String createTime, String createdBy, String state) {
        this.deviceId = deviceId;
        this.metricsName = metricsName;
        this.metricsValue = metricsValue;
        this.source = source;
        this.createTime = createTime;
        this.createdBy = createdBy;
        this.state = state;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }


    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getMetricsName() {
        return metricsName;
    }

    public void setMetricsName(String metricsName) {
        this.metricsName = metricsName;
    }

    public double getMetricsValue() {
        return metricsValue;
    }

    public void setMetricsValue(double metricsValue) {
        this.metricsValue = metricsValue;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Metric [deviceId=" + deviceId + ", metricsName=" + metricsName + ", metricsValue="
                + metricsValue + ", source=" + source + ", createTime=" + createTime + ", state=" + state + "]";
    }
}