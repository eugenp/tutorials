package com.baeldung.reactive.errorhandling;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes{
    
    private HttpStatus status = HttpStatus.BAD_REQUEST;
    private String message = "please provide a name";

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> map = super.getErrorAttributes(request, options);
        map.put("status", getStatus());
        map.put("message", getMessage());
        return map;
    }

    /**
     * @return the status
     */
    public HttpStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
