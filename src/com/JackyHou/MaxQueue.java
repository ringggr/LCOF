package com.JackyHou;

import java.util.*;

// 面试题59 - II. 队列的最大值
class MaxQueue {

    private Queue<Integer> a;
    private Deque<Integer> b;

    public MaxQueue() {
        a = new LinkedList<>();
        b = new LinkedList<>();
    }

    public int max_value() {
        return b.isEmpty() ? -1 : b.peekFirst();
    }

    public void push_back(int value) {
        a.offer(value);
        while (!b.isEmpty() && b.peekLast() < value) {
            b.pollLast();
        }
        b.offerLast(value);
    }

    public int pop_front() {
        if (a.isEmpty()) {
            return -1;
        }
        else {
            if (!b.isEmpty() && a.peek().equals(b.peekFirst())) {
                b.pollFirst();
            }
            return a.poll();
        }
    }
}

/**
 * Your MaxQueue object will be instantiated and called as such:
 * MaxQueue obj = new MaxQueue();
 * int param_1 = obj.max_value();
 * obj.push_back(value);
 * int param_3 = obj.pop_front();
 */