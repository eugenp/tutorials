package com.baeldung.derive4j.pattern;

public class HTTPResponse {
    private int  statusCode;
    private String responseBody;

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public HTTPResponse(int statusCode, String responseBody) {
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }
}
