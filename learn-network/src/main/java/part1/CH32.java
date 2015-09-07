/*
 * Copyright (c) www.ultrapower.com.cn
 */

package part1;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Created by liekkas on 15/4/30.
 */
public class CH32
{
    public static void main(String[] args) throws Exception
    {
        SocketAddress remote = new InetSocketAddress("www.sina.com.cn", 80);
        SocketChannel channel = SocketChannel.open(remote);
        String request = "GET / HTTP/1.1\r\n" +
                "Host:www.sina.com.cn\r\n" +
                "Connection:close\r\n\r\n";
        ByteBuffer header = ByteBuffer.wrap(request.getBytes());
        channel.write(header);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        WritableByteChannel out = Channels.newChannel(System.out);
        while (channel.read(buffer) != -1)
        {
            buffer.flip();
            out.write(buffer);
            buffer.clear();
        }
        channel.close();
    }
}

