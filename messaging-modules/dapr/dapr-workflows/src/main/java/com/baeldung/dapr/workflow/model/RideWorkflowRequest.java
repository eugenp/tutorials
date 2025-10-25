package com.baeldung.dapr.workflow.model;

import com.baeldung.dapr.pubsub.model.RideRequest;

public class RideWorkflowRequest {
    private String rideId;
    private RideRequest rideRequest;
    private String driverId;
    private String workflowInstanceId;

    public RideWorkflowRequest() {
    }

    public RideWorkflowRequest(String rideId, RideRequest rideRequest, String driverId, String workflowInstanceId) {
        this.rideId = rideId;
        this.rideRequest = rideRequest;
        this.driverId = driverId;
        this.workflowInstanceId = workflowInstanceId;
    }

    public String getRideId() {
        return rideId;
    }

    public void setRideId(String rideId) {
        this.rideId = rideId;
    }

    public RideRequest getRideRequest() {
        return rideRequest;
    }

    public void setRideRequest(RideRequest rideRequest) {
        this.rideRequest = rideRequest;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getWorkflowInstanceId() {
        return workflowInstanceId;
    }

    public void setWorkflowInstanceId(String workflowInstanceId) {
        this.workflowInstanceId = workflowInstanceId;
    }

    @Override
    public String toString() {
        return "RideWorkflowRequest{" +
               "rideId='" + rideId + '\'' +
               ", rideRequest=" + rideRequest +
               ", driverId='" + driverId + '\'' +
               ", workflowInstanceId='" + workflowInstanceId + '\'' +
               '}';
    }
}
