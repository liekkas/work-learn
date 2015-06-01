package ch01;

/**
 * Created by liekkas on 15/5/29.
 */

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import utils.BaseCV;
import utils.Imgshow;

import static org.opencv.highgui.Highgui.imread;
import static org.opencv.imgproc.Imgproc.erode;
import static org.opencv.imgproc.Imgproc.getStructuringElement;

/**
 * c++
 * int main(   )
 {
 //载入原图
 Mat srcImage = imread("1.jpg");
 //显示原图
 imshow("【原图】腐蚀操作", srcImage);
 //进行腐蚀操作
 Mat element = getStructuringElement(MORPH_RECT, Size(15, 15));
 Mat dstImage;
 erode(srcImage, dstImage, element);
 //显示效果图
 imshow("【效果图】腐蚀操作", dstImage);
 waitKey(0);

 return 0;
 }
 */
public class ImageErode extends BaseCV {


    public static void main(String[] args) {
        Mat srcImage = imread(ImageErode.class.getResource("/images/m1.jpg").getPath());
        Mat element = getStructuringElement(Imgproc.MORPH_RECT, new Size(15, 15));
        Mat dstImage = new Mat();
        erode(srcImage,dstImage,element);
        Imgshow imgshow = new Imgshow("腐蚀效果");
        imgshow.showImage(dstImage);
    }
}
