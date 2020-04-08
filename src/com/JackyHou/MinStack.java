package com.JackyHou;

import java.util.Stack;

// 30. 包含min函数的栈
public class MinStack {
    /** initialize your data structure here. */
    Stack<Integer> a, b;
    public MinStack() {
        a = new Stack<>();
        b = new Stack<>();
    }

    public void push(int x) {
        a.push(x);
        if (b.empty() || b.peek() >= x) {
            b.push(x);
        }
    }

    public void pop() {
        if (a.pop().equals(b.peek())) {
            b.pop();
        }
    }

    public int top() {
        return a.peek();
    }

    public int min() {
        return b.peek();
    }
}
