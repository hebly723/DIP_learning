package com.graduate.entity;

import com.graduate.algorithm.impl.CVA;
import org.opencv.core.Mat;

public class HashPack {
    private byte[] hashCva;
    private byte[] hashPha;

    public HashPack(){

    }

    public HashPack(Mat mat){
        hashCva = (new CVA()).hashString(mat);
//        hashPha = (new PHA()).hashString(mat);
    }

    public byte[] getHashCva() {
        return hashCva;
    }

    public void setHashCva(byte[] hashCva) {
        this.hashCva = hashCva;
    }

    public byte[] getHashPha() {
        return hashPha;
    }

    public void setHashPha(byte[] hashPha) {
        this.hashPha = hashPha;
    }

    public Hashcva outCva(int id){
        Hashcva cva = new Hashcva();
        cva.setHash(this.hashCva);
        cva.setId(id);
        return cva;
    }
    public Hashpha outPha(int id){
        Hashpha pha = new Hashpha();
        pha.setHash(this.hashPha);
        pha.setId(id);
        return pha;
    }

    @Override
    public String toString() {
        return "HashPack{" +
                "hashCva='" + hashCva + '\'' +
                ", hashPha='" + hashPha + '\'' +
                '}';
    }
}
