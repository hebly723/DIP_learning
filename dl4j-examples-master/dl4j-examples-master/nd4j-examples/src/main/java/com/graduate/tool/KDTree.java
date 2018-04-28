package com.graduate.tool;

import java.util.Arrays;

public class KDTree {
    private int[] dimention;

    public int[] getDimention() {
        return dimention;
    }

    public KDTree(KdTreeNode[] kdTreeNodes) {
        KdTreeNode[] kdTreeNodes1 = new KdTreeNode[kdTreeNodes[0].array.length];
        for (int i=0; i<kdTreeNodes1.length;i++)
        {
            double[] array = new double[2];
            array[0] = i;
            array[1] = Variance(kdTreeNodes,i);
            kdTreeNodes1[i] = new KdTreeNode();
            kdTreeNodes1[i].setArray(array);
        }
        Arrays.sort(kdTreeNodes1, new DimentionCompare(1,false));
        int[] answer = new int[kdTreeNodes1.length];
        for (int i=0; i<kdTreeNodes1.length;i++)
        {
            answer[i] = (int)kdTreeNodes1[i].array[0];
        }
        System.out.println(Arrays.toString(kdTreeNodes1));
        this.dimention = answer;
    }

    /**
     * 排序，生成下一级节点
     * @param kdTreeNode 上一层节点
     * @param kdTreeNodes 待排序节点整体
     * @param begin 数组起始
     * @param end   数据末
     * @param d     此时的维数
     * @param direction true为左，false为右
     * @return
     */
    public void sort(KdTreeNode kdTreeNode,
                           KdTreeNode[] kdTreeNodes,
                           int begin, int end, int d,
                           boolean direction)
    {
        if (begin == end)
        {
            kdTreeNodes[begin].setRoot(kdTreeNode);
            if (direction)
                kdTreeNode.left = kdTreeNodes[begin];
            else
                kdTreeNode.right= kdTreeNodes[begin];

        }
        else if (begin > end) {

//            System.out.println(kdTreeNode+"___________________--");
        }
        else {
            DimentionCompare dimentionCompare = new DimentionCompare(dimention[d]);
            Arrays.sort(kdTreeNodes, begin, end+1, dimentionCompare);
//            System.out.println(dimentionCompare.dimention);
//            for (int i=begin;i<=end;i++) {
//                System.out.print(Arrays.toString(kdTreeNodes[i].array)+"\t");
//            }
//            System.out.println();
            int m = (begin+end+1)/2;
            KdTreeNode kdTreeNode1 = kdTreeNodes[m];
            kdTreeNode1.setRoot(kdTreeNode);
            if (direction)
                kdTreeNode.left = kdTreeNode1;
            else
                kdTreeNode.right = kdTreeNode1;

//            if (m-1>begin)
                sort(kdTreeNode1, kdTreeNodes,
                        begin, m-1,
                        dimention[(d+1)%kdTreeNode1.array.length],true);
//            if (m+1<end)
                sort(kdTreeNode1, kdTreeNodes,
                        m+1, end,
                        dimention[(d+1)%kdTreeNode1.array.length],false);

        }

    }

    public static void firstS(KdTreeNode node){
        System.out.println(Arrays.toString(node.array));
        if (node.left!=null)
            firstS(node.left);
        if (node.right!=null)
            firstS(node.right);
    }

    public static double Variance(KdTreeNode[] x, int d) {
        int m=x.length;
        double sum=0;
        for(int i=0;i<m;i++){//求和
            sum+=x[i].array[d];
        }
        double dAve=sum/m;//求平均值
        double dVar=0;
        for(int i=0;i<m;i++){//求方差
            dVar+=(x[i].array[d]-dAve)*(x[i].array[d]-dAve);
        }
        return dVar/m;
    }

}
