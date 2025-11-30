package com.baeldung.dapr.workflow.model;

public record NotificationInput(RideWorkflowRequest request, double fare) {
}
