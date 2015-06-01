package ch01;

import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;
import utils.BaseCV;
import utils.Imgshow;

/**
 * Created by liekkas on 15/5/29.
 * 调用摄像头
 * c++
 * int main( )
 {
 //【1】从摄像头读入视频
 VideoCapture capture(0);

 //【2】循环显示每一帧
 while(1)
 {
 Mat frame;  //定义一个Mat变量，用于存储每一帧的图像
 capture>>frame;  //读取当前帧
 imshow("读取视频",frame);  //显示当前帧
 waitKey(30);  //延时30ms
 }
 return 0;
 }

 */
public class UseCamera extends BaseCV {

    public static void main(String[] args) {
        Imgshow im = new Imgshow("视频",800,600);
        im.Window.setResizable(true);


        VideoCapture vcam = new VideoCapture(0);
        while (true) {
            Mat m = new Mat();
            vcam.retrieve(m);
            im.showImage(m);
        }

    }
}
