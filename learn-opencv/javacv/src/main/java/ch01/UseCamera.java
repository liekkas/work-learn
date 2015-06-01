package ch01;

import org.bytedeco.javacpp.opencv_core.*;
import org.bytedeco.javacpp.opencv_highgui;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;

import static org.bytedeco.javacpp.opencv_highgui.imshow;

/**
 * Created by liekkas on 15/5/29.
 * 打开摄像头
 */
public class UseCamera {

    public static void main(String[] args) throws FrameGrabber.Exception {
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        grabber.start();

        OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
        IplImage grabbedImage = converter.convert(grabber.grab());

        CanvasFrame frame = new CanvasFrame("人脸识别",CanvasFrame.getDefaultGamma()/grabber.getGamma());

        while (frame.isVisible() && (grabbedImage = converter.convert(grabber.grab()))!=null) {
            frame.showImage(converter.convert(grabbedImage));
        }

        frame.dispose();
        grabber.stop();
    }
}
