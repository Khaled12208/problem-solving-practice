package com.problemsolving;

import com.problemsolving.arrays.ArrayProblems;
import com.problemsolving.recursion.RecursionProblems;
import com.problemsolving.datastructures.DataStructureProblems;
import com.problemsolving.graphs.GraphProblems;
import com.problemsolving.iteration.IterationProblems;
import com.problemsolving.sorting.SortingProblems;
import com.problemsolving.windows.SlidingWindowProblems;
import com.problemsolving.trees.DecisionTreeProblems;
import com.problemsolving.heaps.HeapProblems;
import com.problemsolving.intervals.IntervalProblems;
import com.problemsolving.traversal.TraversalProblems;
import com.problemsolving.systemdesign.SystemDesignProblems;

/**
 * Main Application Class
 * This class demonstrates the problem-solving practice structure
 */
public class App {
    public static void main(String[] args) {
        System.out.println("=== Problem Solving Practice Project ===");
        System.out.println("Welcome to your Java problem-solving practice environment!");
        System.out.println();
        
        // Initialize problem classes
        ArrayProblems arrayProblems = new ArrayProblems();
        RecursionProblems recursionProblems = new RecursionProblems();
        DataStructureProblems dataStructureProblems = new DataStructureProblems();
        GraphProblems graphProblems = new GraphProblems();
        IterationProblems iterationProblems = new IterationProblems();
        SortingProblems sortingProblems = new SortingProblems();
        SlidingWindowProblems slidingWindowProblems = new SlidingWindowProblems();
        DecisionTreeProblems decisionTreeProblems = new DecisionTreeProblems();
        HeapProblems heapProblems = new HeapProblems();
        IntervalProblems intervalProblems = new IntervalProblems();
        TraversalProblems traversalProblems = new TraversalProblems();
        SystemDesignProblems systemDesignProblems = new SystemDesignProblems();
        
        System.out.println("Available Problem Categories:");
        System.out.println("1. Array Manipulation Problems");
        System.out.println("2. Recursion Problems");
        System.out.println("3. Data Structure Problems");
        System.out.println("4. Graph Theory Problems");
        System.out.println("5. Iteration Problems");
        System.out.println("6. Sorting Problems");
        System.out.println("7. Sliding Window Problems");
        System.out.println("8. Decision Tree Problems");
        System.out.println("9. Heap Problems");
        System.out.println("10. Interval Problems");
        System.out.println("11. Traversal Problems (DFS/BFS/Tree)");
        System.out.println("12. System Design Problems (Cache/Circuit Breaker/Rate Limiter)");
        System.out.println();
        
        System.out.println("=== Sample Problem Demonstrations ===");
        
        // Array Problems Demo
        System.out.println("Array Problems:");
        int[] testArray = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println("Test array: " + java.util.Arrays.toString(testArray));
        System.out.println("Max subarray sum (Kadane's): " + arrayProblems.maxSubArraySum(testArray));
        System.out.println();
        
        // Recursion Problems Demo
        System.out.println("Recursion Problems:");
        System.out.println("Fibonacci(6): " + recursionProblems.fibonacci(6));
        System.out.println("Factorial(5): " + recursionProblems.factorial(5));
        System.out.println();
        
        // Data Structure Problems Demo
        System.out.println("Data Structure Problems:");
        DataStructureProblems.Stack stack = new DataStructureProblems.Stack(5);
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println("Stack peek: " + stack.peek());
        System.out.println();
        
        // Graph Problems Demo
        System.out.println("Graph Problems:");
        GraphProblems.Graph graph = new GraphProblems.Graph(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 4);
        System.out.println("Graph created with 5 vertices");
        System.out.println();
        
        // Iteration Problems Demo
        System.out.println("Iteration Problems:");
        System.out.println("Fibonacci iterative(6): " + iterationProblems.fibonacciIterative(6));
        System.out.println();
        
        // Sorting Problems Demo
        System.out.println("Sorting Problems:");
        int[] sortArray = {64, 34, 25, 12, 22, 11, 90};
        System.out.println("Original array: " + java.util.Arrays.toString(sortArray));
        
        // QuickSort demo
        int[] quickSortArray = sortArray.clone();
        sortingProblems.quickSort(quickSortArray);
        System.out.println("QuickSort result: " + java.util.Arrays.toString(quickSortArray));
        
        // Optimized QuickSort demo
        int[] optimizedQuickSortArray = sortArray.clone();
        sortingProblems.quickSortOptimized(optimizedQuickSortArray);
        System.out.println("Optimized QuickSort: " + java.util.Arrays.toString(optimizedQuickSortArray));
        
        // MergeSort demo
        int[] mergeSortArray = sortArray.clone();
        sortingProblems.mergeSort(mergeSortArray);
        System.out.println("MergeSort result: " + java.util.Arrays.toString(mergeSortArray));
        
        // In-Place MergeSort demo
        int[] inPlaceMergeSortArray = sortArray.clone();
        sortingProblems.mergeSortInPlace(inPlaceMergeSortArray);
        System.out.println("In-Place MergeSort: " + java.util.Arrays.toString(inPlaceMergeSortArray));
        System.out.println();
        
        // Sliding Window Problems Demo
        System.out.println("Sliding Window Problems:");
        int[] windowArray = {2, 1, 5, 1, 3, 2};
        System.out.println("Array: " + java.util.Arrays.toString(windowArray));
        System.out.println("Max sum subarray of size 3: " + slidingWindowProblems.maxSumSubarrayOfSizeK(windowArray, 3));
        System.out.println();
        
        // Decision Tree Problems Demo
        System.out.println("Decision Tree Problems:");
        String[] labels = {"Yes", "No", "Yes", "Yes", "No"};
        System.out.println("Labels: " + java.util.Arrays.toString(labels));
        System.out.println("Entropy: " + decisionTreeProblems.calculateEntropy(labels));
        System.out.println();
        
        // Heap Problems Demo
        System.out.println("Heap Problems:");
        int[] heapArray = {3, 2, 1, 5, 6, 4};
        System.out.println("Array: " + java.util.Arrays.toString(heapArray));
        System.out.println("2nd largest element: " + heapProblems.findKthLargest(heapArray, 2));
        System.out.println("Min and Max: " + java.util.Arrays.toString(heapProblems.findMinMax(heapArray)));
        System.out.println();
        
        // Interval Problems Demo
        System.out.println("Interval Problems:");
        int[][] intervals = {{1,3}, {2,6}, {8,10}, {15,18}};
        System.out.println("Original intervals: " + java.util.Arrays.deepToString(intervals));
        int[][] merged = intervalProblems.mergeIntervals(intervals);
        System.out.println("Merged intervals: " + java.util.Arrays.deepToString(merged));
        System.out.println("Can attend meetings: " + intervalProblems.canAttendMeetings(intervals));
        System.out.println();
        
        // Traversal Problems Demo
        System.out.println("Traversal Problems:");
        Integer[] tree = {1, 2, 3, 4, 5, null, 7};
        System.out.println("Tree: " + java.util.Arrays.toString(tree));
        System.out.println("Inorder: " + traversalProblems.inorderTraversal(tree));
        System.out.println("Preorder: " + traversalProblems.preorderTraversal(tree));
        System.out.println("Level Order: " + traversalProblems.levelOrder(tree));
        System.out.println("Max Depth: " + traversalProblems.maxDepth(tree));
        System.out.println();
        
        // System Design Problems Demo
        System.out.println("System Design Problems:");
        
        // LRU Cache demo
        SystemDesignProblems.LRUCache lruCache = new SystemDesignProblems.LRUCache(2);
        lruCache.put(1, 1);
        lruCache.put(2, 2);
        System.out.println("LRU Cache get(1): " + lruCache.get(1));
        lruCache.put(3, 3);
        System.out.println("LRU Cache get(2): " + lruCache.get(2));
        
        // LFU Cache demo
        SystemDesignProblems.LFUCache lfuCache = new SystemDesignProblems.LFUCache(2);
        lfuCache.put(1, 1);
        lfuCache.put(2, 2);
        lfuCache.get(1);
        lfuCache.put(3, 3);
        System.out.println("LFU Cache get(2): " + lfuCache.get(2));
        
        // Rate Limiter demo
        SystemDesignProblems.RateLimiter rateLimiter = new SystemDesignProblems.RateLimiter(5, 1000);
        System.out.println("Rate Limiter (Fixed Window): " + rateLimiter.allowRequestFixedWindow("user1"));
        
        // Circuit Breaker demo
        SystemDesignProblems.CircuitBreaker circuitBreaker = new SystemDesignProblems.CircuitBreaker(3, 5000, 2);
        System.out.println("Circuit Breaker State: " + circuitBreaker.getState());
        System.out.println();
        
        System.out.println("=== Instructions ===");
        System.out.println("1. Open any of the problem classes in the respective packages");
        System.out.println("2. Find the TODO comments indicating where to implement your solutions");
        System.out.println("3. Implement the methods according to the problem descriptions");
        System.out.println("4. Test your implementations by running this main method");
        System.out.println("5. Create test cases to verify your solutions");
        System.out.println();
        
        System.out.println("Happy problem solving! 🚀");
    }
}
