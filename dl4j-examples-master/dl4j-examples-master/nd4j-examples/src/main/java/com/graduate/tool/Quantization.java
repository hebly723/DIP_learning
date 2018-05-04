package com.graduate.tool;

import com.graduate.entity.Matrix;

public class Quantization {
    private Matrix matrix;
    private int dimention;
    private int N;
    public Quantization(Matrix matrix){
        this.matrix = matrix;
        dimention = matrix.getRowDimension();
        N = matrix.getColumnDimension();
    }

    public int[] getHash(){
        double[] doubles = this.variance();
        return getValue(doubles);
    }

    public byte[] getByteHash(){
        double[] doubles = this.variance();
        return getByteValue(doubles);
    }

    private byte[] getByteValue(double[] doubles) {
        double u = 0;
        int dl = doubles.length;
        for (int i=0; i<dl;i++)
        {
            u += doubles[i];
        }
        u /= dl;
        byte[] booleans = new byte[8];
//        System.out.println("u="+u);
        for (int i=0; i<8;i++)
        {
            int i1 = QByte(doubles[i*8+1], u);
            int i2 = QByte(doubles[i*8+2], u);
            int i3 = QByte(doubles[i*8+3], u);
            int i4 = QByte(doubles[i*8+4], u);
            int i5 = QByte(doubles[i*8+5], u);
            int i7 = QByte(doubles[i*8+7], u);
            int i6 = QByte(doubles[i*8+6], u);
            int i0 = QByte(doubles[i*8+0], u);
            booleans[i] = (byte)(i0+
                2*i1+
                4*i2+
                8*i3+
                16*i4+
                32*i5+
                64*i6+
                128*i7);
        }
        return booleans;
    }

    private byte QByte(double i, double mean) {
        if (i>mean)
            return 1;
        else
            return 0;
    }

    public double[] variance(){
        double[] doubles = getU();
        double[] variances = new double[N];
        for (int i=0; i<N; i++)
        {
            double sum = 0;
            for (int j=0; j<dimention;j++)
            {
                double d = matrix.get(j, i) - doubles[i];
//                System.out.println("d2="+d*d);
                sum += d*d;
            }
//            System.out.println("sum+"+sum);
            variances[i] = sum/(dimention-1);
        }
        return variances;
    }
    public double[] getU(){
        double[] doubles = new double[N];
        for (int i=0; i<N;i++)
        {
            double sum = 0;
            for (int j=0; j<dimention;j++)
            {
//                System.out.println("U"+"("+i+","+j+")"+matrix.getMatrix().get(j, i));
                sum+=matrix.get(j, i);
            }
            doubles[i] = sum/(double)dimention;
//            System.out.println("doubles["+i+"]"+doubles[i]);
        }
        return doubles;
    }

    public int[] getValue(double[] doubles){
        double u = 0;
        for (int i=0; i<doubles.length;i++)
        {
            u += doubles[i];
        }
        u /= doubles.length;
        int[] booleans = new int[doubles.length];
//        System.out.println("u="+u);
        for (int i=0; i<doubles.length;i++)
        {
            booleans[i] = Q(doubles[i], u);
        }
        return booleans;
    }

    public int Q(double i, double mean){
//        System.out.println("i="+i);
        if (i>mean)
            return 1;
        else
            return 0;
    }
}
