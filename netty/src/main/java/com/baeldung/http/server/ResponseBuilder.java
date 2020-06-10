package com.baeldung.http.server;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.CharsetUtil;

class ResponseBuilder {

    static StringBuilder addRequestAttributes(HttpRequest request) {
        StringBuilder responseData = new StringBuilder();
        responseData.append("Version: ")
            .append(request.protocolVersion())
            .append("\r\n");
        responseData.append("Host: ")
            .append(request.headers()
                .get(HttpHeaderNames.HOST, "unknown"))
            .append("\r\n");
        responseData.append("URI: ")
            .append(request.uri())
            .append("\r\n\r\n");
        return responseData;
    }

    static StringBuilder addParams(HttpRequest request) {
        StringBuilder responseData = new StringBuilder();
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.uri());
        Map<String, List<String>> params = queryStringDecoder.parameters();
        if (!params.isEmpty()) {
            for (Entry<String, List<String>> p : params.entrySet()) {
                String key = p.getKey();
                List<String> vals = p.getValue();
                for (String val : vals) {
                    responseData.append("Parameter: ")
                        .append(key)
                        .append(" = ")
                        .append(val)
                        .append("\r\n");
                }
            }
            responseData.append("\r\n");
        }
        return responseData;
    }

    static StringBuilder addHeaders(HttpRequest request) {
        StringBuilder responseData = new StringBuilder();
        HttpHeaders headers = request.headers();
        if (!headers.isEmpty()) {
            for (Map.Entry<String, String> header : headers) {
                CharSequence key = header.getKey();
                CharSequence value = header.getValue();
                responseData.append(key)
                    .append(" = ")
                    .append(value)
                    .append("\r\n");
            }
            responseData.append("\r\n");
        }
        return responseData;
    }

    static StringBuilder addBody(HttpContent httpContent) {
        StringBuilder responseData = new StringBuilder();
        ByteBuf content = httpContent.content();
        if (content.isReadable()) {
            responseData.append(content.toString(CharsetUtil.UTF_8)
                .toUpperCase());
            responseData.append("\r\n");
        }
        return responseData;
    }

    static StringBuilder addDecoderResult(HttpObject o) {
        StringBuilder responseData = new StringBuilder();
        DecoderResult result = o.decoderResult();

        if (!result.isSuccess()) {
            responseData.append("..Decoder Failure: ");
            responseData.append(result.cause());
            responseData.append("\r\n");
        }

        return responseData;
    }

    static StringBuilder addLastResponse(HttpRequest request, LastHttpContent trailer) {
        StringBuilder responseData = new StringBuilder();
        responseData.append("Good Bye!\r\n");

        if (!trailer.trailingHeaders()
            .isEmpty()) {
            responseData.append("\r\n");
            for (CharSequence name : trailer.trailingHeaders()
                .names()) {
                for (CharSequence value : trailer.trailingHeaders()
                    .getAll(name)) {
                    responseData.append("P.S. Trailing Header: ");
                    responseData.append(name)
                        .append(" = ")
                        .append(value)
                        .append("\r\n");
                }
            }
            responseData.append("\r\n");
        }
        return responseData;
    }

}
