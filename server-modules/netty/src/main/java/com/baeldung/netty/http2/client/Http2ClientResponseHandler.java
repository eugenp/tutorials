package com.baeldung.netty.http2.client;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http2.HttpConversionUtil;
import io.netty.util.CharsetUtil;

public class Http2ClientResponseHandler extends SimpleChannelInboundHandler<FullHttpResponse> {

    private final Logger logger = LoggerFactory.getLogger(Http2ClientResponseHandler.class);
    private final Map<Integer, MapValues> streamidMap;

    public Http2ClientResponseHandler() {
        streamidMap = new HashMap<Integer, MapValues>();
    }

    public MapValues put(int streamId, ChannelFuture writeFuture, ChannelPromise promise) {
        return streamidMap.put(streamId, new MapValues(writeFuture, promise));
    }

    public String awaitResponses(long timeout, TimeUnit unit) {

        Iterator<Entry<Integer, MapValues>> itr = streamidMap.entrySet()
            .iterator();
        
        String response = null;

        while (itr.hasNext()) {
            Entry<Integer, MapValues> entry = itr.next();
            ChannelFuture writeFuture = entry.getValue()
                .getWriteFuture();

            if (!writeFuture.awaitUninterruptibly(timeout, unit)) {
                throw new IllegalStateException("Timed out waiting to write for stream id " + entry.getKey());
            }
            if (!writeFuture.isSuccess()) {
                throw new RuntimeException(writeFuture.cause());
            }
            ChannelPromise promise = entry.getValue()
                .getPromise();

            if (!promise.awaitUninterruptibly(timeout, unit)) {
                throw new IllegalStateException("Timed out waiting for response on stream id " + entry.getKey());
            }
            if (!promise.isSuccess()) {
                throw new RuntimeException(promise.cause());
            }
            logger.info("---Stream id: " + entry.getKey() + " received---");
            response = entry.getValue().getResponse();
            
            itr.remove();
        }
        
        return response;

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpResponse msg) throws Exception {
        Integer streamId = msg.headers()
            .getInt(HttpConversionUtil.ExtensionHeaderNames.STREAM_ID.text());
        if (streamId == null) {
            logger.error("HttpResponseHandler unexpected message received: " + msg);
            return;
        }

        MapValues value = streamidMap.get(streamId);

        if (value == null) {
            logger.error("Message received for unknown stream id " + streamId);
            ctx.close();
        } else {
            ByteBuf content = msg.content();
            if (content.isReadable()) {
                int contentLength = content.readableBytes();
                byte[] arr = new byte[contentLength];
                content.readBytes(arr);
                String response = new String(arr, 0, contentLength, CharsetUtil.UTF_8);
                logger.info("Response from Server: "+ (response));
                value.setResponse(response);
            }
            
            value.getPromise()
                .setSuccess();
        }
    }

    public static class MapValues {
        ChannelFuture writeFuture;
        ChannelPromise promise;
        String response;

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }

        public MapValues(ChannelFuture writeFuture2, ChannelPromise promise2) {
            this.writeFuture = writeFuture2;
            this.promise = promise2;
        }

        public ChannelFuture getWriteFuture() {
            return writeFuture;
        }

        public ChannelPromise getPromise() {
            return promise;
        }

    }
}
