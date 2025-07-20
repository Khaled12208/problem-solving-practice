package com.problemsolving.windows;

import java.util.*;

/**
 * Sliding Window Problems
 * Practice problems focused on sliding window technique for efficient array/string processing
 */
public class SlidingWindowProblems {
    
    /**
     * Problem 1: Maximum Sum Subarray of Size K
     * 
     * Given an array of integers and a number k, find the maximum sum of a subarray of size k.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     * 
     * Example:
     * Input: arr = [2, 1, 5, 1, 3, 2], k = 3
     * Output: 9 (subarray [5, 1, 3] has maximum sum)
     * 
     * TODO: Implement this method
     */
    public int maxSumSubarrayOfSizeK(int[] arr, int k) {
        // TODO: Your implementation here
        return 0;
    }
    
    /**
     * Problem 2: Smallest Subarray with Given Sum
     * 
     * Given an array of positive integers and a positive number S, find the length of the smallest
     * contiguous subarray whose sum is greater than or equal to S.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     * 
     * Example:
     * Input: arr = [2, 1, 5, 2, 3, 2], S = 7
     * Output: 2 (subarray [5, 2] has sum 7)
     * 
     * TODO: Implement this method
     */
    public int smallestSubarrayWithGivenSum(int[] arr, int S) {
        // TODO: Your implementation here
        return 0;
    }
    
    /**
     * Problem 3: Longest Substring with K Distinct Characters
     * 
     * Given a string, find the length of the longest substring that contains at most k distinct characters.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(k)
     * 
     * Example:
     * Input: s = "eceba", k = 2
     * Output: 3 ("ece" has 2 distinct characters)
     * 
     * TODO: Implement this method
     */
    public int longestSubstringWithKDistinct(String s, int k) {
        // TODO: Your implementation here
        return 0;
    }
    
    /**
     * Problem 4: Longest Substring Without Repeating Characters
     * 
     * Given a string, find the length of the longest substring without repeating characters.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(min(m, n)) where m is the size of the charset
     * 
     * Example:
     * Input: s = "abcabcbb"
     * Output: 3 ("abc" is the longest substring without repeating characters)
     * 
     * TODO: Implement this method
     */
    public int longestSubstringWithoutRepeating(String s) {
        // TODO: Your implementation here
        return 0;
    }
    
    /**
     * Problem 5: Minimum Window Substring
     * 
     * Given two strings s and t, return the minimum window substring of s such that every character
     * in t (including duplicates) is included in the window.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(k) where k is the size of the charset
     * 
     * Example:
     * Input: s = "ADOBECODEBANC", t = "ABC"
     * Output: "BANC" (contains all characters from t)
     * 
     * TODO: Implement this method
     */
    public String minimumWindowSubstring(String s, String t) {
        // TODO: Your implementation here
        return "";
    }
    
    /**
     * Problem 6: Find All Anagrams in a String
     * 
     * Given two strings s and p, return an array of all the start indices of p's anagrams in s.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(k) where k is the size of the charset
     * 
     * Example:
     * Input: s = "cbaebabacd", p = "abc"
     * Output: [0, 6] (anagrams start at indices 0 and 6)
     * 
     * TODO: Implement this method
     */
    public List<Integer> findAnagrams(String s, String p) {
        // TODO: Your implementation here
        return new ArrayList<>();
    }
    
    /**
     * Problem 7: Maximum of All Subarrays of Size K
     * 
     * Given an array and an integer k, find the maximum for each and every contiguous subarray of size k.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(k)
     * 
     * Example:
     * Input: arr = [1, 3, -1, -3, 5, 3, 6, 7], k = 3
     * Output: [3, 3, 5, 5, 6, 7]
     * 
     * TODO: Implement this method
     */
    public int[] maxOfAllSubarraysOfSizeK(int[] arr, int k) {
        // TODO: Your implementation here
        return new int[0];
    }
    
    /**
     * Problem 8: Subarray Product Less Than K
     * 
     * Given an array of positive integers nums and an integer k, return the number of contiguous
     * subarrays where the product of all the elements in the subarray is strictly less than k.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     * 
     * Example:
     * Input: nums = [10, 5, 2, 6], k = 100
     * Output: 8 ([10], [5], [2], [6], [10, 5], [5, 2], [2, 6], [5, 2, 6])
     * 
     * TODO: Implement this method
     */
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        // TODO: Your implementation here
        return 0;
    }
    
    /**
     * Problem 9: Longest Repeating Character Replacement
     * 
     * Given a string s and an integer k, you can choose any character of the string and change it
     * to any other uppercase English character. You can perform this operation at most k times.
     * Return the length of the longest substring containing the same letter.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     * 
     * Example:
     * Input: s = "AABABBA", k = 1
     * Output: 4 (replace one 'A' with 'B' to get "AABBBBA")
     * 
     * TODO: Implement this method
     */
    public int characterReplacement(String s, int k) {
        // TODO: Your implementation here
        return 0;
    }
    
    /**
     * Problem 10: Permutation in String
     * 
     * Given two strings s1 and s2, return true if s2 contains a permutation of s1, or false otherwise.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(k) where k is the size of the charset
     * 
     * Example:
     * Input: s1 = "ab", s2 = "eidbaooo"
     * Output: true ("ba" is a permutation of "ab")
     * 
     * TODO: Implement this method
     */
    public boolean checkInclusion(String s1, String s2) {
        // TODO: Your implementation here
        return false;
    }
    
    /**
     * Problem 11: Minimum Size Subarray Sum
     * 
     * Given an array of positive integers nums and a positive integer target, return the minimal
     * length of a contiguous subarray whose sum is greater than or equal to target.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     * 
     * Example:
     * Input: nums = [2, 3, 1, 2, 4, 3], target = 7
     * Output: 2 ([4, 3] has sum 7)
     * 
     * TODO: Implement this method
     */
    public int minSubArrayLen(int target, int[] nums) {
        int left = 0, sum = 0, minLen = Integer.MAX_VALUE;
        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];
            while (sum >= target) {
                minLen = Math.min(minLen, right - left + 1);
                sum -= nums[left++];
            }
        }
        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }
    
    /**
     * Problem 12: Maximum Average Subarray I
     * 
     * Given an integer array nums consisting of n elements, and an integer k, find a contiguous
     * subarray whose length is equal to k that has the maximum average value.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     * 
     * Example:
     * Input: nums = [1, 12, -5, -6, 50, 3], k = 4
     * Output: 12.75 (subarray [12, -5, -6, 50] has maximum average)
     * 
     * TODO: Implement this method
     */
    public double findMaxAverage(int[] nums, int k) {
        double sum = 0;
        for (int i = 0; i < k; i++) sum += nums[i];
        double max = sum;
        for (int i = k; i < nums.length; i++) {
            sum += nums[i] - nums[i - k];
            max = Math.max(max, sum);
        }
        return max / k;
    }
    
    /**
     * Problem 13: Grumpy Bookstore Owner
     * 
     * There is a bookstore owner that has a store open for n minutes. Every minute, some number
     * of customers enter the store, and all those customers leave at the end of that minute.
     * On some minutes, the bookstore owner is grumpy. You are given an array customers, where
     * customers[i] is the number of customers that enter the store at the start of the ith minute,
     * and a binary array grumpy, where grumpy[i] is 1 if the bookstore owner is grumpy during
     * the ith minute, and is 0 otherwise.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     * 
     * Example:
     * Input: customers = [1, 0, 1, 2, 1, 1, 7, 5], grumpy = [0, 1, 0, 1, 0, 1, 0, 1], minutes = 3
     * Output: 16 (keep owner not grumpy for 3 minutes)
     * 
     * TODO: Implement this method
     */
    public int maxSatisfied(int[] customers, int[] grumpy, int minutes) {
        int satisfied = 0, n = customers.length;
        for (int i = 0; i < n; i++) {
            if (grumpy[i] == 0) satisfied += customers[i];
        }
        int extra = 0, maxExtra = 0;
        for (int i = 0; i < n; i++) {
            if (grumpy[i] == 1) extra += customers[i];
            if (i >= minutes && grumpy[i - minutes] == 1) extra -= customers[i - minutes];
            maxExtra = Math.max(maxExtra, extra);
        }
        return satisfied + maxExtra;
    }
    
    /**
     * Problem 14: Number of Substrings Containing All Three Characters
     * 
     * Given a string s consisting only of characters a, b and c, return the number of substrings
     * containing at least one occurrence of all three characters a, b and c.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     * 
     * Example:
     * Input: s = "abcabc"
     * Output: 10 (all substrings of length 3 or more contain all three characters)
     * 
     * TODO: Implement this method
     */
    public int numberOfSubstrings(String s) {
        int[] count = new int[3];
        int res = 0, left = 0;
        for (int right = 0; right < s.length(); right++) {
            count[s.charAt(right) - 'a']++;
            while (count[0] > 0 && count[1] > 0 && count[2] > 0) {
                res += s.length() - right;
                count[s.charAt(left++) - 'a']--;
            }
        }
        return res;
    }
    
    /**
     * Problem 15: Replace the Substring for Balanced String
     * 
     * You are given a string containing only 4 kinds of characters 'Q', 'W', 'E' and 'R'.
     * A string is said to be balanced if each of its characters appears n/4 times where n
     * is the length of the string.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     * 
     * Example:
     * Input: s = "QWER"
     * Output: 0 (already balanced)
     * 
     * TODO: Implement this method
     */
    public int balancedString(String s) {
        int n = s.length(), res = n, left = 0;
        int[] count = new int[128];
        for (char c : s.toCharArray()) count[c]++;
        int req = n / 4;
        for (int right = 0; right < n; right++) {
            count[s.charAt(right)]--;
            while (left < n && count['Q'] <= req && count['W'] <= req && count['E'] <= req && count['R'] <= req) {
                res = Math.min(res, right - left + 1);
                count[s.charAt(left++)]++;
            }
        }
        return res;
    }
    
    /**
     * Problem 16: Longest Subarray of 1's After Deleting One Element
     * 
     * Given a binary array nums, you should delete one element from it.
     * Return the size of the longest non-empty subarray containing only 1's.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     * 
     * Example:
     * Input: nums = [1, 1, 0, 1]
     * Output: 3 (delete 0 to get [1, 1, 1])
     * 
     * TODO: Implement this method
     */
    public int longestSubarray(int[] nums) {
        int left = 0, zeros = 0, res = 0;
        for (int right = 0; right < nums.length; right++) {
            if (nums[right] == 0) zeros++;
            while (zeros > 1) {
                if (nums[left++] == 0) zeros--;
            }
            res = Math.max(res, right - left);
        }
        return res;
    }
    
    /**
     * Problem 17: Maximum Number of Vowels in a Substring of Given Length
     * 
     * Given a string s and an integer k, return the maximum number of vowel letters in any
     * substring of s with length k.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     * 
     * Example:
     * Input: s = "abciiidef", k = 3
     * Output: 3 ("iii" contains 3 vowels)
     * 
     * TODO: Implement this method
     */
    public int maxVowels(String s, int k) {
        int max = 0, count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (isVowel(s.charAt(i))) count++;
            if (i >= k && isVowel(s.charAt(i - k))) count--;
            max = Math.max(max, count);
        }
        return max;
    }
    private boolean isVowel(char c) {
        return "aeiou".indexOf(c) != -1;
    }

    /**
     * Problem 18: Get Equal Substrings Within Budget
     * 
     * You are given two strings s and t of the same length. You want to change s to t.
     * Changing the i-th character of s to i-th character of t costs |s[i] - t[i]|.
     * Return the maximum length of a substring of s that can be changed to be the same as
     * the corresponding substring of t with a cost less than or equal to maxCost.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     * 
     * Example:
     * Input: s = "abcd", t = "bcdf", maxCost = 3
     * Output: 3 ("abc" can be changed to "bcd" with cost 3)
     * 
     * TODO: Implement this method
     */
    public int equalSubstring(String s, String t, int maxCost) {
        int left = 0, cost = 0, res = 0;
        for (int right = 0; right < s.length(); right++) {
            cost += Math.abs(s.charAt(right) - t.charAt(right));
            while (cost > maxCost) {
                cost -= Math.abs(s.charAt(left) - t.charAt(left));
                left++;
            }
            res = Math.max(res, right - left + 1);
        }
        return res;
    }
    
    /**
     * Problem 19: Count Number of Nice Subarrays
     * 
     * Given an array of integers nums and an integer k. A subarray is called nice if there
     * are k odd numbers on it. Return the number of nice sub-arrays.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     * 
     * Example:
     * Input: nums = [1, 1, 2, 1, 1], k = 3
     * Output: 2 ([1, 1, 2, 1] and [1, 2, 1, 1])
     * 
     * TODO: Implement this method
     */
    public int numberOfSubarrays(int[] nums, int k) {
        int res = 0, count = 0, left = 0;
        for (int right = 0; right < nums.length; right++) {
            if (nums[right] % 2 == 1) k--;
            while (k == 0) {
                k += nums[left++] % 2;
                count++;
            }
            res += count;
        }
        return res;
    }
    
    /**
     * Problem 20: Minimum Operations to Reduce X to Zero
     * 
     * You are given an integer array nums and an integer x. In one operation, you can either
     * remove the leftmost or the rightmost element from the array nums and subtract its value from x.
     * Return the minimum number of operations to reduce x to exactly 0.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     * 
     * Example:
     * Input: nums = [1, 1, 4, 2, 3], x = 5
     * Output: 2 (remove 2 and 3)
     * 
     * TODO: Implement this method
     */
    public int minOperations(int[] nums, int x) {
        int n = nums.length, sum = 0;
        for (int num : nums) sum += num;
        int target = sum - x, left = 0, curr = 0, res = -1;
        for (int right = 0; right < n; right++) {
            curr += nums[right];
            while (curr > target && left <= right) curr -= nums[left++];
            if (curr == target) res = Math.max(res, right - left + 1);
        }
        return res == -1 ? -1 : n - res;
    }
    
    // Utility method to print array
    public void printArray(int[] arr) {
        System.out.println(Arrays.toString(arr));
    }
    
    // Utility method to print array with label
    public void printArray(String label, int[] arr) {
        System.out.println(label + ": " + Arrays.toString(arr));
    }
} 