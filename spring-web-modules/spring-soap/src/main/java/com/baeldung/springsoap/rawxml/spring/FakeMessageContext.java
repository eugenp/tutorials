package com.baeldung.springsoap.rawxml.spring;

import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.context.MessageContext;

import java.io.IOException;
import java.io.InputStream;

public class FakeMessageContext implements MessageContext {
    private final WebServiceMessage request;
    private final WebServiceMessage response;

    FakeMessageContext(WebServiceMessage request, WebServiceMessage response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public WebServiceMessage getRequest() {
        return request;
    }

    @Override
    public WebServiceMessage getResponse() {
        return response;
    }

    @Override
    public void setResponse(WebServiceMessage webServiceMessage) {

    }

    @Override
    public void clearResponse() {

    }

    @Override
    public void readResponse(InputStream inputStream) throws IOException {

    }

    @Override
    public boolean hasResponse() {
        return response != null;
    }

    @Override
    public Object getProperty(String name) {
        return null;
    }

    @Override
    public void setProperty(String name, Object value) {
        // not needed
    }

    @Override
    public void removeProperty(String name) {
        // not needed
    }

    @Override
    public boolean containsProperty(String s) {
        return false;
    }

    @Override
    public String[] getPropertyNames() {
        return new String[0];
    }
}