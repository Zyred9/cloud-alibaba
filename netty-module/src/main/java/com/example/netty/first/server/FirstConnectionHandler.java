package com.example.netty.first.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * <p>
 *      连接处理器
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
@ChannelHandler.Sharable
public class FirstConnectionHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当有客户端建立连接得时候，会触发该方法得回调
     *
     * @param ctx           上下文
     * @throws Exception    处理异常信息
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client "+ ctx.channel().remoteAddress() + " connected.");
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
                "Server received: " +  in.toString(CharsetUtil.UTF_8));
        ctx.write(msg);
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
}
