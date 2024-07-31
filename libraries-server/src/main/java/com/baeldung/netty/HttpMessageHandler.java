package com.baeldung.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;

public class HttpMessageHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {

        String uri = msg.uri();
        HttpMethod httpMethod = msg.method();
        HttpHeaders headers = msg.headers();

        if (HttpMethod.GET == httpMethod) {

            String[] uriComponents = uri.split("[?]");
            String endpoint = uriComponents[0];
            String[] queryParams = uriComponents[1].split("&");

            if ("/calculate".equalsIgnoreCase(endpoint)) {

                String[] firstQueryParam = queryParams[0].split("=");
                String[] secondQueryParam = queryParams[1].split("=");

                Integer a = Integer.valueOf(firstQueryParam[1]);
                Integer b = Integer.valueOf(secondQueryParam[1]);
                String operator = headers.get("operator");

                Operation operation = new Operation(a, b, operator);
                ctx.fireChannelRead(operation);
            }
        } else {
            throw new UnsupportedOperationException("HTTP method not supported");
        }

    }
}
