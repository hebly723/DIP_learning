package com.graduate.tool;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class LSH {
    private int dimention; //维度大小，例如对于sift特征来说就是128
    private int max; //所需向量中元素可能的上限，譬如对于RGB来说，就是255
    private int hashCount; //哈希表的数量，用于更大程度地削减false positive
    //LSH随机选取的采样位数，该值越小，则近似查找能力越大，但相应的false positive也越大；若该值等于size，则为由近似查找退化为精确匹配
    private int bitCount;
    private int size; //转化为01字符串之后的位数，等于max乘以dimensions
    private int[][] hashFamily; //LSH哈希族，保存了随机采样点的INDEX
    //VectorComparator comparator;
    //private HashMap<String, ArrayList<IdentifiedVector>> map;
    private HashMap<String, ArrayList<String>> map;

    public void SimpleLSH(int dimention, int max, int hashCount, int bitCount) {
        this.dimention = dimention;
        this.max = max;
        this.hashCount = hashCount;
        this.bitCount = bitCount;
        this.size = this.dimention * this.max;
        this.hashFamily = new int[hashCount][bitCount];
        // map = new HashMap<String, ArrayList<IdentifiedVector>>();
        map = new HashMap<String, ArrayList<String>>();
//        this.comparator = new VectorComparator(new int[]{0});
    }

    //生成随机的投影点
    private void generataHashFamily()
    {
        Random rd = new Random();
        for (int i = 0; i < hashCount; i++)
        {
            for (int j = 0; j < bitCount; j++)
            {
                hashFamily[i][j] = rd.nextInt(size);
            }
        }
    }

    //将向量转化为二进制字符串，比如元素的最大范围255，则元素65就被转化为65个1以及190个0
    private int[] unAray ( int[] data){
        int unArayData[] = new int[size];
        for (int i = 0; i < data.length; i++)
        {
            for (int j = 0; j < data[i]; j++)
            {
                unArayData[i * max + j] = 1;
            }
        }
        return unArayData;
    }

    //将向量映射为LSH中的key
    private String generateHashKey ( int[] list, int hashNum){
        StringBuilder sb = new StringBuilder();
        int[] tempData = unAray(list);
        int[] hashedData = new int[bitCount];
        //首先将向量转为二进制字符串
        for (int i = 0; i < bitCount; i++) {
            hashedData[i] = tempData[hashFamily[hashNum][i]];
        sb.append(hashedData[i]);
        // System.out.print(hashedData[i]);
    }

        //再用常规hash函数比如MD5对key进行压缩
        MessageDigest messageDigest = null;
        try {
                messageDigest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
        }

        byte[] binary = sb.toString().getBytes();
        byte[] hash = messageDigest.digest(binary);
        String hashV = byte2hex(hash);
//                MathUtils.getHexDigest(hash);
        return hashV;
    }

    //将向量映射为LSH中的key，并保存至map中
    private void generateHashMap (String id,int[] vercotr){
        for (int j = 0; j < hashCount; j++) {
            String key = generateHashKey(vercotr, j);
            ArrayList<String> l;
            if (map.containsKey(key)) {
                l = map.get(key);
            } else {
                l = new ArrayList<String>();
            }
            l.add(id);
            map.put(key, l);
        }
    }

    // 查询与输入向量最接近（海明空间）的向量
    public Set<String> query ( int[] data){
        // Set<IdentifiedVector> result = new HashSet<IdentifiedVector>();
        Set<String> result = new HashSet<String>();
        for (int j = 0; j < hashCount; j++) {
            String key = generateHashKey(data, j);
            if (map.containsKey(key)) {
                result.addAll(map.get(key));
            }
        }
        return result;
    }

    public String byte2hex(byte[] b) //二行制转字符串
    {
        String hs="";
        String stmp="";
        for (int n=0;n<b.length;n++)
        {
            stmp=(Integer.toHexString(b[n] & 0XFF));
            if (stmp.length()==1) hs=hs+"0"+stmp;
            else hs=hs+stmp;
            if (n<b.length-1)  hs=hs+":";
        }
        return hs.toUpperCase();
    }
}
