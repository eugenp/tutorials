package com.baeldung.springsoap.rawxml.spring;

import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

public class SpringSoapCaptureInterceptor implements ClientInterceptor {

    private final SoapXmlSink sink;

    public SpringSoapCaptureInterceptor(SoapXmlSink sink) {
        this.sink = sink;
    }

    @Override
    public boolean handleRequest(MessageContext messageContext) {
        String xml = toString(messageContext.getRequest());
        sink.accept(Direction.REQUEST, xml);
        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext) {
        String xml = toString(messageContext.getResponse());
        sink.accept(Direction.RESPONSE, xml);
        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext) {
        String xml = toString(messageContext.getResponse());
        sink.accept(Direction.FAULT, xml);
        return true;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Exception e) throws WebServiceClientException {

    }

    private String toString(WebServiceMessage message) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            message.writeTo(out);
            return out.toString(StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize Spring WS message", e);
        }
    }

    public enum Direction {
        REQUEST,
        RESPONSE,
        FAULT
    }

    public interface SoapXmlSink {
        void accept(Direction direction, String soapXml);
    }
}