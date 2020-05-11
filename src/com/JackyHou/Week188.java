package com.JackyHou;

import com.sun.javafx.scene.control.skin.SpinnerSkin;

import java.util.*;

public class Week188 {

    public List<String> buildArray(int[] target, int n) {
        List<String> res = new ArrayList<>();
        int pos = 0;
        for (int i = 1; i <= n; i++) {
            if (pos == target.length) {
                break;
            }
            if (i == target[pos]) {
                res.add("Push");
                pos++;
            }
            else {
                res.add("Push");
                res.add("Pop");
            }
        }

        return res;
    }

    public int countTriplets(int[] arr) {
        int res = 0;

        for (int j = 1; j < arr.length; j++) {
            int a = 0;
            for (int i = j - 1; i >= 0; i--) {
                a ^= arr[i];
                int b = 0;
                for (int k = j; k < arr.length; k++) {
                    b ^= arr[k];

                    if (a == b) {
                        res++;
                    }
                }
            }
        }

        return res;
    }


    public int minTime(int n, int[][] edges, List<Boolean> hasApple) {
        int res = 0;
        boolean[] visited = new boolean[n];
        Map<Integer, Integer> from = new HashMap<>();
        for (int[] edge : edges) {
            from.put(edge[1], edge[0]);
        }
        for (int i = n - 1; i >= 0; i--) {
            if (hasApple.get(i)) {
                int f = 0;
                int pos = i;
                while(!visited[pos] && pos != 0) {
                    f++;
                    visited[pos] = true;
                    pos = from.get(pos);
                }
                res += f;
            }
        }
        return res * 2;
    }

    public int ways(String[] pizza, int k) {
        long mod = (long) (1e9+7);

        int row = pizza.length;
        int col = pizza[0].length();

        // 计算前缀和num
        int[][] num = new int[row][col];
        num[0][0] = pizza[0].charAt(0) == 'A' ? 1 : 0;
        for (int i = 1; i < row; i++) {
            num[i][0] = num[i - 1][0] + (pizza[i].charAt(0) == 'A' ? 1 : 0);
        }
        for (int i = 1; i < col; i++) {
            num[0][i] = num[0][i - 1] + (pizza[0].charAt(i) == 'A' ? 1 : 0);
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                num[i][j] = num[i - 1][j] + num[i][j - 1] - num[i - 1][j - 1] + (pizza[i].charAt(j) == 'A' ? 1 : 0);
            }
        }

        int[][][] dp = new int[row][col][k + 1];
        dp[0][0][1] = 1;

        for (int x = 2; x <= k; x++) {
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    // dp为0代表不存在此情况
                    if (dp[i][j][x - 1] == 0) continue;
                    // 穷举水平切
                    for (int z = i + 1; z < row; z++) {
                        if (hasA(num, i, j, z - 1, col - 1) && hasA(num, z, j, row - 1, col - 1)) {
                            dp[z][j][x] += dp[i][j][x - 1];
                            dp[z][j][x] %= mod;
                        }
                    }
                    // 穷举垂直切
                    for (int z = j + 1; z < col; z++) {
                        if (hasA(num, i, j, row - 1, z - 1) && hasA(num, i, z, row - 1, col - 1)) {
                            dp[i][z][x] += dp[i][j][x - 1];
                            dp[i][z][x] %= mod;
                        }
                    }
                }
            }
        }
        long res = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                res += dp[i][j][k];
            }
            res %= mod;
        }
        return (int) res;
    }

    private boolean hasA(int[][] num, int sr, int sc, int er, int ec) {
        int num1 = 0, num2 = 0, num3 = 0, res = 0;
        if (sr != 0 && sc != 0) num1 = num[sr - 1][sc - 1];
        if (sr != 0) num2 = num[sr - 1][ec];
        if (sc != 0) num3 = num[er][sc - 1];
        return num[er][ec] - num2 - num3 + num1 > 0;
    }
}
