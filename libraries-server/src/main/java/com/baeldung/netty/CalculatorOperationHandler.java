package com.baeldung.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

public class CalculatorOperationHandler extends SimpleChannelInboundHandler<Operation> {

    protected void channelRead0(ChannelHandlerContext ctx, Operation msg) throws Exception {
        Long result = calculateEndpoint(msg);
        sendHttpResponse(ctx, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CREATED), result.toString());
        ctx.fireChannelRead(result);
    }

    private long calculateEndpoint(Operation operation) {

        String operator = operation.getOperator().toLowerCase().trim();
        switch (operator) {
            case "add":
                return operation.getNumber1() + operation.getNumber2();
            case "multiply":
                return operation.getNumber1() * operation.getNumber2();
            default:
                throw new IllegalArgumentException("Operation not defined");
        }
    }

    public static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpResponse res, String content) {

        // Generate an error page if response getStatus code is not OK (200).
        ByteBuf buf = Unpooled.copiedBuffer(content, CharsetUtil.UTF_8);
        res.content().writeBytes(buf);

        HttpUtil.setContentLength(res, res.content().readableBytes());

        ctx.channel().writeAndFlush(res);
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {

        sendHttpResponse(ctx, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.INTERNAL_SERVER_ERROR), "Operation not defined");
        ctx.fireExceptionCaught(cause);
    }

}
