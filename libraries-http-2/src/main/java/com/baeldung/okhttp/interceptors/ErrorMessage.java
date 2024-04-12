package com.baeldung.okhttp.interceptors;

public class ErrorMessage {

    private final int status;
    private final String detail;

    public ErrorMessage(int status, String detail) {
        this.status = status;
        this.detail = detail;
    }

    public int getStatus() {
        return status;
    }

    public String getDetail() {
        return detail;
    }

}
