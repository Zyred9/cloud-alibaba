package com.example.netty.active.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *      连接处理器
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
@ChannelHandler.Sharable
public class MonitorServerHandler extends ChannelInboundHandlerAdapter {

    ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 当有客户端建立连接得时候，会触发该方法得回调
     *
     * @param ctx           上下文
     * @throws Exception    处理异常信息
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

    /**
     * 连接非活动客户端监听
     *
     * @param ctx           上下文
     * @throws Exception    ex
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("客户端掉线：" + channel.remoteAddress());
    }

    /**
     * 对于每个传入的消息都要调用
     * @param ctx       连接处理上下文
     * @param msg       接收消息
     * @throws Exception    ex
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        System.out.println(
                "服务端收到消息: " +  in.toString(CharsetUtil.UTF_8));

        ctx.writeAndFlush(Unpooled.copiedBuffer("服务端发送消息....", CharsetUtil.UTF_8));
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 将未决消息冲刷到远程节点，并且关闭该 Channel
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 发生异常的时候，关闭通道
     * @param ctx           上下文
     * @param cause         异常信息
     * @throws Exception    exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 连接建立的时候会触发这个方法调用
     * @param ctx           上下文
     * @throws Exception    ex
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channels.add(channel);
        System.out.println("客户端建立连接：" + channel.remoteAddress());
    }

    /**
     * 连接断开的时候，会调用本方法
     *
     * @param ctx           上下文
     * @throws Exception    异常
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channels.remove(channel);
        System.out.println("客户端断开连接：" + channel.remoteAddress());
    }
}
