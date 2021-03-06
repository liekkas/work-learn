package ch01;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import utils.BaseCV;
import utils.Imgshow;

import static org.opencv.highgui.Highgui.imread;
import static org.opencv.imgproc.Imgproc.*;

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
public class CannyDetection extends BaseCV {

    public static void main(String[] args) {
        Mat srcImage = imread(ImageBlur.class.getResource("/images/z4.jpg").getPath());
        Mat dstImage = new Mat();
        Mat edge = new Mat();
        Mat grayImage = new Mat();

        //创建和src相同大小的矩阵
        dstImage.create(srcImage.size(),srcImage.type());

        resize(srcImage, edge, new Size(360, 480)); //原图缩小到指定尺寸
//        cvtColor(edge, edge, COLOR_BGR2GRAY);

        Canny(edge, edge, 50, 200, 3, true);
//        HoughLinesP(edge,edge,1,0.3,50,40,10);
        Imgshow imgshow = new Imgshow("灰度检测");
        imgshow.showImage(edge);
    }
}
