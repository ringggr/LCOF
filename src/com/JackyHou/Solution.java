package com.JackyHou;

import java.util.Arrays;
import java.util.Stack;

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


    // 32. 最长有效括号
    public int longestValidParentheses(String s) {
        int[] dp = new int[s.length() + 1]; // 指向有效的最左
//        int rest = 0;
        int max_length = 0;
        Stack<Integer> left = new Stack<>();
        dp[0] = 0;
        for (int i = 1; i <= s.length(); i++) {
            if (s.charAt(i - 1) == '(') {
                left.push(i);
                dp[i] = i; // dp[i] = dp[i - 1] == 0 ? i : dp[i - 1];
            }
            else { // ')'. NOTE: when s[i] is ')' and dp[i] = 0, which means this ')' is meaningless.
                if (!left.isEmpty()) {
                    int pre = left.pop();
                    dp[i] = pre;
                    if (dp[pre - 1] != 0 && dp[pre - 1] != pre - 1) {
                        dp[pre] = dp[pre - 1];
                    }
                    int valid_most_left = pre;
                    while (dp[valid_most_left] != 0 && dp[valid_most_left] != valid_most_left) {
                        valid_most_left = dp[valid_most_left];
                    }
                    System.out.printf("第%d个字符，匹配第%d个字符\n", i, valid_most_left);
                    max_length = Math.max(max_length, i - valid_most_left + 1);
                }
                else {
                    // there is no '(' for this ')'
                    dp[i] = 0;
                }
            }
        }
//        System.out.println(Arrays.toString(dp));
        return max_length;
    }

    // 44. 通配符匹配
    // p: '?' match a single character; '*' matches a string
    public boolean isMatch2(String s, String p) {
        if (p == null || p.length() == 0) {
            return s == null || s.length() == 0;
        }
        if (p.equals("*")) {
            return true;
        }
        s = " " + s;
        p = " " + p;
        boolean[][] dp = new boolean[s.length()][p.length()];
        dp[0][0] = true;
        for (int j = 1; j < p.length() && p.charAt(j) == '*'; j++) {
            dp[0][j] = true;
        }
        for (int i = 1; i < s.length(); i++) {
            for (int j = 1; j < p.length(); j++) {
                if (s.charAt(i) == p.charAt(j)) {
                    dp[i][j] = dp[i - 1][j - 1];
                }
                else { // s[i] != p[j]
                    if (p.charAt(j) == '?') {
                        dp[i][j] = dp[i - 1][j - 1];
                    }
                    else if (p.charAt(j) == '*') {
//                        if (j == p.length() - 1) {
//                            return true;
//                        }
                        dp[i][j] = dp[i - 1][j - 1] || dp[i][j - 1] || dp[i - 1][j];
                    }
                }
            }
        }

        return dp[s.length() - 1][p.length() - 1];
    }

    // 53. 最大子序和
    public int maxSubArray(int[] nums) {
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int res = dp[0];

        for (int i = 1; i < nums.length; i++) {
            dp[i] = Math.max(dp[i - 1], 0) + nums[i];
            res = Math.max(dp[i], res);
        }

        return res;
    }

    // 62. 不同路径
    public int uniquePaths(int m, int n) {
        if (m == 0 || n == 0) {
            return 0;
        }
        int[][] dp = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 1;
                }
                else {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }

        return dp[m - 1][n - 1];
    }

    // 63. 不同路径 II
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        if (obstacleGrid.length == 0 || obstacleGrid[0].length == 0) {
            return 0;
        }
        int row = obstacleGrid.length, col = obstacleGrid[0].length;
        if (obstacleGrid[0][0] == 1 || obstacleGrid[row - 1][col- 1] == 1) {
            return 0;
        }
        int[][] dp = new int[row][col];

        for (int i = 0; i < row; i++) {
            if (obstacleGrid[i][0] == 1) {
                break;
            }
            dp[i][0] = 1;
        }
        for (int j = 0; j < col; j++) {
            if (obstacleGrid[0][j] == 1) {
                break;
            }
            dp[0][j] = 1;
        }

        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (obstacleGrid[i][j] == 1) {
                    dp[i][j] = 0;
                }
                else {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }

        return dp[obstacleGrid.length - 1][obstacleGrid[0].length - 1];
    }

    // 64. 最小路径和
    public int minPathSum(int[][] grid) {
        if (grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int rows = grid.length;
        int cols = grid[0].length;
        for (int i = 1; i < rows; i++) {
            grid[i][0] += grid[i - 1][0];
        }
        for (int j = 1; j < cols; j++) {
            grid[0][j] += grid[0][j - 1];
        }
        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < cols; j++) {
                grid[i][j] += Math.min(grid[i - 1][j], grid[i][j - 1]);
            }
        }

        return grid[rows - 1][cols - 1];
    }


    // 70. 爬楼梯
    // 1, 2, 3, 5, 8
    public int climbStairs(int n) {
        if (n <= 3) {
            return n;
        }
        int a = 3, b = 2;
        while (n > 3) {
            int t = a;
            a = b + a;
            b = t;
            n--;
        }
        return a;
    }
}
