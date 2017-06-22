package com.baeldung.spring.drools.model;

public class Order {

    private String serviceName;
    private String serviceType;
    private Boolean nightFlag;
    private Long timeInMinutes;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Boolean getNightFlag() {
        return nightFlag;
    }

    public void setNightFlag(Boolean nightFlag) {
        this.nightFlag = nightFlag;
    }

    public Long getTimeInMinutes() {
        return timeInMinutes;
    }

    public void setTimeInMinutes(Long timeInMinutes) {
        this.timeInMinutes = timeInMinutes;
    }

}
