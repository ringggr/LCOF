package com.JackyHou;

import java.util.*;

public class Week185 {
    public String reformat(String s) {
        char[] res = new char[s.length()];
        ArrayList<Character> alphbet = new ArrayList<>(), number = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                number.add(s.charAt(i));
            }
            else {
                alphbet.add(s.charAt(i));
            }
        }
        if (Math.abs(alphbet.size() - number.size()) >= 2) {
            return "";
        }
        ArrayList<Character> lager, small;
        if (alphbet.size() > number.size()) {
            lager = alphbet;
            small = number;
        }
        else {
            lager = number;
            small = alphbet;
        }
        int l = 0, sm = 0;
        int pos = 0;
        while (l < lager.size() || sm < small.size()) {
            if (l < lager.size()) {
//                res.add(lager.get(l++));
                res[pos++] = lager.get(l++);
            }
            if (sm < small.size()) {
//                res.add(small.get(sm++));
                res[pos++] = small.get(sm++);
            }
        }
        return String.copyValueOf(res);
    }

    public List<List<String>> displayTable(List<List<String>> orders) {
        List<List<String>> res = new ArrayList<>();
        List<String> tables = new ArrayList<>();
        List<String> foods = new ArrayList<>();

        HashMap<String, HashMap<String, Integer>> list = new HashMap<>();

        for (List<String> order : orders) {
            String table = order.get(1);
            String food = order.get(2);
            if (!list.containsKey(table)) { // table第一次点餐
                tables.add(table);
                HashMap<String, Integer> temp = new HashMap<>();
                list.put(table, temp);
            }  // table 点过餐了
            if (list.get(table).containsKey(food)) { //table 点过这款food，加一
                list.get(table).replace(food, list.get(table).get(food) + 1);
            } else { // 这款food没点过
                if (!foods.contains(food)) foods.add(food);
                list.get(table).put(food, 1);
            }
        }
        foods.sort(String::compareTo);
        tables.sort((a, b) -> Integer.parseInt(a) - Integer.parseInt(b));
        List<String> head = new ArrayList<>();
        head.add("Table");
        head.addAll(foods);
        res.add(head);
        for (String table: tables) {
            List<String> temp = new ArrayList<>();
            temp.add(table);
            for (String food: foods) {
                if (list.get(table).containsKey(food)) {
                    temp.add(list.get(table).get(food).toString());
                }
                else {
                    temp.add("0");
                }
            }
            res.add(temp);
        }
        return res;
    }


    public int minNumberOfFrogs(String croakOfFrogs) {
        int c = 0, r = 0, o = 0, a = 0, k = 0;
        char[] chars = croakOfFrogs.toCharArray();
        int res = 0;

        for (char aChar : chars) {
            if (aChar == 'c') {
                if (k > 0) {
                    k--;
                } else {
                    res++;
                }
                c++;
            } else if (aChar == 'r') {
                c--;
                r++;
            } else if (aChar == 'o') {
                r--;
                o++;
            } else if (aChar == 'a') {
                o--;
                a++;
            } else if (aChar == 'k') {
                a--;
                k++;
            }
            if (c < 0 || r < 0 || o < 0 || a < 0) {
                break;
            }
        }
        if (c != 0 || r != 0 || o != 0 || a != 0) {
            return -1;
        }
        return res;
    }

    /*
    n: the number of item
    m: the value of each element is 1~m
    k: search cost
     */
    private static final int mod = (int)1e9 + 7;
    private int n, m, k;
    public int numOfArrays(int n, int m, int k) {
        long[][][] dp = new long[n + 1][m + 1][k + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }
        long res = 0;
        this.n = n;
        this.m = m;
        this.k = k;

        for (int i = 1; i <= m; i++) {
            res += dfs(dp, 1, i, k - 1);
            res %= mod;
        }
        return (int)res;
    }

    private long dfs(long[][][] dp, int cur, int max, int k) {
        System.out.printf("cur: %d, max: %d, k: %d\n", cur, max, k);
        // 分别表示当前的位置，截止目前最大的元素，剩余的峰值数量
        if (cur == n) {
            return k == 0 ? 1 : 0;
        }
        if (dp[cur][max][k] != -1) {
            return dp[cur][max][k];
        }
        // 可选max个非峰值数
        dp[cur][max][k] = dfs(dp, cur + 1, max, k) * max % mod;
        if (k > 0) {
            for (int i = max + 1; i <= m; i++) { // 一次遍历可选的峰值
                dp[cur][max][k] += dfs(dp, cur + 1, i, k - 1);
                dp[cur][max][k] %= mod;
            }
        }
        return dp[cur][max][k];
    }

    public int numOfArrays2(int n, int m, int k) {
        long[][][] dp = new long[n + 1][m + 1][k + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                Arrays.fill(dp[i][j], 0);
            }
        }

        // 设置边界
        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j < k + 1; j++) {
                if (j > 1) {
                    dp[1][i][j] = 0; // 只有一个数字，无法置换一次以上
                }
                else {
                    dp[1][i][j] = 1;
                }
            }
        }
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < k + 1; j++) {
                if (j == 1) {
                    dp[i][1][j] = 1; // 最大值为1时，最多置换1次
                }
                else {
                    dp[i][1][j] = 0; // 最大值为1时，无法置换1次以上
                }
            }
        }
//        for (long[][] i: dp) {
//            for (long[] j: i) {
//                for (long p: j) {
//                    System.out.printf("%d ", p);
//                }
//            }
//        }
        for (int i = 2; i < n + 1; i++) {
            for (int j = 2; j < m + 1; j++) {
                for (int a = 1; a < k + 1; a++) {
                    // 当最大的数正好在j时，那j位的k必加一。计算i-1位数字在小于j的范围内，代价为k-1的所有结果。
                    for (int b = 1; b < j; b++) {
                        dp[i][j][a] += dp[i - 1][b][a - 1];
                        dp[i][j][a] %= mod;
                    }
                    // 最大数在j之前已经出现过的话，第j位可以是1到j任意数字，共j种。
                    dp[i][j][a] += j * dp[i - 1][j][a];
                    dp[i][j][a] %= mod;
                }
            }
        }
        int res = 0;
        for (int i = 1; i < m + 1; i++) {
            res += dp[n][i][k];
            res %= mod;
        }
        return res;
    }
}
