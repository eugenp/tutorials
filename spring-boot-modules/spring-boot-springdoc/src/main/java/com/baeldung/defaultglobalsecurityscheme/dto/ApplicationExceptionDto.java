package com.baeldung.defaultglobalsecurityscheme.dto;

public class ApplicationExceptionDto {
    private long errorCode;
    private String description;

    public ApplicationExceptionDto() {
        super();
    }

    public long getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(long errorCode) {
        this.errorCode = errorCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
