package com.baeldung.springsoap.rawxml.jaxws;

import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPBody;
import jakarta.xml.soap.SOAPElement;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class RawSoapCaptureHandlerUnitTest {

    private static final String SOAP11_NS = "http://schemas.xmlsoap.org/soap/envelope/";

    @Test
    void givenOutboundMessage_whenHandleMessage_thenSinkReceivesOutboundAndXml() throws Exception {
        CapturingSink sink = new CapturingSink();
        RawSoapCaptureHandler handler = new RawSoapCaptureHandler(sink);
        SOAPMessage soapMessage = createSoapMessageWithGreeting("Test");
        SOAPMessageContext ctx = new FakeSoapMessageContext(soapMessage, true);
        boolean result = handler.handleMessage(ctx);
        assertTrue(result);
        assertEquals(1, sink.calls.size());
        CapturedCall call = sink.calls.get(0);
        assertEquals(RawSoapCaptureHandler.Direction.OUTBOUND, call.direction);
        assertXmlLooksLikeSoapEnvelope(call.xml);
        assertTrue(call.xml.contains("greeting"));
        assertTrue(call.xml.contains("Test"));
        assertTrue(call.xml.contains("http://example.com"));
    }

    @Test
    void givenInboundMessage_whenHandleMessage_thenSinkReceivesInboundAndXml() throws Exception {
        CapturingSink sink = new CapturingSink();
        RawSoapCaptureHandler handler = new RawSoapCaptureHandler(sink);
        SOAPMessage soapMessage = createSoapMessageWithGreeting("Hello");
        SOAPMessageContext ctx = new FakeSoapMessageContext(soapMessage, false);
        boolean result = handler.handleMessage(ctx);
        assertTrue(result);
        assertEquals(1, sink.calls.size());
        CapturedCall call = sink.calls.get(0);
        assertEquals(RawSoapCaptureHandler.Direction.INBOUND, call.direction);
        assertXmlLooksLikeSoapEnvelope(call.xml);
        assertTrue(call.xml.contains("greeting"));
        assertTrue(call.xml.contains("Hello"));
    }

    @Test
    void givenFaultMessage_whenHandleFault_thenSinkReceivesFaultAndXml() throws Exception {
        CapturingSink sink = new CapturingSink();
        RawSoapCaptureHandler handler = new RawSoapCaptureHandler(sink);
        SOAPMessage soapMessage = createSoapMessageWithGreeting("FaultPayload");
        SOAPMessageContext ctx = new FakeSoapMessageContext(soapMessage, false);
        boolean result = handler.handleFault(ctx);
        assertTrue(result);
        assertEquals(1, sink.calls.size());
        CapturedCall call = sink.calls.get(0);
        assertEquals(RawSoapCaptureHandler.Direction.FAULT, call.direction);
        assertXmlLooksLikeSoapEnvelope(call.xml);
        assertTrue(call.xml.contains("FaultPayload"));
    }

    @Test
    void givenHandler_whenGetHeaders_thenReturnEmptySet() {
        RawSoapCaptureHandler handler = new RawSoapCaptureHandler((d, x) -> {});
        assertNotNull(handler.getHeaders());
        assertTrue(handler.getHeaders().isEmpty());
    }

    // ---------- Sink + capture model ----------

    private static void assertXmlLooksLikeSoapEnvelope(String xml) {
        assertNotNull(xml);
        assertTrue(xml.contains(SOAP11_NS));
        assertTrue(xml.matches("(?s).*<[^:>]+:Envelope\\b[^>]*>.*"));
        assertTrue(xml.matches("(?s).*<[^:>]+:Body\\b[^>]*>.*</[^:>]+:Body>.*"));
        assertTrue(xml.matches("(?s).*</[^:>]+:Envelope>.*"));
    }

    private static SOAPMessage createSoapMessageWithGreeting(String text) throws Exception {
        MessageFactory factory = MessageFactory.newInstance();
        SOAPMessage message = factory.createMessage();
        SOAPBody body = message.getSOAPBody();
        SOAPElement greeting = body.addChildElement("greeting", "ns", "http://example.com");
        greeting.addTextNode(text);
        message.saveChanges();
        return message;
    }
    private static final class CapturedCall {
        final RawSoapCaptureHandler.Direction direction;
        final String xml;

        private CapturedCall(RawSoapCaptureHandler.Direction direction, String xml) {
            this.direction = direction;
            this.xml = xml;
        }
    }

    private static final class CapturingSink implements RawSoapCaptureHandler.SoapXmlSink {
        final List<CapturedCall> calls = new ArrayList<>();

        @Override
        public void accept(RawSoapCaptureHandler.Direction direction, String soapXml) {
            calls.add(new CapturedCall(direction, soapXml));
        }
    }
}
