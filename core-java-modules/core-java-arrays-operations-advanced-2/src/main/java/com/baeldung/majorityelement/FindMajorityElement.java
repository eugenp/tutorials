package com.baeldung.majorityelement;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FindMajorityElement {

    public static int findMajorityElementUsingForLoop(int[] nums) {
        int majorityThreshold = nums.length / 2;
        int majorityElement = -1;
        for (int i = 0; i < nums.length; i++) {
            int count = 0;
            for (int j = 0; j < nums.length; j++) {
                if (nums[i] == nums[j]) {
                    count++;
                }
            }
            if (count > majorityThreshold) {
                return majorityElement = nums[i];
            }
        }
        return majorityElement;
    }

    public static int findMajorityElementUsingSorting(int[] nums) {
        Arrays.sort(nums);
        int majorityThreshold = nums.length / 2;
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == nums[majorityThreshold]) {
                count++;
            }

            if (count > majorityThreshold) {
                return nums[majorityThreshold];
            }
        }
        return -1;
    }

    public static int findMajorityElementUsingHashMap(int[] nums) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();

        for (int num : nums) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }

        int majorityThreshold = nums.length / 2;
        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > majorityThreshold) {
                return entry.getKey();
            }
        }
        return -1;
    }

    public static int findMajorityElementUsingMooreVoting(int[] nums) {
        int majorityThreshold = nums.length / 2;
        int candidate = nums[0];
        int count = 1;

        for (int i = 1; i < nums.length; i++) {
            if (count == 0) {
                candidate = nums[i];
                count = 1;
            } else if (candidate == nums[i]) {
                count++;
            } else {
                count--;
            }

            System.out.println("Iteration " + i + ": [candidate - " + candidate + ", count - " + count + ", element - " + nums[i] + "]");
        }

        count = 0;
        for (int num : nums) {
            if (num == candidate) {
                count++;
            }
        }
        return count > majorityThreshold ? candidate : -1;
    }

    public static void main(String[] args) {
        int[] nums = { 8, 7, 7, 9, 9, 9, 7, 9, 9 };
        int majorityElement = findMajorityElementUsingMooreVoting(nums);

        if (majorityElement != -1) {
            System.out.println("Majority element with maximum occurrences: " + majorityElement);
        } else {
            System.out.println("No majority element found");
        }
    }
}
