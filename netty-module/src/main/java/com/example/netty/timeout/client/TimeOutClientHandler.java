package com.example.netty.timeout.client;

import cn.hutool.core.date.DateUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

/**
 * <p>
 *      客户端连接处理
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
@ChannelHandler.Sharable
public class TimeOutClientHandler extends SimpleChannelInboundHandler<ByteBuf> {


    /**
     * 当从服务器接收到一条消息时被调用
     * @param ctx           上下文
     * @param in            消息
     * @throws Exception    ex
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        // 记录已接收消息的转储
        System.out.println(
                "客户端接收消息: " + in.toString(CharsetUtil.UTF_8));
    }

    /**
     * 在到服务器的连接已经建立之后将被调用
     * @param ctx           上下文
     * @throws Exception    ex
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //  当channel是活跃的时候，发送一条消息到服务端
        ctx.writeAndFlush(Unpooled.copiedBuffer("客户端发送一条信息: ".concat(DateUtil.now()),
                CharsetUtil.UTF_8));
    }

    /**
     * 在处理过程中引发异常时被调用
     * @param ctx           上下文
     * @param cause         异常信息
     * @throws Exception    ex
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
