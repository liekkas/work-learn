/*
 * Copyright (c) 2015.  liekkas.zeng
 */

package hi;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

/**
 * Created by liekkas on 15/5/27.
 */
public class HelloOpenCV {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        System.out.println("Welcome to OpenCV " + Core.VERSION);
        Mat m = Mat.eye(3,3, CvType.CV_8UC1);
        System.out.println("OpenCV Mat data:\n" + m.dump());
    }
}
