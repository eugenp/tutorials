package com.baeldung.springsoap.rawxml.spring;

import org.springframework.ws.WebServiceMessage;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class FakeWebServiceMessage implements WebServiceMessage {

    private final String xml;

    FakeWebServiceMessage(String xml) {
        this.xml = xml;
    }

    @Override
    public Source getPayloadSource() {
        return null;
    }

    @Override
    public Result getPayloadResult() {
        return null;
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        outputStream.write(xml.getBytes(StandardCharsets.UTF_8));
    }
}

