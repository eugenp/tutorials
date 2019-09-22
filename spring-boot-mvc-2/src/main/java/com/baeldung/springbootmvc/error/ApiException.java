package com.baeldung.springbootmvc.error;

@SuppressWarnings("serial")
public abstract class ApiException extends RuntimeException {

    protected abstract String getErrorId();
    protected abstract String getErrorMessage();
    
    public String toJson() {
        return "{" +
            "\"error\":\"" + getErrorId() + "\"," +
            "\"message\":\"" + getErrorMessage() + "\"," +
            "}";
    }
}
