package com.example.netty.active.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * <p>
 *      服务监听客户端
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
public class MonitorClient {

    static int port = 8888;
    static String host = "localhost";

    public static void main(String[] args) throws InterruptedException {
        MonitorClient client = new MonitorClient();
        client.start();
    }

    private void start() throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(group);
            b.remoteAddress(new InetSocketAddress(host, port));
            b.channel(NioSocketChannel.class);
            b.handler(new ChannelInitializer<SocketChannel>(){
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new MonitorClientHandler());
                }
            });

            ChannelFuture f = b.connect().sync();
            f.channel().closeFuture().sync();

        }finally {
            group.shutdownGracefully().sync();
        }
    }

}
