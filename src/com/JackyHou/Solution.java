package com.JackyHou;

import java.net.ServerSocket;
import java.util.*;

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

    // 72. 编辑距离
    public int minDistance(String word1, String word2) {
        // dp[i][j] 第i个word2字符 对应第j个word1字符，至少需要编辑多少次
        // i j 不一样，替换，
        if (word1.length() == 0 || word2.length() == 0) {
            return word1.length() + word2.length();
        }
        if (word1.equals(word2)) {
            return 0;
        }

        word1 = " " + word1;
        word2 = " " + word2;
        int[][] dp = new int[word1.length()][word2.length()];

        for (int i = 0; i < word1.length(); i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j < word2.length(); j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i < word1.length(); i++) {
            for (int j = 1; j < word2.length(); j++) {
                if (word1.charAt(i) != word2.charAt(j)) {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                }
                else {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1] - 1) + 1;
                }
            }
        }

        return dp[word1.length() - 1][word2.length() - 1];
    }

    // 85. 最大矩形
    // 思路，dp[i][j]用于存储当前位置的到这边连续的1有多少个，作为边长width，
    // 再向上遍历当前列的边长width，直到值为0，取最小的width。算出width*b，取最大面积。
    // 关联题目：84
    public int maximalRectangle(char[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int[][] dp = new int[matrix.length][matrix[0].length];
        int res = 0;
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                if (matrix[i][j] == '1') {
                    dp[i][j] = j == 0 ? 1 : dp[i][j - 1] + 1;

                    int width = dp[i][j];

                    for (int row = i; row >= 0 && dp[row][j] != 0; row--) {
                        width = Math.min(width, dp[row][j]);
                        res = Math.max(res, width * (i - row + 1));
                    }
                }
            }
        }
        return res;
    }

    public int maximalRectangle2(char[][] matrix) {
        if (matrix.length == 0) {
            return 0;
        }
        int res = 0;
        int[] dp = new int[matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                dp[j] = matrix[i][j] == '1' ? dp[j] + 1 : 0;
            }
            res = Math.max(res, largestRectangleArea2(dp));
        }
        return res;
    }

    // 84. 柱状图中最大的矩形
    public int largestRectangleArea(int[] heights) {
        int res = 0;
        for (int i = 0; i < heights.length; i++) {
            int h = heights[i];
            for (int j = i; j >= 0; j--) {
                h = Math.min(h, heights[j]);
                res = Math.max(res, (i - j + 1) * h);
            }
        }
        return res;
    }
    // 采用 单调队列
    public int largestRectangleArea2(int[] heights) {
        int res = 0;
        int[] new_h = new int[heights.length + 2];
        Deque<Integer> minQ = new LinkedList<>(); // 单调递增队列

        System.arraycopy(heights, 0, new_h, 1, heights.length);
        for (int i = 0; i < new_h.length; i++) {
            while (!minQ.isEmpty() && new_h[minQ.peekLast()] > new_h[i]) {
                int cur = minQ.pollLast();
                res = Math.max(res, (i - minQ.peekLast() - 1) * new_h[cur]);
                System.out.println(cur + " " + res);
            }
            minQ.add(i);
        }
        return res;
    }

    // 87. 扰乱字符串
    public boolean isScramble(String s1, String s2) {
        return false;
    }

    // 91. 解码方法
    public int numDecodings(String s) {
        if (s.length() == 0 || s.charAt(0) == '0') {
            return 0;
        }
        int[] dp = new int[s.length()];
        dp[0] = 1;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == '0' && (s.charAt(i - 1) > '2' || s.charAt(i - 1) == '0')) // 错误字符串
            {
                return 0;
            }
            // 0必和前位组合，且前位的计数不考虑。
            if (s.charAt(i) == '0') {
                dp[i] = i >= 2 ? dp[i - 2] : dp[i - 1];
            }
            else {
                // 只能和前一个数组成一个密码, 不能组合
                if (s.charAt(i - 1) == '0' || s.charAt(i - 1) > '2' || (s.charAt(i - 1) == '2' && s.charAt(i) > '6')) {
                    dp[i] = dp[i - 1];
                }
                else { // 不组合的数量 + 组合的数量
                    dp[i] = dp[i - 1] + dp[i - 2];
                }
            }
        }
        return dp[s.length() - 1];
    }

    // 2. 两数相加
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null || l2 == null) {
            return l1 == null ? l2 : l1;
        }
        ListNode dummyHead = new ListNode(0);
        ListNode p = l1, q = l2, curr = dummyHead;
        int carry = 0;
        while (p != null || q != null) {
            int x = (p != null) ? p.val : 0;
            int y = (q != null) ? q.val : 0;
            int sum = carry + x + y;
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
            if (p != null) p = p.next;
            if (q != null) q = q.next;
        }
        if (carry > 0) {
            curr.next = new ListNode(carry);
        }
        return dummyHead.next;
    }

    // 287
    public int findDuplicate(int[] nums) {
        int min = 1, max = nums.length - 1;
        while (min < max) {
            int mid = (min + max) >> 1;
            int cnt = 0;
            for (int num : nums) {
                if (num <= mid) {
                    cnt++;
                }
            }
            if (cnt <= mid) {
                min = mid + 1;
            }
            else {
                max = mid;
            }
        }
        return min;
    }

    // 141
    public boolean hasCycle(ListNode head) {
        ArrayList<ListNode> visited = new ArrayList<>();
        while(head != null) {
            if (visited.contains(head)) {
                return true;
            }
            visited.add(head);
            head = head.next;
        }
        return false;
    }

    // 142
    public ListNode detectCycle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null) {
            if (fast.next == null) return null;
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) return fast;
        }
        return null;
    }

    // 376 DP
    public int wiggleMaxLength(int[] nums) {
        if (nums.length < 2) return nums.length;
        int[][] dp = new int[nums.length][2]; // i: 知道第i个数，0: 最多几个，1: 此数与上一位的差
        dp[0][0] = 1;
        for (int i = 1; i < nums.length; i++) {
            dp[i][1] = nums[i] - nums[i - 1];
            if (dp[i][1] == 0) {
                dp[i][0] = dp[i - 1][0];
                dp[i][1] = dp[i - 1][1];
            }
            else {
                int t = dp[i][1] * dp[i - 1][1];
                if (t == 0) { // 前面全是一样的
                    dp[i][0] = 2;
                }
                else if (t < 0) {
                    dp[i][0] = dp[i - 1][0] + 1;
                } else {
                    dp[i][0] = dp[i - 1][0];
                }
            }
        }
        return dp[dp.length - 1][0];
    }

    // 394
    public String decodeString(String s) {
        StringBuilder res = new StringBuilder();
        int pos = 0;
        boolean re_tag = false;
        while (pos < s.length()) {
            char t = s.charAt(pos);
            if (t >= '0' && t <= '9') {
                int n = (int)(t - '0');
                int pos_t = pos + 1;
                while (pos_t < s.length() && Character.isDigit(s.charAt(pos_t))) {
                    n = n * 10 + (int)(s.charAt(pos_t) - '0');
                    pos_t++;
                }
                int start = pos_t + 1;
                int end = start;
                int left = 0;
                for (;end < s.length(); end++) {
                    if (s.charAt(end) == '[') {
                        left++;
                        re_tag = true;
                    }
                    if (s.charAt(end) == ']'){
                        if(left==0) {
                            break;
                        }
                        else {
                            left--;
                        }
                    }
                }
                for(int i = 0; i < n; i++) {
                    res.append(s.substring(start, end));
                }
                pos = end + 1;
            }
            else {
                res.append(t);
                pos++;
            }
        }

        return re_tag ? decodeString(res.toString()) : res.toString();
    }
}


