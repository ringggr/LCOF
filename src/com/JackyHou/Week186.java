package com.JackyHou;

import sun.awt.image.ImageWatched;

import java.util.*;

public class Week186 {
// 周赛 186场

    public int maxScore(String s) {
        char[] chars = s.toCharArray();
        int ling = 0;
        int yi = 0;
        boolean tag = false;
        int split = s.length() / 2; // split位 为第二组的考虑，考虑1
        int all_l = 0;
        for (int i = 0; i < s.length(); i++) {
            if (chars[i] == '0') {
                all_l++;
            }
            if (i < split && chars[i] == '0') {
                ling++;
            }
            if (i >= split && chars[i] == '1') {
                yi++;
            }
        }
        if (all_l == s.length() || all_l == 0) {
            return s.length() - 1;
        }
        int max = ling + yi;

        int l = ling, y = yi;
        // 向左：
        for (int i = split - 1; i > 0; i--) {
            if (chars[i] == '1') {
                y++;
            }
            else{
                l--;
            }
            max = Math.max(y + l, max);
        }
        l = ling;
        y = yi;
        // 向右
        // split位 为0的考虑
        for (int i = split; i < chars.length - 1; i++) {
            if (chars[i] == '0') {
                l++;
            }
            else {
                y--;
            }
            max = Math.max(y + l, max);
        }
        return max;
    }

    // 5393. 可获得的最大点数
    public int maxScore(int[] cardPoints, int k) {
        // 转变思路，寻找length-k个连续数组的最小值。
        int num = cardPoints.length - k;
        int minn = 0;
        for (int i = 0; i < num; i++) {
            minn += cardPoints[i];
        }
        System.out.println(minn);
        int sum = minn;
        int first = 0, fifth = num - 1;
        int temp = minn;
        for (int i = num; i < cardPoints.length; i++) {
            sum += cardPoints[i];
            temp = temp - cardPoints[first++] + cardPoints[++fifth];
            minn = Math.min(minn, temp);
        }
        System.out.println(minn);
        return sum - minn;
    }

    // 5394. 对角线遍历 II
    public int[] findDiagonalOrder(List<List<Integer>> nums) {
        int row = nums.size();
        int count = 0;
        HashMap<Integer, ArrayList<Integer>> i_j = new LinkedHashMap<>();
        for (int i = 0; i < nums.size(); i++) {
            count += nums.get(i).size();
            for (int j = 0; j < nums.get(i).size(); j++) {
                ArrayList<Integer> temp = i_j.getOrDefault(i + j, new ArrayList<>());
                temp.add(nums.get(i).get(j));
                i_j.put(i + j, temp);
            }
        }
        int[] res = new int[count];
        int p = 0;
        for (int key: i_j.keySet()) {
            ArrayList<Integer> temp = i_j.get(key);
            for (int i = temp.size() - 1; i >= 0; i--) {
                res[p++] = temp.get(i);
            }
        }
        return res;
    }

    // 5180. 带限制的子序列和
    public int constrainedSubsetSum(int[] nums, int k) {
        int[] dp = new int[nums.length];
        int res = nums[0];
        dp[0] = nums[0];
        LinkedList<Integer> queue = new LinkedList<>();
        queue.addFirst(0);
        for (int i = 1; i < nums.length; i++) {
            dp[i] = Math.max(dp[queue.peekFirst()] + nums[i], nums[i]);
            res = Math.max(res, dp[i]);
            while (!queue.isEmpty() && dp[queue.peekLast()] <= dp[i]) {
                queue.pollLast();
            }
            queue.addLast(i);
            if (i - queue.peekFirst() == k) {
                queue.pollFirst();
            }
        }
        return res;
    }
}
