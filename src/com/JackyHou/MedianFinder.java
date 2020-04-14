package com.JackyHou;


import java.util.ArrayList;

// 41. 数据流中的中位数
public class MedianFinder {
    ArrayList<Integer> list;
    /** initialize your data structure here. */
    public MedianFinder() {
        list = new ArrayList<>();
    }

    public void addNum(int num) {
        if (list.isEmpty()) {
            list.add(num);
        }
        else if (num >= list.get(list.size() - 1)) {
            list.add(num);
        }
        else {
            boolean first_time = true;
            ArrayList<Integer> temp = new ArrayList<>();
            for (Integer integer : list) {
                if (integer >= num && first_time) {
                    temp.add(num);
                    first_time = false;
                }
                temp.add(integer);
            }
            list = temp;
        }
    }

    public double findMedian() {
        System.out.println(list.toString());
        if (list.size() == 0) return 0;
        if (list.size() % 2 == 1) {
            return (double)list.get(list.size() / 2);
        }
        else {
            return ((double)(list.get(list.size() / 2 - 1) + list.get(list.size() / 2))) / 2;
        }
    }
}
