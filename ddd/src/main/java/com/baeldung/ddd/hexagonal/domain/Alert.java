package com.baeldung.ddd.hexagonal.domain;

public class Alert {
    private AlertId id;
    private String summary;
    private String description;
    private Severity severity;
    private Status status;

    private Alert(String summary, String description, Severity severity) {
        this.id = new AlertId(summary);
        this.summary = summary;
        this.description = description;
        this.severity = severity;
        this.status = Status.OPEN;
    }

    public static Alert createAlert(String summary, String description, Severity severity) {
        // Logic to de-duplicate alerts can go here later.
        return new Alert(summary, description, severity);
    }

    public void resolve() {
        this.status = Status.CLOSED;
    }

    public AlertId getId() {
        return id;
    }

    public String getSummary() {
        return summary;
    }

    public String getDescription() {
        return description;
    }

    public Severity getSeverity() {
        return severity;
    }

    public Status getStatus() {
        return status;
    }

}
