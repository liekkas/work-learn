/*
 * Copyright (c) www.ultrapower.com.cn
 */

package ch02;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * 客户端业务逻辑
 * Created by liekkas on 15/5/7.
 *
 * 和编写服务器的ChannelHandler一样,在这里将自定义一个继承
 SimpleChannelInboundHandler的ChannelHandler来处理业务;通过重写父类的三个方法来处理感兴趣的事件:
 channelActive():客户端连接服务器后被调用
 channelRead0():从服务器接收到数据后调用
 exceptionCaught():发生异常时被调用


 可能你会问为什么在这里使用的是SimpleChannelInboundHandler而不使用ChannelInboundHandlerAdapter?
 主要原因是 ChannelInboundHandlerAdapter在处理完消息后需要负责释放资源。在这里将调用ByteBuf.release()来释放资源。
 SimpleChannelInboundHandler会在 完成channelRead0后释放消息,这是通过Netty处理所有消息的ChannelHandler
 实现了ReferenceCounted接口达到的。
 为什么在服务器中不使用SimpleChannelInboundHandler呢?因为服务器要返回相同的消息给客户端,在服务器执行完成写操作之
 前不能释放调用读取到的消息,因为写操作是异步的,一旦写操作完成后,Netty中会自动释放消息。
 */
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf>{

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!运行不息！", CharsetUtil.UTF_8));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("Client Received:" + msg.toString(CharsetUtil.UTF_8));
    }
}
