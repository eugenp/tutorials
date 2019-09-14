package com.baeldung.spring.rest.error.exception;

@SuppressWarnings("serial")
public abstract class ApiException extends RuntimeException {

    protected abstract String getErrorId();
    protected abstract String getErrorMessage();
    protected abstract String getDetail();
    
    protected String getHelpUrl() {
        return "https://example.com/help/error/" + getErrorId();
    }
    
    public String getJson() {
        return "{" +
            "\"error\":\"" + getErrorId() + "\"," +
            "\"message\":\"" + getErrorMessage() + "\"," +
            "\"detail\":\"" + getDetail() + "\"," +
            "\"help\":\"" + getHelpUrl() + "\"" +
            "}";
    }
}
