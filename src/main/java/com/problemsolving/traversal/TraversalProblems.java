package com.problemsolving.traversal;

import java.util.*;

/**
 * Collection of DFS, BFS, and tree traversal problems.
 * This class contains various algorithms for traversing trees and graphs.
 */
public class TraversalProblems {
    
    /**
     * Problem: Binary Tree Inorder Traversal
     * Given the root of a binary tree, return the inorder traversal of its nodes' values.
     * 
     * Example:
     * Input: root = [1,null,2,3]
     * Output: [1,3,2]
     * 
     * @param root array representation of binary tree
     * @return inorder traversal
     */
    public List<Integer> inorderTraversal(Integer[] root) {
        List<Integer> result = new ArrayList<>();
        inorderHelper(root, 0, result);
        return result;
    }
    
    private void inorderHelper(Integer[] root, int index, List<Integer> result) {
        if (index >= root.length || root[index] == null) return;
        
        inorderHelper(root, 2 * index + 1, result); // Left
        result.add(root[index]); // Root
        inorderHelper(root, 2 * index + 2, result); // Right
    }
    
    /**
     * Problem: Binary Tree Preorder Traversal
     * Given the root of a binary tree, return the preorder traversal of its nodes' values.
     * 
     * Example:
     * Input: root = [1,null,2,3]
     * Output: [1,2,3]
     * 
     * @param root array representation of binary tree
     * @return preorder traversal
     */
    public List<Integer> preorderTraversal(Integer[] root) {
        List<Integer> result = new ArrayList<>();
        preorderHelper(root, 0, result);
        return result;
    }
    
    private void preorderHelper(Integer[] root, int index, List<Integer> result) {
        if (index >= root.length || root[index] == null) return;
        
        result.add(root[index]); // Root
        preorderHelper(root, 2 * index + 1, result); // Left
        preorderHelper(root, 2 * index + 2, result); // Right
    }
    
    /**
     * Problem: Binary Tree Postorder Traversal
     * Given the root of a binary tree, return the postorder traversal of its nodes' values.
     * 
     * Example:
     * Input: root = [1,null,2,3]
     * Output: [3,2,1]
     * 
     * @param root array representation of binary tree
     * @return postorder traversal
     */
    public List<Integer> postorderTraversal(Integer[] root) {
        List<Integer> result = new ArrayList<>();
        postorderHelper(root, 0, result);
        return result;
    }
    
    private void postorderHelper(Integer[] root, int index, List<Integer> result) {
        if (index >= root.length || root[index] == null) return;
        
        postorderHelper(root, 2 * index + 1, result); // Left
        postorderHelper(root, 2 * index + 2, result); // Right
        result.add(root[index]); // Root
    }
    
    /**
     * Problem: Binary Tree Level Order Traversal
     * Given the root of a binary tree, return the level order traversal of its nodes' values.
     * 
     * Example:
     * Input: root = [3,9,20,null,null,15,7]
     * Output: [[3],[9,20],[15,7]]
     * 
     * @param root array representation of binary tree
     * @return level order traversal
     */
    public List<List<Integer>> levelOrder(Integer[] root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root.length == 0 || root[0] == null) return result;
        
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> level = new ArrayList<>();
            
            for (int i = 0; i < levelSize; i++) {
                int index = queue.poll();
                if (index < root.length && root[index] != null) {
                    level.add(root[index]);
                    
                    // Add children to queue
                    int leftChild = 2 * index + 1;
                    int rightChild = 2 * index + 2;
                    
                    if (leftChild < root.length && root[leftChild] != null) {
                        queue.offer(leftChild);
                    }
                    if (rightChild < root.length && root[rightChild] != null) {
                        queue.offer(rightChild);
                    }
                }
            }
            
            if (!level.isEmpty()) {
                result.add(level);
            }
        }
        
        return result;
    }
    
    /**
     * Problem: Binary Tree Level Order Traversal II
     * Given the root of a binary tree, return the bottom-up level order traversal of its nodes' values.
     * 
     * Example:
     * Input: root = [3,9,20,null,null,15,7]
     * Output: [[15,7],[9,20],[3]]
     * 
     * @param root array representation of binary tree
     * @return bottom-up level order traversal
     */
    public List<List<Integer>> levelOrderBottom(Integer[] root) {
        List<List<Integer>> result = levelOrder(root);
        Collections.reverse(result);
        return result;
    }
    
    /**
     * Problem: Binary Tree Zigzag Level Order Traversal
     * Given the root of a binary tree, return the zigzag level order traversal of its nodes' values.
     * 
     * Example:
     * Input: root = [3,9,20,null,null,15,7]
     * Output: [[3],[20,9],[15,7]]
     * 
     * @param root array representation of binary tree
     * @return zigzag level order traversal
     */
    public List<List<Integer>> zigzagLevelOrder(Integer[] root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root.length == 0 || root[0] == null) return result;
        
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        boolean leftToRight = true;
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> level = new ArrayList<>();
            
            for (int i = 0; i < levelSize; i++) {
                int index = queue.poll();
                if (index < root.length && root[index] != null) {
                    level.add(root[index]);
                    
                    int leftChild = 2 * index + 1;
                    int rightChild = 2 * index + 2;
                    
                    if (leftChild < root.length && root[leftChild] != null) {
                        queue.offer(leftChild);
                    }
                    if (rightChild < root.length && root[rightChild] != null) {
                        queue.offer(rightChild);
                    }
                }
            }
            
            if (!level.isEmpty()) {
                if (!leftToRight) {
                    Collections.reverse(level);
                }
                result.add(level);
                leftToRight = !leftToRight;
            }
        }
        
        return result;
    }
    
    /**
     * Problem: Maximum Depth of Binary Tree
     * Given the root of a binary tree, return its maximum depth.
     * 
     * Example:
     * Input: root = [3,9,20,null,null,15,7]
     * Output: 3
     * 
     * @param root array representation of binary tree
     * @return maximum depth
     */
    public int maxDepth(Integer[] root) {
        return maxDepthHelper(root, 0);
    }
    
    private int maxDepthHelper(Integer[] root, int index) {
        if (index >= root.length || root[index] == null) return 0;
        
        int leftDepth = maxDepthHelper(root, 2 * index + 1);
        int rightDepth = maxDepthHelper(root, 2 * index + 2);
        
        return Math.max(leftDepth, rightDepth) + 1;
    }
    
    /**
     * Problem: Minimum Depth of Binary Tree
     * Given the root of a binary tree, return its minimum depth.
     * 
     * Example:
     * Input: root = [3,9,20,null,null,15,7]
     * Output: 2
     * 
     * @param root array representation of binary tree
     * @return minimum depth
     */
    public int minDepth(Integer[] root) {
        return minDepthHelper(root, 0);
    }
    
    private int minDepthHelper(Integer[] root, int index) {
        if (index >= root.length || root[index] == null) return 0;
        
        int leftDepth = minDepthHelper(root, 2 * index + 1);
        int rightDepth = minDepthHelper(root, 2 * index + 2);
        
        if (leftDepth == 0) return rightDepth + 1;
        if (rightDepth == 0) return leftDepth + 1;
        
        return Math.min(leftDepth, rightDepth) + 1;
    }
    
    /**
     * Problem: Path Sum
     * Given the root of a binary tree and an integer targetSum, return true if the tree has a 
     * root-to-leaf path such that adding up all the values along the path equals targetSum.
     * 
     * Example:
     * Input: root = [5,4,8,11,null,13,4,7,2,null,null,null,1], targetSum = 22
     * Output: true
     * 
     * @param root array representation of binary tree
     * @param targetSum target sum
     * @return true if path exists
     */
    public boolean hasPathSum(Integer[] root, int targetSum) {
        return hasPathSumHelper(root, 0, targetSum, 0);
    }
    
    private boolean hasPathSumHelper(Integer[] root, int index, int targetSum, int currentSum) {
        if (index >= root.length || root[index] == null) return false;
        
        currentSum += root[index];
        
        // Check if leaf node
        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;
        boolean isLeaf = (leftChild >= root.length || root[leftChild] == null) && 
                        (rightChild >= root.length || root[rightChild] == null);
        
        if (isLeaf) {
            return currentSum == targetSum;
        }
        
        return hasPathSumHelper(root, leftChild, targetSum, currentSum) ||
               hasPathSumHelper(root, rightChild, targetSum, currentSum);
    }
    
    /**
     * Problem: Path Sum II
     * Given the root of a binary tree and an integer targetSum, return all root-to-leaf paths 
     * where the sum of the node values in the path equals targetSum.
     * 
     * Example:
     * Input: root = [5,4,8,11,null,13,4,7,2,null,null,null,1], targetSum = 22
     * Output: [[5,4,11,2],[5,8,4,5]]
     * 
     * @param root array representation of binary tree
     * @param targetSum target sum
     * @return all paths that sum to target
     */
    public List<List<Integer>> pathSum(Integer[] root, int targetSum) {
        List<List<Integer>> result = new ArrayList<>();
        pathSumHelper(root, 0, targetSum, 0, new ArrayList<>(), result);
        return result;
    }
    
    private void pathSumHelper(Integer[] root, int index, int targetSum, int currentSum, 
                             List<Integer> path, List<List<Integer>> result) {
        if (index >= root.length || root[index] == null) return;
        
        currentSum += root[index];
        path.add(root[index]);
        
        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;
        boolean isLeaf = (leftChild >= root.length || root[leftChild] == null) && 
                        (rightChild >= root.length || root[rightChild] == null);
        
        if (isLeaf && currentSum == targetSum) {
            result.add(new ArrayList<>(path));
        }
        
        pathSumHelper(root, leftChild, targetSum, currentSum, path, result);
        pathSumHelper(root, rightChild, targetSum, currentSum, path, result);
        
        path.remove(path.size() - 1);
    }
    
    /**
     * Problem: Sum Root to Leaf Numbers
     * Given the root of a binary tree containing digits from 0 to 9 only, each root-to-leaf 
     * path in the tree represents a number. Return the total sum of all root-to-leaf numbers.
     * 
     * Example:
     * Input: root = [1,2,3]
     * Output: 25 (12 + 13 = 25)
     * 
     * @param root array representation of binary tree
     * @return sum of all root-to-leaf numbers
     */
    public int sumNumbers(Integer[] root) {
        return sumNumbersHelper(root, 0, 0);
    }
    
    private int sumNumbersHelper(Integer[] root, int index, int currentSum) {
        if (index >= root.length || root[index] == null) return 0;
        
        currentSum = currentSum * 10 + root[index];
        
        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;
        boolean isLeaf = (leftChild >= root.length || root[leftChild] == null) && 
                        (rightChild >= root.length || root[rightChild] == null);
        
        if (isLeaf) {
            return currentSum;
        }
        
        return sumNumbersHelper(root, leftChild, currentSum) +
               sumNumbersHelper(root, rightChild, currentSum);
    }
    
    /**
     * Problem: Binary Tree Right Side View
     * Given the root of a binary tree, imagine yourself standing on the right side of it, 
     * return the values of the nodes you can see ordered from top to bottom.
     * 
     * Example:
     * Input: root = [1,2,3,null,5,null,4]
     * Output: [1,3,4]
     * 
     * @param root array representation of binary tree
     * @return right side view
     */
    public List<Integer> rightSideView(Integer[] root) {
        List<Integer> result = new ArrayList<>();
        if (root.length == 0 || root[0] == null) return result;
        
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            
            for (int i = 0; i < levelSize; i++) {
                int index = queue.poll();
                if (index < root.length && root[index] != null) {
                    // Add rightmost node of each level
                    if (i == levelSize - 1) {
                        result.add(root[index]);
                    }
                    
                    int leftChild = 2 * index + 1;
                    int rightChild = 2 * index + 2;
                    
                    if (leftChild < root.length && root[leftChild] != null) {
                        queue.offer(leftChild);
                    }
                    if (rightChild < root.length && root[rightChild] != null) {
                        queue.offer(rightChild);
                    }
                }
            }
        }
        
        return result;
    }
    
    /**
     * Problem: Binary Tree Left Side View
     * Given the root of a binary tree, imagine yourself standing on the left side of it, 
     * return the values of the nodes you can see ordered from top to bottom.
     * 
     * Example:
     * Input: root = [1,2,3,null,5,null,4]
     * Output: [1,2,5]
     * 
     * @param root array representation of binary tree
     * @return left side view
     */
    public List<Integer> leftSideView(Integer[] root) {
        List<Integer> result = new ArrayList<>();
        if (root.length == 0 || root[0] == null) return result;
        
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            
            for (int i = 0; i < levelSize; i++) {
                int index = queue.poll();
                if (index < root.length && root[index] != null) {
                    // Add leftmost node of each level
                    if (i == 0) {
                        result.add(root[index]);
                    }
                    
                    int leftChild = 2 * index + 1;
                    int rightChild = 2 * index + 2;
                    
                    if (leftChild < root.length && root[leftChild] != null) {
                        queue.offer(leftChild);
                    }
                    if (rightChild < root.length && root[rightChild] != null) {
                        queue.offer(rightChild);
                    }
                }
            }
        }
        
        return result;
    }
    
    /**
     * Problem: Count Complete Tree Nodes
     * Given the root of a complete binary tree, return the number of the nodes in the tree.
     * 
     * Example:
     * Input: root = [1,2,3,4,5,6]
     * Output: 6
     * 
     * @param root array representation of complete binary tree
     * @return number of nodes
     */
    public int countNodes(Integer[] root) {
        return countNodesHelper(root, 0);
    }
    
    private int countNodesHelper(Integer[] root, int index) {
        if (index >= root.length || root[index] == null) return 0;
        
        return 1 + countNodesHelper(root, 2 * index + 1) + 
                   countNodesHelper(root, 2 * index + 2);
    }
    
    /**
     * Problem: Average of Levels in Binary Tree
     * Given the root of a binary tree, return the average value of the nodes on each level.
     * 
     * Example:
     * Input: root = [3,9,20,null,null,15,7]
     * Output: [3.0,14.5,11.0]
     * 
     * @param root array representation of binary tree
     * @return average of each level
     */
    public List<Double> averageOfLevels(Integer[] root) {
        List<Double> result = new ArrayList<>();
        if (root.length == 0 || root[0] == null) return result;
        
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            double levelSum = 0;
            int count = 0;
            
            for (int i = 0; i < levelSize; i++) {
                int index = queue.poll();
                if (index < root.length && root[index] != null) {
                    levelSum += root[index];
                    count++;
                    
                    int leftChild = 2 * index + 1;
                    int rightChild = 2 * index + 2;
                    
                    if (leftChild < root.length && root[leftChild] != null) {
                        queue.offer(leftChild);
                    }
                    if (rightChild < root.length && root[rightChild] != null) {
                        queue.offer(rightChild);
                    }
                }
            }
            
            if (count > 0) {
                result.add(levelSum / count);
            }
        }
        
        return result;
    }
    
    /**
     * Problem: Find Largest Value in Each Tree Row
     * Given the root of a binary tree, return an array of the largest value in each row of the tree.
     * 
     * Example:
     * Input: root = [1,3,2,5,3,null,9]
     * Output: [1,3,9]
     * 
     * @param root array representation of binary tree
     * @return largest value in each row
     */
    public List<Integer> largestValues(Integer[] root) {
        List<Integer> result = new ArrayList<>();
        if (root.length == 0 || root[0] == null) return result;
        
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            int maxVal = Integer.MIN_VALUE;
            
            for (int i = 0; i < levelSize; i++) {
                int index = queue.poll();
                if (index < root.length && root[index] != null) {
                    maxVal = Math.max(maxVal, root[index]);
                    
                    int leftChild = 2 * index + 1;
                    int rightChild = 2 * index + 2;
                    
                    if (leftChild < root.length && root[leftChild] != null) {
                        queue.offer(leftChild);
                    }
                    if (rightChild < root.length && root[rightChild] != null) {
                        queue.offer(rightChild);
                    }
                }
            }
            
            if (maxVal != Integer.MIN_VALUE) {
                result.add(maxVal);
            }
        }
        
        return result;
    }
} 