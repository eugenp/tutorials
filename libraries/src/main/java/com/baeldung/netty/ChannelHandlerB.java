package com.baeldung.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.logging.Logger;

public class ChannelHandlerB extends ChannelInboundHandlerAdapter {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.info("Exception Handled in ChannelHandler B");
        logger.info(cause.getLocalizedMessage());
        // do more exception handling
        ctx.close();
    }
}
