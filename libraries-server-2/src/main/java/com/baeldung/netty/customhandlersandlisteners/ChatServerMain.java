package com.baeldung.netty.customhandlersandlisteners;

import com.baeldung.netty.customhandlersandlisteners.handler.ServerEventHandler;
import com.baeldung.netty.customhandlersandlisteners.listener.ChannelInfoListener;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public final class ChatServerMain {

    public static final String HOST = "localhost";
    public static final int PORT = 8081;

    public static void main(String[] args) {
        EventLoopGroup serverGroup = new NioEventLoopGroup(1);
        EventLoopGroup clientGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(serverGroup, clientGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel channel) throws Exception {
                        channel.pipeline()
                            .addFirst(new StringDecoder(), new ServerEventHandler(), new StringEncoder());
                    }
                });

            ChannelFuture future = bootstrap.bind(HOST, PORT)
                .sync();

            System.out.println("chat server started. ready to accept clients.");
            future.addListener(new ChannelInfoListener("server online"));

            future.channel()
                .closeFuture()
                .sync();
        } catch (Throwable e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            serverGroup.shutdownGracefully();
            clientGroup.shutdownGracefully();
        }
    }
}