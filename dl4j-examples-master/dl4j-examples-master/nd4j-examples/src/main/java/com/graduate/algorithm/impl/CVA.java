package com.graduate.algorithm.impl;

import com.graduate.algorithm.Algorithm;
import com.graduate.entity.Matrix;
import com.graduate.entity.Pixel;
import com.graduate.tool.*;
import org.opencv.core.Mat;
import org.opencv.core.Size;

import static com.graduate.algorithm.impl.PHA.hexStrToBinaryStr;
import static com.graduate.entity.Pixel.zero;
import static com.graduate.tool.CvMath.*;
import static org.opencv.core.CvType.CV_32S;
import static org.opencv.imgproc.Imgproc.INTER_CUBIC;
import static org.opencv.imgproc.Imgproc.resize;

public class CVA implements Algorithm {
    @Override
    public byte[] hashString(Mat originImage) {

        Mat srcImage = originImage;
        Blur blur = new Blur();
        Size dSize = new Size(512, 512);
        Mat originCub = new Mat(dSize, CV_32S);
        /**
          * 先进行规格化，化成512×512的矩阵
          */

        resize(srcImage, originCub, dSize, 0, 0, INTER_CUBIC);
        /**
          * 通过高斯滤波
          */
        Mat blurMat = blur.GaussianBlurD(originCub, 3, 3, 16, 16);
        /**
          * 提取颜色向量角，生成矩阵
          */
//        imwrite("/home/hebly723/1.jpg", blurMat);
        Pixel ref = CVAMethod.calAverage(blurMat);
        double[][] array = CVAMethod.getCVA(blurMat, ref);
        double[][] dctlMat = new double[64][4032];
//        for (double[] doubles : array) {
//            System.out.println(Arrays.toString(doubles));
//        }

        /**
         * 分块
         */
        for (int i = 0; i < blurMat.width() / 64; i++) {
            for (int j = 0; j < blurMat.width() / 64; j++) {
                /**
                  * 作DCT变换，取前几个作为特征向量，生成特征矩阵
                  */
                DCT_Trans dct_trans = new DCT_Trans(64);
                dctlMat[i*8+j] = dct_trans.CVADCTProgressD(array, i * 64, j * 64, 64);
//                dctlMat.add(doubles);
            }
        }
        System.out.println("size"+dctlMat.length);
        System.out.println("length"+dctlMat[0].length);
        Matrix dctMat = new Matrix(dctlMat);

//        System.out.println(dctMat);
        /**
          * 计算相同余弦位置的方差和标准差
          */
        double[][] urList = getUR(dctlMat);
        /**
          * 归一，类似正态
          */
        double[][] sList = new double[dctMat.getColumnDimension()][dctMat.getRowDimension()];
        for (int i = 0; i < dctMat.getColumnDimension(); i++) {
//            double[] sdlist = new double[dctMat.getRowDimension()];
            for (int j = 0; j < dctMat.getRowDimension(); j++) {
                double k = urList[j][1];
                if (k == 0)
                    k = zero;
                double d = (dctMat.get(j, i) - urList[j][0]) / k;
                sList[i][j]=d;
//                System.out.print(d + "\t");
            }
//            System.out.println();
//            sList.add(sdlist);
        }
//        System.out.println(3);
//        List<Boolean> booleans = new ArrayList<>();
//        System.out.println("size" + sList.size());
//        System.out.println("0-size" + sList.get(0).size());
//        /**
//          * 置乱
//          */
//        Fisher_Yates(sList, password);
        Matrix matrix = new Matrix(sList);
//
//        System.out.println(matrix.getMatrix());
        LLEArg lleArg = new LLEArg(matrix);
        lleArg.setDimention(40);
        lleArg.setNeighborhoodNumber(15);
//        System.out.println(matrix);
        Matrix lleAnswer = lleArg.getAnswer(matrix);
//
        Quantization quantization = new Quantization(lleAnswer);
//
//        System.out.println(5);
        byte[] comps = quantization.getByteHash();
//        System.out.println(Arrays.toString(comps));
//        System.out.println("length"+comps.length);
//        StringBuffer hashCode = new StringBuffer();

        System.out.println(comps.length);
//        for (int i = 0; i < comps.length; i += 4) {
//            int result = comps[i] * (int) Math.pow(2, 3) +
//                comps[i + 1] * (int) Math.pow(2, 2) +
//                comps[i + 2] * (int) Math.pow(2, 1) + comps[i + 3];
//                hashCode.append(Integer.toHexString(result));//二进制转为16进制
//        }
//        String sourceHashCode = hashCode.toString();
        return comps;
//        return null;
    }
//
    @Override
    public double hashCompare(byte[] src, byte[] newString) {
        int difference = 0;
        int len =src.length;
//        String bSrc = hexStrToBinaryStr(src);
//        String bNew = hexStrToBinaryStr(newString);
//        System.out.println(bSrc);
//        System.out.println(bNew);
        for (int i = 0; i < len; i++) {
            if(src[i] != newString[i]) {
                difference++;
            }
        }

        return (1 - ((double)difference)/src.length);
    }
}
