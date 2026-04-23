package com.baeldung.dapr.workflow.model;

public record RideWorkflowStatus(String rideId, String status, String message) {
}
