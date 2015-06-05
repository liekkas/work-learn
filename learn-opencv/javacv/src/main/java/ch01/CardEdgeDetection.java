package ch01;

import org.bytedeco.javacpp.opencv_core.*;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;

/**
 * Created by liekkas on 15/6/1.
 */
public class CardEdgeDetection {

    public static void main(String[] args) {
        IplImage img = cvLoadImage(ShowImage.class.getResource("/images/z.png").getPath(),CV_LOAD_IMAGE_UNCHANGED);

        IplImage gray = cvCreateImage(cvGetSize(img), IPL_DEPTH_8U, 1);
        IplImage smooth = cvCreateImage(cvGetSize(img),IPL_DEPTH_8U,1);

        cvCvtColor(img, gray, COLOR_BGR2GRAY);
        cvSmooth(gray, smooth, CV_BLUR, 9, 9, 2, 2);
        cvThreshold(gray, gray, 155, 255, CV_THRESH_BINARY);

        int N = 7;
        int aperature_size = N;
        double lowThresh = 20;
        double hightThresh = 20;
        cvCanny(gray,gray,lowThresh*N*N,hightThresh*N*N,aperature_size);

        cvShowImage("Test", gray);
        cvWaitKey(5000);
        cvReleaseImage(gray);
    }


}
