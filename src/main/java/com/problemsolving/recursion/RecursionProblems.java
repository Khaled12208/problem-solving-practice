package com.problemsolving.recursion;

import java.util.*;

/**
 * Recursion Problems
 * Practice problems focused on recursive algorithms and thinking
 */
public class RecursionProblems {
    
    /**
     * Problem 1: Fibonacci Number
     * 
     * Calculate the nth Fibonacci number using recursion.
     * F(n) = F(n-1) + F(n-2), where F(0) = 0 and F(1) = 1
     * 
     * Example:
     * Input: n = 6
     * Output: 8 (sequence: 0, 1, 1, 2, 3, 5, 8)
     * 
     * TODO: Implement this method
     */
    public int fibonacci(int n) {
        // TODO: Your implementation here
        return 0;
    }
    
    /**
     * Problem 2: Factorial
     * 
     * Calculate the factorial of a number using recursion.
     * n! = n * (n-1)!
     * 
     * Example:
     * Input: n = 5
     * Output: 120 (5 * 4 * 3 * 2 * 1)
     * 
     * TODO: Implement this method
     */
    public int factorial(int n) {
        // TODO: Your implementation here
        return 0;
    }
    
    /**
     * Problem 3: Power Function
     * 
     * Calculate x raised to the power n using recursion.
     * 
     * Example:
     * Input: x = 2, n = 3
     * Output: 8 (2^3 = 2 * 2 * 2)
     * 
     * TODO: Implement this method
     */
    public double power(double x, int n) {
        // TODO: Your implementation here
        return 0.0;
    }
    
    /**
     * Problem 4: Tower of Hanoi
     * 
     * Solve the Tower of Hanoi puzzle using recursion.
     * Move n disks from source rod to destination rod using auxiliary rod.
     * 
     * Example:
     * Input: n = 3, source = 'A', auxiliary = 'B', destination = 'C'
     * Output: Print the moves to solve the puzzle
     * 
     * TODO: Implement this method
     */
    public void towerOfHanoi(int n, char source, char auxiliary, char destination) {
        // TODO: Your implementation here
    }
    
    /**
     * Problem 5: Generate All Subsets
     * 
     * Generate all possible subsets of a given array using recursion.
     * 
     * Example:
     * Input: nums = [1, 2, 3]
     * Output: [[], [1], [2], [1, 2], [3], [1, 3], [2, 3], [1, 2, 3]]
     * 
     * TODO: Implement this method
     */
    public List<List<Integer>> generateSubsets(int[] nums) {
        // TODO: Your implementation here
        return new ArrayList<>();
    }
    
    /**
     * Problem 6: Generate All Permutations
     * 
     * Generate all possible permutations of a given array using recursion.
     * 
     * Example:
     * Input: nums = [1, 2, 3]
     * Output: [[1, 2, 3], [1, 3, 2], [2, 1, 3], [2, 3, 1], [3, 1, 2], [3, 2, 1]]
     * 
     * TODO: Implement this method
     */
    public List<List<Integer>> generatePermutations(int[] nums) {
        // TODO: Your implementation here
        return new ArrayList<>();
    }
    
    /**
     * Problem 7: Binary Search (Recursive)
     * 
     * Implement binary search using recursion to find a target element in a sorted array.
     * 
     * Example:
     * Input: nums = [1, 3, 5, 7, 9, 11], target = 7
     * Output: 3 (index of 7)
     * 
     * TODO: Implement this method
     */
    public int binarySearch(int[] nums, int target) {
        // TODO: Your implementation here
        return -1;
    }
    
    /**
     * Problem 8: Merge Sort (Recursive)
     * 
     * Implement merge sort using recursion to sort an array.
     * 
     * Example:
     * Input: nums = [64, 34, 25, 12, 22, 11, 90]
     * Output: [11, 12, 22, 25, 34, 64, 90]
     * 
     * TODO: Implement this method
     */
    public void mergeSort(int[] nums) {
        // TODO: Your implementation here
    }
    
    /**
     * Problem 9: Quick Sort (Recursive)
     * 
     * Implement quick sort using recursion to sort an array.
     * 
     * Example:
     * Input: nums = [64, 34, 25, 12, 22, 11, 90]
     * Output: [11, 12, 22, 25, 34, 64, 90]
     * 
     * TODO: Implement this method
     */
    public void quickSort(int[] nums) {
        // TODO: Your implementation here
    }
    
    /**
     * Problem 10: N-Queens Problem
     * 
     * Place N queens on an NxN chessboard so that no two queens threaten each other.
     * Return all distinct solutions.
     * 
     * Example:
     * Input: n = 4
     * Output: List of all valid board configurations
     * 
     * TODO: Implement this method
     */
    public List<List<String>> solveNQueens(int n) {
        // TODO: Your implementation here
        return new ArrayList<>();
    }
} 