package ch01;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.IplImage;

import static org.bytedeco.javacpp.opencv_core.cvReleaseImage;
import static org.bytedeco.javacpp.opencv_highgui.*;

/**
 * Created by liekkas on 15/5/29.
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

    public static void main(String[] args) {

        //方法1 -- 若想带标题要用BytePointer，中文标题不支持
        Mat srcImage = imread(ShowImage.class.getResource("/images/m1.jpg").getPath());
        imshow(new BytePointer("sven"), srcImage);
        waitKey(5000);

        //方法2 -- 有标题
//        IplImage img = cvLoadImage(ShowImage.class.getResource("/images/m1.jpg").getPath());
//        cvShowImage("Test", img);
//        cvWaitKey(5000);
//        cvReleaseImage(img);

    }
}
