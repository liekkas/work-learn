/*
 * Copyright (c) www.ultrapower.com.cn
 */

package tcp;

import org.apache.commons.io.FileUtils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by liekkas on 15/4/30.
 */
public class FileClient {

    public static void main(String[] args) throws IOException {

        Socket socket = null;
        DataOutputStream dos = null;
        FileInputStream fis = null;
        boolean success = false;
        try {
            socket = new Socket("localhost",2000);

            File file = FileUtils.getFile("pom.xml");

            long total = file.length();
            dos = new DataOutputStream(socket.getOutputStream());
            fis = new FileInputStream(file);

            byte[] sendBytes = new byte[1024];
            int len = 0;
            double sum = 0.0;
            while ((len = fis.read(sendBytes,0,sendBytes.length))>0){
                sum += len;
                System.out.println(">>> 已传输："+((sum / total)*100)+"%");
                dos.write(sendBytes, 0, len);
                dos.flush();
            }

            success = true;
        } catch (IOException e) {
            success = false;
            e.printStackTrace();
        } finally {
            if (socket != null) socket.close();
            if (dos != null) dos.close();
            if (fis != null) fis.close();
        }

        System.out.println(">>> 文件传输：" + (success ? "成功":"失败"));
    }
}
