package com.JackyHou;

import java.awt.image.BandedSampleModel;
import java.io.InputStream;
import java.util.*;

public class Week187 {

    // 1.
    public String destCity(List<List<String>> paths) {
        Set<String> start_city = new HashSet<>();
        Set<String> end_city = new HashSet<>();
        for (List<String> path : paths) {
            String start = path.get(0);
            String end = path.get(1);
            start_city.add(start);
            end_city.add(end);
        }
        for (String c: end_city) {
            if (!start_city.contains(c)) {
                return c;
            }
        }
        return "";
    }

    // 2.
    public boolean kLengthApart(int[] nums, int k) {
        boolean res = true;
        int pre = -(k + 1);
        for (int i = 0; i < nums.length && res; i++) {
            if (nums[i] == 1) {
                if (i - pre < k + 1) {
                    res = false;
                }
                pre = i;
            }
        }
        return res;
    }

    // 3.
    public int longestSubarray(int[] nums, int limit) {
        if (nums.length == 1) {
            return 1;
        }

        Deque<Integer> maxQ = new LinkedList<>();
        Deque<Integer> minQ = new LinkedList<>();

        int res = 0;
        int left = 0;
        for (int right = 0; right < nums.length; right++) {
            while (!maxQ.isEmpty() && nums[maxQ.peekLast()] <= nums[right]) {
                maxQ.pollLast();
            }
            maxQ.offerLast(right);
            System.out.printf("left: %d, right: %d, maxQ: ", left, right);
            System.out.println(Arrays.asList(maxQ.toArray()));

            while (!minQ.isEmpty() && nums[minQ.peekLast()] >= nums[right]) {
                minQ.pollLast();
            }
            minQ.offerLast(right);
            System.out.printf("left: %d, right: %d, minQ: ", left, right);
            System.out.println(Arrays.asList(minQ.toArray()));
            // 判断是否符合
            while (!minQ.isEmpty() && !maxQ.isEmpty() && nums[maxQ.peekFirst()] - nums[minQ.peekFirst()] > limit) {
                if (minQ.peekFirst() <= left) minQ.pollFirst();
                if (maxQ.peekFirst() <= left) maxQ.pollFirst();
                left++;
            }
            System.out.println("After check: ");
            System.out.println("maxQ: " + Arrays.asList(maxQ.toArray()));
            System.out.println("minQ: " + Arrays.asList(minQ.toArray()));
            res = Math.max(res, right - left + 1);
            System.out.println("length: " + res);
            System.out.println("====================");
        }
        return res;
    }
    public static int longestSubarray2(int[] nums, int limit) {
        int left = 0; // 满足条件的最小下标数组值
        int res = 0;
        if (nums.length == 1) return 1;
        for (int i = 1; i < nums.length; i++) {
            // 队列维护
            for (int j = i - 1; j >= left; j--) {
                // 如果存在相同的值，代表当前的值是符合条件的，不再算，直接进行下一个值
                if (nums[i] == nums[j]) { // 关键剪枝。
                    break;
                }
                if (Math.abs(nums[j] - nums[i]) > limit) {
                    left = j + 1;
                    break;
                }
            }
            res = Math.max(res, i - left + 1);
        }
        return res;
    }

    // 4.
    public int kthSmallest(int[][] mat, int k) {
        int m = mat.length;
        int n = mat[0].length;

        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            res.add(mat[0][i]);
        }

        for (int i = 1; i < m; i++) {
            List<Integer> newSum = new ArrayList<>();
            for (int oldSum: res) {
                for (int num: mat[i]) {
                    newSum.add(oldSum + num);
                }
            }
            Collections.sort(newSum);
            res = newSum.subList(0, Math.min(k, newSum.size()));
        }
        return res.get(k - 1);
    }


}
// 4,2,2,2,4,4,2,2
// 4,8,5,1,7,9
// 7,40,10,10,40,39,96,21,54,73,33,17,2,72,5,76,28,73,59,22,100,91,80,66,5,49,26,45,
// 13,27,74,87,56,76,25,64,14,86,50,38,65,64,3,42,79,52,37,3,21,26,42,73,18,44,55,28,35,87

// 8, , 4, 3, 8, limit = 4