package com.problemsolving.trees;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Decision Tree Problems
 * Practice problems focused on decision tree algorithms and implementations
 */
public class DecisionTreeProblems {
    
    /**
     * Problem 1: Implement Basic Decision Tree Node
     * 
     * Create a basic decision tree node structure that can be used for classification.
     * 
     * TODO: Implement this class
     */
    public static class DecisionTreeNode {
        private String feature;
        private double threshold;
        private DecisionTreeNode left;
        private DecisionTreeNode right;
        private String prediction;
        private boolean isLeaf;
        
        public DecisionTreeNode() {}
        public DecisionTreeNode(String feature, double threshold) {
            this.feature = feature;
            this.threshold = threshold;
            this.isLeaf = false;
        }
        public DecisionTreeNode(String prediction) {
            this.prediction = prediction;
            this.isLeaf = true;
        }
        public String getFeature() { return feature; }
        public void setFeature(String feature) { this.feature = feature; }
        public double getThreshold() { return threshold; }
        public void setThreshold(double threshold) { this.threshold = threshold; }
        public DecisionTreeNode getLeft() { return left; }
        public void setLeft(DecisionTreeNode left) { this.left = left; }
        public DecisionTreeNode getRight() { return right; }
        public void setRight(DecisionTreeNode right) { this.right = right; }
        public String getPrediction() { return prediction; }
        public void setPrediction(String prediction) { this.prediction = prediction; }
        public boolean isLeaf() { return isLeaf; }
        public void setLeaf(boolean leaf) { this.isLeaf = leaf; }
    }
    
    /**
     * Problem 2: Calculate Entropy
     * 
     * Calculate the entropy of a dataset for a given target variable.
     * Entropy = -Σ(p * log2(p)) where p is the probability of each class.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(k) where k is the number of unique classes
     * 
     * Example:
     * Input: labels = ["Yes", "No", "Yes", "Yes", "No"]
     * Output: 0.971 (entropy value)
     * 
     * TODO: Implement this method
     */
    public double calculateEntropy(String[] labels) {
        int n = labels.length;
        Map<String, Integer> map = new HashMap<>();
        for (String label : labels) {
            map.put(label, map.getOrDefault(label, 0) + 1);
        }
        double entropy = 0.0;
        for (int count : map.values()) {
            double p = (double) count / n;
            entropy -= p * (p == 0 ? 0 : Math.log(p) / Math.log(2));
        }
        return entropy;
    }
    
    /**
     * Problem 3: Calculate Information Gain
     * 
     * Calculate the information gain for splitting a dataset on a given feature.
     * Information Gain = Entropy(parent) - Σ(|Sv|/|S| * Entropy(Sv))
     * 
     * Time Complexity: O(n * m) where n is number of samples, m is number of features
     * Space Complexity: O(k) where k is the number of unique values
     * 
     * Example:
     * Input: feature_values = [1, 2, 1, 2, 1], labels = ["Yes", "No", "Yes", "No", "Yes"]
     * Output: information gain value
     * 
     * TODO: Implement this method
     */
    public double calculateInformationGain(double[] featureValues, String[] labels) {
        double baseEntropy = calculateEntropy(labels);
        Map<Double, List<String>> split = new HashMap<>();
        for (int i = 0; i < featureValues.length; i++) {
            split.computeIfAbsent(featureValues[i], k -> new ArrayList<>()).add(labels[i]);
        }
        double splitEntropy = 0.0;
        for (List<String> group : split.values()) {
            double p = (double) group.size() / labels.length;
            splitEntropy += p * calculateEntropy(group.toArray(new String[0]));
        }
        return baseEntropy - splitEntropy;
    }
    
    /**
     * Problem 4: Find Best Split
     * 
     * Find the best feature and threshold to split the dataset on.
     * 
     * Time Complexity: O(n * m * log(n)) where n is samples, m is features
     * Space Complexity: O(n)
     * 
     * Example:
     * Input: dataset with features and labels
     * Output: best feature name and threshold
     * 
     * TODO: Implement this method
     */
    public Map<String, Object> findBestSplit(double[][] features, String[] labels) {
        int n = features.length, m = features[0].length;
        double bestGain = -1;
        int bestFeature = -1;
        double bestThreshold = 0;
        for (int j = 0; j < m; j++) {
            double[] col = new double[n];
            for (int i = 0; i < n; i++) col[i] = features[i][j];
            Set<Double> unique = new HashSet<>();
            for (double v : col) unique.add(v);
            for (double threshold : unique) {
                String[] leftLabels = Arrays.stream(IntStream.range(0, n).filter(i -> col[i] <= threshold).toArray()).mapToObj(i -> labels[i]).toArray(String[]::new);
                String[] rightLabels = Arrays.stream(IntStream.range(0, n).filter(i -> col[i] > threshold).toArray()).mapToObj(i -> labels[i]).toArray(String[]::new);
                double gain = calculateEntropy(labels) - ((double) leftLabels.length / n) * calculateEntropy(leftLabels) - ((double) rightLabels.length / n) * calculateEntropy(rightLabels);
                if (gain > bestGain) {
                    bestGain = gain;
                    bestFeature = j;
                    bestThreshold = threshold;
                }
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("featureIndex", bestFeature);
        result.put("threshold", bestThreshold);
        result.put("infoGain", bestGain);
        return result;
    }
    
    /**
     * Problem 5: Build Decision Tree
     * 
     * Build a decision tree recursively using the ID3 algorithm.
     * 
     * Time Complexity: O(n * m * log(n) * depth)
     * Space Complexity: O(n * depth)
     * 
     * Example:
     * Input: training data with features and labels
     * Output: root node of the decision tree
     * 
     * TODO: Implement this method
     */
    public DecisionTreeNode buildDecisionTree(double[][] features, String[] labels, int maxDepth) {
        return buildDecisionTreeHelper(features, labels, maxDepth, 0);
    }

    public DecisionTreeNode buildDecisionTreeHelper(double[][] features, String[] labels, int maxDepth, int depth) {
        if (labels.length == 0) return null;
        if (allSame(labels) || depth == maxDepth) {
            return new DecisionTreeNode(majorityLabel(labels));
        }
        Map<String, Object> split = findBestSplit(features, labels);
        int featureIndex = (int) split.get("featureIndex");
        double threshold = (double) split.get("threshold");
        List<Integer> leftIdx = new ArrayList<>();
        List<Integer> rightIdx = new ArrayList<>();
        for (int i = 0; i < features.length; i++) {
            if (features[i][featureIndex] <= threshold) leftIdx.add(i);
            else rightIdx.add(i);
        }
        double[][] leftFeatures = new double[leftIdx.size()][];
        String[] leftLabels = new String[leftIdx.size()];
        for (int i = 0; i < leftIdx.size(); i++) {
            leftFeatures[i] = features[leftIdx.get(i)];
            leftLabels[i] = labels[leftIdx.get(i)];
        }
        double[][] rightFeatures = new double[rightIdx.size()][];
        String[] rightLabels = new String[rightIdx.size()];
        for (int i = 0; i < rightIdx.size(); i++) {
            rightFeatures[i] = features[rightIdx.get(i)];
            rightLabels[i] = labels[rightIdx.get(i)];
        }
        DecisionTreeNode node = new DecisionTreeNode("Feature" + featureIndex, threshold);
        node.setLeft(buildDecisionTreeHelper(leftFeatures, leftLabels, maxDepth, depth + 1));
        node.setRight(buildDecisionTreeHelper(rightFeatures, rightLabels, maxDepth, depth + 1));
        return node;
    }

    private boolean allSame(String[] labels) {
        for (int i = 1; i < labels.length; i++) {
            if (!labels[i].equals(labels[0])) return false;
        }
        return true;
    }

    private String majorityLabel(String[] labels) {
        Map<String, Integer> count = new HashMap<>();
        for (String label : labels) count.put(label, count.getOrDefault(label, 0) + 1);
        return Collections.max(count.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    /**
     * Problem 6: Predict Using Decision Tree
     * 
     * Make a prediction for a given sample using the trained decision tree.
     * 
     * Time Complexity: O(depth)
     * Space Complexity: O(1)
     * 
     * Example:
     * Input: root node and feature vector
     * Output: predicted class
     * 
     * TODO: Implement this method
     */
    public String predict(DecisionTreeNode root, double[] features) {
        DecisionTreeNode node = root;
        while (!node.isLeaf()) {
            int featureIndex = Integer.parseInt(node.getFeature().replace("Feature", ""));
            if (features[featureIndex] <= node.getThreshold()) node = node.getLeft();
            else node = node.getRight();
        }
        return node.getPrediction();
    }
    
    /**
     * Problem 7: Calculate Gini Impurity
     * 
     * Calculate the Gini impurity of a dataset.
     * Gini = 1 - Σ(p²) where p is the probability of each class.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(k)
     * 
     * Example:
     * Input: labels = ["Yes", "No", "Yes", "Yes", "No"]
     * Output: 0.48 (gini impurity value)
     * 
     * TODO: Implement this method
     */
    public double calculateGiniImpurity(String[] labels) {
        // TODO: Your implementation here
        return 0.0;
    }
    
    /**
     * Problem 8: Prune Decision Tree
     * 
     * Prune a decision tree to prevent overfitting using reduced error pruning.
     * 
     * Time Complexity: O(n * depth)
     * Space Complexity: O(depth)
     * 
     * Example:
     * Input: decision tree and validation data
     * Output: pruned decision tree
     * 
     * TODO: Implement this method
     */
    public DecisionTreeNode pruneTree(DecisionTreeNode root, double[][] validationFeatures, String[] validationLabels) {
        // TODO: Your implementation here
        return null;
    }
    
    /**
     * Problem 9: Visualize Decision Tree
     * 
     * Create a string representation of the decision tree for visualization.
     * 
     * Time Complexity: O(nodes)
     * Space Complexity: O(depth)
     * 
     * Example:
     * Input: root node
     * Output: string representation of the tree
     * 
     * TODO: Implement this method
     */
    public String visualizeTree(DecisionTreeNode root) {
        // TODO: Your implementation here
        return "";
    }
    
    /**
     * Problem 10: Random Forest Implementation
     * 
     * Implement a simple random forest by combining multiple decision trees.
     * 
     * Time Complexity: O(n_trees * n * m * log(n) * depth)
     * Space Complexity: O(n_trees * n * depth)
     * 
     * Example:
     * Input: training data and number of trees
     * Output: list of decision trees
     * 
     * TODO: Implement this method
     */
    public List<DecisionTreeNode> buildRandomForest(double[][] features, String[] labels, int nTrees, int maxDepth) {
        // TODO: Your implementation here
        return new ArrayList<>();
    }
    
    /**
     * Problem 11: Predict Using Random Forest
     * 
     * Make a prediction using majority voting from multiple decision trees.
     * 
     * Time Complexity: O(n_trees * depth)
     * Space Complexity: O(n_trees)
     * 
     * Example:
     * Input: list of trees and feature vector
     * Output: predicted class
     * 
     * TODO: Implement this method
     */
    public String predictRandomForest(List<DecisionTreeNode> trees, double[] features) {
        // TODO: Your implementation here
        return "";
    }
    
    /**
     * Problem 12: Feature Importance
     * 
     * Calculate the importance of each feature in the decision tree.
     * 
     * Time Complexity: O(nodes)
     * Space Complexity: O(features)
     * 
     * Example:
     * Input: decision tree and feature names
     * Output: map of feature names to importance scores
     * 
     * TODO: Implement this method
     */
    public Map<String, Double> calculateFeatureImportance(DecisionTreeNode root, String[] featureNames) {
        // TODO: Your implementation here
        return new HashMap<>();
    }
    
    /**
     * Problem 13: Cross Validation
     * 
     * Implement k-fold cross validation for decision tree evaluation.
     * 
     * Time Complexity: O(k * n * m * log(n) * depth)
     * Space Complexity: O(n)
     * 
     * Example:
     * Input: dataset and number of folds
     * Output: average accuracy across folds
     * 
     * TODO: Implement this method
     */
    public double crossValidate(double[][] features, String[] labels, int kFolds, int maxDepth) {
        // TODO: Your implementation here
        return 0.0;
    }
    
    /**
     * Problem 14: Handle Missing Values
     * 
     * Implement a strategy to handle missing values in decision tree training.
     * 
     * Time Complexity: O(n * m)
     * Space Complexity: O(n * m)
     * 
     * Example:
     * Input: dataset with missing values
     * Output: dataset with imputed values
     * 
     * TODO: Implement this method
     */
    public double[][] handleMissingValues(double[][] features) {
        // TODO: Your implementation here
        return new double[0][0];
    }
    
    /**
     * Problem 15: Decision Tree for Regression
     * 
     * Implement a decision tree for regression problems (predicting continuous values).
     * 
     * Time Complexity: O(n * m * log(n) * depth)
     * Space Complexity: O(n * depth)
     * 
     * Example:
     * Input: features and continuous target values
     * Output: regression decision tree
     * 
     * TODO: Implement this method
     */
    public DecisionTreeNode buildRegressionTree(double[][] features, double[] targets, int maxDepth) {
        // TODO: Your implementation here
        return null;
    }
    
    /**
     * Problem 16: Calculate Mean Squared Error
     * 
     * Calculate the mean squared error for regression predictions.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     * 
     * Example:
     * Input: actual and predicted values
     * Output: MSE value
     * 
     * TODO: Implement this method
     */
    public double calculateMSE(double[] actual, double[] predicted) {
        // TODO: Your implementation here
        return 0.0;
    }
    
    /**
     * Problem 17: Decision Tree Serialization
     * 
     * Serialize and deserialize a decision tree to/from JSON format.
     * 
     * Time Complexity: O(nodes)
     * Space Complexity: O(nodes)
     * 
     * Example:
     * Input: decision tree
     * Output: JSON string representation
     * 
     * TODO: Implement this method
     */
    public String serializeTree(DecisionTreeNode root) {
        // TODO: Your implementation here
        return "";
    }
    
    /**
     * Problem 18: Deserialize Decision Tree
     * 
     * Deserialize a decision tree from JSON format.
     * 
     * Time Complexity: O(nodes)
     * Space Complexity: O(nodes)
     * 
     * Example:
     * Input: JSON string
     * Output: decision tree root node
     * 
     * TODO: Implement this method
     */
    public DecisionTreeNode deserializeTree(String json) {
        // TODO: Your implementation here
        return null;
    }
    
    /**
     * Problem 19: Decision Tree for Multi-class Classification
     * 
     * Extend the decision tree to handle multi-class classification problems.
     * 
     * Time Complexity: O(n * m * log(n) * depth)
     * Space Complexity: O(n * depth)
     * 
     * Example:
     * Input: features and multi-class labels
     * Output: multi-class decision tree
     * 
     * TODO: Implement this method
     */
    public DecisionTreeNode buildMultiClassTree(double[][] features, String[] labels, int maxDepth) {
        // TODO: Your implementation here
        return null;
    }
    
    /**
     * Problem 20: Decision Tree Ensemble Methods
     * 
     * Implement ensemble methods like AdaBoost with decision trees.
     * 
     * Time Complexity: O(n_iterations * n * m * log(n) * depth)
     * Space Complexity: O(n_iterations * n * depth)
     * 
     * Example:
     * Input: training data and number of iterations
     * Output: ensemble of weighted decision trees
     * 
     * TODO: Implement this method
     */
    public List<Map<String, Object>> buildAdaBoostEnsemble(double[][] features, String[] labels, int nIterations) {
        // TODO: Your implementation here
        return new ArrayList<>();
    }
    
    // Utility method to print tree structure
    public void printTree(DecisionTreeNode root) {
        // TODO: Your implementation here
    }
    
    // Utility method to calculate accuracy
    public double calculateAccuracy(String[] actual, String[] predicted) {
        // TODO: Your implementation here
        return 0.0;
    }
} 