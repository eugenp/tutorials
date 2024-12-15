package com.baeldung.modifyrequest.requestwrapper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Enumeration;

import org.springframework.http.HttpHeaders;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class EscapeHtmlRequestWrapper extends HttpServletRequestWrapper {
    private String body = null;
    public EscapeHtmlRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.body = this.escapeHtml(request);
    }

    private String escapeHtml(HttpServletRequest request) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        try (InputStream inputStream = request.getInputStream()) {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            char[] charBuffer = new char[128];
            int bytesRead = -1;
            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                stringBuilder.append(charBuffer, 0, bytesRead);
            }
        }
        String input = stringBuilder.toString();
        // Escape HTML characters
        return input.replaceAll("&", "&amp;")
            .replaceAll("<", "&lt;")
            .replaceAll(">", "&gt;")
            .replaceAll("'", "&#39;");
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
        ServletInputStream servletInputStream = new ServletInputStream() {

            @Override
            public int read() {
                return byteArrayInputStream.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }
        };
        return servletInputStream;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        if(HttpHeaders.CONTENT_LENGTH.equals(name)) {
            return Collections.enumeration(Collections.singletonList(String.valueOf(body.length())));
        }
        return super.getHeaders(name);
    }
}
