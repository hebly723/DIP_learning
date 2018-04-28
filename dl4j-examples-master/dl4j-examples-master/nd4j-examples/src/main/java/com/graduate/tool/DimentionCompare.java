package com.graduate.tool;

import java.util.Comparator;

public class DimentionCompare implements Comparator<KdTreeNode> {
        int dimention;
        boolean flag = true;
        public DimentionCompare (int dimention){
            this.dimention = dimention;
            this.flag = true;
        }
        public DimentionCompare (int dimention, boolean flag){
            this.dimention = dimention;
            this.flag = flag;
        }
        @Override
        public int compare(KdTreeNode o1, KdTreeNode o2) {
            double m = o1.array[dimention] - o2.array[dimention];
            if (flag) {
                if (m > 0)
                    return 1;
                if (m == 0)
                    return 0;
                return -1;
            }else{
                if (m > 0)
                    return -1;
                if (m == 0)
                    return 0;
                return 1;
            }
        }
    }
