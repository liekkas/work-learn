/*
 * Copyright (c) www.ultrapower.com.cn
 */

package ch02;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by liekkas on 15/5/7.
 *
 *   服务器写好了,现在来写一个客户端连接服务器。应答程序的客户端包括以下几步:
 * 1.创建Bootstrap对象用来引导启动客户端
 * 2.创建EventLoopGroup对象并设置到Bootstrap中,EventLoopGroup可以理解为是一个线程池,这个线程池用来处理连接、接受数据、发送数据
 * 3.创建InetSocketAddress并设置到Bootstrap中,InetSocketAddress是指定连接的服务器地址
 * 4.添加一个ChannelHandler,客户端成功连接服务器后就会被执行
 * 5.调用Bootstrap.connect()来连接服务器
 * 6.最后关闭EventLoopGroup来释放资源
 */
public class EchoClient {
    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host,port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new EchoClientHandler());
                        }
                    });

            ChannelFuture future = bootstrap.connect().sync();
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 2){
            System.err.println("Usage:" + EchoClient.class.getSimpleName() + " <host> <port>");
        }
        final String host = args[0];
        final int port = Integer.parseInt(args[1]);
        new EchoClient(host,port).start();
    }
}
