package com.baeldung.springsoap.rawxml.jaxws;

import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.handler.soap.SOAPHandler;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;

import javax.xml.namespace.QName;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Set;

public class RawSoapCaptureHandler implements SOAPHandler<SOAPMessageContext> {

    private final SoapXmlSink sink;

    public RawSoapCaptureHandler(SoapXmlSink sink) {
        this.sink = sink;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        Boolean outbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        Direction direction = Boolean.TRUE.equals(outbound) ? Direction.OUTBOUND : Direction.INBOUND;

        String xml = toString(context.getMessage());
        sink.accept(direction, xml);
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        String xml = toString(context.getMessage());
        sink.accept(Direction.FAULT, xml);
        return true;
    }

    @Override
    public void close(MessageContext context) {
    }

    @Override
    public Set<QName> getHeaders() {
        return Collections.emptySet();
    }

    private String toString(SOAPMessage message) {
        try {
            message.saveChanges();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            message.writeTo(out);
            return out.toString(StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize SOAP message", e);
        }
    }

    public enum Direction {
        OUTBOUND,
        INBOUND,
        FAULT
    }

    public interface SoapXmlSink {
        void accept(Direction direction, String soapXml);
    }
}