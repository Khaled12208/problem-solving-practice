package com.problemsolving.arrays;

import java.util.*;

/**
 * Array Manipulation Problems
 * Practice problems focused on array operations and algorithms
 */
public class ArrayProblems {
    
    /**
     * Problem 1: Find the maximum subarray sum (Kadane's Algorithm)
     * 
     * Given an array of integers, find the contiguous subarray with the largest sum.
     * 
     * Example:
     * Input: [-2, 1, -3, 4, -1, 2, 1, -5, 4]
     * Output: 6 (subarray [4, -1, 2, 1])
     * 
     * TODO: Implement this method
     */
    public int maxSubArraySum(int[] nums) {
        int res = nums[0];
        int maxEnding = nums[0];

        for(int i = 1; i < nums.length; i++){
            maxEnding = Math.max(maxEnding + nums[i], nums[i]);
            res = Math.max(res, maxEnding);
        }
        return res;
    }
    
    /**
     * Problem 2: Two Sum
     * 
     * Given an array of integers nums and an integer target, return indices of the two numbers 
     * such that they add up to target.
     * 
     * Example:
     * Input: nums = [2, 7, 11, 15], target = 9
     * Output: [0, 1] (because nums[0] + nums[1] = 2 + 7 = 9)
     * 
     * TODO: Implement this method
     */
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < nums.length; i++){
            if(map.containsKey(target - nums[i])){
                return new int[]{map.get(target - nums[i]), i};
            }
            map.put(nums[i], i);
        }
        return new int[0];
        
    }
    
    /**
     * Problem 3: Rotate Array
     * 
     * Given an array, rotate the array to the right by k steps, where k is non-negative.
     * 
     * Example:
     * Input: nums = [1, 2, 3, 4, 5, 6, 7], k = 3
     * Output: [5, 6, 7, 1, 2, 3, 4]
     * 
     * TODO: Implement this method
     */
    public void rotateArray(int[] nums, int k) {
        int n = nums.length;
        k = k % n;
        reverse(nums, 0, n-1);
        reverse(nums, 0, k-1);
        reverse(nums, k, n-1);
    }

    public void reverse(int[] nums, int start, int end){
        while(start < end){
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }
    
    /**
     * Problem 4: Find Missing Number
     * 
     * Given an array nums containing n distinct numbers in the range [0, n], 
     * return the only number in the range that is missing from the array.
     * 
     * Example:
     * Input: nums = [3, 0, 1]
     * Output: 2 (because 2 is missing from the array)
     * 
     * TODO: Implement this method
     */
    public int missingNumber(int[] nums) {  
        int n = nums.length;
        int res = 0;
        for(int i = 0; i < n; i++){
            res = res ^ nums[i] ^ (i+1);
        }
        return res;
    }
    
    /**
     * Problem 5: Product of Array Except Self
     * 
     * Given an integer array nums, return an array answer such that answer[i] is equal 
     * to the product of all the elements of nums except nums[i].
     * 
     * Example:
     * Input: nums = [1, 2, 3, 4]
     * Output: [24, 12, 8, 6]
     * 
     * TODO: Implement this method
     */
    public int[] productExceptSelf(int[] nums) {   
        int n = nums.length;
        int[] res = new int[n];
        int left = 1;
        for(int i = 0; i < n; i++){
            res[i] = left;
            left = left * nums[i];
        }
        int right = 1;
        for(int i = n-1; i >= 0; i--){
            res[i] = res[i] * right;
            right = right * nums[i];
        }
        return res;
    }
    
    /**
     * Problem 6: Find Duplicate Number
     * 
     * Given an array of integers nums containing n + 1 integers where each integer 
     * is in the range [1, n] inclusive, find the duplicate number.
     * 
     * Example:
     * Input: nums = [1, 3, 4, 2, 2]
     * Output: 2
     * 
     * TODO: Implement this method
     */
    public int findDuplicate(int[] nums) {
        int slow = nums[0];
        int fast = nums[0];
        do{
            slow = nums[slow];
            fast = nums[nums[fast]];
        }while(slow != fast);
        slow = nums[0]; 
        while(slow != fast){
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow;
    }
    
    /**
     * Problem 7: Move Zeroes
     * 
     * Given an integer array nums, move all 0's to the end of it while maintaining 
     * the relative order of the non-zero elements.
     * 
     * Example:
     * Input: nums = [0, 1, 0, 3, 12]
     * Output: [1, 3, 12, 0, 0]
     * 
     * TODO: Implement this method
     */
    public void moveZeroes(int[] nums) {
        int nonZeroIndex = 0;
        for(int i = 0; i < nums.length; i++){
            if(nums[i] != 0){
                nums[nonZeroIndex] = nums[i];
                nonZeroIndex++;
            }
        }
        for(int i = nonZeroIndex; i < nums.length; i++){
            nums[i] = 0;
        }
    }
    
    /**
     * Problem 8: Container With Most Water
     * 
     * Given n non-negative integers height where each represents a point at coordinate (i, height[i]),
     * find two lines that together with the x-axis form a container that would hold the most water.
     * 
     * Example:
     * Input: height = [1, 8, 6, 2, 5, 4, 8, 3, 7]
     * Output: 49
     * 
     * TODO: Implement this method
     */
    public int maxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int maxArea = 0;
        
        while(left < right){
            int width = right - left;
            int h = Math.min(height[left], height[right]);
            maxArea = Math.max(maxArea, width * h);
            
            if(height[left] < height[right]){
                left++;
            } else {
                right--;
            }
        }
        return maxArea;
    }
} 