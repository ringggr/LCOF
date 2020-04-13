package com.JackyHou;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Codec {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        ArrayList<Integer> ans = new ArrayList<>();
        if (root == null) {
            return ans.toString();
        }
        Queue<TreeNode> level = new LinkedList<>();
        level.offer(root);
        while (!level.isEmpty()) {
            TreeNode temp = level.poll();
            if (temp == null) {
                ans.add(null);
            }
            else {
                ans.add(temp.val);
                level.add(temp.left);
                level.add(temp.right);
            }
        }
        while (ans.get(ans.size() - 1) == null) {
            ans.remove(ans.size() - 1);
        }
        return ans.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        data = data.replaceAll(" ", "");
        if (data.equals("[]")) return null;
        data = data.substring(1, data.length() - 1);
        String[] ans = data.split(",");
//        System.out.println(Integer.valueOf(null));
        Queue<TreeNode> level = new LinkedList<>();
        TreeNode root = new TreeNode(Integer.parseInt(ans[0]));
        level.offer(root);
        int n = 1;
        while (!level.isEmpty()) {
            TreeNode temp = level.poll();
            if (n == ans.length) break;
            if (!ans[n].equals("null")) {
                TreeNode left = new TreeNode(Integer.parseInt(ans[n]));
                temp.left = left;
                level.offer(left);
            }
            n++;
            if (n == ans.length) break;
            if (!ans[n].equals("null")) {
                TreeNode right = new TreeNode(Integer.parseInt(ans[n]));
                temp.right = right;
                level.offer(right);
            }
            n++;
        }

        return root;
    }
}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.deserialize(codec.serialize(root));