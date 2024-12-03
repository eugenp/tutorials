package com.baeldung.petstore.client.invoker.auth;

import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;

public interface Authentication {
    /**
     * Apply authentication settings to header and / or query parameters.
     * @param queryParams The query parameters for the request
     * @param headerParams The header parameters for the request
     * @param cookieParams The cookie parameters for the request
     */
    public void applyToParams(MultiValueMap<String, String> queryParams, HttpHeaders headerParams, MultiValueMap<String, String> cookieParams);
}
