package com.JackyHou;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Week190 {

    public int isPrefixOfWord(String sentence, String searchWord) {
        int len = searchWord.length();
        sentence = " " + sentence.trim();
        int res = 0;
        for (int i = 0; i < sentence.length(); i++) {
            char c = sentence.charAt(i);
            if (c == ' ') {
                res++;
                int pos = 0;
                while (pos < sentence.length() && pos < searchWord.length() &&sentence.charAt(i + 1 + pos) == searchWord.charAt(pos)) {
                    pos++;
                }
                if (pos >= len) {
                    return res;
                }
                else {
                    i += pos;
                }
            }

        }
        return -1;
    }

    // 2
    public int maxVowels(String s, int k) {
        int res = 0;
        HashSet<Character> yuan = new HashSet<>();
        yuan.add('a');
        yuan.add('e');
        yuan.add('i');
        yuan.add('o');
        yuan.add('u');

        int i;
        for (i = 0; i < s.length() && i < k; i++) {
            char c = s.charAt(i);
            if (yuan.contains(c)) {
                res++;
            }
        }
        if (res == k) {
            return res;
        }
        int left = 0;
        int num = res;
        for (; i < s.length(); i++, left++) {
            char pre = s.charAt(left);
            char last = s.charAt(i);

            if (yuan.contains(pre)) {
                num--;
            }
            if (yuan.contains(last)) {
                num++;
            }
            res = Math.max(res, num);
            if (res == k) {
                return res;
            }
        }
        return res;
    }


    // 3
    //
    HashMap<Integer, Integer> map = new HashMap<>();
    int res = 0;
    public int pseudoPalindromicPaths (TreeNode root) {
        dfs(root);
        return res;
    }

    private void dfs(TreeNode root) {
        if (root == null) return;
        map.put(root.val, map.getOrDefault(root.val, 0) + 1);
        if (root.left == null && root.right == null) {
            int single = 0;
            for (int key: map.keySet()) {
                if (map.get(key) % 2 == 1) {
                    single++;
                }
            }
            if (single <= 1) {
                res++;
            }
        }
        dfs(root.left);
        dfs(root.right);
        map.put(root.val, map.getOrDefault(root.val, 1) - 1);
    }

    // 4
    // https://leetcode-cn.com/problems/max-dot-product-of-two-subsequences/
    public int maxDotProduct(int[] nums1, int[] nums2) {
        int n = nums1.length, m = nums2.length;
        int[][] dp = new int[n + 1][m + 1];
        int res = Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                int t = dp[i - 1][j - 1] + nums1[i - 1] * nums2[j - 1];
                res = Math.max(res, t);
                dp[i][j] = Math.max(dp[i][j - 1], Math.max(dp[i - 1][j], t));
            }
        }
        return res;
    }

}
