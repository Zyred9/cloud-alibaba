package com.example.netty.active.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * <p>
 *      服务监听服务端
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
public class MonitorServer {

    static int port = 8888;

    public static void main(String[] args) throws InterruptedException {
        MonitorServer server = new MonitorServer();
        server.start ();
    }

    private void start() throws InterruptedException {

        final MonitorServerHandler handler = new MonitorServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();
        try {

            b.group(group);
            b.channel(NioServerSocketChannel.class);
            // 使用指定端口作为 socket 接收地址
            b.localAddress(new InetSocketAddress(port));
            // 添加一个 ServerHandler 到子 Channel 的 ChannelPipeline
            b.childHandler(
                    new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 标注为@Shareable，所以我们可以总是使用同样的实例
                            socketChannel.pipeline().addLast(handler);
                        }
                    }
            );
            // 异步地绑定服务器；调用 sync()方法阻塞等待直到绑定完成
            ChannelFuture sync = b.bind().sync();
            // 获取 Channel 的 CloseFuture，并且阻塞当前线程直到它完成
            sync.channel().closeFuture().sync();
        } catch (Exception ex) {
            // 关闭 EventLoopGroup, 释放所有的资源
            group.shutdownGracefully().sync();
        }
    }

}
