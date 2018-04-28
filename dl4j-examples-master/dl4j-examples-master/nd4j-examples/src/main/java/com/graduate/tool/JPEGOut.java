package com.graduate.tool;

import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;

import static org.opencv.imgcodecs.Imgcodecs.CV_IMWRITE_JPEG_QUALITY;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;

public class JPEGOut {
    public static void transferJPEG(Mat inMat, String outFile, int number){

        MatOfInt matOfInt = new MatOfInt(CV_IMWRITE_JPEG_QUALITY, number);

        imwrite(outFile, inMat, matOfInt);
    }
}
