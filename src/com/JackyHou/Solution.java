package com.JackyHou;

import java.util.Arrays;

public class Solution {
    // 5. 最长回文子串
    // DP
    public String longestPalindrome(String s) {
        if (s == null || s.length() == 0 || s.length() == 1) {
            return s;
        }
        boolean[][] dp = new boolean[s.length()][s.length()];
        int start = 0;
        int end = 0;
        int max_len = 0;
        for (int j = 1; j < s.length(); j++) {
            for (int i = 0; i < j; i++) {
                if (s.charAt(i) == s.charAt(j) && (j - i <= 2 || dp[i + 1][j - 1])) {
                    // j - i <= 2 很关键，不仅要考虑字符和左边一位，还要考虑当前字符和左边的第二位（此时不考虑中间是啥）
                    dp[i][j] = true;
                    if (j - i + 1 > max_len) {
                        max_len = j - i + 1;
                        start = i;
                        end = j;
                    }
                }
            }
        }

        return s.substring(start, end + 1);
    }


    // 10. 正则表达式匹配
    // DP
    public boolean isMatch(String s, String p) {
        if (p == null || p.length() == 0) {
            return s == null || s.length() == 0;
        }
        s = " " + s;
        p = " " + p;
        char[] s2c = s.toCharArray();
        char[] p2c = p.toCharArray();
        boolean[][] dp = new boolean[s2c.length][p2c.length]; // dp[i][j], 原句的前i，能不能被正则的前j位匹配
        dp[0][0] = true;
        for (int j = 2; j < p2c.length; j++) { // 初始化匹配*组合的空字符串
            if (p2c[j] == '*' && dp[0][j - 2]) {
                dp[0][j] = true;
            }
        }
        for (int i = 1; i < s2c.length; i++) { // s
            for (int j = 1; j < p2c.length; j++) { // p: relu
                if (s2c[i] == p2c[j] || p2c[j] == '.') {
                    dp[i][j] = dp[i - 1][j - 1];
                }
                else if (p2c[j] == '*') {
                    if (p2c[j - 1] == s2c[i] || p2c[j - 1] == '.') {
                        //         * > 1,匹配前一个s；*=1，匹配*前的规则。
                        dp[i][j] = dp[i - 1][j] || dp[i][j - 1];
                    }
                    dp[i][j] |= dp[i][j-2]; // 或者直接不考虑 *组合
                }
            }
        }

        return dp[s.length() - 1][p.length() - 1];
    }
}
