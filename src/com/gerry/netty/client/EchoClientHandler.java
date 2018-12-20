package com.gerry.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
        String receive = byteBuf.toString(CharsetUtil.UTF_8);
        System.out.println("Client Receive:" + receive);
    }

    //与服务器建立连接
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //给服务器发消息
        ctx.channel().writeAndFlush(Unpooled.copiedBuffer("i am client !\n", CharsetUtil.UTF_8));
        ctx.channel().flush();
        System.out.println("channelActive");
    }

    //与服务器断开连接
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive");
    }

    //异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //关闭管道
        ctx.channel().close();
        //打印异常信息
        cause.printStackTrace();
    }

}