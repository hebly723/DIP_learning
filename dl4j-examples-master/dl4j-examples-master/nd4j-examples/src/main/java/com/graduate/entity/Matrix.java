package com.graduate.entity;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.eigen.Eigen;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.inverse.InvertMatrix;
import org.opencv.core.Mat;

import java.util.*;

/**
 * 矩阵及其运算的封装
 */
public class Matrix implements Cloneable {
//    private int width;
//    private int height;
//    private List<List<Double>> matrixList;
    private INDArray matrix;

    public Matrix(INDArray matrix){
        this.matrix = matrix;
    }

    public Matrix(int w ,int h)
    {
        this.matrix = Nd4j.create(h, w);
    }

    public Matrix(ArrayList<double[]> arrayList){
        INDArray[] indArrays = new INDArray[arrayList.size()];
        for (int i=0; i<indArrays.length; i++)
        {
            indArrays[i] = Nd4j.create(arrayList.get(i));
        }
//        double[][] doubles = (double[][])arrayList.toArray();
        this.matrix = Nd4j.vstack(indArrays);
//        this.matrix.transposei();
    }

    public Matrix(double[][] matrices)
    {
        this.matrix = Nd4j.create(matrices);
//        this.matrix.transposei();
    }

//    public Matrix(List<List<Double>> lists){
//        matrix = Nd4j.create(lists.toArray());
//        for (int i=0; i<lists.get(0).size();i++)
//        {
//            for (int j=0; j<lists.size(); j++)
//            {
//                matrix.set(i,j,lists.get(j).get(i));
//            }
//        }
//    }

    /**
     * 求矩阵的转置
     */
    public Matrix reverse(){

        return new Matrix(this.matrix.transpose());
    }

    /**
     * 将矩阵所有元素取负
     */
    public Matrix multi(double k){
        return new Matrix(this.matrix.mul(k));
    }


    /**
     * 矩阵相乘
     * @param matrix 乘矩阵
     */
    public Matrix multi(Matrix matrix){
        return new Matrix(this.matrix.mmul(matrix.getMatrix()));
    }

    /**
     * 矩阵相加
     * @param matrix
     */
    public Matrix plus(Matrix matrix){
        Matrix matrix1;
        try {
            matrix1 = new Matrix(this.matrix.add(matrix.getMatrix()));
        }catch (Exception e)
        {
            System.out.println(this.matrix);
            System.out.println(matrix.getMatrix());
            matrix1 = new Matrix(this.matrix.add(matrix.getMatrix()));
        }
        return matrix1;
    }

    /**
     * 矩阵分割为一个个列向量
     * @return 矩阵数组
     */
    public Matrix[] divideCol(){
        Matrix[] matrices = new Matrix[this.getColumnDimension()];
        for (int i=0; i<matrices.length;i++)
        {
//            Jama.Matrix matrix1 = this.getMatrix().
//                    getMatrix(0,this.getMatrix().getRowDimension()-1,i,i).copy();
            matrices[i] = new Matrix(matrix.getColumns(i));
        }
        return matrices;
    }
    /**
     * 矩阵分割为一个个列向量
     * @return 矩阵数组
     */
    public List<Matrix> divideColArrayList(){
        List<Matrix> matrices = new ArrayList();
        for (int i=0; i<matrix.columns();i++)
        {
            matrices.add(new Matrix(matrix.getColumns(i)));
        }
        return matrices;
    }

    /**
     * 矩阵分割为一个个行向量
     * @return 矩阵数组
     */
    public Matrix[] divideRow(){
        Matrix[] matrices = new Matrix[this.getRowDimension()];
        for (int i=0; i<matrices.length;i++)
        {
            matrices[i] = new Matrix(matrix.getRow(i));
        }
        return matrices;
    }

    /**
     * 矩阵分割为一个个行向量
     * @return 矩阵数组
     */
    public List<Matrix> divideRowArrayList(){
        List<Matrix> matrices = new ArrayList();
        for (int i=0; i<this.getRowDimension();i++)
        {
            matrices.add(new Matrix(matrix.getRow(i)));
        }
        return matrices;
    }
    /**
     * 列向量数组合并为一个矩阵
     * @param matrices
     * @return
     */
    public static Matrix merge(Matrix[] matrices){
        INDArray matrix;
        if (matrices.length == 0)
            return null;
        if (matrices[0].getRowDimension()>1)
        {
            INDArray[] indArrays = new INDArray[matrices.length];
            for (int i=0; i<matrices.length;i++)
                indArrays[i] = matrices[i].getMatrix();
            matrix = Nd4j.hstack(indArrays);
        }
        else
        {
            INDArray[] indArrays = new INDArray[matrices.length];
            for (int i=0; i<matrices.length;i++)
                indArrays[i] = matrices[i].getMatrix();
            matrix = Nd4j.vstack(indArrays);
        }
        return new Matrix(matrix);
    }
    /**
     * 列向量数组合并为一个矩阵
     * @param matrices
     * @return
     */
    public static Matrix mergeArrayList(List<Matrix> matrices){
        INDArray matrix;
        if (matrices.size() == 0)
            return null;
        if (matrices.get(0).getRowDimension()>1)
        {
            INDArray[] indArrays = new INDArray[matrices.size()];
            for (int i=0; i<matrices.size();i++)
                indArrays[i] = matrices.get(i).getMatrix();
            matrix = Nd4j.hstack(indArrays);
        }
        else
        {
            INDArray[] indArrays = new INDArray[matrices.size()];
            for (int i=0; i<matrices.size();i++)
                indArrays[i] = matrices.get(i).getMatrix();
            matrix = Nd4j.vstack(indArrays);
        }
        return new Matrix(matrix);
    }

    /**
     * 矩阵求逆
     * @return
     */
    public Matrix getInverse(){
//        System.out.println(matrix.det());
        if (matrix.rank()>matrix.columns()) {
            return  new Matrix(InvertMatrix.invert(matrix.dup(), false));
        }else{
            Matrix i = Matrix.initI(this.getRowDimension(), this.getColumnDimension());
            return new Matrix(InvertMatrix.invert((i.plus(this)).getMatrix().dup(), false));
        }
    }

    /**
     * 矩阵行列式值计算
     * @return
     */
    public double getValue()//计算n阶行列式（N=n-1）
    {
        Jama.Matrix matrix = new Jama.Matrix(this.matrix.dup().toDoubleMatrix());
        return matrix.det();
    }

    /**
     * 形成k维全一列向量
     * @param k
     * @return
     */
    public static Matrix initCol(int k)
    {
        INDArray indArray = Nd4j.ones(k);
        return new Matrix(indArray.transposei());
    }

    /**
     * 求矩阵特征值特征向量
     * @return
     */
    public Jama.EigenvalueDecomposition getSVD(){
        return new Jama.Matrix(this.matrix.dup().toDoubleMatrix()).eig();

    }

    public double[] getD(){
        Jama.Matrix matrix = this.getSVD().getD();
        double[] doubles = new double[matrix.getColumnDimension()];
        for (int i=0; i<doubles.length; i++)
        {
            doubles[i] = matrix.get(i, i);
        }
        return doubles;
    }
//
//    public Matrix round(){
//        double[][] doubles = matrix.getArray();
//        for (int i=0; i<doubles.length;i++)
//        {
//            for (int j=0; j<doubles[i].length;j++)
//            {
////                if (Double.isNaN(doubles[i][j])){
////                    doubles[i][j] = 1.0;
////                }else
//                    doubles[i][j] = roundValue(doubles[i][j],2);
//            }
//        }
//        return this;
//    }
//
//    public static double roundValue(double f, int newScale){
////        try {
//            BigDecimal b = new BigDecimal(new Double(f).toString());
//            double f1 = b.setScale(newScale, BigDecimal.ROUND_HALF_UP).doubleValue();
//            return f1;
////        }catch (Exception e)
////        {
////            System.out.println("出错"+f);
////        }
////        return 0;
//    }

    /**
     * 按特征值绝对值排序
     * @Return
     */
    public Matrix sort(){
        double[] doubles = this.getD();
        Matrix[] matrices = this.getV().divideCol();
//        System.out.println(Matrix.merge(matrices));
//        for (int i=0;i<doubles.length;i++)
//            System.out.println(doubles[i]);
        Map<Double, Matrix> map = new HashMap();
        DV[] dvs = new DV[doubles.length];
        for (int i=0; i<doubles.length;i++)
        {
            dvs[i] = new DV(matrices[i],doubles[i]);
        }
        Arrays.sort(dvs, new DVComparator());

        return Matrix.merge(DV.getMatrix(dvs));
    }

    public Matrix getV(){
//        System.out.println(1);
        Jama.Matrix matrix = this.getSVD().getV();
//        System.out.println(2);
//        Jama.Matrix matrix1 = new Jama.Matrix(matrix.getRowDimension(),
//                matrix.getColumnDimension());
//        System.out.println(3);
//        for (int j=0; j<matrix.getColumnDimension();j++){
//            for (int i=0; i<matrix.getRowDimension(); i++)
//            {
//                double d = matrix.get(i, j);
//                matrix1.set(i,j,d);
//            }
//        }
//        System.out.println(4);
        return new Matrix(matrix.getArray());
    }

    public Matrix removeRow(int index){
//        int k = 0;
        List<Matrix> matrices = this.divideRowArrayList();

        matrices.remove(index);
        Matrix matrix = Matrix.mergeArrayList(matrices);

        return matrix;
    }

    public Matrix removeCol(int index){
        List<Matrix> matrices = this.divideColArrayList();

        matrices.remove(index);
        Matrix matrix = Matrix.mergeArrayList(matrices);

        return matrix;
    }

    /**
     * 转换为数组变量
     * @return
     */
    public double[][] toArray(){
        return matrix.dup().toDoubleMatrix();
    }

    public INDArray toINDArray(){
        return matrix.dup();
    }

    /**
     * 形成k维单位矩阵
     * @param w
     * @param h
     * @return
     */
    public static Matrix initI(int w, int h)
    {
        double[][] matrix = new double[h][w];

        for (int i=0;i<h&&i<w;i++)
        {
            matrix[i][i] = 1;
        }

        Matrix matrix1 = (new Matrix(matrix)).reverse();
        return matrix1;
    }

    public INDArray getMatrix() {
        return matrix;
    }

    public void setMatrix(INDArray matrix) {
        this.matrix = matrix;
    }

    public int getRowDimension(){
        return this.getMatrix().rows();
    }

    public int getColumnDimension(){
        return this.getMatrix().columns();
    }

    public double get(int i,int j)
    {
        return matrix.getDouble(i,j);
    }

    public void set( int i, int j, double d)
    {
        matrix.put(i,j,d);
    }

    public static boolean isZeroMatrix(Mat matrix)
    {
        boolean f = true;
        for (int i=0; i<matrix.rows();i++)
        {
            for (int j=0; j<matrix.cols();j++)
            {
                if (matrix.get(i,j)[0] < 220||matrix.get(i,j)[1] < 220||matrix.get(i,j)[2] < 220)
                    f = false;
            }
        }
        return f;
    }
    public static boolean isZeroMatrix(Matrix matrix)
    {
        boolean f = true;
        double d = matrix.get(0,0);
        for (int i=0; i<matrix.getRowDimension();i++)
        {
            for (int j=0; j<matrix.getColumnDimension();j++)
            {
                if (matrix.get(i,j)!=0)
                {
                    if (matrix.get(i,j)>0.01)
                        if ((matrix.get(i,j)>1.25*d||matrix.get(i,j)<0.75*d))
                        f = false;
                }

            }
        }
        return f;
    }

    public boolean checkMatrix(){
        return isZeroMatrix(this.divideCol()[0])||
                isZeroMatrix(this.divideCol()[this.getColumnDimension()-1])||
                isZeroMatrix(this.divideRow()[0])||
                isZeroMatrix(this.divideRow()[this.getRowDimension()-1]);
    }

    @Override
    public String toString() {
        String str =  "Matrix{" +
                "width=" + this.getColumnDimension() +
                ", height=" + this.getRowDimension() +
                ", matrixList="+ System.lineSeparator();
        for (int i=0; i<this.getRowDimension(); i++)
        {
            for (int j=0; j<this.getColumnDimension(); j++)
            {
                str = str + this.get(i,j) + "\t";
            }
            str = str + System.lineSeparator();
        }
        char end = '}';
        str = str + end;
        return str;
    }

    public static Matrix[] MatToMatrix(Mat srcImage){
        Matrix[] matrix = new Matrix[3];
        for (int i=0; i<matrix.length;i++)
            matrix[i] = new Matrix(srcImage.height(), srcImage.width());
        for (int i=0; i<srcImage.height();i++)
        {
            for (int j=0; j<srcImage.width();j++)
            {
                matrix[0].set(i,j,srcImage.get(i, j)[0]);
                matrix[1].set(i,j,srcImage.get(i, j)[1]);
                matrix[2].set(i,j,srcImage.get(i, j)[2]);
            }
        }
        return matrix;
    }

}
