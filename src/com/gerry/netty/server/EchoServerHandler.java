package com.gerry.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

/**
 * TCP以流的方式进行传输，创建单独的连接通道
 * 需注意粘包拆包问题
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 注意
        // netty从缓存读取和写入数据均是以ByteBuf对象操作
        try {
            ByteBuf in = (ByteBuf) msg;
            // 添加计数引用
            in.retain();
            // 释放计数引用
            in.release();
            System.out.print(in.toString(CharsetUtil.UTF_8));
            // 创建ByteBuf对象
            ByteBuf buf = Unpooled.buffer();
            buf.writeBytes("I love you !".getBytes());
            ctx.channel().write(buf);
            // 将字符串转为ByteBuf
            ctx.channel().write(Unpooled.copiedBuffer("I'm server.\n", CharsetUtil.UTF_8));
            ctx.channel().flush();
        } finally {
            // 抛弃收到的数据
            // 从InBound里读取的ByteBuf要手动释放，还有自己创建的ByteBuf要自己负责释放。这两处要调用这个release方法。
            // write ByteBuf到OutBound时由netty负责释放，不需要手动调用release
            ReferenceCountUtil.release(msg);
        }
    }

    //新客户端接入
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
    }

    //客户端断开
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive");
    }

    //异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //关闭通道
        ctx.channel().close();
        //打印异常
        cause.printStackTrace();
    }
}