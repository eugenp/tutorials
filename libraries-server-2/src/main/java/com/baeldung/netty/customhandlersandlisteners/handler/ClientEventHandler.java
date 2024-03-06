package com.baeldung.netty.customhandlersandlisteners.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientEventHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext context, String msg) {
        System.out.println(msg);
    }
}
