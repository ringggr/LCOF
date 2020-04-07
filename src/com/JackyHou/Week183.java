package com.JackyHou;

import java.util.*;

import static java.lang.Integer.max;

public class Week183 {
    /*
    第183场周赛 https://leetcode-cn.com/circle/discuss/DyKK75/
     */
    public List<Integer> minSubsequence(int[] nums) {
        List<Integer> result = new ArrayList<>();

        Arrays.sort(nums);
        int total = 0;
        for (int num : nums) {
            total += num;
        }
        int sum = 0;
        for (int i = nums.length - 1; i >= 0; i--) {
            result.add(nums[i]);
            sum += nums[i];
            if (sum > total - sum) {
                return result;
            }
        }
        return result;
    }

    public int numSteps(String s) {
        int i = 0;
        if (s.equals("1")) return i;
        do {
            i++;
            s = f(s);
            if (s.charAt(0) == '0') {
                s = "1" + s;
            }
        } while (!s.equals("1"));
        return i;
    }

    private String f(String s) {
        char[] re = s.toCharArray();
        if (re[s.length() - 1] == '1') {
            re[s.length() - 1] = '0';
            int i = s.length() - 2;
            while (re[i] == '1') {
                re[i] = '0';
                i--;
                if (i == -1) {
                    break;
                }
            }
            if (i >= 0) {
                re[i] = '1';
            }
            return String.valueOf(re);
        }
        return s.substring(0, s.length() - 1);
    }

    public String longestDiverseString(int a, int b, int c) {
        char[] result = new char[a + b + c];
        int len = 0;

        Map<Character, Integer> map = new TreeMap<>();
        map.put('a', a);
        map.put('b', b);
        map.put('c', c);


        char last = ' ';
        List<Map.Entry<Character, Integer>> list = new ArrayList<>(map.entrySet());
        Map.Entry<Character, Integer> now;
        while (map.get('a') > 0 || map.get('b') > 0 || map.get('c') > 0) {
            list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
//            System.out.println(list.toString());
            now = (last == list.get(0).getKey()) ? list.get(1) : list.get(0);
            if (now.getValue() == 0) {
                break;
            }
            if (now.getValue() == 1) {
                result[len++] = now.getKey();
                now.setValue(now.getValue() - 1);
                last = now.getKey();
            }
            else {
                result[len++] = now.getKey();
                result[len++] = now.getKey();
                now.setValue(now.getValue() - 2);
                last = now.getKey();
            }
        }

        char now_char = list.get(0).getKey();
        int n = list.get(0).getValue();
        while (n > 0) {
            char pre = result[0];
            int pre_len = len;
            int n_tag = n;
            for (int i = 1; i < pre_len; i++) {
                if (result[i] == pre && result[i] != now_char) {
                    if (n >= 2) {
                        result = insert(result, now_char, i);
                        result = insert(result, now_char, i);
                        len += 2;
                        n = n - 2;
                    }
                    else if (n == 1) {
                        result = insert(result, now_char, i);
                        n--;
                        len++;
                    }
                    else if (n == 0) {
                        break;
                    }
                }
                pre = result[i];
            }
            if (n_tag == n) break;
        }
//        System.out.println(list.toString());
        return String.valueOf(Arrays.copyOf(result, len));
    }

    private char[] insert(char[] result, char a, int index) {
        char[] temp = new char[result.length + 1];

        for (int i = 0; i < temp.length; i++) {
            if (i < index) {
                temp[i] = result[i];
            }
            if (i == index) {
                temp[i] = a;
            }
            if (i > index) {
                temp[i] = result[i - 1];
            }
        }

        return temp;
    }

    public String stoneGameIII(int[] stoneValue) {
        int[] dp = new int[50003];
        int sum = 0;

        for (int n = stoneValue.length, i = n - 1; i >= 0; i--) {
            dp[i] = Integer.MIN_VALUE;
            sum += stoneValue[i];
            for (int j = 1; j <= 3; j++) {
                dp[i] = max(dp[i], sum - dp[i + j]);
            }
        }
        System.out.println(Arrays.toString(Arrays.copyOf(dp, stoneValue.length)));
        if (sum - dp[0] > dp[0]) {
            return "Bob";
        }
        else if (sum - dp[0] == dp[0]) {
            return "Tie";
        }
        return "Alice";
    }
}
