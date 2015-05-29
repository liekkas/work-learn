/*
 * Copyright (c) 2015.  liekkas.zeng
 */

package hi;


import org.opencv.core.*;
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.CascadeClassifier;

/**
 * Created by liekkas on 15/5/26.
 */
public class FaceDetection {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        System.out.println("Running FaceDetection");

        //照片源
        Mat image = Highgui.imread(FaceDetection.class.getResource("/images/z.png").getPath());

        //正脸检测
        CascadeClassifier faceCC = new CascadeClassifier(
                FaceDetection.class.getResource("/cascades/haarcascades/haarcascade_frontalface_alt.xml").getPath());
        MatOfRect faceMor = new MatOfRect();
        faceCC.detectMultiScale(image,faceMor);
        for (Rect rect : faceMor.toArray()) {
            Core.rectangle(image,new Point(rect.x,rect.y),new Point(rect.x+rect.width,rect.y+rect.height),
                    new Scalar(0,255,0));
        }
        System.out.println(String.format("Detected %s faces", faceMor.toArray().length));

        //测脸检测
        CascadeClassifier profileCC = new CascadeClassifier(
                FaceDetection.class.getResource("/cascades/haarcascades/haarcascade_profileface.xml").getPath());
        MatOfRect profileMor = new MatOfRect();
        profileCC.detectMultiScale(image,profileMor);
        for (Rect rect : profileMor.toArray()) {
            Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0));
        }
        System.out.println(String.format("Detected %s profileFaces", profileMor.toArray().length));

        //眼睛检测
        CascadeClassifier eyeCC = new CascadeClassifier(
                FaceDetection.class.getResource("/cascades/haarcascades/haarcascade_lefteye_2splits.xml").getPath());
        MatOfRect eyeMor = new MatOfRect();
        eyeCC.detectMultiScale(image,eyeMor);
        for (Rect rect : eyeMor.toArray()) {
            Core.rectangle(image,new Point(rect.x,rect.y),new Point(rect.x+rect.width,rect.y+rect.height),
                    new Scalar(0,255,0));
        }
        System.out.println(String.format("Detected %s eyes", eyeMor.toArray().length));

        //鼻子检测
        CascadeClassifier noseCC = new CascadeClassifier(
                FaceDetection.class.getResource("/cascades/haarcascades/haarcascade_mcs_nose.xml").getPath());
        MatOfRect noseMor = new MatOfRect();
        noseCC.detectMultiScale(image,noseMor);
        for (Rect rect : noseMor.toArray()) {
            Core.rectangle(image,new Point(rect.x,rect.y),new Point(rect.x+rect.width,rect.y+rect.height),
                    new Scalar(0,255,0));
        }
        System.out.println(String.format("Detected %s noses", noseMor.toArray().length));


        //耳朵检测
        CascadeClassifier earCC = new CascadeClassifier(
                FaceDetection.class.getResource("/cascades/haarcascades/haarcascade_mcs_leftear.xml").getPath());
        MatOfRect earMor = new MatOfRect();
        earCC.detectMultiScale(image, earMor);
        for (Rect rect : earMor.toArray()) {
            Core.rectangle(image,new Point(rect.x,rect.y),new Point(rect.x+rect.width,rect.y+rect.height),
                    new Scalar(0,255,0));
        }
        System.out.println(String.format("Detected %s ears", earMor.toArray().length));

        //输出成像结果
        String filename = "learn-opencv/opencv2/openv2-out.png";
        System.out.println(String.format("Writing %s",filename));
        Highgui.imwrite(filename,image);
    }
}
