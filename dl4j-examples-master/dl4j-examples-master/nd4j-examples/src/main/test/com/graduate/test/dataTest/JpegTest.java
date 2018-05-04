package com.graduate.test.dataTest;

import com.graduate.algorithm.Algorithm;
import com.graduate.algorithm.impl.CVA;
import com.graduate.algorithm.impl.PHA;
import com.graduate.dao.HashcvaMapper;
import com.graduate.dao.HashphaMapper;
import com.graduate.dao.ImageDao;
import com.graduate.entity.*;
import com.graduate.service.ExcelService;
import com.graduate.service.impl.textInfoImpl;
import com.graduate.test.BaseTest;
import com.graduate.tool.JPEGOut;
import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.opencv.imgcodecs.Imgcodecs.imread;

/**
 * 图片来自USC  SIPI 图像库中aerials
 */
public class JpegTest extends BaseTest{
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
    @Autowired
    private HashphaMapper hashphaDao;
    @Autowired
    private HashcvaMapper hashcvaDao;
    @Autowired
    private ImageDao imageDao;
    private final String path = "src/main/ImageSource/1.jpg";
    private final String prefix = "src/main/ImageSource/misc";
    private final int begin = 90;
    private final int limit = 100;
    private final int pace  = 10;
    private class KeyValue{
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

    @Test
    public void JPEGDataCollectPHA(){
        File file = new File(prefix);
        File[] files = file.listFiles();
        Map<String, String> map = new HashMap();
//        for (File file1 : files)
//            map.put(file1.getName(), (new PHA()).hashString(imread(file1.getAbsolutePath())));
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
                PHA pha = new PHA();
                HashPack hashPack1 = new HashPack();
//                System.out.println(file1.getAbsolutePath());
                byte[] phaStr = pha.hashString(matTest);
//                if (phaStr.equals(pha.hashString(k)))
//                    System.out.println("right");
                hashPack1.setHashPha(phaStr);
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
                KeyValue keyValue1 = new KeyValue();
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
//
//    @Test
//    public void JPEGDataCollectCVA(){
//        File file = new File(prefix);
//        File[] files = file.listFiles();
//        Map<String, String> map = new HashMap();
//        for (File file1 : files)
//            map.put(file1.getName(), (new CVA()).hashString(imread(file1.getAbsolutePath())).toString());
//        List<Double> pList = new ArrayList<>();
//        for(int number = begin; number <= limit; number = number + pace ) {
//            List<KeyValue> list1 = new ArrayList();
//            for (File file1 : files) {
////                System.out.println("_____________________---------------------------------");
//                System.out.println(number + file1.getName());
//                boolean flagCva = false;
//                JPEGOut.transferJPEG(imread(file1.getAbsolutePath()), path, number);
////                Mat k = imread(file1.getAbsolutePath());
//                Mat matTest = imread(path);
//                CVA cva = new CVA();
//                HashPack hashPack1 = new HashPack();
////                System.out.println(file1.getAbsolutePath());
//                byte[] cvaStr = cva.hashString(matTest);
////                if (phaStr.equals(pha.hashString(k)))
////                    System.out.println("right");
//                hashPack1.setHashCva(cvaStr);
//                try {
//                    List<Image> images = hashphaDao.selectHash(hashPack1);
//
//                for (Image image : images) {
//                        if (image.getDetail().equals(file1.getName()))
//                        {
////                if (cvaStr.equals(map.get(file1.getName())))
//                           flagCva = true;
//                            break;
//                        }
//                    }
//                } catch (Exception e) {
//                }
//                KeyValue keyValue1 = new KeyValue();
//                keyValue1.put(file1.getAbsolutePath(), flagCva);
//                list1.add(keyValue1);
//            }
////            System.out.println(list1.size()+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//            int pCount = 0;
//            for (KeyValue keyValue : list1) {
//                if (keyValue.value) {
//                    pCount++;
////                    System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&"+pCount);
//                }
//            }
//            pList.add(((double) pCount) / list1.size());
//            list1.clear();
//        }
//        System.out.println("CVA");
//        ExcelService excelService = null;
//        try {
//            excelService = new textInfoImpl();
//            excelService.write(pList);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        int i = 0;
//        for(int number = begin; number < limit; number += pace )
//        {
//            System.out.println( number + "\t\t匹配百分比" + pList.get(i));
//            i++;
//        }
//
//    }

    @Test
    public void JPEGDataCollectImageData(){
        File file = new File(prefix);
        File[] files = file.listFiles();
        Map<String, String> map = new HashMap();
        for (File file1 : files){
            Algorithm algorithm = new CVA();
            Mat mat = imread(file1.getAbsolutePath());
            String cvaStr = algorithm.hashString(mat).toString();
            Image image = new Image();
            image.setDetail(file1.getName());
            image.setLocation(file1.getAbsolutePath());
            imageDao.insert(image);
            /**
             * 得到插入之后的图像ID
             */
            ImageExample imageExample = new ImageExample();
            ImageExample.Criteria imageCriteria = imageExample.createCriteria();
            imageCriteria.andDetailEqualTo(image.getDetail());
            imageCriteria.andLocationEqualTo(image.getLocation());
            List<Image> images = imageDao.selectByExample(imageExample);
            /**
             * hashcva
             */
            Hashcva hashcva = new Hashcva();
            hashcva.setId(images.get(0).getId());
            hashcva.setHash(algorithm.hashString(mat));
            hashcvaDao.insertSelective(hashcva);
            /**
             * hashpha
             */
            algorithm = new PHA();
            Hashpha hashpha = new Hashpha();
            hashpha.setId(images.get(0).getId());
            hashpha.setHash(algorithm.hashString(mat));
            hashphaDao.insertSelective(hashpha);
        }


    }

    @Test
    public void JPEGDataAdd(){

    }

}
