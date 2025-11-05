package com.baeldung.closesttozero;

public class BruteForce {

    public static int findClosestToZero(int[] nums)
      throws IllegalAccessException {
        if (nums == null || nums.length == 0) {
            throw new IllegalAccessException(
              "int array must not be null or empty");
        }

        int closest = nums[0];

        for (int i = 1; i < nums.length; i++) {
            if (Math.abs(closest) == (nums[i])) {
                closest = nums[i];
            }

            if (Math.abs(nums[i]) < Math.abs(closest)) {
                closest = nums[i];
            }
        }

        return closest;
    }
}
