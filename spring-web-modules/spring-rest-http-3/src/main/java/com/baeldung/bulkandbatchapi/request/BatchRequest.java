package com.baeldung.bulkandbatchapi.request;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpMethod;

public class BatchRequest {
    @NotNull
    private HttpMethod method;
    @NotNull
    private String relativeUrl;
    @NotNull
    private JsonNode data;

    public JsonNode getData() {
        return data;
    }

    public @NotNull String getRelativeUrl() {
        return relativeUrl;
    }

    public void setRelativeUrl(@NotNull String relativeUrl) {
        this.relativeUrl = relativeUrl;
    }

    public void setData(@NotNull JsonNode data) {
        this.data = data;
    }

    public @NotNull HttpMethod getMethod() {
        return method;
    }

    public void setMethod(@NotNull HttpMethod method) {
        this.method = method;
    }
}
