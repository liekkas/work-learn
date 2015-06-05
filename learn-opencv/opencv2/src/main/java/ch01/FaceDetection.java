package ch01;

import org.opencv.core.*;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import utils.BaseCV;
import utils.Imgshow;

import static org.opencv.imgproc.Imgproc.*;

/**
 * Created by liekkas on 15/5/29.
 *
 * 针对眼镜方面需要的参数，我考虑了几个关键测量位置，以左右内眼角确定鼻梁宽度，以内外眼角确定眼镜宽度，
 * 以外眼角到耳朵边缘确定镜腿长度。以外眼角，外眼角投影，耳朵边缘形成的三角确定镜腿角度
 需要在人脸识别基础上准确定位出上述关键点坐标
 */
public class FaceDetection extends BaseCV{

    private static void calc(Mat image){

//        System.out.println(image.width() + " h:" + image.height());

        //灰度化
        Mat grayImage = new Mat();
//        resize(image, grayImage, new Size(image.cols() / 2, image.rows() / 2)); //原图缩小两倍

        resize(image, grayImage, new Size(720, 1280)); //原图缩小到指定尺寸
        cvtColor(grayImage, grayImage, Imgproc.COLOR_BGR2GRAY);
        threshold(grayImage, grayImage, 80, 255, 3);
        Mat useImage = grayImage;
//        Mat useImage = image;


        //正脸检测
        CascadeClassifier faceCC = new CascadeClassifier(
                FaceDetection.class.getResource("/cascades/haarcascades/haarcascade_frontalface_alt2.xml").getPath());
//                FaceDetection.class.getResource("/cascades/lbpcascades/lbpcascade_profileface.xml").getPath());
        MatOfRect faceMor = new MatOfRect();
        faceCC.detectMultiScale(useImage, faceMor);
        for (Rect rect : faceMor.toArray()) {
            Core.rectangle(useImage, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(255, 100, 255), 1);
//            System.out.println("width:" + rect.width + " height:" + rect.height);

        }
//        System.out.println(String.format("Detected %s faces", faceMor.toArray().length));

        /*//正脸检测
        CascadeClassifier faceCC2 = new CascadeClassifier(
                FaceDetection.class.getResource("/cascades/haarcascades/haarcascade_frontalface_alt.xml").getPath());
        MatOfRect faceMor2 = new MatOfRect();
        faceCC2.detectMultiScale(useImage, faceMor2);
        for (Rect rect : faceMor2.toArray()) {
            Core.rectangle(useImage, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(255, 255, 0),1);
            System.out.println("width:" + rect.width + " height:" + rect.height);

        }
        System.out.println(String.format("Detected %s faces", faceMor.toArray().length));
*/
        //眼镜宽度检测 -- 只有一个
        CascadeClassifier eyeCC = new CascadeClassifier(
                FaceDetection.class.getResource("/cascades/haarcascades/haarcascade_mcs_eyepair_big.xml").getPath());
//                FaceDetection.class.getResource("/cascades/haarcascades/haarcascade_righteye_2splits.xml").getPath());
        MatOfRect eyeMor = new MatOfRect();
        eyeCC.detectMultiScale(useImage, eyeMor);

        int glassTotalLen = 0;
        for (Rect rect : eyeMor.toArray()) {
            Core.rectangle(useImage, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0));
            glassTotalLen = rect.width;
        }

        //鼻子宽度检测 -- 只有一个
//        CascadeClassifier noseCC = new CascadeClassifier(
//                FaceDetection.class.getResource("/cascades/haarcascades/haarcascade_mcs_rightear.xml").getPath());
////                FaceDetection.class.getResource("/cascades/haarcascades/haarcascade_righteye_2splits.xml").getPath());
//        MatOfRect noseMor = new MatOfRect();
//        noseCC.detectMultiScale(useImage, noseMor);
//
//        for (Rect rect : noseMor.toArray()) {
//            Core.rectangle(useImage, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
//                    new Scalar(0, 255, 0));
//            System.out.println(rect.width);
//        }

        //眼睛检测
        CascadeClassifier eyeCC2 = new CascadeClassifier(
                FaceDetection.class.getResource("/cascades/haarcascades/haarcascade_righteye_2splits.xml").getPath());
//                FaceDetection.class.getResource("/cascades/haarcascades/haarcascade_eye.xml").getPath());
        MatOfRect eyeMor2 = new MatOfRect();
        eyeCC2.detectMultiScale(useImage,eyeMor2);

        int leftend = 0,rightbegin = 0;
        int noseLen = 0; //鼻梁宽度
        int numeye = eyeMor2.toArray().length;
        if (numeye == 2) {
            Rect rect1 = eyeMor2.toArray()[0];
            Rect rect2 = eyeMor2.toArray()[1];

            Core.rectangle(useImage, new Point(rect1.x, rect1.y), new Point(rect1.x + rect1.width, rect1.y + rect1.height),
                    new Scalar(0, 255, 255));

            Core.rectangle(useImage, new Point(rect2.x, rect2.y), new Point(rect2.x + rect2.width, rect2.y + rect2.height),
                    new Scalar(0, 255, 255));

            //判断左右位置，左眼的x值小于右眼
            if (rect1.x < rect2.x){
                leftend = rect1.x + rect1.width;
                rightbegin = rect2.x;
            } else {
                leftend = rect2.x + rect2.width;
                rightbegin = rect1.x;
            }

            noseLen = Math.abs(rightbegin-leftend);
        }

        //单个镜片的宽度
        long glassLen = Math.round((glassTotalLen-Math.abs(rightbegin - leftend))*0.5);
        System.out.println("测得眼镜参数为：" + glassLen+"□"+noseLen+"-"+glassTotalLen);


        /*if (numeye > 1){
            for (int j = 0; j < numeye; j++) {
                Rect rect = eyeMor2.toArray()[j];
                Core.rectangle(useImage, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                        new Scalar(0, 255, 255));

                System.out.println("eye["+j+"] : " + rect.x + "width:" + rect.width + " height:" + rect.height);
            }

            System.out.println("鼻梁宽度为："+Math.abs(rightbegin-leftend));
        }*/

        Imgshow imgshow = new Imgshow("人脸检测");
        imgshow.showImage(useImage);
    }

    public static void main(String[] args) {


        for (int i = 0; i < 4; i++) {
            //照片源
            Mat image = Highgui.imread(FaceDetection.class.getResource(
//                    "/images/zhangzs/0" + (57 + i) + ".jpg"
                    "/images/zhaobj/0" + (67 + i) + ".jpg"
//                    "/images/m" + (1+i) +".jpg"
            ).getPath());

            calc(image);
        }
    }
}
