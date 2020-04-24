package com.JackyHou;

import sun.jvm.hotspot.ui.EditorCommands;

import java.util.*;

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

    // 19. 正则表达式匹配
    public boolean isMatch(String s, String p) {
        if (s.length() == 0) {
            if (p.length() % 2 != 0) return false;
            int i = 1;
            while (i < p.length()) {
                if (p.charAt(i) != '*') return false;
                i += 2;
            }
            return true;
        }

        if (p.length() == 0) return false;

        char match = s.charAt(0), relu = p.charAt(0), next = '?';
        if (p.length() > 1) next = p.charAt(1);

        if (next != '*') {
            if (match == relu || relu == '.') {
//                System.out.printf("s length: %d\n", s.length());
                return isMatch(s.substring(1), p.substring(1));
            }
            else {
                return false;
            }
        }
        else {
            if (match == relu || relu == '.') {
                return isMatch(s.substring(1), p) || isMatch(s, p.substring(2));
            }
            else {
                return isMatch(s, p.substring(2));
            }
        }
    }

    public boolean isMatchByDP(String A, String B) {
        int n = A.length(), m = B.length();
        boolean[][] dp = new boolean[n + 1][m + 1];

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                // if B is empty, return false.
                if (j == 0) {
                    dp[i][j] = (i == 0); // if A is empty too, return true.
                }
                else {
                    // if B[j] is * right now.
                    if (B.charAt(j - 1) == '*') {
                        // do not concern it
                        if (j >= 2) {
                            dp[i][j] = dp[i][j] | dp[i][j - 2];
                        }
                        // concern it
                        if (i >= 1 && j >= 2 && (A.charAt(i - 1) == B.charAt(j - 2) || B.charAt(j - 2) == '.')) {
                            dp[i][j] = dp[i][j] | dp[i - 1][j];
                        }
                    }
                    else { // B[j] is not *
                        if (i > 0 && (A.charAt(i - 1) == B.charAt(j - 1) || B.charAt(j - 1) == '.')) {
                            dp[i][j] = dp[i - 1][j - 1];
                        }
                    }
                }
            }
        }
        return dp[n][m];
    }

    // 20. 表示数值的字符串
    public boolean isNumber(String s) {
        boolean res = true;

        char[] n = s.toCharArray();

        boolean hasE = false;
        boolean dot = false;
        boolean allSpace = true;
        for(int i = 0; i < n.length; i++) {
            if (isNotNum(n[i])) {
                if (n.length == 1) {
                    return false;
                }
                if (n[i] == 'e') {
                    if (hasE) return false;
                    hasE = true;
                    if (i == 0 || (isNotNum(n[i - 1]) && n[i - 1] != '.') || i == n.length - 1 || (isNotNum(n[i + 1]) && n[i + 1] != '+' && n[i + 1] != '-')) {
                        return false;
                    }
                } else if (n[i] == '-' || n[i] == '+') {
                    if (i != 0 && n[i - 1] != 'e' && n[i - 1] != ' ' || i == n.length - 1 || (isNotNum(n[i + 1]) && n[i + 1] != '.')) {
//                        System.out.println(n[i + 1]);
                        return false;
                    }
                } else if (n[i] == '.') {
                    if (dot) return false;
                    if (hasE) return false;
                    dot = true;
                    if ((i != 0 && isNotNum(n[i - 1]) && n[i - 1] != '+' && n[i - 1] != '-' && n[i - 1] != ' ') || i != n.length - 1 && (isNotNum(n[i + 1]) && n[i + 1] != ' ' && n[i + 1] != 'e')) {
                        return false;
                    }
                    if ((i == 0 && isNotNum(n[i + 1])) || (i != 0 && i != n.length - 1 && isNotNum(n[i - 1]) && isNotNum(n[i + 1])) || (i == n.length - 1 && isNotNum(n[i - 1]))) {
                        return false;
                    }
                } else if (n[i] == ' ') {
                    if (i != 0 && i != n.length - 1) {
                        if (n[i - 1] != ' ' && n[i + 1] != ' ') {
                            return false;
                        }
                    }
                }
                else {
//                    System.out.println(n[i]);
                    return false;
                }
            }
            if (n[i] != ' ') {
                allSpace = false;
            }
        }
        if (allSpace) return false;
        return res;
    }

    private boolean isNotNum(char c) {
        boolean res = false;

        if (c - '0' > 9 || c - '0' < 0) {
            return true;
        }

        return res;
    }

    // 21. 调整数组顺序使奇数在前
    public int[] exchange(int[] nums) {
        int[] res = new int[nums.length];
        int head = 0, rear = nums.length - 1;
        for (int num : nums) {
            if (num % 2 == 0) {
                res[rear--] = num;
            } else {
                res[head++] = num;
            }
        }
        return res;
    }

    // 22. 链表中倒数第k个节点
    public ListNode getKthFromEnd(ListNode head, int k) {
        ListNode res = head;
        int i = 0;
        while (head != null) {
            if (i >= k) {
                res = res.next;
            }
            head = head.next;
            i++;
        }
        return res;
    }

    // 24. 反转链表
    public ListNode reverseList(ListNode head) {
        ListNode pre = null, next;
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    // 25. 合并两个排序的链表
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(0), pos = head;

        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                pos.next = l1;
                l1 = l1.next;
            }
            else {
                pos.next = l2;
                l2 = l2.next;
            }
            pos = pos.next;
        }

        pos.next = l1 != null ? l1 : l2;

        return head.next;
    }

    // 26. 树的子结构
    public boolean isSubStructure(TreeNode A, TreeNode B) {
        if (A == null || B == null) return false;
        if (isNowStructure(A, B)) {
            return true;
        }
        return isSubStructure(A.left, B) || isSubStructure(A.right, B);
    }

    private boolean isNowStructure(TreeNode A, TreeNode B) {
        if (B == null) {
            return true;
        }
        if (A == null || A.val != B.val) {
            return false;
        }
        return isNowStructure(A.left, B.left) && isNowStructure(A.right, B.right);
    }

    // 27. 二叉树的镜像
    public TreeNode mirrorTree(TreeNode root) {
        if (root == null) return root;
        TreeNode temp = root.left;
        root.left = mirrorTree(root.right);
        root.right = mirrorTree(temp);
        return root;
    }

    // 28. 对称的二叉树
    public boolean isSymmetric(TreeNode root) {
        TreeNode mirrorRoot =  mirrorTree2(root);
        return isSame(root, mirrorRoot);
    }

    private TreeNode mirrorTree2(TreeNode root) {
        if (root == null) return null;
        TreeNode newNode = new TreeNode(root.val);
        newNode.left = mirrorTree2(root.right);
        newNode.right = mirrorTree2(root.left);
        return newNode;
    }

    private boolean isSame(TreeNode a, TreeNode b) {
        if (a == null && b == null) {
            return true;
        }
        if (a == null || b == null || a.val != b.val) {
            return false;
        }
        return isSame(a.left, b.left) && isSame(a.right, b.right);
    }

    // 28. 对称的二叉树
    public boolean isSymmetric2(TreeNode root) {
        return root == null || isSym(root.left, root.right);
    }

    private boolean isSym(TreeNode a, TreeNode b) {
        if (a == null && b == null) return true;
        if (a == null || b == null || a.val != b.val) return false;
        return isSym(a.left, b.right) && isSym(a.right, b.left);
    }

    // 29. 顺时针打印矩阵
    public int[] spiralOrder(int[][] matrix) {
        if(matrix.length == 0) return new int[0];
        int l = 0, r = matrix[0].length - 1, t = 0, b = matrix.length - 1, x = 0;
        int[] res = new int[(r + 1) * (b + 1)];
        while(true) {
            for(int i = l; i <= r; i++) res[x++] = matrix[t][i]; // left to right.
            if(++t > b) break;
            for(int i = t; i <= b; i++) res[x++] = matrix[i][r]; // top to bottom.
            if(l > --r) break;
            for(int i = r; i >= l; i--) res[x++] = matrix[b][i]; // right to left.
            if(t > --b) break;
            for(int i = b; i >= t; i--) res[x++] = matrix[i][l]; // bottom to top.
            if(++l > r) break;
        }
        return res;
    }

    // 31. 栈的压入、弹出序列
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        if (pushed.length == 0 && popped.length == 0) return true;
        if (pushed.length == 0) return false;
        Stack<Integer> a = new Stack<>();
        int push = 0, pop = 0;
        while (push < pushed.length) {
            a.push(pushed[push++]);
            while (pop < popped.length && !a.isEmpty() && popped[pop] == a.peek()) {
                System.out.println(a.peek());
                a.pop();
                pop++;
            }
        }
        return a.empty();
    }

    // 32 - I. 从上到下打印二叉树
    // 层序遍历
    public int[] levelOrder(TreeNode root) {
        if (root == null) return new int[0];

//        int[] a = new int[1001];
        int i = 0;
        Queue<Integer> res = new LinkedList<>();
        Queue<TreeNode> level = new LinkedList<>();
        level.offer(root);
        while (!level.isEmpty()) {
            TreeNode temp = level.poll();
            res.offer(temp.val);
            i++;
            if (temp.left != null) level.offer(temp.left);
            if (temp.right != null) level.offer(temp.right);
        }
        int[] a = new int[i];
        i = 0;
        while (!res.isEmpty()) {
            a[i++] = res.poll();
        }
        return a;
    }

    // 32 - II. 从上到下打印二叉树 II
    /*
        3
       / \
      9  20
        /  \
       15   7
    返回其层次遍历结果：
    [
      [3],
      [20,9],
      [15,7]
    ]
     */
    public List<List<Integer>> levelOrder2(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        Queue<TreeNode> level = new LinkedList<>();
        if (root != null) {
            level.offer(root);
            while (!level.isEmpty()) {
                List<Integer> temp = new ArrayList<>();
                for (int i = level.size(); i > 0; i--) {
                    TreeNode aNode = level.poll();
                    if (aNode != null) {
                        temp.add(aNode.val);
                        if (aNode.left != null) level.offer(aNode.left);
                        if (aNode.right != null) level.offer(aNode.right);
                    }
                }

                res.add(temp);
            }
        }
        return res;
    }

    // 32 - III. 从上到下打印二叉树 III
    // 偶数行逆序
    public List<List<Integer>> levelOrder3(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        Queue<TreeNode> level = new LinkedList<>();
        if (root != null) {
            level.offer(root);
            while (!level.isEmpty()) {
                List<Integer> temp = new ArrayList<>();
                for (int i = level.size(); i > 0; i--) {
                    TreeNode aNode = level.poll();
                    if (aNode != null) {
                        temp.add(aNode.val);
                        if (aNode.left != null) level.offer(aNode.left);
                        if (aNode.right != null) level.offer(aNode.right);
                    }
                }
                if (res.size() % 2 == 1) {
                    Collections.reverse(temp);
                }
                res.add(temp);
            }
        }
        return res;
    }

    // 33. 二叉搜索树的后序遍历序列
    public boolean verifyPostorder(int[] postorder) {
        System.out.println(Arrays.toString(postorder));
        if (postorder.length <= 3) return true;
        int root = postorder[postorder.length - 1];
        int len = 0;
        boolean tag = true;
        for (int value : postorder) {
            if (value < root && tag) {
                len++;
            }
            if (value > root) tag = false;
            if (value < root && !tag) {
                return false;
            }
        }
        int[] left = Arrays.copyOf(postorder, len);
        int[] right = Arrays.copyOfRange(postorder, len, postorder.length - 1);
        return verifyPostorder(left) && verifyPostorder(right);
    }
    // 高级方法：时间复杂度O(n)
    public boolean verifyPostorder2(int[] postorder) {
        Stack<Integer> stack = new Stack<>();
        int root = Integer.MAX_VALUE;
        for(int i = postorder.length - 1; i >= 0; i--) {
            if(postorder[i] > root) return false;
            while(!stack.isEmpty() && stack.peek() > postorder[i])
                root = stack.pop();
            stack.add(postorder[i]);
        }
        return true;
    }

    // 34. 二叉树中和为某一值的路径
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();

        getPath(root, sum, res, 0, temp);

        return res;
    }

    private void getPath(TreeNode root, int sum, List<List<Integer>> res, int nowSum, List<Integer> nowPath) {
        if (root != null) {
            if (root.left == null && root.right == null) {
                if (nowSum + root.val == sum) {
                    nowPath.add(root.val);
                    res.add(new ArrayList<>(nowPath));
                    nowPath.remove(nowPath.size() - 1);
                }
            } else {
                nowPath.add(root.val);
                getPath(root.left, sum, res, nowSum + root.val, nowPath);
                getPath(root.right, sum, res, nowSum + root.val, nowPath);
                nowPath.remove(nowPath.size() - 1);
            }
        }
    }

    // 35. 复杂链表的复制
    public Node copyRandomList(Node head) {
        if (head == null) return null;
        Map<Node, Node> map = new HashMap<>();
        Node new_head = new Node(head.val);
        map.put(head, new_head);
        Node pre = new_head;
        Node pos = head.next;
        while (pos != null) {
            Node temp = new Node(pos.val);
            pre.next = temp;
            pre = temp;
            map.put(pos, temp);
            pos = pos.next;
        }
        pre = new_head;
        pos = head;
        while (pos != null) {
            pre.random = map.get(pos.random);
            pos = pos.next;
            pre = pre.next;
        }
        return new_head;
    }

    HashMap<Node, Node> visitedHash = new HashMap<>();
    public Node copyRandomList2(Node head) {

        if (head == null) {
            return null;
        }

        // If we have already processed the current node, then we simply return the cloned version of it.
        if (this.visitedHash.containsKey(head)) {
            return this.visitedHash.get(head);
        }

        // Create a new node with the value same as old node. (i.e. copy the node)
        Node node = new Node(head.val);

        // Save this value in the hash map. This is needed since there might be
        // loops during traversal due to randomness of random pointers and this would help us avoid
        // them.
        this.visitedHash.put(head, node);

        // Recursively copy the remaining linked list starting once from the next pointer and then from
        // the random pointer.
        // Thus we have two independent recursive calls.
        // Finally we update the next and random pointers for the new node created.
        node.next = copyRandomList2(head.next);
        node.random = copyRandomList2(head.random);

        return node;
    }

    // 36. 二叉搜索树与双向链表
    DeNode pre, head;
    public DeNode treeToDoublyList(DeNode root) {
        if (root == null) return null;
        dfs(root);
        head.left = pre;
        pre.right = head;
        return head;
    }

    private void dfs(DeNode now) {
       if (now == null) return ;
       dfs(now.left);
       if (pre != null) pre.right = now;
       else head = now;
       now.left = pre;
       pre = now;
       dfs(now.right);
    }

    // 38. 字符串的排列
    public String[] permutation(String s) {
        char[] chars = s.toCharArray();
        List<String> res = new ArrayList<>();
        f(0, chars, res);
        int size = res.size();
        return res.toArray(new String[size]);
    }


    private void f(int now, char[] chars, List<String> res) {
        if (now == chars.length - 1) {
            res.add(String.valueOf(chars));
        }
        else {
            HashSet<Character> uesdChar = new HashSet<>();
            for (int i = now; i < chars.length; i++) {
                if (uesdChar.contains(chars[i])) continue;
                uesdChar.add(chars[i]);
                swap(i, now, chars);
                f(now + 1, chars, res);
                swap(i, now, chars);
            }
        }
    }

    private void swap(int i, int j, char[] chars) {
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }

    // 39. 数组中出现次数超过一半的数字
    public int majorityElement(int[] nums) {
        if (nums.length == 0) return 0;
        HashMap<Integer, Integer> counts = new HashMap<>();
        int res = nums[0];
        for (int num : nums) {
            if (counts.containsKey(num)) {
                int temp = counts.get(num);
                temp++;
                counts.replace(num, temp);
                if (temp > nums.length / 2) {
                    res = num;
                    break;
                }
            } else {
                counts.put(num, 1);
            }
        }
        return res;
    }

    public int majorityElement2(int[] nums) {
        int major = 0, count = 0;
        for (int num: nums) {
            if (count == 0) {
                count++;
                major = num;
            }
            else {
                count += (major == num ? 1 : -1);
            }
        }
        return major;
    }

    // 40. 最小的k个数
    public int[] getLeastNumbers(int[] arr, int k) {
        Arrays.sort(arr);
        return Arrays.copyOf(arr, k);
    }

    public int[] getLeastNumbers2(int[] arr, int k) {
        if (k == 0 || arr.length == 0) {
            return new int[0];
        }
        // 最后一个参数表示我们要找的是下标为k-1的数
        return quickSearch(arr, 0, arr.length - 1, k - 1);
    }

    private int[] quickSearch(int[] nums, int lo, int hi, int k) {
        // 每快排切分1次，找到排序后下标为j的元素，如果j恰好等于k就返回j以及j左边所有的数；
        int j = partition(nums, lo, hi);
        if (j == k) {
            return Arrays.copyOf(nums, j + 1);
        }
        // 否则根据下标j与k的大小关系来决定继续切分左段还是右段。
        return j > k? quickSearch(nums, lo, j - 1, k): quickSearch(nums, j + 1, hi, k);
    }

    // 快排切分，返回下标j，使得比nums[j]小的数都在j的左边，比nums[j]大的数都在j的右边。
    private int partition(int[] nums, int lo, int hi) {
        int v = nums[lo];
        int i = lo, j = hi + 1;
        while (true) {
            while (++i <= hi && nums[i] < v);
            while (--j >= lo && nums[j] > v);

            if (i >= j) {
                break;
            }
            int t = nums[j];
            nums[j] = nums[i];
            nums[i] = t;
        }
        nums[lo] = nums[j];
        nums[j] = v;
        return j;
    }

    // 41. 数据流中的中位数
    // 见MedianFinder类

    // 42. 连续子数组的最大和
    public int maxSubArray(int[] nums) {
        int res = nums[0];
        for (int i = 1; i < nums.length; i++) {
            nums[i] += Math.max(nums[i - 1], 0);
            res = Math.max(nums[i], res);
        }
        return res;
    }

    // 43. 1～n整数中1出现的次数
    public int countDigitOne(int n) {
        if (n <= 0) return 0;
        String temp = String.valueOf(n);
        int high = temp.charAt(0) - '0';
        int pow = (int)Math.pow(10, temp.length() - 1);
        int last = n - high * pow;
        if (high == 1) {
            return countDigitOne(pow - 1) + last + 1 + countDigitOne(last);
        }
        else {
            return high * countDigitOne(pow - 1) + countDigitOne(last) + pow;
        }
    }

    // 44. 数字序列中某一位的数字
    public int findNthDigit(int n) {
        /*
        位数               1      2           3              n
        最大的n            9    180+9    2700+180+9      9*10^(n-1)*n + 前一个
         */
        long target = 9;
        long weishu = 1;
        long pre = 0;
        while (target < n) {
            weishu += 1; // 升级一个位数
            pre = target;
            target = (long)(9 * Math.pow(10, weishu - 1) * weishu) + target;
        } // 出while后weishu为n对应数字的位数，pre为weishu-1位数字的最大n，
        long temp = n - pre; // 从第一位weishu位数字开始的第temp个数字。
        long count = temp / weishu, charat = temp % weishu;
        return String.valueOf((long)Math.pow(10, weishu - 1) + count).charAt((int)charat) - '0';
    }

    // 45. 把数组排成最小的数
    public String minNumber(int[] nums) {
        List<String> res = new ArrayList<>();
        for (int i: nums) {
            res.add(String.valueOf(i));
        }
//        res.sort(new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                return (o1+o2).compareTo(o2+o1);
//            }
//        });
        res.sort((a, b) -> (a + b).compareTo(b + a));
        StringBuilder res1 = new StringBuilder();
        for (String s: res) {
            res1.append(s);
        }
        return res1.toString();
    }

    // 46. 把数字翻译成字符串
    int count46 = 0;
    public int translateNum(int num) {
        count46 = 0;
        String number = String.valueOf(num);
        f(number.toCharArray(), 0);

        return count46;
    }

    private void f(char[] num, int n) {
        if (n == num.length) {
            count46++;
        }
        else {
            f(num, n + 1);
            if (n < num.length - 1 && num[n] != '0') {
                if ((num[n] - '0') * 10 + (num[n + 1] - '0') <= 25) {
                    f(num, n + 2);
                }
            }
        }
    }

    // 47. 礼物的最大价值
    int maxValue = 0;
    public int maxValue(int[][] grid) {
        int[][] dp = new int[grid.length][grid[0].length];
        dp[0][0] = grid[0][0];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                int left = i == 0 ? 0 : dp[i - 1][j];
                int top = j == 0 ? 0 : dp[i][j - 1];
                dp[i][j] = Math.max(left, top) + grid[i][j];
            }
        }

        return dp[dp.length - 1][dp[0].length - 1];
    }

    // 48. 最长不含重复字符的子字符串
    public int lengthOfLongestSubstring(String s) {
        int max_length = 0;
        for (int i = 0; i < s.length(); i++) {
            Set<Character> used_alphbet = new HashSet<>();
            for (int j = i; j < s.length(); j++) {
//                System.out.printf("i: %d, j : %d\n", i, j);
                if (used_alphbet.contains(s.charAt(j))) {
                    System.out.println(used_alphbet.toString());
                    if (used_alphbet.size() > max_length) {
                        max_length = used_alphbet.size();
                    }
                    break;
                }
                else {
                    used_alphbet.add(s.charAt(j));
                }
            }

            if (used_alphbet.size() > max_length) {
                System.out.println(used_alphbet.toString());
                max_length = used_alphbet.size();
            }
        }
        return max_length;
    }

    // 48. 最长不含重复字符的子字符串
    // 动态规划，记录每个字符结尾的最长长度，
    // 如果本字符的上一次出现位置，在上一个字符的长度之外，则上一个max+1，
    // 否则就直接返回当前位置-上次位置。
    public int lengthOfLongestSubstring2(String s) {
        int max_length = 0;
        HashMap<Character, Integer> distence = new HashMap<>();
        int last_max = 0;
        for (int j = 0; j < s.length(); j++) {
            int i = distence.getOrDefault(s.charAt(j), -1);
            distence.put(s.charAt(j), j);
            last_max = last_max < j - i ? last_max + 1 : j - i;
            max_length = Math.max(max_length, last_max);
        }
        return max_length;
    }

    // 49. 丑数
    public int nthUglyNumber(int n) {
        int i = 0;
        int num = 0;
        while (i < n) {
            num++;
            if (checkUgly(num)) {
                System.out.println(num);
                i++;
            }
        }
        return num;
    }

    private boolean checkUgly(int num) {
        if (num == 1 || num == 2 || num == 3 || num == 5 || num == 6 || num == 10 || num == 15 || num == 30) {
            return true;
        }
        if (num % 7 == 0 || num % 11 == 0 || num % 13 == 0 || num % 17 == 0 || num % 19 == 0 || num % 23 == 0) {
            return false;
        }
        int[] a = new int[]{2, 3, 5};
        boolean[] ress = new boolean[3];
        for (int i = 0; i < a.length; i++) {
            if (num % a[i] == 0) {
                ress[i] = checkUgly(num / a[i]);
            }
            else {
                ress[i] = false;
            }
        }
        return ress[0] || ress[1] || ress[2];
    }
    /* 49. 丑数 动态规划
    丑数 = 某较小丑数 × 某因子
     */
    public int nthUglyNumber2(int n) {
        int a = 0, b = 0, c = 0;
        int[] dp = new int[n];
        dp[0] = 1;
        for (int i = 1; i < n; i++) {
            int n2 = dp[a] * 2, n3 = dp[b] * 3, n5 = dp[c] * 5;
            dp[i] = Math.min(Math.min(n2, n3), n5);
            if (dp[i] == n2) a++;
            if (dp[i] == n3) b++;
            if (dp[i] == n5) c++;
        }
        return dp[n - 1];
    }

    // 采用最小堆
    public int nthUglyNumber3(int n) {
        PriorityQueue<Long> head = new PriorityQueue<>();
        HashSet<Long> visited = new HashSet<>();
        int[] ugly = new int[1690];
        int[] compnent = new int[]{2, 3, 5};
        head.add(1L);
        visited.add(1L);
        for (int i = 0; i < n; i++) {
            Long min_ugly = head.poll();
            assert min_ugly != null;
            ugly[i] = min_ugly.intValue();
            for (int j: compnent) {
                if (!visited.contains(min_ugly * j)) {
                    visited.add(min_ugly * j);
                    head.add(min_ugly * j);
                }
            }
        }
        return ugly[n - 1];
    }

    // 50. 第一个只出现一次的字符
    // 有序哈希表
    public char firstUniqChar(String s) {
        Map<Character, Boolean> res = new LinkedHashMap<>();
        for (char c: s.toCharArray()) {
            res.put(c, !res.containsKey(c));
        }
        for (Map.Entry<Character, Boolean> c: res.entrySet()) {
            if (c.getValue()) return c.getKey();
        }
        return ' ';
    }

    // 52. 两个链表的第一个公共节点
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        Set<ListNode> visited = new HashSet<>();
        while (headA != null) {
            visited.add(headA);
            headA = headA.next;
        }
        while (headB != null) {
            if (visited.contains(headB)) {
                return headB;
            }
            headB = headB.next;
        }
        return null;
    }

    // 链表A和B，通过在A后面拼接B，B后面拼接A，这样就实现两指针同步在A+B上运动。
    public ListNode getIntersectionNode2(ListNode headA, ListNode headB) {
        ListNode pa = headA, pb = headB;
        while (pa != pb) {
            pa = pa == null ? headB : pa.next;
            pb = pb == null ? headA : pb.next;
        }
        return pa;
    }

    // 53 - I. 在排序数组中查找数字 I
    // 二分查找
    public int search(int[] nums, int target) {
        if (nums.length == 0) return 0;
        if (nums.length == 1) {
            return nums[0] == target ? 1 : 0;
        }
        if (nums[nums.length / 2] < target) {
            return search(Arrays.copyOfRange(nums, nums.length / 2, nums.length), target);
        }
        else if (nums[nums.length / 2] > target) {
            return search(Arrays.copyOf(nums, nums.length / 2), target);
        }
        // 中间就是目标，左右分别遍历至非目标
        int pos = nums.length / 2;
        int count = 1;
        for (int i = pos - 1; i >= 0 && nums[i] == target; i--) {
            count++;
        }
        for (int i = pos + 1; i < nums.length && nums[i] == target; i++) {
            count++;
        }
        return count;
    }

    // 53 - II. 0～n-1中缺失的数字
    public int missingNumber(int[] nums) {
        if (nums.length == 1) {
            return nums[0] == 0 ? 1 : 0;
        }
        if (nums[nums.length - 1] == nums.length - 1) {
            return nums.length;
        }
        int i = 0, j = nums.length - 1;
        while (i != j) {
            int mid = (i + j) / 2;
            if (nums[mid] == mid) {
                i = mid + 1;
            }
            else {
                j = mid;
            }
        }
        return i;
    }

    // 54. 二叉搜索树的第k大节点
    int res = 0;
    public int kthLargest(TreeNode root, int k) {
        postMidOrder(root, k);
        return res;
    }

    private void postMidOrder(TreeNode root, int k) {
        if (root == null) return;
        postMidOrder(root.right, k);
        if (k == 0) return;
        if (--k == 0) res = root.val;
        postMidOrder(root.left, k);
    }

    // 55 - I. 二叉树的深度
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int depth = 0;
        // int i = 0, j = 0;
        Queue<TreeNode> level = new LinkedList<>();
        level.add(root);
        while (!level.isEmpty()) {
            int size = level.size();
            depth++;
            for (int i = 0; i < size; i++) {
                TreeNode temp = level.poll();
                if (temp.left != null) level.add(temp.left);
                if (temp.right != null) level.add(temp.right);
            }
        }
        return depth;
    }
}
