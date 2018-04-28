package com.graduate.tool;

import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.FeatureDetector;

public class Sift {
    public static Mat getSift(Mat test_mat)
    {
        Mat desc = new Mat();
        FeatureDetector fd = FeatureDetector.create(FeatureDetector.SIFT);
        MatOfKeyPoint mkp =new MatOfKeyPoint();
        fd.detect(test_mat, mkp);
        DescriptorExtractor de = DescriptorExtractor.create(DescriptorExtractor.SIFT);
        de.compute(test_mat,mkp,desc );//提取sift特征
        System.out.println(desc.cols());
        System.out.println(desc.rows());
        return desc;
    }
}
