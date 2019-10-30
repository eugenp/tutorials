package com.baeldung.reactive.logging.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.logging.LoggingHandler;
import java.nio.charset.Charset;

import static io.netty.util.internal.PlatformDependent.allocateUninitializedArray;
import static java.lang.Math.max;
import static java.nio.charset.Charset.defaultCharset;

public class CustomLogger extends LoggingHandler {
    public CustomLogger(Class<?> clazz) {
        super(clazz);
    }

    @Override
    protected String format(ChannelHandlerContext ctx, String event, Object arg) {
        if (arg instanceof ByteBuf) {
            ByteBuf msg = (ByteBuf) arg;
            return decode(msg, msg.readerIndex(), msg.readableBytes(), defaultCharset());
        }
        return super.format(ctx, event, arg);
    }

    private String decode(ByteBuf src, int readerIndex, int len, Charset charset) {
        if (len != 0) {
            byte[] array;
            int offset;
            if (src.hasArray()) {
                array = src.array();
                offset = src.arrayOffset() + readerIndex;
            } else {
                array = allocateUninitializedArray(max(len, 1024));
                offset = 0;
                src.getBytes(readerIndex, array, 0, len);
            }
            return new String(array, offset, len, charset);
        }
        return "";
    }
}
