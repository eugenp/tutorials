package com.baeldung.valueannotationmock;

import org.springframework.beans.factory.annotation.Value;

public class ValueAnnotationConstructorMock {

    private final String apiUrl;
    private final String apiPassword;

    public ValueAnnotationConstructorMock(@Value("#{myProps['api.url']}") String apiUrl,
        @Value("#{myProps['api.password']}") String apiPassword) {
        this.apiUrl = apiUrl;
        this.apiPassword = apiPassword;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public String getApiPassword() {
        return apiPassword;
    }
}
