package com.baeldung.dapr.workflow.model;

public class NotificationInput {
    private RideWorkflowRequest request;
    private double fare;

    public NotificationInput() {
    }

    public NotificationInput(RideWorkflowRequest request, double fare) {
        this.request = request;
        this.fare = fare;
    }

    public RideWorkflowRequest getRequest() {
        return request;
    }

    public void setRequest(RideWorkflowRequest request) {
        this.request = request;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    @Override
    public String toString() {
        return "NotificationInput{" +
               "request=" + request +
               ", fare=" + fare +
               '}';
    }
}
