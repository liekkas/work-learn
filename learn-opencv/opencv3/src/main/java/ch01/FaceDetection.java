package ch01;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import utils.BaseCV;
import utils.Imgshow;

import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgproc.Imgproc.cvtColor;
import static org.opencv.imgproc.Imgproc.equalizeHist;
import static org.opencv.imgproc.Imgproc.rectangle;

/**
 * Created by liekkas on 15/5/29.
 *
 * 针对眼镜方面需要的参数，我考虑了几个关键测量位置，以左右内眼角确定鼻梁宽度，以内外眼角确定眼镜宽度，
 * 以外眼角到耳朵边缘确定镜腿长度。以外眼角，外眼角投影，耳朵边缘形成的三角确定镜腿角度
 需要在人脸识别基础上准确定位出上述关键点坐标
 */
public class FaceDetection extends BaseCV{

    public static void main(String[] args) {
        //照片源
        Mat image = imread(FaceDetection.class.getResource("/images/m1.jpg").getPath());

        //灰度化
        Mat grayImage = new Mat();
        cvtColor(image,grayImage, Imgproc.COLOR_BGR2GRAY);
        equalizeHist(grayImage,grayImage);

//        Mat useImage = grayImage;
        Mat useImage = image;

        //正脸检测
        CascadeClassifier faceCC = new CascadeClassifier(
                FaceDetection.class.getResource("/cascades/haarcascades/haarcascade_frontalface_alt.xml").getPath());
        MatOfRect faceMor = new MatOfRect();
        faceCC.detectMultiScale(useImage, faceMor);
        for (Rect rect : faceMor.toArray()) {
            rectangle(useImage, new Point(rect.x, rect.y),
                    new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0));

            System.out.println("width:"+rect.width + " height:"+rect.height);
        }
        System.out.println(String.format("Detected %s faces", faceMor.toArray().length));

        //眼睛检测
        CascadeClassifier eyeCC = new CascadeClassifier(
                FaceDetection.class.getResource("/cascades/haarcascades/haarcascade_eye.xml").getPath());
        MatOfRect eyeMor = new MatOfRect();
        eyeCC.detectMultiScale(useImage,eyeMor);
        for (Rect rect : eyeMor.toArray()) {
            rectangle(useImage, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0));

            System.out.println("width:" + rect.width + " height:" + rect.height);
        }
        System.out.println(String.format("Detected %s eyes", eyeMor.toArray().length));

        Imgshow imgshow = new Imgshow("人脸检测",360,480);
        imgshow.showImage(useImage);
    }
}
