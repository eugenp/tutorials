package com.baeldung.bulkandbatchapi.request;

import jakarta.validation.constraints.NotNull;

public class BatchRequest {
    @NotNull
    private String method;
    @NotNull
    private String relativeUrl;
    @NotNull
    private Object data;

    public Object getData() {
        return data;
    }

    public @NotNull String getRelativeUrl() {
        return relativeUrl;
    }

    public void setRelativeUrl(@NotNull String relativeUrl) {
        this.relativeUrl = relativeUrl;
    }

    public void setData(@NotNull Object data) {
        this.data = data;
    }

    public @NotNull String getMethod() {
        return method;
    }

    public void setMethod(@NotNull String method) {
        this.method = method;
    }
}
