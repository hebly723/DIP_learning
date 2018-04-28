package com.graduate.test.dataTest;

import com.graduate.dao.HashcvaDao;
import com.graduate.dao.HashphaDao;
import com.graduate.dao.ImageDao;
import com.graduate.entity.*;
import com.graduate.test.BaseTest;
import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.opencv.imgcodecs.Imgcodecs.imread;

/**
 * 图片来自USC  SIPI 图像库中aerials
 */
public class refresh extends BaseTest{
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
    @Autowired
    private HashphaDao hashphaDao;
    @Autowired
    private HashcvaDao hashcvaDao;
    @Autowired
    private ImageDao imageDao;
    private final String path = "/home/hebly723/1.jpeg";
//    @Before
//    public void init(){
//        algorithm = new CVA();
//    }
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
    /**
     * 添加图片
     * 图片来自USC  SIPI 图像库中aerials
     */
    @Test
    public void addSource()
    {
        System.out.println("getCvaPeople3");
        String prefix = "/home/hebly723/下载/aerials";
        File file = new File(prefix);
        File[] files = file.listFiles();
        for (File file1 : files)
        {
            Image image = new Image();
            image.setLocation(file1.getAbsolutePath());
            image.setDetail(file1.getName());
            imageDao.insertSelective(image);
            ImageExample imageExample = new ImageExample();
            ImageExample.Criteria criteria = imageExample.createCriteria();
            criteria.andLocationEqualTo(file1.getAbsolutePath());
            int id = imageDao.selectByExample(imageExample).get(0).getId();

            Mat mat = imread(file1.getAbsolutePath());

//            PHA pha = new PHA();
//            HashPack hashPack1 = new HashPack();
//            System.out.println(file1.getAbsolutePath());
//            String phaStr = pha.hashString(mat);
//            hashPack1.setHashPha(phaStr);
//
            HashPack hashPack = new HashPack(mat);
//
//            hashphaDao.insertSelective(hashPack.outPha(id));
            hashcvaDao.insertSelective(hashPack.outCva(id));
//            hashphaDao.insertSelective(hashPack1.outPha(id));
        }
    }
    @Test
    public void back(){
        System.out.println("getCvaPeople3");
        String prefix = "/home/hebly723/下载/aerials";
        File file = new File(prefix);
        File[] files = file.listFiles();
        for (File file1 : files)
        {
            Image image = new Image();
            image.setLocation(file1.getAbsolutePath());
            image.setDetail(file1.getName());
            ImageExample imageExample = new ImageExample();
            ImageExample.Criteria criteria = imageExample.createCriteria();
            criteria.andLocationEqualTo(file1.getAbsolutePath());
            try
            {
                int id = imageDao.selectByExample(imageExample).get(0).getId();



                Mat mat = imread(file1.getAbsolutePath());

                HashPack hashPack = new HashPack(mat);

                HashphaExample hashphaExample = new HashphaExample();
                HashcvaExample hashcvaExample = new HashcvaExample();
                hashcvaExample.createCriteria().andIdEqualTo(id);
                hashphaExample.createCriteria().andIdEqualTo(id);
                hashphaDao.deleteByExample(hashphaExample);
                hashcvaDao.deleteByExample(hashcvaExample);
                imageDao.deleteByExample(imageExample);
            }catch (Exception e) {}
        }
    }
    @Test
    public void selectDump(){
        List<Image> images = hashphaDao.selectDump();
        List<HashPack> hashPacks = new ArrayList<HashPack>();
        for (Image image : images)
        {
            HashPack hashPack = new HashPack();
            HashphaExample hashphaExample = new HashphaExample();
            hashphaExample.createCriteria().andIdEqualTo(image.getId());
            hashPack.setHashPha(hashphaDao.selectByExample(hashphaExample).get(0).getHash());
            HashcvaExample hashcvaExample = new HashcvaExample();
            hashcvaExample.createCriteria().andIdEqualTo(image.getId());
            hashPack.setHashCva(hashcvaDao.selectByExample(hashcvaExample).get(0).getHash());

            hashPacks.add(hashPack);
        }
        int i=0;
        for (HashPack hashPack:hashPacks)
        {
            System.out.println(images.get(i)+""+hashPack);
            i++;
        }
    }

    @Test
    public void singleCheck()
    {
        String str = "/home/hebly723/下载/aerials/3.2.25.tiff";
        Mat mat = imread(str);
        HashPack ans = new HashPack(mat);

        List<Image> image = hashphaDao.selectHash(ans);
        for (Image image1 : image)
        {
            System.out.println(image1);
        }
    }
}
