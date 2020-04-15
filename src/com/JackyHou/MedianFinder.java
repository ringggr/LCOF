package com.JackyHou;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

// 41. 数据流中的中位数
public class MedianFinder {
    PriorityQueue<Integer> maxhead, minhead;
    /** initialize your data structure here. */
    public MedianFinder() {
        minhead = new PriorityQueue<>();
        maxhead = new PriorityQueue<>((a, b) -> b - a);

    }

    public void addNum(int num) {
        if (minhead.size() <= maxhead.size()) {
            maxhead.add(num);
            minhead.add(maxhead.poll());
        }
        else {
            minhead.add(num);
            maxhead.add(minhead.poll());
        }
    }

    public double findMedian() {
        if (maxhead.size() == minhead.size() && maxhead.size() != 0) {
            return (double)(minhead.peek() + maxhead.peek()) / 2;
        }
        else if (maxhead.size() > minhead.size()) {
            return (double)maxhead.peek();
        }
        else {
            return minhead.isEmpty() ? Double.NaN : minhead.peek();
        }
    }
}
