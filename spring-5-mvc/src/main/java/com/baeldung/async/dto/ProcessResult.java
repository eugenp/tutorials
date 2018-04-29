package com.baeldung.async.dto;

public class ProcessResult {
    
    private String status;
    private Integer value;
    
    public ProcessResult(String status, int i) {
        this.status = status;
        this.value = i;
    }
    
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Integer getValue() {
        return value;
    }
    public void setValue(Integer value) {
        this.value = value;
    }
    
}
