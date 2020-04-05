package com.baeldung.netty.http2.client;

import java.util.concurrent.TimeUnit;

import com.baeldung.netty.http2.Http2Util;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.ssl.SslContext;

public final class Http2Client {

    static final String HOST = "127.0.0.1";
    static final int PORT = 8443;

    public static void main(String[] args) throws Exception {
        SslContext sslCtx = Http2Util.createSSLContext(false);

        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Http2ClientInitializer initializer = new Http2ClientInitializer(sslCtx, Integer.MAX_VALUE);

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.remoteAddress(HOST, PORT);
            b.handler(initializer);

            Channel channel = b.connect()
                .syncUninterruptibly()
                .channel();
            System.out.println("Connected to [" + HOST + ':' + PORT + ']');

            Http2SettingsHandler http2SettingsHandler = initializer.getSettingsHandler();
            http2SettingsHandler.awaitSettings(60, TimeUnit.SECONDS);

            System.err.println("Sending request(s)...");
            
            FullHttpRequest request = Http2Util.createGetRequest(HOST, PORT);
            
            Http2ClientResponseHandler responseHandler = initializer.getResponseHandler();
            int streamId = 3;
            
            responseHandler.put(streamId, channel.write(request), channel.newPromise());
            channel.flush();
            responseHandler.awaitResponses(60, TimeUnit.SECONDS);
            System.out.println("Finished HTTP/2 request(s)");

            channel.close()
                .syncUninterruptibly();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
