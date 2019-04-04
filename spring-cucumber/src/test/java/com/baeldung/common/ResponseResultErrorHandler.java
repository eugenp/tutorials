package com.baeldung.common;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

public class ResponseResultErrorHandler implements ResponseErrorHandler {
    public void setResults(ResponseResults results) {
        this.results = results;
    }

    private ResponseResults results = null;

    public Boolean getHadError() {
        return hadError;
    }

    public void setHadError(Boolean hadError) {
        this.hadError = hadError;
    }

    private Boolean hadError = false;

    public ResponseResults getResults() {
        return results;
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        hadError = response.getRawStatusCode() >= 400;
        return hadError;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        results = new ResponseResults(response);
    }
}