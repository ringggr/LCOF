package com.JackyHou;

import java.util.*;

public class Sprint425 {
    // LCP 11.
    public int expectNumber(int[] scores) {
        HashSet<Integer> temp = new HashSet<>();
        for (int score : scores) {
            temp.add(score);
        }
        return temp.size();
    }

    // 1, 2, 3, 3 _> 3
    // 1, 3, 2, 3, -> 2
    // 2, 2, 3, 3, -> 3
    // 1, 4, 4, 3, 3, 3, 3, -> 3
    // LCP 12. 小张刷题计划
    public int minTime(int[] time, int m) {
        int n = time.length;
        if (n <= m) return 0;
        int sum = 0;
        int left = 0;
        int right = 0;
        for (int i: time) right += i;

        while (left < right) {
            int mid = (left + right) / 2;
            if (check(time, mid, m)) {
                right = mid;
            }
            else {
                left = mid + 1;
            }
        }
        return left;
    }

    private boolean check(int[] time, int thresh, int m) {
        int cnt = 1, sum = time[0], maxVal = time[0];
        for (int i = 1; i < time.length; i++) {
            sum += time[i];
            maxVal = Math.max(maxVal, time[i]);
            if (sum - maxVal > thresh) {
                cnt++;
                maxVal = sum = time[i];
            }
            if (cnt > m) {
                return false;
            }
        }
        return true;
    }
}
