package com.baeldung.paimon;

public class Metric {
    private String deviceId;
    private String metricsName;
    private double metricsValue;
    private String source;
    private String createTime;

    public Metric() {
    }

    public Metric(String deviceId, String metricsName, double metricsValue, String source, String createTime) {
        this.deviceId = deviceId;
        this.metricsName = metricsName;
        this.metricsValue = metricsValue;
        this.source = source;
        this.createTime = createTime;
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
}