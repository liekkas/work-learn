package utils;

import org.opencv.core.Core;

/**
 * Created by liekkas on 15/5/29.
 */
public class BaseCV {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
}
