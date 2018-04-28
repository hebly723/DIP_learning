package com.graduate.test;

import com.graduate.entity.Pixel;
import com.graduate.tool.CVAMethod;
import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import static org.junit.Assert.*;
import static org.opencv.imgcodecs.Imgcodecs.imread;

public class CVAMethodTest {
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
    @Test
    public void calAverage() {
        String str = "/home/hebly723/下载/aerials/3.2.25.tiff";
        Mat mat = imread(str);
        mat.toString();
        Pixel pixel = CVAMethod.calAverage(mat);
        System.out.println(pixel);
    }
}