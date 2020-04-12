package com.JackyHou;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class week184 {

    public List<String> stringMatching(String[] words) {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j < words.length; j++) {
                if (i != j) {
                    if (words[i].contains(words[j])) {
                        if (!res.contains(words[j])) {
                            res.add(words[j]);
                        }
                        break;
                    }
                }
            }
        }

        return res;
    }

    public int[] processQueries(int[] queries, int m) {
        int[] p = new int[m];
        int[] res = new int[queries.length];
        for (int i = 0; i < m; i++) {
            p[i] = i + 1;
        }

        for (int i = 0; i < queries.length; i++) {
            int val = queries[i];
            int pos = 0;
            for (int j = 0; j < m; j++) {
                if (p[j] == val) {
                    pos = j;
                    break;
                }
            }
            res[i] = pos;
            System.arraycopy(p, 0, p, 1, pos);
            p[0] = val;

        }
        return res;
    }

    public String entityParser(String text) {
        HashMap<String, Character> trans = new HashMap<>();
        trans.put("&quot;", '"');
        trans.put("&apos;", '\'');
        trans.put("&amp;", '&');
        trans.put("&gt;", '>');
        trans.put("&lt;", '<');
        trans.put("&frasl;", '/');

        char[] res = new char[text.length()];
        int n = 0;
        for (int i = 0; i < text.length();) {
            if (text.charAt(i) == '&') {
                for (int j = i; j < text.length(); j++) {
                    if (text.charAt(j) == ';') {
                        String temp = text.substring(i, j + 1);
                        if (trans.containsKey(temp)) {
                            res[n++] = trans.get(temp);
                            i = j + 1;
                            break;
                        }
                        else {
                            res[n++] = '&';
                            i++;
                        }
                    }
                }
            }
            else {
                res[n++] = text.charAt(i);
                i++;
            }
        }
        return String.copyValueOf(res,0, n);
    }

    int count = 0;
    public int numOfWays(int n) {
        if (n == 0) return 0;
        int[][] grid = new int[n][3];
//        boolean[][] visited = new boolean[n][3];

        f(0, 0, n, grid);

        return count;
    }

    private void f(int i, int j, int n, int[][] grid) {
        if (j == 3) {
            i++;
            j = 0;
        }
        if (i == n) { // 最后一块
            count = (int)((count + 1) % (10e9 + 7 ));
        }
        else {
            for (int color = 0; color < 3; color++) {
                grid[i][j] = color;
                if (i > 0) {
                    // 不是第一行
                    if (grid[i][j] == grid[ i - 1][j]) continue;
                }
                if (j > 0) {
                    // 不是第一列
                    if (grid[i][j] == grid[i][j - 1]) continue;
                }
                f(i, j + 1, n, grid);
            }
        }
    }

    public int numOfWays2(int n) {
        if (n == 0)
            return 0;
        else if (n == 1)
            return 12;
        long mod = (long)(1e9+7);
        long aba = 6;
        long abc = 6;
        for(int i = 2; i <=n; i++)
        {
            long new_aba = ((aba * 3) % mod + (abc * 2) % mod) % mod;
            long new_abc = ((aba * 2) % mod + (abc * 2) % mod) % mod;
            aba = new_aba;
            abc = new_abc;
        }
        return (int)((aba + abc) % mod);
    }

}
