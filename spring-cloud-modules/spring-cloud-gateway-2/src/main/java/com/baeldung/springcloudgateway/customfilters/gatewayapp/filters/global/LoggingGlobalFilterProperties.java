package com.baeldung.springcloudgateway.customfilters.gatewayapp.filters.global;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("logging.global")
public class LoggingGlobalFilterProperties {
    
    private boolean enabled;
    private boolean requestHeaders;
    private boolean requestBody;
    private boolean responseHeaders;
    private boolean responseBody;
    
    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public boolean isRequestHeaders() {
        return requestHeaders;
    }
    public void setRequestHeaders(boolean requestHeaders) {
        this.requestHeaders = requestHeaders;
    }
    public boolean isRequestBody() {
        return requestBody;
    }
    public void setRequestBody(boolean requestBody) {
        this.requestBody = requestBody;
    }
    public boolean isResponseHeaders() {
        return responseHeaders;
    }
    public void setResponseHeaders(boolean responseHeaders) {
        this.responseHeaders = responseHeaders;
    }
    public boolean isResponseBody() {
        return responseBody;
    }
    public void setResponseBody(boolean responseBody) {
        this.responseBody = responseBody;
    }
    
    

}
