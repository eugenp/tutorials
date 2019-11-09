package com.baeldung.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.logging.Logger;

public class ChannelHandlerA extends ChannelInboundHandlerAdapter {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        throw new Exception("Ooops");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.info("Exception Occurred in ChannelHandler A");
        ctx.fireExceptionCaught(cause);
    }
}
