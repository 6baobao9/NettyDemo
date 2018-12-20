package com.gerry.netty.server;

import com.gerry.netty.config.Config;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 基于TCP的服务端
 */
public class EchoServer {
    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();        // 用来接收进来的连接
        EventLoopGroup workerGroup = new NioEventLoopGroup();    // 用来处理已经被接收的连接
        System.out.println("准备运行端口：" + Config.port);

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup);
            //设置将要被实例化的ServerChannel类
            b.channel(NioServerSocketChannel.class);          // 这里告诉Channel如何接收新的连接
            b.childHandler(new ServerChannelInitializer());
            //标识当服务器请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度
            b.option(ChannelOption.SO_BACKLOG, 128);
            // 是否启用心跳保活机机制
            b.childOption(ChannelOption.SO_KEEPALIVE, true);

            // 绑定端口，开始接收进来的连接
            ChannelFuture channelFuture = b.bind(Config.port).sync();
            if (channelFuture.isSuccess()) {
                System.out.println("TCP服务启动 成功---------------");
            }
            // 等待服务器socket关闭
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}