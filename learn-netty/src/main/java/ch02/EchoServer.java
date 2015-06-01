/*
 * Copyright (c) www.ultrapower.com.cn
 */

package ch02;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by liekkas on 15/5/7.
 *
 * 1.创建ServerBootstrap实例来引导绑定和启动服务器
 * 2.创建NioEventLoopGroup对象来处理事件,如接受新连接、接收数据、写数据等等
 * 3.指定InetSocketAddress,服务器监听此端口
 * 4.设置childHandler执行所有的连接请求
 * 5.都设置完毕了,最后调用ServerBootstrap.bind() 方法来绑定服务器
 */
public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //创建ServerBootstrap实例
            ServerBootstrap bootstrap = new ServerBootstrap();

            //指定NIO端口，地址
            //添加handler到通道管道
            bootstrap.group(group)
                     .channel(NioServerSocketChannel.class)
                     .localAddress(new InetSocketAddress(port))
                     .childHandler(new ChannelInitializer<SocketChannel>() {
                         @Override
                         protected void initChannel(SocketChannel ch) throws Exception {
                             ch.pipeline().addLast(new EchoServerHandler());
                         }
                     });

            //绑定服务，等待服务关闭、释放资源等
            ChannelFuture future = bootstrap.bind().sync();

            System.out.println(EchoServer.class.getName() + " started and listen on " + future.channel().localAddress());

            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 1){
            System.err.println("Usage:" + EchoServer.class.getSimpleName() + " <port>");
        }

        int port = Integer.parseInt(args[0]);
        new EchoServer(port).start();
    }
}
