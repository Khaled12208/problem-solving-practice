package com.problemsolving.sorting;

import java.util.*;

/**
 * Sorting Problems
 * Practice problems focused on various sorting algorithms and their implementations
 */
public class SortingProblems {
    
    /**
     * Problem 1: Bubble Sort
     * 
     * Implement bubble sort algorithm. Repeatedly steps through the list, compares adjacent elements
     * and swaps them if they are in the wrong order.
     * 
     * Time Complexity: O(n²)
     * Space Complexity: O(1)
     * 
     * Example:
     * Input: [64, 34, 25, 12, 22, 11, 90]
     * Output: [11, 12, 22, 25, 34, 64, 90]
     * 
     * TODO: Implement this method
     */
    public void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }
    
    /**
     * Problem 2: Selection Sort
     * 
     * Implement selection sort algorithm. Divides the input list into a sorted and unsorted region,
     * and repeatedly selects the smallest element from the unsorted region.
     * 
     * Time Complexity: O(n²)
     * Space Complexity: O(1)
     * 
     * Example:
     * Input: [64, 34, 25, 12, 22, 11, 90]
     * Output: [11, 12, 22, 25, 34, 64, 90]
     * 
     * TODO: Implement this method
     */
    public void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            swap(arr, i, minIndex);
        }
    }
    
    /**
     * Problem 3: Insertion Sort
     * 
     * Implement insertion sort algorithm. Builds the final sorted array one item at a time by
     * repeatedly inserting a new element into the sorted portion of the array.
     * 
     * Time Complexity: O(n²) worst case, O(n) best case
     * Space Complexity: O(1)
     * 
     * Example:
     * Input: [64, 34, 25, 12, 22, 11, 90]
     * Output: [11, 12, 22, 25, 34, 64, 90]
     * 
     * TODO: Implement this method
     */
    public void insertionSort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }
    
    /**
     * Problem 4: Merge Sort
     * 
     * Implement merge sort algorithm. A divide-and-conquer algorithm that recursively breaks down
     * the problem into smaller subproblems until they become simple enough to solve directly.
     * 
     * Time Complexity: O(n log n)
     * Space Complexity: O(n)
     * 
     * Example:
     * Input: [64, 34, 25, 12, 22, 11, 90]
     * Output: [11, 12, 22, 25, 34, 64, 90]
     * 
     * TODO: Implement this method
     */
    public void mergeSort(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        mergeSortHelper(arr, 0, arr.length - 1);
    }
    
    private void mergeSortHelper(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            
            // Recursively sort left and right halves
            mergeSortHelper(arr, left, mid);
            mergeSortHelper(arr, mid + 1, right);
            
            // Merge the sorted halves
            merge(arr, left, mid, right);
        }
    }
    
    private void merge(int[] arr, int left, int mid, int right) {
        // Calculate sizes of two subarrays
        int n1 = mid - left + 1;
        int n2 = right - mid;
        
        // Create temporary arrays
        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];
        
        // Copy data to temporary arrays
        for (int i = 0; i < n1; i++) {
            leftArray[i] = arr[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArray[j] = arr[mid + 1 + j];
        }
        
        // Merge the temporary arrays back into arr[left..right]
        int i = 0, j = 0, k = left;
        
        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
                arr[k] = leftArray[i];
                i++;
            } else {
                arr[k] = rightArray[j];
                j++;
            }
            k++;
        }
        
        // Copy remaining elements of leftArray if any
        while (i < n1) {
            arr[k] = leftArray[i];
            i++;
            k++;
        }
        
        // Copy remaining elements of rightArray if any
        while (j < n2) {
            arr[k] = rightArray[j];
            j++;
            k++;
        }
    }
    
    /**
     * Problem 4b: In-Place Merge Sort
     * 
     * Optimized merge sort that uses less extra space by merging in-place.
     * 
     * Time Complexity: O(n log n)
     * Space Complexity: O(1) - minimal extra space
     * 
     * Example:
     * Input: [64, 34, 25, 12, 22, 11, 90]
     * Output: [11, 12, 22, 25, 34, 64, 90]
     */
    public void mergeSortInPlace(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        mergeSortInPlaceHelper(arr, 0, arr.length - 1);
    }
    
    private void mergeSortInPlaceHelper(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            
            mergeSortInPlaceHelper(arr, left, mid);
            mergeSortInPlaceHelper(arr, mid + 1, right);
            
            mergeInPlace(arr, left, mid, right);
        }
    }
    
    private void mergeInPlace(int[] arr, int left, int mid, int right) {
        int i = left;
        int j = mid + 1;
        
        // If the last element of left subarray is <= first element of right subarray
        if (arr[mid] <= arr[j]) {
            return; // Already sorted
        }
        
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                i++;
            } else {
                // Store the value to be moved
                int value = arr[j];
                int index = j;
                
                // Shift all elements from i to j-1 one position to the right
                while (index != i) {
                    arr[index] = arr[index - 1];
                    index--;
                }
                arr[i] = value;
                
                // Update all the indices
                i++;
                mid++;
                j++;
            }
        }
    }
    
    /**
     * Problem 5: Quick Sort
     * 
     * Implement quick sort algorithm. A divide-and-conquer algorithm that picks a pivot element
     * and partitions the array around the pivot.
     * 
     * Time Complexity: O(n log n) average, O(n²) worst case
     * Space Complexity: O(log n) average
     * 
     * Example:
     * Input: [64, 34, 25, 12, 22, 11, 90]
     * Output: [11, 12, 22, 25, 34, 64, 90]
     * 
     * TODO: Implement this method
     */
    public void quickSort(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        quickSortHelper(arr, 0, arr.length - 1);
    }
    
    private void quickSortHelper(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            quickSortHelper(arr, low, pivotIndex - 1);  // Sort left part
            quickSortHelper(arr, pivotIndex + 1, high); // Sort right part
        }
    }
    
    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high];  // Choose last element as pivot
        int i = low - 1;        // Index of smaller element
        
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        
        swap(arr, i + 1, high);
        return i + 1;
    }
    
    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    /**
     * Problem 5b: Quick Sort with Median-of-Three Pivot
     * 
     * Optimized quick sort using median-of-three pivot selection to avoid worst case.
     * 
     * Time Complexity: O(n log n) average, O(n²) worst case (rare)
     * Space Complexity: O(log n) average
     * 
     * Example:
     * Input: [64, 34, 25, 12, 22, 11, 90]
     * Output: [11, 12, 22, 25, 34, 64, 90]
     */
    public void quickSortOptimized(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        quickSortOptimizedHelper(arr, 0, arr.length - 1);
    }
    
    private void quickSortOptimizedHelper(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partitionOptimized(arr, low, high);
            quickSortOptimizedHelper(arr, low, pivotIndex - 1);
            quickSortOptimizedHelper(arr, pivotIndex + 1, high);
        }
    }
    
    private int partitionOptimized(int[] arr, int low, int high) {
        // Median-of-three pivot selection
        int mid = low + (high - low) / 2;
        int pivot = medianOfThree(arr, low, mid, high);
        
        // Move pivot to end
        swap(arr, pivot, high);
        pivot = arr[high];
        
        int i = low - 1;
        
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        
        swap(arr, i + 1, high);
        return i + 1;
    }
    
    private int medianOfThree(int[] arr, int low, int mid, int high) {
        int a = arr[low];
        int b = arr[mid];
        int c = arr[high];
        
        if (a < b) {
            if (b < c) return mid;      // a < b < c
            else if (a < c) return high; // a < c < b
            else return low;            // c < a < b
        } else {
            if (a < c) return low;      // b < a < c
            else if (b < c) return high; // b < c < a
            else return mid;            // c < b < a
        }
    }
    
    /**
     * Problem 6: Heap Sort
     * 
     * Implement heap sort algorithm. Uses a binary heap data structure to sort elements.
     * 
     * Time Complexity: O(n log n)
     * Space Complexity: O(1)
     * 
     * Example:
     * Input: [64, 34, 25, 12, 22, 11, 90]
     * Output: [11, 12, 22, 25, 34, 64, 90]
     * 
     * TODO: Implement this method
     */
    public void heapSort(int[] arr) {
        int n = arr.length;
        
        // Build max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }
        
        // Extract elements from heap one by one
        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);
            heapify(arr, i, 0);
        }
    }
    
    private void heapify(int[] arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        
        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }
        
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }
        
        if (largest != i) {
            swap(arr, i, largest);
            heapify(arr, n, largest);
        }
    }
    
    /**
     * Problem 7: Counting Sort
     * 
     * Implement counting sort algorithm. A non-comparison based sorting algorithm that sorts
     * elements by counting the number of occurrences of each unique element.
     * 
     * Time Complexity: O(n + k) where k is the range of input
     * Space Complexity: O(k)
     * 
     * Example:
     * Input: [1, 4, 1, 2, 7, 5, 2], range = 10
     * Output: [1, 1, 2, 2, 4, 5, 7]
     * 
     * TODO: Implement this method
     */
    public void countingSort(int[] arr, int range) {
        int n = arr.length;
        int[] output = new int[n];
        int[] count = new int[range + 1];
        
        // Count occurrences
        for (int i = 0; i < n; i++) {
            count[arr[i]]++;
        }
        
        // Calculate positions
        for (int i = 1; i <= range; i++) {
            count[i] += count[i - 1];
        }
        
        // Build output array
        for (int i = n - 1; i >= 0; i--) {
            output[count[arr[i]] - 1] = arr[i];
            count[arr[i]]--;
        }
        
        // Copy back to original array
        for (int i = 0; i < n; i++) {
            arr[i] = output[i];
        }
    }
    
    /**
     * Problem 8: Radix Sort
     * 
     * Implement radix sort algorithm. A non-comparison based sorting algorithm that sorts
     * data with integer keys by grouping keys by the individual digits.
     * 
     * Time Complexity: O(d * (n + k)) where d is the number of digits, k is the base
     * Space Complexity: O(n + k)
     * 
     * Example:
     * Input: [170, 45, 75, 90, 802, 24, 2, 66]
     * Output: [2, 24, 45, 66, 75, 90, 170, 802]
     * 
     * TODO: Implement this method
     */
    public void radixSort(int[] arr) {
        int max = getMax(arr);
        
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countSort(arr, exp);
        }
    }
    
    private int getMax(int[] arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }
    
    private void countSort(int[] arr, int exp) {
        int n = arr.length;
        int[] output = new int[n];
        int[] count = new int[10];
        
        for (int i = 0; i < n; i++) {
            count[(arr[i] / exp) % 10]++;
        }
        
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }
        
        for (int i = n - 1; i >= 0; i--) {
            int digit = (arr[i] / exp) % 10;
            output[count[digit] - 1] = arr[i];
            count[digit]--;
        }
        
        for (int i = 0; i < n; i++) {
            arr[i] = output[i];
        }
    }
    
    /**
     * Problem 9: Bucket Sort
     * 
     * Implement bucket sort algorithm. Distributes the elements of an array into a number of buckets,
     * then sorts each bucket individually.
     * 
     * Time Complexity: O(n + k) average case, O(n²) worst case
     * Space Complexity: O(n + k)
     * 
     * Example:
     * Input: [0.897, 0.565, 0.656, 0.1234, 0.665, 0.3434]
     * Output: [0.1234, 0.3434, 0.565, 0.656, 0.665, 0.897]
     * 
     * TODO: Implement this method
     */
    public void bucketSort(double[] arr) {
        int n = arr.length;
        if (n <= 0) return;
        
        // Create buckets
        List<Double>[] buckets = new List[n];
        for (int i = 0; i < n; i++) {
            buckets[i] = new ArrayList<>();
        }
        
        // Distribute elements into buckets
        for (int i = 0; i < n; i++) {
            int bucketIndex = (int) (n * arr[i]);
            buckets[bucketIndex].add(arr[i]);
        }
        
        // Sort individual buckets
        for (int i = 0; i < n; i++) {
            Collections.sort(buckets[i]);
        }
        
        // Concatenate buckets
        int index = 0;
        for (int i = 0; i < n; i++) {
            for (double value : buckets[i]) {
                arr[index++] = value;
            }
        }
    }
    
    /**
     * Problem 10: Shell Sort
     * 
     * Implement shell sort algorithm. An optimization of insertion sort that allows the exchange
     * of items that are far apart.
     * 
     * Time Complexity: O(n^1.25) to O(n^1.5)
     * Space Complexity: O(1)
     * 
     * Example:
     * Input: [64, 34, 25, 12, 22, 11, 90]
     * Output: [11, 12, 22, 25, 34, 64, 90]
     * 
     * TODO: Implement this method
     */
    public void shellSort(int[] arr) {
        int n = arr.length;
        
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int temp = arr[i];
                int j;
                for (j = i; j >= gap && arr[j - gap] > temp; j -= gap) {
                    arr[j] = arr[j - gap];
                }
                arr[j] = temp;
            }
        }
    }
    
    /**
     * Problem 11: Comb Sort
     * 
     * Implement comb sort algorithm. An improvement over bubble sort that uses a gap sequence
     * to eliminate turtles (small values near the end of the list).
     * 
     * Time Complexity: O(n²) worst case, O(n log n) average case
     * Space Complexity: O(1)
     * 
     * Example:
     * Input: [64, 34, 25, 12, 22, 11, 90]
     * Output: [11, 12, 22, 25, 34, 64, 90]
     * 
     * TODO: Implement this method
     */
    public void combSort(int[] arr) {
        int n = arr.length;
        int gap = n;
        boolean swapped = true;
        
        while (gap != 1 || swapped) {
            gap = getNextGap(gap);
            swapped = false;
            
            for (int i = 0; i < n - gap; i++) {
                if (arr[i] > arr[i + gap]) {
                    swap(arr, i, i + gap);
                    swapped = true;
                }
            }
        }
    }
    
    private int getNextGap(int gap) {
        gap = (gap * 10) / 13;
        return Math.max(gap, 1);
    }
    
    /**
     * Problem 12: Cycle Sort
     * 
     * Implement cycle sort algorithm. An in-place, unstable sorting algorithm that minimizes
     * the number of writes to the array.
     * 
     * Time Complexity: O(n²)
     * Space Complexity: O(1)
     * 
     * Example:
     * Input: [64, 34, 25, 12, 22, 11, 90]
     * Output: [11, 12, 22, 25, 34, 64, 90]
     * 
     * TODO: Implement this method
     */
    public void cycleSort(int[] arr) {
        int n = arr.length;
        for (int cycleStart = 0; cycleStart < n - 1; cycleStart++) {
            int item = arr[cycleStart];
            int pos = cycleStart;
            for (int i = cycleStart + 1; i < n; i++) {
                if (arr[i] < item) pos++;
            }
            if (pos == cycleStart) continue;
            while (item == arr[pos]) pos++;
            int temp = arr[pos];
            arr[pos] = item;
            item = temp;
            while (pos != cycleStart) {
                pos = cycleStart;
                for (int i = cycleStart + 1; i < n; i++) {
                    if (arr[i] < item) pos++;
                }
                while (item == arr[pos]) pos++;
                temp = arr[pos];
                arr[pos] = item;
                item = temp;
            }
        }
    }
    
    /**
     * Problem 13: Pancake Sort
     * 
     * Implement pancake sort algorithm. Sorts an array by repeatedly flipping portions of the array.
     * 
     * Time Complexity: O(n²)
     * Space Complexity: O(1)
     * 
     * Example:
     * Input: [64, 34, 25, 12, 22, 11, 90]
     * Output: [11, 12, 22, 25, 34, 64, 90]
     * 
     * TODO: Implement this method
     */
    public void pancakeSort(int[] arr) {
        int n = arr.length;
        for (int currSize = n; currSize > 1; currSize--) {
            int maxIdx = 0;
            for (int i = 1; i < currSize; i++) {
                if (arr[i] > arr[maxIdx]) maxIdx = i;
            }
            if (maxIdx != currSize - 1) {
                reverse(arr, 0, maxIdx);
                reverse(arr, 0, currSize - 1);
            }
        }
    }

    private void reverse(int[] arr, int start, int end) {
        while (start < end) {
            swap(arr, start, end);
            start++;
            end--;
        }
    }
    
    /**
     * Problem 14: Gnome Sort
     * 
     * Implement gnome sort algorithm. A simple sorting algorithm that works by repeatedly
     * stepping through the list, comparing adjacent elements and swapping them if necessary.
     * 
     * Time Complexity: O(n²)
     * Space Complexity: O(1)
     * 
     * Example:
     * Input: [64, 34, 25, 12, 22, 11, 90]
     * Output: [11, 12, 22, 25, 34, 64, 90]
     * 
     * TODO: Implement this method
     */
    public void gnomeSort(int[] arr) {
        int n = arr.length;
        int index = 0;
        while (index < n) {
            if (index == 0) index++;
            if (arr[index] >= arr[index - 1]) index++;
            else {
                swap(arr, index, index - 1);
                index--;
            }
        }
    }
    
    /**
     * Problem 15: Cocktail Sort (Bidirectional Bubble Sort)
     * 
     * Implement cocktail sort algorithm. A variation of bubble sort that sorts in both directions
     * on each pass through the list.
     * 
     * Time Complexity: O(n²)
     * Space Complexity: O(1)
     * 
     * Example:
     * Input: [64, 34, 25, 12, 22, 11, 90]
     * Output: [11, 12, 22, 25, 34, 64, 90]
     * 
     * TODO: Implement this method
     */
    public void cocktailSort(int[] arr) {
        boolean swapped = true;
        int start = 0, end = arr.length - 1;
        while (swapped) {
            swapped = false;
            for (int i = start; i < end; i++) {
                if (arr[i] > arr[i + 1]) {
                    swap(arr, i, i + 1);
                    swapped = true;
                }
            }
            if (!swapped) break;
            swapped = false;
            end--;
            for (int i = end - 1; i >= start; i--) {
                if (arr[i] > arr[i + 1]) {
                    swap(arr, i, i + 1);
                    swapped = true;
                }
            }
            start++;
        }
    }
    
    /**
     * Problem 16: Odd-Even Sort
     * 
     * Implement odd-even sort algorithm. A parallel sorting algorithm that compares adjacent
     * elements and swaps them if they are in the wrong order.
     * 
     * Time Complexity: O(n²)
     * Space Complexity: O(1)
     * 
     * Example:
     * Input: [64, 34, 25, 12, 22, 11, 90]
     * Output: [11, 12, 22, 25, 34, 64, 90]
     * 
     * TODO: Implement this method
     */
    public void oddEvenSort(int[] arr) {
        boolean sorted = false;
        int n = arr.length;
        while (!sorted) {
            sorted = true;
            for (int i = 1; i < n - 1; i += 2) {
                if (arr[i] > arr[i + 1]) {
                    swap(arr, i, i + 1);
                    sorted = false;
                }
            }
            for (int i = 0; i < n - 1; i += 2) {
                if (arr[i] > arr[i + 1]) {
                    swap(arr, i, i + 1);
                    sorted = false;
                }
            }
        }
    }
    
    /**
     * Problem 17: Bitonic Sort
     * 
     * Implement bitonic sort algorithm. A parallel sorting algorithm that works by building
     * a bitonic sequence and then sorting it.
     * 
     * Time Complexity: O(n log² n)
     * Space Complexity: O(n log n)
     * 
     * Example:
     * Input: [64, 34, 25, 12, 22, 11, 90]
     * Output: [11, 12, 22, 25, 34, 64, 90]
     * 
     * TODO: Implement this method
     */
    public void bitonicSort(int[] arr) {
        // TODO: Your implementation here
    }
    
    /**
     * Problem 18: Tim Sort
     * 
     * Implement a simplified version of Tim sort algorithm. A hybrid sorting algorithm derived
     * from merge sort and insertion sort.
     * 
     * Time Complexity: O(n log n)
     * Space Complexity: O(n)
     * 
     * Example:
     * Input: [64, 34, 25, 12, 22, 11, 90]
     * Output: [11, 12, 22, 25, 34, 64, 90]
     * 
     * TODO: Implement this method
     */
    public void timSort(int[] arr) {
        // TODO: Your implementation here
    }
    
    /**
     * Problem 19: Tree Sort
     * 
     * Implement tree sort algorithm. Uses a binary search tree to sort elements.
     * 
     * Time Complexity: O(n log n) average, O(n²) worst case
     * Space Complexity: O(n)
     * 
     * Example:
     * Input: [64, 34, 25, 12, 22, 11, 90]
     * Output: [11, 12, 22, 25, 34, 64, 90]
     * 
     * TODO: Implement this method
     */
    public void treeSort(int[] arr) {
        // TODO: Your implementation here
    }
    
    /**
     * Problem 20: Strand Sort
     * 
     * Implement strand sort algorithm. A recursive sorting algorithm that sorts elements
     * by repeatedly extracting sorted sublists.
     * 
     * Time Complexity: O(n²)
     * Space Complexity: O(n)
     * 
     * Example:
     * Input: [64, 34, 25, 12, 22, 11, 90]
     * Output: [11, 12, 22, 25, 34, 64, 90]
     * 
     * TODO: Implement this method
     */
    public void strandSort(int[] arr) {
        // TODO: Your implementation here
    }
    
    /**
     * Problem 21: Sort Array with Duplicates
     * 
     * Sort an array that contains duplicates while maintaining the relative order of equal elements.
     * 
     * Example:
     * Input: [3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5]
     * Output: [1, 1, 2, 3, 3, 4, 5, 5, 5, 6, 9]
     * 
     * TODO: Implement this method
     */
    public void sortWithDuplicates(int[] arr) {
        // TODO: Your implementation here
    }
    
    /**
     * Problem 22: Sort Array in Wave Form
     * 
     * Sort an array in wave form such that arr[0] >= arr[1] <= arr[2] >= arr[3] <= arr[4]...
     * 
     * Example:
     * Input: [10, 5, 6, 3, 2, 20, 100, 80]
     * Output: [10, 5, 6, 2, 20, 3, 100, 80]
     * 
     * TODO: Implement this method
     */
    public void sortInWaveForm(int[] arr) {
        // TODO: Your implementation here
    }
    
    /**
     * Problem 23: Sort Array by Frequency
     * 
     * Sort an array by frequency of elements. If frequencies are same, maintain original order.
     * 
     * Example:
     * Input: [2, 5, 2, 8, 5, 6, 8, 8]
     * Output: [8, 8, 8, 2, 2, 5, 5, 6]
     * 
     * TODO: Implement this method
     */
    public void sortByFrequency(int[] arr) {
        // TODO: Your implementation here
    }
    
    /**
     * Problem 24: Sort Array with 0s, 1s, and 2s (Dutch National Flag)
     * 
     * Sort an array containing only 0s, 1s, and 2s in a single pass.
     * 
     * Example:
     * Input: [0, 1, 2, 0, 1, 2]
     * Output: [0, 0, 1, 1, 2, 2]
     * 
     * TODO: Implement this method
     */
    public void sort012(int[] arr) {
        // TODO: Your implementation here
    }
    
    /**
     * Problem 25: Sort Array with Minimum Swaps
     * 
     * Sort an array with minimum number of swaps.
     * 
     * Example:
     * Input: [4, 3, 2, 1]
     * Output: [1, 2, 3, 4] (minimum swaps needed)
     * 
     * TODO: Implement this method
     */
    public int sortWithMinSwaps(int[] arr) {
        // TODO: Your implementation here
        return 0;
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