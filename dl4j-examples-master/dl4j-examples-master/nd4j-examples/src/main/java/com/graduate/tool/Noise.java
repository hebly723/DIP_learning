package com.graduate.tool;

import org.opencv.core.Mat;

import java.util.Random;

public class Noise {
    /**
     * 椒盐噪声
     * @param srcImage
     * @param n
     * @return
     */
    public static Mat saltNoise(Mat srcImage, int n){
        Mat dstImage = srcImage.clone();
        Random random = new Random();
        for (int k = 0; k < n; k++)
        {

            //随机取值行列
            int i = random.nextInt(dstImage.rows());
            int j = random.nextInt(dstImage.cols());
            //图像通道判定
            if (dstImage.channels() == 1)
            {
                dstImage.put(i, j, 255);       //盐噪声
            }
            else
            {
                dstImage.put(i, j, 255, 255, 255);
            }
        }
        for (int k = 0; k < n; k++)
        {
            //随机取值行列
            int i = random.nextInt(dstImage.rows());
            int j = random.nextInt(dstImage.cols());
            //图像通道判定
            if (dstImage.channels() == 1)
            {
                dstImage.put(i, j, 0);     //椒噪声
            }
            else
            {
                dstImage.put(i, j, 0, 0, 0);
            }
        }
        return dstImage;
    }
}
