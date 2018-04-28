package com.graduate.test;

import com.graduate.tool.DimentionCompare;
import com.graduate.tool.KDTree;
import com.graduate.tool.KdTreeNode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static com.graduate.tool.KDTree.firstS;
import static org.junit.Assert.*;

public class KDTreeTest {

    KdTreeNode[] kdTreeNodes;
    @Before
    public void setUp() throws Exception {
        double[][] doubles = {{2,3},{5,4},{9,6},{4,7},{8,1},{7,2}};
        kdTreeNodes = new KdTreeNode[doubles.length];
        for (int i=0;i<doubles.length;i++)
        {
            kdTreeNodes[i] = new KdTreeNode();
            kdTreeNodes[i].setArray(doubles[i]);
            kdTreeNodes[i].setLeft(null);
            kdTreeNodes[i].setRight(null);
        }
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void sort() {
        KDTree kdTree = new KDTree(kdTreeNodes);
        Arrays.sort(kdTreeNodes, new DimentionCompare(kdTree.getDimention()[0]));
        System.out.println(Arrays.toString(kdTree.getDimention()));
//        for (int i=0;i<kdTreeNodes.length;i++) {
//            System.out.print(Arrays.toString(kdTreeNodes[i].getArray())+"\t");
//        }
        int m = (kdTreeNodes.length+1)/2;
        KdTreeNode head = kdTreeNodes[m];



        kdTree.sort(head,kdTreeNodes,0,m-1,kdTree.getDimention()[1],true);
        kdTree.sort(head,kdTreeNodes,m+1,kdTreeNodes.length-1, kdTree.getDimention()[1],false);
//        for (int i=0; i<kdTreeNodes.length;i++)
//        {
//            for (double d : kdTreeNodes[i].getArray())
//            {
//                System.out.print(d+"\t");
//            }
//            System.out.print(kdTreeNodes[i].getLeft()+"\t");
//            System.out.print(kdTreeNodes[i].getRight()+"\t");
//            System.out.println(kdTreeNodes[i]);
//        }
//        System.out.println(head);
//        for (double d : head.getArray())
//        {
//            System.out.print(d+"\t");
//
//        }
//        System.out.print(head.getLeft()+"\t");
//        System.out.print(head.getRight()+"\t");
        System.out.println(head);
    }
}