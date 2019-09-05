package com.baeldung.reactive.logging.jetty;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpHeader;

@Slf4j
public class RequestLogEnhancer {

    public static Request enhance(Request request) {
        StringBuilder group = new StringBuilder();
        request.onRequestBegin(theRequest -> group
          .append("Request ")
          .append(theRequest.getMethod())
          .append(" ")
          .append(theRequest.getURI())
          .append("\n"));
        request.onRequestHeaders(theRequest -> {
            for (HttpField header : theRequest.getHeaders())
                group
                  .append(header)
                  .append("\n");
        });
        request.onRequestContent((theRequest, content) -> {
            group.append(toString(content, getCharset(theRequest.getHeaders())));
        });
        request.onRequestSuccess(theRequest -> {
            log.debug(group.toString());
            group.delete(0, group.length());
        });
        group.append("\n");
        request.onResponseBegin(theResponse -> {
            group
              .append("Response \n")
              .append(theResponse.getVersion())
              .append(" ")
              .append(theResponse.getStatus());
            if (theResponse.getReason() != null) {
                group
                  .append(" ")
                  .append(theResponse.getReason());
            }
            group.append("\n");
        });
        request.onResponseHeaders(theResponse -> {
            for (HttpField header : theResponse.getHeaders())
                group
                  .append(header)
                  .append("\n");
        });
        request.onResponseContent((theResponse, content) -> {
            group.append(toString(content, getCharset(theResponse.getHeaders())));
        });
        request.onResponseSuccess(theResponse -> {
            log.debug(group.toString());
        });
        return request;
    }

    private static String toString(ByteBuffer buffer, Charset charset) {
        byte[] bytes;
        if (buffer.hasArray()) {
            bytes = new byte[buffer.capacity()];
            System.arraycopy(buffer.array(), 0, bytes, 0, buffer.capacity());
        } else {
            bytes = new byte[buffer.remaining()];
            buffer.get(bytes, 0, bytes.length);
        }
        return new String(bytes, charset);
    }

    private static Charset getCharset(HttpFields headers) {
        String contentType = headers.get(HttpHeader.CONTENT_TYPE);
        if (contentType != null) {
            String[] tokens = contentType
              .toLowerCase(Locale.US)
              .split("charset=");
            if (tokens.length == 2) {
                String encoding = tokens[1].replaceAll("[;\"]", "");
                return Charset.forName(encoding);
            }
        }
        return StandardCharsets.UTF_8;
    }

}

