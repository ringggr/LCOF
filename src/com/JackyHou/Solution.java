package com.JackyHou;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Solution {
    public int findRepeatNumber(int[] nums) {
        Set<Integer> number = new HashSet<>();
        for (Integer i : nums
        ) {
//            System.out.println(i);
            if (number.contains(i)) {
//                System.out.println(number.toString() + i);
                return i;
            } else {
                number.add(i);
            }
        }
//        System.out.println(number);
        return -1;
    }

    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }

        int row_last = matrix[0].length - 1;

        for (int[] ints : matrix) {

            if (ints[row_last] < target) continue;
            else if (ints[row_last] == target) return true;
            for (int j = row_last - 1; j >= 0; j--) {
                if (ints[j] == target) return true;
                if (ints[j] < target) break;
            }
        }
        return false;
    }

    public String replaceSpace(String s) {
        char[] result = new char[s.length() * 3];
        int size = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                result[size++] = '%';
                result[size++] = '2';
                result[size++] = '0';
            } else {
                result[size++] = s.charAt(i);
            }
        }

        return new String(result, 0, size);
    }

    public int[] reversePrint(ListNode head) {
        Stack<Integer> result = new Stack<>();
        while (head != null) {
            result.push(head.val);
            head = head.next;
        }
        int size = result.size();
        int[] print = new int[result.size()];
        for (int i = 0; i < size; i++) { // 注意这里不能直接用result.size()，每次弹出，size会变。
            print[i] = result.pop();
        }
        return print;
    }

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder.length != inorder.length || preorder.length == 0) {
            System.out.println("empty");
            return null;
        }

        TreeNode now = new TreeNode(preorder[0]);

        // 切割左右子树
        // 1. 切割中序
        boolean left_tag = true;
        int[] left_inorder = new int[inorder.length];
        int[] right_inorder = new int[inorder.length];
        int left_size = 0, right_size = 0;

        for (int value : inorder) {
            if (value == preorder[0]) {
                left_tag = false;
                continue;
            }
            if (left_tag) {
                left_inorder[left_size++] = value;
            } else {
                right_inorder[right_size++] = value;
            }
        }
        left_inorder = Arrays.copyOfRange(left_inorder, 0, left_size);
        right_inorder = Arrays.copyOfRange(right_inorder, 0, right_size);

        // 2. 切割前序
        int[] left_preorder = new int[left_size];
        int[] right_preorder = new int[right_size];
        int temp_left = 0, temp_right = 0;
        for (int i = 1; i < preorder.length; i++) {
            if (temp_left < left_size) {
                left_preorder[temp_left++] = preorder[i];
            } else {
                right_preorder[temp_right++] = preorder[i];
            }
        }

        now.left = buildTree(left_preorder, left_inorder); // 左子树构建
        now.right = buildTree(right_preorder, right_inorder); // 右子树构建

        return now;
    }


    public int fib(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int[] f = {0, 1};
        int i = 2;
        int tag = 0;
        int result = 0;
        while (i++ <= n) {
            f[tag] = (int) (f[0] % (1e9 + 7)) + (int) (f[1] % (1e9 + 7));
            result = f[tag];
            tag = (tag == 0 ? 1 : 0);
        }
        return (int) (result % (1e9 + 7));
    }

    // 10-II 青蛙跳台阶问题 斐波那契数列变形
    public int numWays(int n) {
        if (n == 0) {
            return 1;
        }
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        int[] f = {1, 2};
        int i = 3;
        int tag = 0;
        int result = 0;
        while (i++ <= n) {
            f[tag] = (int) (f[0] % (1e9 + 7)) + (int) (f[1] % (1e9 + 7));
            result = f[tag];
            tag = (tag == 0 ? 1 : 0);
        }
        return (int) (result % (1e9 + 7));
    }

    // 11. 旋转数组的最小数字
    public int minArray(int[] numbers) {
        if (numbers.length == 0) {
            return 0;
        }

        int first = numbers[0];
        if (numbers.length == 1) {
            return first;
        }

        int last = first;
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] < last) {
                return numbers[i];
            } else {
                last = numbers[i];
            }
        }
        return first;
    }

    // 12. 矩阵中的路径
    public boolean exist(char[][] board, String word) {
        int[][] went;
        int matched = 0; // 当前匹配到第几个字符

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == word.charAt(0)) {
                    // System.out.printf("%s %d %d\n", board[i][j], i, j);
                    went = new int[board.length][board[0].length];
                    went[i][j] = 1;
                    if (my_match(board, went, word, i, j, 1)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean my_match(char[][] board, int[][] went, String word, int row, int col, int matched) {
        // System.out.printf("Get in a match function, matched: %d.\n", matched);
        if (matched == word.length()) {
            // System.out.println("bingo!");
            return true;
        }
        // 可以使用更简洁的写法：进入每层时统一判断坐标合法性，而不是这样分方向单独判断。
        // 上
        if (row != 0) {
            if (went[row - 1][col] == 0) {
                // System.out.printf("matched: %d. 上\n", matched);
                if (board[row - 1][col] == word.charAt(matched)) {
                    // System.out.printf("%s %d %d\n", board[row - 1][col], row - 1, col);
                    went[row - 1][col] = 1;
                    if (my_match(board, went, word, row - 1, col, matched + 1)) {
                        return true;
                    }
                    went[row - 1][col] = 0;
                }
            }
        }

        // 下
        if (row + 1 < board.length) {
            if (went[row + 1][col] == 0) {
                // System.out.printf("matched: %d. 下\n", matched);
                if (board[row + 1][col] == word.charAt(matched)) {
                    // System.out.printf("%s %d %d\n", board[row + 1][col], row + 1, col);
                    went[row + 1][col] = 1;
                    if (my_match(board, went, word, row + 1, col, matched + 1)) {
                        return true;
                    }
                    went[row + 1][col] = 0;
                }
            }
        }

        // 左
        if (col != 0) {
            if (went[row][col - 1] == 0) {
                // System.out.printf("matched: %d. 左\n", matched);
                if (board[row][col - 1] == word.charAt(matched)) {
                    // System.out.printf("%s %d %d\n", board[row][col - 1], row, col - 1);
                    went[row][col - 1] = 1;
                    if (my_match(board, went, word, row, col - 1, matched + 1)) {
                        return true;
                    }
                    went[row][col - 1] = 0;
                }
            }
        }

        // 右
        if (col + 1 < board[0].length) {
            if (went[row][col + 1] == 0) {
                // System.out.printf("matched: %d. 右\n", matched);
                if (board[row][col + 1] == word.charAt(matched)) {
                    // System.out.printf("%s %d %d\n", board[row][col + 1], row, col + 1);
                    went[row][col + 1] = 1;
                    if (my_match(board, went, word, row, col + 1, matched + 1)) {
                        return true;
                    }
                    went[row][col + 1] = 0;
                }
            }
        }

        return false;
    }

    // 13. 机器人的运动范围
    public int movingCount(int m, int n, int k) {
        int[][] visited = new int[m][n];
        int count;
        count = dfs(0, 0, visited, k);
        return count;
    }

    private int dfs(int row, int col, int[][] visited, int k) {
        if (row < 0 || row >= visited.length || col < 0 || col >= visited[0].length || visited[row][col] == 1) {
            return 0;
        }
        int tag = getSum(row) + getSum(col);
        if (tag > k) return 0;
        visited[row][col] = 1;
        int count = 1;

        return count + dfs(row - 1, col, visited, k) +
                dfs(row + 1, col, visited, k) +
                dfs(row, col - 1, visited, k) +
                dfs(row, col + 1, visited, k);
    }

    private int getSum(int n) {
        int result = 0;
        while (n > 0) {
            result += n % 10;
            n /= 10;
        }
        return result;
    }

    // 14-I 剪绳子
    public int cuttingRope(int n) {
        /*
         * 10: 3, 3, 4
         * 30: 10, 10, 10----7, 7, 8, 8
         */
        if (n == 2) return 1;
        if (n == 3) return 2;
        int tag = 0;
        for (int i = 2; i <= n / 2; i++) {
            int temp = multiBySplit(n, i);
            if (temp > tag) tag = temp;
        }
        return tag;
    }

    private int multiBySplit(int n, int split) {
//        int r = n;
        int multi = 1;
        int temp;
        while (split > 0) {
            temp = n / split;
            n = n - temp;
            multi *= temp;
            split--;
        }
        return multi;
    }

    // 14-II 剪绳子II
    public int cuttingRope2(int n) {
        if (n <= 3) return n - 1;

        int rest = n % 3;
        int m = n / 3;

        long temp = 1; // 必须要用long类型，用int报错
        for (int i = 0; i < (m - 1); i++) {
            temp *= 3;
            temp = temp % (1000000007);
        }
        if (rest == 0) {
            return (int)((temp * 3) % (1e9 + 7));
        }
        if (rest == 1) {
            return (int)((temp * 4) % (1e9 + 7));
        }
        return (int)((temp * 6) % (1e9 + 7));
    }

    // 15. 二进制中1的个数
    // you need to treat n as an unsigned value
    public int hammingWeight(int n) {
        // return Integer.toBinaryString(n).replaceAll("0", "").length();

        int result = 0;
        while (n != 0) {
            result += n & 1;
            n >>>= 1;
        }
        return result;
    }

    // 16. 数值的整数次方
    public double myPow(double x, int n) {
        long new_n = n;
        if (x == 1.0) return 1.0;
        if (x == -1.0) {
            if (new_n % 2 == 0) {
                return 1.0;
            }
            else {
                return -1.0;
            }
        }
        if (new_n < 0) {
            x = 1.0 / x;
            new_n = -new_n;
        }

        double result = 1.0;
//        System.out.println(n);
        for (int i = 0; i < new_n && result != 0.0; i++) {
            result *= x;
//            System.out.println(result);
        }
        return result;
    }

    // 二进制 & 二分法 快速求幂
    public double myPow2(double x, int n) {
        if (x == 1) return 1;
        if (x == -1.0) {
            if (n % 2 == 0) {
                return 1.0;
            }
            else {
                return -1.0;
            }
        }
        long new_n = n;
        if (n < 0) {
            new_n *= -1;
            x = 1 / x;
        }

        double result = 1.0;
        while (new_n> 0) {
            if ((new_n & 1) == 1) result *= x;
            x *= x;
            new_n >>= 1;
        }
        return result;
    }

    // 17. 打印从1到最大的n位数
    public int[] printNumbers(int n) {
        int length = (int)Math.pow(10, n);
        int[] res = new int[length - 1];
        for (int i = 0; i < length - 1; i++) {
            res[i] = i + 1;
        }
        return res;
    }

    // 18. 删除链表的节点
    /*
     * Definition for singly-linked list.
     * public class ListNode {
     *     int val;
     *     ListNode next;
     *     ListNode(int x) { val = x; }
     * }
     */
    public ListNode deleteNode(ListNode head, int val) {
        if (head.val == val) return head.next;

        ListNode now, passed;
        now = passed = head;

        while (now != null) {
            if (now.val == val) {
                passed.next = now.next;
                return head;
            }
            passed = now;
            now = now.next;
        }

        return head;
    }
}
