package com.graduate.test;

import com.graduate.tool.Rotation;
import org.junit.Before;
import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import static org.junit.Assert.*;
import static org.opencv.highgui.HighGui.imshow;
import static org.opencv.highgui.HighGui.waitKey;
import static org.opencv.imgcodecs.Imgcodecs.imread;

public class RotationTest {
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
    String location;
    Mat mat;
    @Before
    public void init(){
        location =
            "src/main/ImageSource/misc/4.1.01.tiff";
        mat = imread(location);
    }
    @Test
    public void originVersion() {
        double rotation = 45;
        Rotation.originVersion(location, rotation);
    }

    @Test
    public void rotation90() {
        Mat dst = Rotation.rotation90(mat);
        imshow("image", mat);
        imshow("result", dst);
        waitKey(0);
    }

    @Test
    public void rotation180() {
        Mat dst = Rotation.rotation180(mat);
        imshow("image", mat);
        imshow("result", dst);
        waitKey(0);
    }

    @Test
    public void rotation270() {
        Mat dst = Rotation.rotation270(mat);
        imshow("image", mat);
        imshow("result", dst);
        waitKey(0);
    }
}
