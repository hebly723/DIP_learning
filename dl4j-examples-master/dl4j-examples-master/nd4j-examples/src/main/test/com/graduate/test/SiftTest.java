package com.graduate.test;

import com.graduate.entity.Matrix;
import com.graduate.tool.LLEArg;
import com.graduate.tool.Sift;
import org.junit.Before;
import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import static org.opencv.imgcodecs.Imgcodecs.imread;

public class SiftTest extends BaseTest {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    private Matrix matrix;
    double[][] doubles = {
        {1, 2, 3},
        {2, 2, 1},
        {3, 4, 3}
    };
    private LLEArg lleEmbed;
    @Before
    public void init(){
        matrix = new Matrix(doubles);
        lleEmbed = new LLEArg(matrix);
        System.out.println(matrix);
    }
    @Test
    public void testSiftTest()
    {
        Mat mat1 = imread("/home/hebly723/图片/头像.png");
        Mat mat2 = Sift.getSift(mat1);
        System.out.println(mat2.width()+"|"+mat2.height());
    }
}
