package ch01;

import org.bytedeco.javacpp.opencv_core.*;
import org.bytedeco.javacpp.opencv_objdetect.*;

import static org.bytedeco.javacpp.helper.opencv_objdetect.cvHaarDetectObjects;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_core.CV_AA;
import static org.bytedeco.javacpp.opencv_highgui.imread;
import static org.bytedeco.javacpp.opencv_imgproc.CV_BGR2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.cvCvtColor;
import static org.bytedeco.javacpp.opencv_objdetect.CV_HAAR_DO_CANNY_PRUNING;

/**
 * Created by liekkas on 15/5/29.
 */
public class FaceDetection {

    public static void main(String[] args) {
        Mat srcImage = imread(FaceDetection.class.getResource("/images/m1.jpg").getPath());
        CvHaarClassifierCascade classifierCascade = new CvHaarClassifierCascade(
                cvLoad(FaceDetection.class.getResource("/cascades/haarcascades/haarcascade_frontalface_alt.xml").getPath()));

        Mat grayImage = new Mat();
        //人脸检测前先把图片灰度化

    }
}
