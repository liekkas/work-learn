package ch01;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import utils.Imgshow;

/**
 * Created by liekkas on 15/5/29.
 * 载入图片并呈现
 */
/**
 * c++
 * // 【1】读入一张图片
 Mat img=imread("1.jpg");
 // 【2】在窗口中显示载入的图片
 imshow("【载入的图片】",img);
 // 【3】等待6000 ms后窗口自动关闭
 waitKey(6000);
 */
public class ShowImage {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        Mat img = Highgui.imread(
                ShowImage.class.getResource("/images/m1.jpg").getPath());
        Imgshow imgshow = new Imgshow("图片");
        imgshow.showImage(img);
    }
}
