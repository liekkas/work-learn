/*
 * Copyright (c) www.ultrapower.com.cn
 */

package ch10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by liekkas on 15/5/8.
 */
public class FixedLengthFraemDecoder extends ByteToMessageDecoder {

    private final int len;

    public FixedLengthFraemDecoder(int len) {
        this.len = len;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        while (in.readableBytes() >= len){
            ByteBuf buf = in.readBytes(len);
            out.add(buf);
        }
    }
}
