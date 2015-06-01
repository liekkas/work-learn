package ch01;

/**
 * Created by liekkas on 15/5/29.
 */

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Size;

import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;

/**
 * c++
 * int main( )
 {
 //【1】载入原始图
 Mat srcImage=imread("1.jpg");

 //【2】显示原始图
 imshow( "均值滤波【原图】", srcImage );

 //【3】进行均值滤波操作
 Mat dstImage;
 blur( srcImage, dstImage, Size(7, 7));

 //【4】显示效果图
 imshow( "均值滤波【效果图】" ,dstImage );

 waitKey( 0 );
 }
 */
public class ImageBlur {

    public static void main(String[] args) {
        Mat srcImage = imread(ImageBlur.class.getResource("/images/m1.jpg").getPath());
        Mat dstImage = new Mat();
        blur(srcImage,dstImage,new Size(7,7));
        imshow(new BytePointer("Blur Effect"), dstImage);
        waitKey(5000);
    }
}
