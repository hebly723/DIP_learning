package com.graduate.entity;

import org.nd4j.linalg.api.ndarray.INDArray;

public class DV {
    private Matrix matrix;
    private double d;
    public DV(Matrix matrix, double d){
        this.matrix = matrix;
        this.d = d;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    public double getD() {
        return d;
    }

    public void setD(double d) {
        this.d = d;
    }
    public static INDArray[] getMatrix(DV[] dvs){
        INDArray[] matrices = new INDArray[dvs.length];
        for (int i=0; i<dvs.length; i++)
        {
            matrices[i] = dvs[i].getMatrix().getMatrix();
        }
        return matrices;
    }

    @Override
    public String toString() {
        return "DV{" +
                "matrix=" + matrix +
                ", d=" + d +
                '}';
    }
}
