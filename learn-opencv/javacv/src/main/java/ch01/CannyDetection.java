package ch01;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Size;

import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
/**
 * Created by liekkas on 15/5/29.
 * 使用Canny函数进行灰度检测
 *
 * C++
 * int main( )
 {
 //【0】载入原始图
 Mat srcImage = imread("1.jpg");  //工程目录下应该有一张名为1.jpg的素材图
 imshow("【原始图】Canny边缘检测", srcImage); 	//显示原始图
 Mat dstImage,edge,grayImage;	//参数定义

 //【1】创建与src同类型和大小的矩阵(dst)
 dstImage.create( srcImage.size(), srcImage.type() );

 //【2】将原图像转换为灰度图像
 cvtColor( srcImage, grayImage, CV_BGR2GRAY );

 //【3】先用使用 3x3内核来降噪
 blur( grayImage, edge, Size(3,3) );

 //【4】运行Canny算子
 Canny( edge, edge, 3, 9,3 );

 //【5】显示效果图
 imshow("【效果图】Canny边缘检测", edge);

 waitKey(0);

 return 0;
 }
 */
public class CannyDetection{

    public static void main(String[] args) {
        Mat srcImage = imread(ImageBlur.class.getResource("/images/m1.jpg").getPath());
        Mat dstImage = new Mat();
        Mat edge = new Mat();
        Mat grayImage = new Mat();

        //创建和src相同大小的矩阵
        dstImage.create(srcImage.size(),srcImage.type());

        //灰度化原图
        cvtColor(srcImage,grayImage,COLOR_BGR2GRAY);

        blur(grayImage, edge, new Size(3, 3));

        Canny(edge, edge, 3, 9.3);

        imshow("Test",edge);
        waitKey(5000);
    }
}
