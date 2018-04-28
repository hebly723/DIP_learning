package com.graduate.test.dataTest;

import com.graduate.algorithm.impl.CVA;
import com.graduate.entity.HashPack;
import com.graduate.service.ExcelService;
import com.graduate.service.impl.textInfoImpl;
import com.graduate.tool.JPEGOut;
import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.opencv.imgcodecs.Imgcodecs.imread;

/**
 * 图片来自USC  SIPI 图像库中aerials
 */
public class JarTest{
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
    public static String path = "/home/hebly723/1.jpg";
    public static String prefix = "/home/hebly723/下载/aerials";
    public static int begin = 90;
    public static int limit = 100;
    public static int pace  = 10;
    public static class KeyValue{
        public String key;
        public boolean value;
        public void put(String key, boolean value){
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "KeyValue{" +
                    "key='" + key + '\'' +
                    ", value=" + value +
                    '}';
    }
}

    public static void main(String args[]){
        File file = new File(JarTest.prefix);
        File[] files = file.listFiles();
        Map<String, String> map = new HashMap();
        for (File file1 : files)
            map.put(file1.getName(), (new CVA()).hashString(imread(file1.getAbsolutePath())).toString());
        List<Double> pList = new ArrayList<>();
        for(int number = begin; number <= limit; number = number + pace ) {
            List<KeyValue> list1 = new ArrayList();
            for (File file1 : files) {
//                System.out.println("_____________________---------------------------------");
                System.out.println(number + file1.getName());
                boolean flagPha = false;
                JPEGOut.transferJPEG(imread(file1.getAbsolutePath()), path, number);
//                Mat k = imread(file1.getAbsolutePath());
                Mat matTest = imread(path);
                CVA pha = new CVA();
                HashPack hashPack1 = new HashPack();
//                System.out.println(file1.getAbsolutePath());
                byte[] phaStr = pha.hashString(matTest);
//                if (phaStr.equals(pha.hashString(k)))
//                    System.out.println("right");
                hashPack1.setHashCva(phaStr);
//                try {
//                    List<Image> images = hashphaDao.selectHash(hashPack1);

//                for (Image image : images) {
//                        if (image.getDetail().equals(file1.getName()))
//                        {
                if (phaStr.equals(map.get(file1.getName())))
                            flagPha = true;
//                            break;
//                        }
//                    }
//                } catch (Exception e) {
//                }
                JarTest.KeyValue keyValue1 = new KeyValue();
                keyValue1.put(file1.getAbsolutePath(), flagPha);
                list1.add(keyValue1);
            }
//            System.out.println(list1.size()+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            int pCount = 0;
            for (KeyValue keyValue : list1) {
                if (keyValue.value) {
                    pCount++;
//                    System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&"+pCount);
                }
            }
            pList.add(((double) pCount) / list1.size());
            list1.clear();
        }
        System.out.println("PHA");
        ExcelService excelService = null;
        try {
            excelService = new textInfoImpl();
            excelService.write(pList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int i = 0;
        for(int number = begin; number < limit; number += pace )
        {
            System.out.println( number + "\t\t匹配百分比" + pList.get(i));
            i++;
        }

    }

    @Test
    public void JPEGDataAdd(){

    }

}
