package com.JackyHou;

import java.time.chrono.MinguoDate;
import java.util.*;

public class Spring418 {
    public int minCount(int[] coins) {
        int count = 0;
        for (int coin : coins) {
            count += (coin / 2);
            if (coin % 2 == 1) count++;
        }
        return count;
    }


    int res2 = 0;
    public int numWays(int n, int[][] relation, int k) {
        f(0, 0, n, relation, k);
        return res2;
    }

    private void f(int nk, int npos, int n, int[][] relation, int k) {
        if (nk == k) {
            if (npos == n - 1) {
                res2++;
            }
        }
        else if (nk < k) {
            for (int[] ints : relation) {
                if (npos == ints[0]) {
                    f(nk + 1, ints[1], n, relation, k);
                }
            }
        }
    }

    public int[] getTriggerTime(int[][] increase, int[][] requirements) {
        int[] res = new int[requirements.length];
        Arrays.fill(res, -1);
        for (int i = 1; i < increase.length; i++) {
            increase[i][0] += increase[i - 1][0];
            increase[i][1] += increase[i - 1][1];
            increase[i][2] += increase[i - 1][2];
        }
        for (int i = 0; i < requirements.length; i++) {
            int left = 0, right = increase.length - 1;
            if (requirements[i][0] == 0 && requirements[i][1] == 0 && requirements[i][2] == 0) {
                res[i] = 0;
                continue;
            }
            if (requirements[i][0] > increase[right][0] ||
                    requirements[i][1] > increase[right][1] ||
                    requirements[i][2] > increase[right][2]) {
                continue;
            }
            while (left < right) {
                int mid = (left + right) / 2;
                if (requirements[i][0] <= increase[mid][0] &&
                requirements[i][1] <= increase[mid][1] &&
                requirements[i][2] <= increase[mid][2]) {
                    // mid满足了，再往左边试试有没有更小的，注意要把mid包含，有可能mid就是最小的天数。
                    right = mid;
                }
                else {
                    // mid不满足的话，left设为mid后一天
                    left = mid + 1;
                }
            }
            res[i] = left + 1;  // 加一得天数
        }
        return res;
    }

    public int minJump(int[] jump) {
        boolean[] visited = new boolean[jump.length];
        Queue<Integer> queue = new LinkedList<>();
        int last = 0, res = 0;
        queue.add(0);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int now_pos = queue.poll();
                int next = now_pos + jump[now_pos];
                if (next >= jump.length) {
                    return res + 1;
                }
                if (!visited[next]) {
                    queue.offer(next);
                    visited[next] = true;
                }
                for (int j = last; j < now_pos; j++) {
                    if (!visited[j]) {
                        queue.offer(j);
                        visited[j] = true;
                    }
                }
                // last = now_pos;
                last = Math.max(last, now_pos);
            }
            res++;
        }
        return res;
    }

    // LCP 10. 二叉树任务调度
    public double minimalExecTime(TreeNode root) {
        return getexecTime(root)[0];
    }

    /*
    double[2]:
    double[0] = 当前最少耗时
    double[1] = 串行执行时间
     */
    private double[] getexecTime(TreeNode node) {
        if (node == null) {
            return new double[]{0.0, 0.0};
        }
        double[] exec_left = getexecTime(node.left);
        double[] exec_right = getexecTime(node.right);

        double sum = exec_left[1] + exec_right[1];
        double minTime = Math.max(Math.max(exec_left[0], exec_right[0]), sum / 2) + node.val;

        return new double[]{minTime, sum + node.val};
    }
}
