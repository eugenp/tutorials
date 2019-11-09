package com.baeldung.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ResponseDataEncoder extends MessageToByteEncoder<ResponseData> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ResponseData msg, ByteBuf out) throws Exception {
        out.writeInt(msg.getIntValue());
    }
}
