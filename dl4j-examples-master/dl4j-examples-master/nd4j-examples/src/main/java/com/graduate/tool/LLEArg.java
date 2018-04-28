package com.graduate.tool;

import com.graduate.entity.Matrix;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.Arrays;
import java.util.List;

import static com.graduate.entity.Pixel.zero;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * LLE降维相关算法封装
 */
public class LLEArg {
    private int dimention;
    private int neighborhoodNumber;
    private double[][] distanceArray;
    public LLEArg(Matrix matrix){
        dimention = 2;
        neighborhoodNumber = 2;
        Matrix[] matrices = matrix.divideCol();
        Matrix[] answer;
        double[][] array = new double[matrices.length][matrices.length];
        for (int i=0; i<matrices.length; i++)
        {
            array[i][i] = -1;
            for (int j=i+1;j<matrices.length;j++)
            {
                double m = distance(matrices[i], matrices[j]);
                array[i][j] = m;
                array[j][i] = m;
            }
            this.distanceArray = array;
        }
    }
    /**
     * 求与给定列向量相邻的列向量
     * @param matrix
     * @param i 该列向量在列向量数组中的下标
     * @return
     */
    public Matrix[] neighborhood( Matrix matrix, int n){
        KdTreeNode[] kdTreeNodes = new KdTreeNode[distanceArray[n].length];
        for (int i=0; i<distanceArray[n].length;i++){
            kdTreeNodes[i] = new KdTreeNode();
            double[] array = new double[2];
            array[0] = i;
            array[1] = distanceArray[n][i];
            kdTreeNodes[i].setArray(array);
        }
        Arrays.sort(kdTreeNodes, new DimentionCompare(1));
//        System.out.println(Arrays.toString(kdTreeNodes));
        Matrix[] answer = new Matrix[neighborhoodNumber];
        for (int i = 0;i < neighborhoodNumber; i++)
        {
            answer[i] = matrix.divideCol()[(int)Math.ceil(kdTreeNodes[i+1].array[0])%matrix.getColumnDimension()];
        }
//        answer = new Matrix[neighborhoodNumber];
//        int begin = (i-neighborhoodNumber+matrices.length)%matrices.length;
//        int k=0;
//        while (k<answer.length)
//        {
//            if (begin!=i)
//            {
//                answer[k] = matrices[begin];
//                k++;
//            }
//            begin=(begin+1)%matrices.length;
//        }
        return answer;
    }

    public double distance(Matrix matrix1, Matrix matrix2){
        double sum = 0;

        return matrix1.getMatrix().distance2(matrix2.getMatrix());
    }

    /**
     * 求协方差矩阵
     * @param matrix
     * @param index
     * @return
     */
    public Matrix getZ(Matrix matrix, int index){
//        System.out.println("matrixZ\n"+matrix);
        Matrix[] neighbor = neighborhood(matrix, index).clone();

        Matrix[] answer = new Matrix[neighbor.length];

        for (int i=0; i<neighbor.length;i++)
        {
            answer[i] = matrix.divideCol()[index].plus(neighbor[i].multi(-1));
            neighbor[i].multi(-1);
        }

        Matrix oz = Matrix.merge(answer);

        Matrix result = oz.reverse().multi(oz);

        return result;
    }

    /**
     * 求权重系数
     * @param matrix
     * @param index
     */
    public Matrix getWI(Matrix matrix, int index){
        Matrix z = getZ(matrix, index);
        Matrix wU = (z.getInverse().multi(Matrix.initCol(neighborhoodNumber)));

        Matrix wD = (Matrix.initCol(neighborhoodNumber).reverse().multi(
                        z.getInverse().multi(Matrix.initCol(neighborhoodNumber))));

        double k = wD.get(0,0);

        if (k==0)
            k = zero;

//        System.out.println("getWI"+wD);
        return wU.multi(1/k);
    }

    /**
     * 求权重系数矩阵
     * @param matrix
     */
    public Matrix getW(Matrix matrix){
        Matrix[] matrix1 = new Matrix[matrix.getColumnDimension()];
        for (int i=0; i<matrix.getColumnDimension();i++)
        {
            matrix1[i] = getWI(matrix, i);
        }
//        System.out.println("getW");
        return Matrix.merge(matrix1);
    }

    /**
     * 计算过渡矩阵
     * @param matrix
     * @return
     */
    public Matrix getM(Matrix matrix){
        Matrix w = getW(matrix);
        Matrix i = Matrix.initI(w.getRowDimension(),
                w.getColumnDimension());
//        w.multi(-1);
        Matrix m = i.plus(w.multi(-1));
//        System.out.println(m);
        Matrix matrix1 = (m.reverse()).multi(m);
//        System.out.println("N\n"+matrix1);
//        System.out.println("getM");
        return matrix1;
    }

    public Matrix getAnswer(Matrix matrix){
//        System.out.println(1);
        Matrix matrix1 = getM(matrix);
//        System.out.println(matrix1.getV());
//        System.out.println(2);
        Matrix matrix3 = matrix1.getV();
//        System.out.println(2.5);
        matrix3 = matrix3.sort();
//        System.out.println(3);
//        System.out.println(matrix3);

        List<Matrix> indArrayList = matrix3.divideColArrayList();
        for (int j = indArrayList.size()-1; j>=dimention; j--)
        {
            indArrayList.remove(j);
        }
        indArrayList.remove(0);
//        Jama.Matrix matrix2 = matrix3.getMatrix().getMatrix(0,matrix3.getRowDimension()-1,
//                1, dimention).copy();
//        System.out.println(4);

//        System.out.println("getAnswer");
        Matrix matrix2 = Matrix.mergeArrayList(indArrayList).reverse();
        return matrix2;
    }

    public int getDimention() {
        return dimention;
    }

    public void setDimention(int dimention) {
        this.dimention = dimention;
    }

    public int getNeighborhoodNumber() {
        return neighborhoodNumber;
    }

    public void setNeighborhoodNumber(int neighborhoodNumber) {
        this.neighborhoodNumber = neighborhoodNumber;
    }
}
