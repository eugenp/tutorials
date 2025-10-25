package com.baeldung.dapr.workflow.model;

public class RideWorkflowStatus {
    private String rideId;
    private String status;
    private String message;

    public RideWorkflowStatus() {
    }

    public RideWorkflowStatus(String rideId, String status, String message) {
        this.rideId = rideId;
        this.status = status;
        this.message = message;
    }

    public String getRideId() {
        return rideId;
    }

    public void setRideId(String rideId) {
        this.rideId = rideId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RideWorkflowStatus{" +
               "rideId='" + rideId + '\'' +
               ", status='" + status + '\'' +
               ", message='" + message + '\'' +
               '}';
    }
}
