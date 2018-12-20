package com.gerry.netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.util.CharsetUtil;

/**
 * 解码
 * DelimiterBasedFrameDecoder  防止 沾包
 */
public class IODecoder extends DelimiterBasedFrameDecoder {

    // 分隔符
    private static ByteBuf delimiter = Unpooled.copiedBuffer("\n", CharsetUtil.UTF_8);
    // 拼接单个流的最大长度，超过这个会报错
    private static int maxFrameLength = 1024 * 6; //数据大小


    public IODecoder() {
        super(maxFrameLength, delimiter);
    }

    /**
     * 重新 自定义解码
     */
    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
        // 对数据  buffer 解码
        return super.decode(ctx, buffer);
    }
}