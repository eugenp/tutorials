package com.baeldung.reactive.logging.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.internal.PlatformDependent;
import java.nio.charset.Charset;

public class CustomLogger extends LoggingHandler {
    public CustomLogger(Class<?> clazz) {
        super(clazz);
    }

    @Override
    protected String format(ChannelHandlerContext ctx, String eventName, Object arg) {
        if (arg instanceof ByteBuf) {
            ByteBuf msg = (ByteBuf) arg;
            return decodeString(msg, msg.readerIndex(), msg.readableBytes(), Charset.defaultCharset());
        }
        return "";
    }

    private String decodeString(ByteBuf src, int readerIndex, int len, Charset charset) {
        if (len == 0) return "";
        byte[] array;
        int offset;
        if (src.hasArray()) {
            array = src.array();
            offset = src.arrayOffset() + readerIndex;
        } else {
            array = PlatformDependent.allocateUninitializedArray(Math.max(len, 1024));
            offset = 0;
            src.getBytes(readerIndex, array, 0, len);
        }
        return new String(array, offset, len, charset);
    }
}
