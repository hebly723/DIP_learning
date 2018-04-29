package com.graduate.test;

import com.graduate.algorithm.Algorithm;
import com.graduate.algorithm.impl.CVA;
import com.graduate.algorithm.impl.PHA;
import com.graduate.dao.*;
import com.graduate.entity.*;
import com.graduate.service.ExcelService;
import com.graduate.service.impl.textInfoImpl;
import com.graduate.test.dataTest.JpegTest;
import com.graduate.tool.JPEGOut;
import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static com.graduate.tool.StringMethod.strictTrim;
import static org.opencv.imgcodecs.Imgcodecs.imread;

public class TestImageDaoTest extends BaseTest {
  static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
  private Logger logger = LoggerFactory.getLogger(this.getClass());
  @Autowired
  private ImageDao imageDao;
  @Autowired
  private TestImageDao testImageDao;
  @Autowired
  private HashcvaMapper hashcvaDao;
  private final String path = "/home/hebly723/test/";
  private final String prefix = "/home/hebly723/下载/aerials";
  private final int begin = 1;
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
  public void insertImage() {
    ImageExample imageExample = new ImageExample();
    ImageExample.Criteria criteria = imageExample.createCriteria();
    criteria.andIdGreaterThan(1000);
    List<Image> images = imageDao.selectByExample(imageExample);
    for (Image image : images) {
      for (int number = begin; number <= limit; number = number + pace) {
        String uuid = UUID.randomUUID().toString();
        JPEGOut.transferJPEG(imread(image.getLocation()), path + uuid +".jpg", number);
        TestImage testImage = new TestImage();
        testImage.setId(image.getId());
        testImage.setDetail(number+"");
        testImage.setLocation(path + uuid +".jpg");
        testImageDao.insertSelective(testImage);
      }
    }
  }
  @Test
  public void JPEGDataCollectCVA(){
    Map<String, String> map = new HashMap();
    List<Double> pList = new ArrayList<>();

    for(int number = begin; number <= limit; number = number + pace ) {

      TestImageExample imageExample = new TestImageExample();
      TestImageExample.Criteria criteria = imageExample.createCriteria();
      criteria.andDetailEqualTo(number+"");
      List<TestImage> images = testImageDao.selectByExample(imageExample);
      List<KeyValue> list1 = new ArrayList();
      for (TestImage testImage : images)
      {


        boolean flagCva = false;
        Mat matTest = imread(testImage.getLocation());
        CVA cva = new CVA();
        String cvaStr = cva.hashString(matTest).toString();
        HashcvaExample hashcvaExample = new HashcvaExample();
        hashcvaExample.createCriteria().andIdEqualTo(testImage.getId());
        byte[] originStr = hashcvaDao.selectByExample(hashcvaExample).get(0).getHash();
        if (cvaStr.equals(originStr))
          flagCva = true;
        KeyValue keyValue1 = new KeyValue();
        keyValue1.put(testImage.getLocation(), flagCva);
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
    System.out.println("CVA");
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
}
