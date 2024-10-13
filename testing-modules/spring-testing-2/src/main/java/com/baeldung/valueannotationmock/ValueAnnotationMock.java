package com.baeldung.valueannotationmock;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ValueAnnotationMock {

    @Value("${external.api.url}")
    private String apiUrl;

    public String getApiUrl() {
        return apiUrl;
    }

    public String callExternalApi() {
        return String.format("Calling API at %s", apiUrl);
    }
}

