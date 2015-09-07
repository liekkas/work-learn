/*
 * Copyright (c) www.ultrapower.com.cn
 */

package part1;

import java.io.*;
import java.net.Socket;

/**
 * Created by liekkas on 15/4/29.
 */
public class CH14_03 {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("lab",22);

        //向lab发送数据
        OutputStream ops = socket.getOutputStream();
        OutputStreamWriter opsw = new OutputStreamWriter(ops);

        BufferedWriter bw = new BufferedWriter(opsw);
        bw.write("hello lab\\r\\n\\r\\n");
        bw.flush();

        //从服务端接收数据
        InputStream ips = socket.getInputStream();
        InputStreamReader ipsr = new InputStreamReader(ips);

        BufferedReader br = new BufferedReader(ipsr);
        String s = "";
        while ((s = br.readLine())!=null){
            System.out.println(s);
        }
        socket.close();
    }
}
