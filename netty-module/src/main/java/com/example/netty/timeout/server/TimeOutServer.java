package com.example.netty.timeout.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.InetSocketAddress;

/**
 * <p>
 *      第一个 netty 服务器程序
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
public class TimeOutServer {

    static int port = 8888;

    public static void main(String[] args) throws InterruptedException {
        TimeOutServer server = new TimeOutServer();
        server.start ();
    }

    private void start() throws InterruptedException {

        EventLoopGroup group = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();

            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    // 使用指定端口作为 socket 接收地址
                    .localAddress(new InetSocketAddress(port))
                    // 添加一个 ServerHandler 到子 Channel 的 ChannelPipeline
                    .childHandler(
                            new ChannelInitializer<SocketChannel>() {
                                @Override
                                protected void initChannel(SocketChannel socketChannel) throws Exception {
                                    // 标注为@Shareable，所以我们可以总是使用同样的实例
                                    socketChannel.pipeline()
                                            .addLast(new TimeOutServerHandler())
                                            // 服务端新增读取超时监听器
                                            .addLast(new IdleStateHandler(5, 0, 0));
                                }
                            }
                    );
            // 异步地绑定服务器；调用 sync()方法阻塞等待直到绑定完成
            ChannelFuture sync = b.bind().sync();
            // 获取 Channel 的 CloseFuture，并且阻塞当前线程直到它完成
            sync.channel().closeFuture().sync();
        } finally {
            // 关闭 EventLoopGroup, 释放所有的资源
            group.shutdownGracefully().sync();
        }
    }

}
