package com.gerry.netty.udp.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

/**
 * UDP传输以 DatagramPacket 为对象，无连接状态
 */
public class UdpServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
        ByteBuf buf = packet.copy().content();
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, "UTF-8");
        System.out.println(body);//打印收到的信息
        //向客户端发送消息
        String json = "你好！我是服务端。";
        // 由于数据报的数据是以字符数组传的形式存储的，所以传转数据
        byte[] bytes = json.getBytes("UTF-8");
        DatagramPacket data = new DatagramPacket(Unpooled.copiedBuffer(bytes), packet.sender());
        ctx.writeAndFlush(data);//向客户端发送消息
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.print("接受请求...");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 因为UDP无连接状态，所以监测不到通道断开
        System.out.print("断开");
    }
}
