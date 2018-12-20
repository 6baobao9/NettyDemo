package com.gerry.netty.client;

import com.gerry.netty.decoder.IODecoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 注册解码器防止拆包粘包
        pipeline.addLast("framer", new IODecoder());
        //自定义Hadler
        pipeline.addLast("handler", new EchoClientHandler());
    }
}
