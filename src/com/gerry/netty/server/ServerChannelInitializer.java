package com.gerry.netty.server;

import com.gerry.netty.decoder.IODecoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.concurrent.TimeUnit;

public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    static final EventExecutorGroup group = new DefaultEventExecutorGroup(2);

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 注册解码器防止拆包粘包
        pipeline.addLast("framer", new IODecoder());
        //IdleStateHandler心跳机制,如果超时触发Handle中userEventTrigger()方法
        pipeline.addLast("idleStateHandler", new IdleStateHandler(15, 0, 0, TimeUnit.MINUTES));
        //自定义Hadler
        pipeline.addLast("handler", new EchoServerHandler());
    }
}
