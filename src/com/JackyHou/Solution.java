package com.JackyHou;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Solution {
    public int findRepeatNumber(int[] nums) {
        Set<Integer> number = new HashSet<>();
        for (Integer i: nums
             ) {
//            System.out.println(i);
            if (number.contains(i)) {
//                System.out.println(number.toString() + i);
                return i;
            }
            else {
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
            }
            else {
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
            }
            else {
                right_preorder[temp_right++] = preorder[i];
            }
        }

        now.left = buildTree(left_preorder, left_inorder); // 左子树构建
        now.right = buildTree(right_preorder, right_inorder); // 右子树构建

        return now;
    }
}
