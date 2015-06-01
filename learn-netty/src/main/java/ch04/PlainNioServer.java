/*
 * Copyright (c) www.ultrapower.com.cn
 */

package ch04;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by liekkas on 15/5/8.
 * 原生非阻塞方式，代码基本重写
 */
public class PlainNioServer {

    public void server(int port) throws Exception{
        System.out.println("Listening for connections on port");

        Selector selector = Selector.open();

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        ServerSocket serverSocket = serverSocketChannel.socket();

        serverSocket.bind(new InetSocketAddress(port));

        serverSocketChannel.configureBlocking(false);

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        final ByteBuffer msg = ByteBuffer.wrap("Hi!\r\n".getBytes());

        while (true){
            int n = selector.select();
            if (n>0){
                Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                while (iter.hasNext()){
                    SelectionKey key = iter.next();
                    iter.remove();

                    try {
                        if (key.isAcceptable()){
                            ServerSocketChannel server = (ServerSocketChannel) key.channel();
                            SocketChannel client = server.accept();
                            System.out.println("Accepted connection from " + client);
                            client.configureBlocking(false);
                            client.register(selector,SelectionKey.OP_WRITE,msg.duplicate());
                        }

                        if (key.isWritable()){
                            SocketChannel client =
                                    (SocketChannel)key.channel();
                            ByteBuffer buffer =
                                    (ByteBuffer)key.attachment();
                            while (buffer.hasRemaining()) {
                                if (client.write(buffer) == 0) {		//9
                                    break;
                                }
                            }
                            client.close();					//10
                        }
                    } catch (IOException ex) {
                        key.cancel();
                        try {
                            key.channel().close();
                        } catch (IOException cex) {
                            // ignore on close
                        }
                    }
                }
            }
        }
    }
}
