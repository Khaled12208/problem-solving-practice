package com.problemsolving.twopointers;

import java.util.*;

/**
 * Two Pointers Problems
 * Classic problems that use the two pointers or three pointers technique for efficient array/string processing.
 */
public class TwoPointersProblems {
    /**
     * Problem 1: Two Sum II - Input array is sorted
     * Given a sorted array and a target, return indices of the two numbers such that they add up to target.
     * Indices are 1-based.
     */
    public int[] twoSumSorted(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int sum = nums[left] + nums[right];
            if (sum == target) return new int[]{left + 1, right + 1};
            else if (sum < target) left++;
            else right--;
        }
        return new int[0];
    }

    /**
     * Problem 2: Remove Duplicates from Sorted Array
     * Return the length of the array after removing duplicates in-place.
     */
    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;
        int i = 0;
        for (int j = 1; j < nums.length; j++) {
            if (nums[j] != nums[i]) {
                i++;
                nums[i] = nums[j];
            }
        }
        return i + 1;
    }

    /**
     * Problem 3: Reverse String
     * Reverse the input char array in-place.
     */
    public void reverseString(char[] s) {
        int left = 0, right = s.length - 1;
        while (left < right) {
            char temp = s[left];
            s[left] = s[right];
            s[right] = temp;
            left++;
            right--;
        }
    }

    /**
     * Problem 4: Valid Palindrome
     * Return true if the string is a palindrome, considering only alphanumeric characters and ignoring cases.
     */
    public boolean isPalindrome(String s) {
        int left = 0, right = s.length() - 1;
        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) left++;
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) right--;
            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) return false;
            left++;
            right--;
        }
        return true;
    }

    /**
     * Problem 5: 3Sum
     * Find all unique triplets in the array which gives the sum of zero.
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            int left = i + 1, right = nums.length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    res.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    while (left < right && nums[right] == nums[right - 1]) right--;
                    left++;
                    right--;
                } else if (sum < 0) left++;
                else right--;
            }
        }
        return res;
    }

    /**
     * Problem 6: Trapping Rain Water
     * Given n non-negative integers representing an elevation map, compute how much water it can trap after raining.
     */
    public int trap(int[] height) {
        int left = 0, right = height.length - 1, leftMax = 0, rightMax = 0, res = 0;
        while (left < right) {
            if (height[left] < height[right]) {
                if (height[left] >= leftMax) leftMax = height[left];
                else res += leftMax - height[left];
                left++;
            } else {
                if (height[right] >= rightMax) rightMax = height[right];
                else res += rightMax - height[right];
                right--;
            }
        }
        return res;
    }

    /**
     * Problem 7: Squares of a Sorted Array
     * Given a sorted array, return an array of the squares of each number, also sorted.
     */
    public int[] sortedSquares(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        int left = 0, right = n - 1, pos = n - 1;
        while (left <= right) {
            if (Math.abs(nums[left]) > Math.abs(nums[right])) {
                res[pos--] = nums[left] * nums[left++];
            } else {
                res[pos--] = nums[right] * nums[right--];
            }
        }
        return res;
    }

    /**
     * Problem 8: Move Zeroes
     * Move all zeroes to the end of the array while maintaining the relative order of the non-zero elements.
     */
    public void moveZeroes(int[] nums) {
        int insertPos = 0;
        for (int num : nums) if (num != 0) nums[insertPos++] = num;
        while (insertPos < nums.length) nums[insertPos++] = 0;
    }

    /**
     * Problem 9: Remove Element
     * Remove all instances of val in-place and return the new length.
     */
    public int removeElement(int[] nums, int val) {
        int i = 0;
        for (int j = 0; j < nums.length; j++) {
            if (nums[j] != val) nums[i++] = nums[j];
        }
        return i;
    }

    /**
     * Problem 10: 4Sum
     * Find all unique quadruplets in the array which gives the sum of target.
     */
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        int n = nums.length;
        for (int i = 0; i < n - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            for (int j = i + 1; j < n - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) continue;
                int left = j + 1, right = n - 1;
                while (left < right) {
                    long sum = (long) nums[i] + nums[j] + nums[left] + nums[right];
                    if (sum == target) {
                        res.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                        while (left < right && nums[left] == nums[left + 1]) left++;
                        while (left < right && nums[right] == nums[right - 1]) right--;
                        left++;
                        right--;
                    } else if (sum < target) left++;
                    else right--;
                }
            }
        }
        return res;
    }

    /**
     * Problem 11: 3Sum Closest
     * Find three integers in nums such that the sum is closest to target.
     */
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int closest = nums[0] + nums[1] + nums[2];
        for (int i = 0; i < nums.length - 2; i++) {
            int left = i + 1, right = nums.length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (Math.abs(sum - target) < Math.abs(closest - target)) closest = sum;
                if (sum < target) left++;
                else if (sum > target) right--;
                else return sum;
            }
        }
        return closest;
    }

    /**
     * Problem 12: Sort Colors (Dutch National Flag)
     * Sort an array with 0s, 1s, and 2s in-place.
     */
    public void sortColors(int[] nums) {
        int low = 0, mid = 0, high = nums.length - 1;
        while (mid <= high) {
            if (nums[mid] == 0) {
                int temp = nums[low]; nums[low] = nums[mid]; nums[mid] = temp;
                low++; mid++;
            } else if (nums[mid] == 1) {
                mid++;
            } else {
                int temp = nums[mid]; nums[mid] = nums[high]; nums[high] = temp;
                high--;
            }
        }
    }
} 