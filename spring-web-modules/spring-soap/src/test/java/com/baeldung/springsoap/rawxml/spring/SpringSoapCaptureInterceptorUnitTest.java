package com.baeldung.springsoap.rawxml.spring;

import org.junit.jupiter.api.Test;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SpringSoapCaptureInterceptorUnitTest {

    private static final String SOAP11_NS = "http://schemas.xmlsoap.org/soap/envelope/";

    @Test
    void givenRequest_whenHandleRequest_thenSinkReceivesRequestAndXml() throws Exception {
        CapturingSink sink = new CapturingSink();
        ClientInterceptor interceptor = new SpringSoapCaptureInterceptor(sink);
        String xml = sampleSoapEnvelope("RequestPayload");
        MessageContext ctx = new FakeMessageContext(new FakeWebServiceMessage(xml), null);

        boolean result = interceptor.handleRequest(ctx);

        assertTrue(result);
        assertEquals(1, sink.calls.size());
        assertEquals(SpringSoapCaptureInterceptor.Direction.REQUEST, sink.calls.get(0).direction);
        String captured = sink.calls.get(0).xml;
        assertXmlLooksLikeSoapEnvelope(captured);
        assertTrue(captured.contains("RequestPayload"));
    }

    @Test
    void givenResponse_whenHandleResponse_thenSinkReceivesResponseAndXml() throws Exception {
        CapturingSink sink = new CapturingSink();
        ClientInterceptor interceptor = new SpringSoapCaptureInterceptor(sink);
        String xml = sampleSoapEnvelope("ResponsePayload");
        MessageContext ctx = new FakeMessageContext(null, new FakeWebServiceMessage(xml));
        boolean result = interceptor.handleResponse(ctx);

        assertTrue(result);
        assertEquals(1, sink.calls.size());
        assertEquals(SpringSoapCaptureInterceptor.Direction.RESPONSE, sink.calls.get(0).direction);
        String captured = sink.calls.get(0).xml;
        assertXmlLooksLikeSoapEnvelope(captured);
        assertTrue(captured.contains("ResponsePayload"));
    }

    @Test
    void givenFaultResponse_whenHandleFault_thenSinkReceivesFaultAndXml() throws Exception {
        CapturingSink sink = new CapturingSink();
        ClientInterceptor interceptor = new SpringSoapCaptureInterceptor(sink);
        String xml = sampleSoapEnvelope("FaultPayload");
        MessageContext ctx = new FakeMessageContext(null, new FakeWebServiceMessage(xml));
        boolean result = interceptor.handleFault(ctx);

        assertTrue(result);
        assertEquals(1, sink.calls.size());
        assertEquals(SpringSoapCaptureInterceptor.Direction.FAULT, sink.calls.get(0).direction);
        String captured = sink.calls.get(0).xml;
        assertXmlLooksLikeSoapEnvelope(captured);
        assertTrue(captured.contains("FaultPayload"));
    }

    // ---------- Sink + capture model ----------
    private static void assertXmlLooksLikeSoapEnvelope(String xml) {
        assertNotNull(xml);
        assertFalse(xml.isEmpty());
        assertTrue(xml.contains(SOAP11_NS));
        assertTrue(xml.matches("(?s).*<[^:>]+:Envelope\\b[^>]*>.*"));
        assertTrue(xml.matches("(?s).*<[^:>]+:Body\\b[^>]*>.*</[^:>]+:Body>.*"));
        assertTrue(xml.matches("(?s).*</[^:>]+:Envelope>.*"));
    }

    private static String sampleSoapEnvelope(String payloadText) {
        return ""
          + "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
          + "<soapenv:Envelope xmlns:soapenv=\"" + SOAP11_NS + "\">"
          + "  <soapenv:Header/>"
          + "  <soapenv:Body>"
          + "    <ns:greeting xmlns:ns=\"http://example.com\">" + payloadText + "</ns:greeting>"
          + "  </soapenv:Body>"
          + "</soapenv:Envelope>";
    }
    private static final class CapturedCall {
        final SpringSoapCaptureInterceptor.Direction direction;
        final String xml;

        private CapturedCall(SpringSoapCaptureInterceptor.Direction direction, String xml) {
            this.direction = direction;
            this.xml = xml;
        }
    }

    private static final class CapturingSink implements SpringSoapCaptureInterceptor.SoapXmlSink {
        final List<CapturedCall> calls = new ArrayList<>();

        @Override
        public void accept(SpringSoapCaptureInterceptor.Direction direction, String soapXml) {
            calls.add(new CapturedCall(direction, soapXml));
        }
    }

}
