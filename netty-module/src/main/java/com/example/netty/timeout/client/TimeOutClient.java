package com.example.netty.timeout.client;

import cn.hutool.core.date.DateUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *      超时连接客户端，6秒一次，服务端5秒接收一次
 *
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
public class TimeOutClient {

    static int port = 8888;
    static String host = "localhost";

    public static void main(String[] args) throws InterruptedException {
        TimeOutClient client = new TimeOutClient();
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
                    ch.pipeline()
                            .addLast(new TimeOutClientHandler());
                            // 添加超时监测，读操作5秒超时
                }
            });

            ChannelFuture f = b.connect().sync();

            // 每6秒发送一次消息到服务端
            for (;;) {
                TimeUnit.SECONDS.sleep(2);
                f.channel().writeAndFlush(Unpooled.copiedBuffer(DateUtil.now(), CharsetUtil.UTF_8));
            }

//             f.channel().closeFuture().sync();

        }finally {
             group.shutdownGracefully().sync();
        }
    }

}
