package com.JackyHou;

import java.util.HashSet;
import java.util.Set;

public class Solution {
    public int findRepeatNumber(int[] nums) {
        Set<Integer> number = new HashSet<Integer>();
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
}
