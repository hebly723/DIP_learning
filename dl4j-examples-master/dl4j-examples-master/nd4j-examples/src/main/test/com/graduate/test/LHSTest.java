package com.graduate.test;

import org.junit.Before;
import org.junit.Test;
import org.opencv.core.Mat;

import static org.opencv.imgcodecs.Imgcodecs.imread;

public class LHSTest extends BaseTest {

    Mat mat;
    @Before
    public void init()
    {
        mat = imread("/home/hebly723/图片/头像.png");
    }

    @Test
    public void testLHS(){
//        mat.get()
    }
}
