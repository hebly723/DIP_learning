package com.graduate.tool;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;

import static java.lang.System.exit;
import static org.opencv.highgui.HighGui.imshow;
import static org.opencv.highgui.HighGui.waitKey;
import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgproc.Imgproc.getRotationMatrix2D;
import static org.opencv.imgproc.Imgproc.warpAffine;

/**
 * 旋转类
 */
public class Rotation {
    public Mat randRotation(Mat mat, int number){
        switch (number)
        {
            case 0:
                return mat;
            case 1:
                return rotation90(mat);
            case 2:
                return rotation180(mat);
            case 3:
                return rotation270(mat);
            default:
                System.out.println("Error");
                exit(0);
        }
        return null;
    }
    public static Mat rotation90(Mat location){
        Mat src = location;
        Mat dst = new Mat();

        Size src_sz = src.size();
        Size dst_sz = new Size(src_sz.height, src_sz.width);
        int len = src.cols()>src.rows()?src.cols():src.rows();
        double width = src.cols()/2,
            height = src.rows()/2;
        //指定旋转中心
        Point center = new Point( width, height);

        //获取旋转矩阵（2x3矩阵）
        Mat rot_mat = getRotationMatrix2D(center, 90, 1.0);

        //根据旋转矩阵进行仿射变换
        warpAffine(src, dst, rot_mat, dst_sz);

        //显示旋转效果
//        imshow("image", src);
//        imshow("result", dst);

//        waitKey(0);

        return dst;
    }
    public static Mat rotation180(Mat location){
        Mat src = location;
        Mat dst = new Mat();

        Size src_sz = src.size();
        Size dst_sz = new Size(src_sz.height, src_sz.width);
        int len = src.cols()>src.rows()?src.cols():src.rows();
        double width = src.cols()/2,
            height = src.rows()/2;
        //指定旋转中心
        Point center = new Point( width, height);

        //获取旋转矩阵（2x3矩阵）
        Mat rot_mat = getRotationMatrix2D(center, 180, 1.0);

        //根据旋转矩阵进行仿射变换
        warpAffine(src, dst, rot_mat, dst_sz);

        //显示旋转效果
//        imshow("image", src);
//        imshow("result", dst);

//        waitKey(0);

        return dst;
    }
    public static Mat rotation270(Mat location){
        Mat src = location;
        Mat dst = new Mat();

        Size src_sz = src.size();
        Size dst_sz = new Size(src_sz.height, src_sz.width);
        int len = src.cols()>src.rows()?src.cols():src.rows();
        double width = src.cols()/2,
            height = src.rows()/2;
        //指定旋转中心
        Point center = new Point( width, height);

        //获取旋转矩阵（2x3矩阵）
        Mat rot_mat = getRotationMatrix2D(center, 270, 1.0);

        //根据旋转矩阵进行仿射变换
        warpAffine(src, dst, rot_mat, dst_sz);

        //显示旋转效果
//        imshow("image", src);
//        imshow("result", dst);

//        waitKey(0);

        return dst;
    }
    public static void originVersion(String location, double angle){
        Mat src = imread(location);
        Mat dst = new Mat();

        Size src_sz = src.size();
        Size dst_sz = new Size(src_sz.height, src_sz.width);
        int len = src.cols()>src.rows()?src.cols():src.rows();

        //指定旋转中心
        Point center = new Point(len / 2., len / 2.);

        //获取旋转矩阵（2x3矩阵）
        Mat rot_mat = getRotationMatrix2D(center, angle, 1.0);

        //根据旋转矩阵进行仿射变换
        warpAffine(src, dst, rot_mat, dst_sz);

        //显示旋转效果
        imshow("image", src);
        imshow("result", dst);

        waitKey(0);

//        return 0;
    }
}
