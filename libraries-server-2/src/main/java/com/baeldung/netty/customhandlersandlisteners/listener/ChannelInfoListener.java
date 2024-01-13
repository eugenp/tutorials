package com.baeldung.netty.customhandlersandlisteners.listener;

import java.time.Instant;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.GenericFutureListener;

public class ChannelInfoListener implements GenericFutureListener<ChannelFuture> {

    @Override
    public void operationComplete(ChannelFuture future) throws Exception {
        Channel channel = future.channel();
        System.out.println(Instant.now() + " - my channel id: " + channel.id()
            .asLongText());

        if (!future.isSuccess()) {
            future.cause()
                .printStackTrace();
        }
    }
}
