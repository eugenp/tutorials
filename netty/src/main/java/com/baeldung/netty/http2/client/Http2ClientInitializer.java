package com.baeldung.netty.http2.client;

import com.baeldung.netty.http2.Http2Util;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;

public class Http2ClientInitializer extends ChannelInitializer<SocketChannel> {

    private final SslContext sslCtx;
    private final int maxContentLength;
    private Http2SettingsHandler settingsHandler;
    private Http2ClientResponseHandler responseHandler;

    public Http2ClientInitializer(SslContext sslCtx, int maxContentLength) {
        this.sslCtx = sslCtx;
        this.maxContentLength = maxContentLength;
    }

    @Override
    public void initChannel(SocketChannel ch) throws Exception {

        settingsHandler = new Http2SettingsHandler(ch.newPromise());
        responseHandler = new Http2ClientResponseHandler();
        
        if (sslCtx != null) {
            ChannelPipeline pipeline = ch.pipeline();
            pipeline.addLast(sslCtx.newHandler(ch.alloc(), Http2Client.HOST, Http2Client.PORT));
            pipeline.addLast(Http2Util.getClientAPNHandler(maxContentLength, settingsHandler, responseHandler));
        }
    }

    public Http2SettingsHandler getSettingsHandler() {
        return settingsHandler;
    }
    
    public Http2ClientResponseHandler getResponseHandler() {
        return responseHandler;
    }
}
