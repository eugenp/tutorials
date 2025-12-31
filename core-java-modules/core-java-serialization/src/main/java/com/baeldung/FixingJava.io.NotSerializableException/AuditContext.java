package com.example;

public class AuditContext {

    private String traceId;

    public AuditContext(String traceId) {
        this.traceId = traceId;
    }

    public String getTraceId() {
        return traceId;
    }
}
