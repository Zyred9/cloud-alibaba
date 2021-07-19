package com.example.netty.timeout.server;

import cn.hutool.core.date.DateUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
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
public class TimeOutServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当有客户端建立连接得时候，会触发该方法得回调
     *
     * @param ctx           上下文
     * @throws Exception    处理异常信息
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
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

        ctx.writeAndFlush(Unpooled.copiedBuffer("服务端发送消息....".concat(DateUtil.now()), CharsetUtil.UTF_8));
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
     * 实现超时监测
     *
     * @param ctx           上下文
     * @param evt           超市事件
     * @throws Exception    ex
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;

            // 读取消息超时
            if (event.state() == IdleState.READER_IDLE) {
                System.out.println("客户端消息包读取超时");
                ctx.writeAndFlush(Unpooled.copiedBuffer("心跳消息:"
                        .concat(DateUtil.now()).concat("\r\n"), CharsetUtil.UTF_8));
            }

        }
    }
}
