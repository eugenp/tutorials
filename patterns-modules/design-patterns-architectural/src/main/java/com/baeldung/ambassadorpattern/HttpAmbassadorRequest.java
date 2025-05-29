package com.baeldung.ambassadorpattern;

import java.util.Map;

public class HttpAmbassadorRequest {

    private String uri;
    private Map<String, String> queryParams;

    public HttpAmbassadorRequest(String uri, Map<String, String> queryParams) {
        this.uri = uri;
        this.queryParams = queryParams;
    }

    public String getUri() {
        return uri;
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

}
