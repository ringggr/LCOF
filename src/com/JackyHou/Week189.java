package com.JackyHou;

import java.util.*;
import java.util.stream.Collectors;

public class Week189 {
    // 5412
    public int busyStudent(int[] startTime, int[] endTime, int queryTime) {
        int count = 0;
        for (int i = 0; i < startTime.length; i++) {
            if (startTime[i] <= queryTime && endTime[i] >= queryTime) {
                count++;
            }
        }
        return count;
    }

    // 5413
    public String arrangeWords(String text) {
        ArrayList<String> res = new ArrayList<>();
        int left = 0, right = 0;
        text = text.trim();
        text = String.valueOf((char)(text.charAt(0) - 'A' + 'a')) + text.substring(1, text.length());
        for (right= 0; right < text.length(); right++) {
            if(text.charAt(right) == ' ') {
                res.add(text.substring(left, right));
                left = right + 1;
            }
        }
        if (text.charAt(right - 1) != ' ') {
            res.add(text.substring(left, right));
        }
        res.sort((a, b) -> (a.length() - b.length()));
        StringBuilder t = new StringBuilder();
        for (String s: res) {
            t.append(s).append(' ');
        }
        String ans = t.toString();
        return String.valueOf((char)(ans.charAt(0) - 'a' + 'A')) + ans.substring(1, t.length() - 1);
    }

    // 5414
    public List<Integer> peopleIndexes(List<List<String>> favoriteCompanies) {
        List<Integer> res = new ArrayList<>();
        List<Set<String>> list = new ArrayList<>();
        //用hashset结构来重构原有的数组，便于后续查找
        for (List<String> companies: favoriteCompanies) {
            Set<String> set = new HashSet<>(companies);
            list.add(set);
        }
        for (int i = 0; i < favoriteCompanies.size(); i++) {
            Set<String> s = list.get(i);
            boolean flag = true;
            for (int j = 0; j < favoriteCompanies.size(); j++) {
                //当其他的收藏长度比当前长度长时，就需要判断当前的是否为其他的子收藏
                if (list.get(j).size() >= s.size() && i != j) {
                    if (list.get(j).containsAll(s)) {
                        flag = false;  //一旦发现是其他的子收藏就跳出，不用再查找
                        break;
                    }
                }
            }
            if (flag) res.add(i);
        }
        return res;
    }

    // 5415. 圆形靶内的最大飞镖数量
    // https://leetcode-cn.com/problems/maximum-number-of-darts-inside-of-a-circular-dartboard/
    public int numPoints(int[][] points, int r) {
        return 0;
    }
}


