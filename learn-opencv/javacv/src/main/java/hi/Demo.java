/*
 * Copyright (c) www.ultrapower.com.cn
 */

package hi;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.indexer.DoubleIndexer;
import org.bytedeco.javacpp.opencv_objdetect;
import org.bytedeco.javacv.*;

import java.io.File;
import java.net.URL;

import static org.bytedeco.javacpp.opencv_calib3d.cvRodrigues2;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_objdetect.*;

/**
 * Created by liekkas on 15/5/28.
 */
public class Demo {

    public static void main(String[] args) throws Exception {
        String classifierName = null;
        if (args.length > 0){
            classifierName = args[0];
        } else {
            URL url = new URL("https://raw.github.com/Itseez/opencv/2.4.0/data/haarcascades/haarcascade_frontalface_alt.xml");
            File file = Loader.extractResource(url,null,"classifier",".xml");
            file.deleteOnExit();
            classifierName = file.getAbsolutePath();
        }

//        classifierName = Demo.class.getResource("/haarcascades/haarcascade_frontalface_alt.xml").getPath();
        Loader.load(opencv_objdetect.class);
        CvHaarClassifierCascade classifierCascade = new CvHaarClassifierCascade(cvLoad(classifierName));
        if (classifierCascade.isNull()){
            System.out.println("Error loading classifier file \""+classifierName+"\".");
            System.exit(1);
        }

        FrameGrabber grabber = FrameGrabber.createDefault(0);
        grabber.start();

        OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
        IplImage grabbedImage = converter.convert(grabber.grab());
        int width = grabbedImage.width();
        int height = grabbedImage.height();

        IplImage grayImage = IplImage.create(width, height, IPL_DEPTH_8U, 1);
        IplImage rotatedImage = grabbedImage.clone();

        CvMemStorage storage = CvMemStorage.create();

        FrameRecorder recorder = FrameRecorder.createDefault("output.avi",width,height);
        recorder.start();

        CanvasFrame frame = new CanvasFrame("人脸识别",CanvasFrame.getDefaultGamma()/grabber.getGamma());
        CvMat randomR = CvMat.create(3,3),randomAxis = CvMat.create(3,1);
        DoubleIndexer Ridx = randomR.createIndexer(),axisIdx = randomAxis.createIndexer();
        axisIdx.put(0, (Math.random() - 0.5) / 4, (Math.random() - 0.5) / 4, (Math.random() - 0.5) / 4);
        cvRodrigues2(randomAxis, randomR, null);
        double f = (width + height)/2.0;
        Ridx.put(0, 2, Ridx.get(0, 2)*f);
        Ridx.put(1, 2, Ridx.get(1, 2)*f);
        Ridx.put(2, 0, Ridx.get(2, 0)/f);
        Ridx.put(2, 1, Ridx.get(2, 1) / f);
        System.out.println(Ridx);

        CvPoint hatPoints = new CvPoint(3);
        while (frame.isVisible() && (grabbedImage = converter.convert(grabber.grab()))!=null){
            cvClearMemStorage(storage);
            
            //人脸检测前先把图片灰度化
            cvCvtColor(grabbedImage,grayImage,CV_BGR2GRAY);
            CvSeq faces = cvHaarDetectObjects(grayImage,classifierCascade,storage,1.1,3,CV_HAAR_DO_CANNY_PRUNING);

            int total = faces.total();
            for (int i = 0; i < total; i++) {
                CvRect r = new CvRect(cvGetSeqElem(faces,i));
                int x = r.x(),y = r.y(),w = r.width(),h = r.height();
                cvRectangle(grabbedImage,cvPoint(x,y),cvPoint(x+w,y+h),CvScalar.RED,1,CV_AA,0);

                hatPoints.position(0).x(x-w/10)   .y(y-h/10);
                hatPoints.position(1).x(x+w*11/10).y(y-h/10);
                hatPoints.position(2).x(x+w/2)    .y(y-h/2);
                cvFillConvexPoly(grabbedImage, hatPoints.position(0), 3, CvScalar.GREEN, CV_AA, 0);
            }

            cvThreshold(grayImage,grayImage,64,255,CV_THRESH_BINARY);

            CvSeq contour = new CvSeq(null);
            cvFindContours(grayImage, storage, contour, Loader.sizeof(CvContour.class),
                    CV_RETR_LIST, CV_CHAIN_APPROX_SIMPLE);
            while (contour != null && !contour.isNull()) {
                if (contour.elem_size() > 0) {
                    CvSeq points = cvApproxPoly(contour, Loader.sizeof(CvContour.class),
                            storage, CV_POLY_APPROX_DP, cvContourPerimeter(contour)*0.02, 0);
                    cvDrawContours(grabbedImage, points, CvScalar.BLUE, CvScalar.BLUE, -1, 1, CV_AA);
                }
                contour = contour.h_next();
            }

            cvWarpPerspective(grabbedImage, rotatedImage, randomR);

            Frame rotatedFrame = converter.convert(rotatedImage);
            frame.showImage(rotatedFrame);
            recorder.record(rotatedFrame);
        }

        frame.dispose();
        recorder.stop();
        grabber.stop();
    }
}
