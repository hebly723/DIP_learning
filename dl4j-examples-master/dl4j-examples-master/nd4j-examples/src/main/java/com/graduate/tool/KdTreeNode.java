package com.graduate.tool;

import java.util.Arrays;

public class KdTreeNode{
        /**
         * 左侧的节点
         */
        KdTreeNode left;
        /**
         * 右侧的节点
         */
        KdTreeNode right;
        /**
         * 向量
         */
        double[] array;
        /**
         * 根节点
         */
        KdTreeNode root;

        public KdTreeNode getRoot() {
                return root;
        }

        public void setRoot(KdTreeNode root) {
                this.root = root;
        }

        public KdTreeNode getLeft() {
                return left;
        }

        public void setLeft(KdTreeNode left) {
                this.left = left;
        }

        public KdTreeNode getRight() {
                return right;
        }

        public void setRight(KdTreeNode right) {
                this.right = right;
        }

        public double[] getArray() {
                return array;
        }

        public void setArray(double[] array) {
                this.array = array;
        }

        @Override
        public String toString() {
                return "KdTreeNode{" +
                        "left=" + left +
                        ", right=" + right +
                        ", array=" + Arrays.toString(array) +
                        '}';
        }
}
