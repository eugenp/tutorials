package com.baeldung.netty.customhandlersandlisteners.handler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import com.baeldung.netty.customhandlersandlisteners.model.Message;
import com.baeldung.netty.customhandlersandlisteners.model.OfflineMessage;
import com.baeldung.netty.customhandlersandlisteners.model.OnlineMessage;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerEventHandler extends SimpleChannelInboundHandler<String> {

    private static final Map<String, Channel> clients = new HashMap<>();
    private static final int MAX_HISTORY = 5;
    private static final Queue<String> history = new LinkedList<>();

    private void handleBroadcast(Message message, ChannelHandlerContext context) {
        final String channelId = context.channel()
            .id()
            .asLongText();

        System.out.printf("[clients: %d] message: %s\n", clients.size(), message);
        clients.forEach((id, channel) -> {
            if (!id.equals(channelId))
                channel.writeAndFlush(message.toString());
        });

        history.add(message.toString() + "\n");
        if (history.size() > MAX_HISTORY)
            history.poll();
    }

    @Override
    public void channelRead0(ChannelHandlerContext context, String msg) {
        handleBroadcast(Message.parse(msg), context);
    }

    @Override
    public void channelActive(final ChannelHandlerContext context) {
        Channel channel = context.channel();
        clients.put(channel.id()
            .asLongText(), channel);

        history.forEach(message -> channel
            .writeAndFlush(message));

        handleBroadcast(new OnlineMessage(context.toString()), context);
    }

    @Override
    public void channelInactive(ChannelHandlerContext context) {
        Channel channel = context.channel();
        clients.remove(channel.id()
            .asLongText());

        handleBroadcast(new OfflineMessage(context.toString()), context);
    }
}