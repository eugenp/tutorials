package com.baeldung.http.server;

import java.util.Map;

public class ResponseAggregator {
    int status;
    String version;
    Map<String, String> headers;
    boolean isChunked;
    String content;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public boolean isChunked() {
        return isChunked;
    }

    public void setChunked(boolean isChunked) {
        this.isChunked = isChunked;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
