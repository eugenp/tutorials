package com.baeldung.jaxws.model;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Result {

    private boolean success;
    private Object response;
    private String errorMessage;

    public Result() {

    }

    public Result(boolean success, Object response, String errorMessage) {
        setSuccess(success);
        setResponse(response);
        setErrorMessage(errorMessage);
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
