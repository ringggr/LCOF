package com.JackyHou;

import com.sun.corba.se.spi.ior.IdentifiableContainerBase;
import sun.awt.datatransfer.DataTransferer;

import java.util.*;
import java.util.function.Function;

public class BiWeek26 {
    public int maxPower(String s) {
        if (s.length() == 0) return 0;
        int res = 1;
        char last = s.charAt(0);
        int count = 1;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                count++;
                res = Math.max(res, count);
            }
            else {
                count = 1;
            }
        }
        return res;
    }


    public List<String> simplifiedFractions(int n) {
        List<String> res = new ArrayList<>();
        if (n == 1) return res;
        Map<Integer, HashSet<Integer>> map = new HashMap<>();
        for (int i = 2; i <= n; i++) { // i 分母
            for (int j = 1; j < i; j++) { // j 分子
                int fenzi = j;
                int fenmu = i;
                int gcd = GCD(fenzi, fenmu);
                while (gcd != 1) {
                    fenzi /= gcd;
                    fenmu /= gcd;
                    gcd = GCD(fenzi, fenmu);
                }
                if (!map.containsKey(fenmu)) {
                    HashSet<Integer> temp = new HashSet<>();
                    temp.add(fenzi);
                    map.put(fenmu, temp);
                }
                else {
                    map.get(fenmu).add(fenzi);
                }
                System.out.println(j + "/" + i+"==========");
            }
        }
        for (int i: map.keySet()) {
            for (int j: map.get(i)) {
                res.add(j + "/" + i);
            }
        }
        return res;
    }

    private int GCD(int m, int n) {
        int result = 0;
        while (n != 0) {
            result = m % n;
            m = n;
            n = result;
        }
        return m;
    }

    public int goodNodes(TreeNode root) {
        if (root == null) return 0;
        return 1 + dfs(root.left, root.val) + dfs(root.right, root.val);
    }
    private int dfs(TreeNode root, int max) {
        if (root == null) return 0;
        int count = 0;
        if (root.val >= max) {
            count++;
            max = root.val;
        }
        return count + dfs(root.left, max) + dfs(root.right, max);
    }

    public String largestNumber(int[] cost, int target) {
        String[] dp = new String[target + 1];
        Comparator<String> comparator = Comparator.comparing(String::length).thenComparing(String::compareTo);
        for (int i = 0; i <= target; i++) {
            String max = null;
            for (int j = 0; j < cost.length; j++) {
                String temp = null;
                if (i - cost[j] > 0) {
                    String string = dp[i - cost[j]];
                    if (string != null) {
                        temp = String.valueOf(j + 1).concat(string);
                    }
                }
                else if (i == cost[j]) {
                    temp = String.valueOf(j + 1);
                }
                System.out.println("i: " + i + ", j: " + j + ", temp: " + temp);
                if (max == null || (temp != null && comparator.compare(max, temp) < 0)) {
                    max = temp;
                }
            }
            dp[i] = max;
        }
        return dp[target] == null ? "0" : dp[target];
    }
}
