package com.problemsolving.systemdesign;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Set;
import java.util.HashSet;

/**
 * Distributed Cache with Consistent Hashing using Java's TreeMap
 * 
 * Implement a distributed cache using consistent hashing to distribute data across nodes.
 * This provides better load distribution and minimal data movement when nodes are added/removed.
 * 
 * Implementation uses Java's TreeMap for the hash ring and HashMap for node data storage.
 * 
 * Time Complexity: O(log n) for node operations, O(1) for data operations
 * Space Complexity: O(n) where n is the number of nodes and data items
 * 
 * Example:
 * DistributedCache cache = new DistributedCache(3);
 * cache.addNode("node1");
 * cache.addNode("node2");
 * cache.put("key1", "value1");
 * String value = cache.get("key1");
 */
public class DistributedCache {
    private final TreeMap<Integer, String> ring;
    private final Map<String, Map<String, String>> nodeData;
    private final int virtualNodes;
    
    public DistributedCache(int virtualNodes) {
        this.ring = new TreeMap<>();
        this.nodeData = new HashMap<>();
        this.virtualNodes = virtualNodes;
    }
    
    /**
     * Add a new node to the distributed cache
     */
    public void addNode(String nodeId) {
        for (int i = 0; i < virtualNodes; i++) {
            String virtualNodeId = nodeId + "#" + i;
            int hash = hash(virtualNodeId);
            ring.put(hash, nodeId);
        }
        nodeData.put(nodeId, new HashMap<>());
    }
    
    /**
     * Remove a node from the distributed cache
     * Data will be redistributed to other nodes
     */
    public void removeNode(String nodeId) {
        for (int i = 0; i < virtualNodes; i++) {
            String virtualNodeId = nodeId + "#" + i;
            int hash = hash(virtualNodeId);
            ring.remove(hash);
        }
        
        // Redistribute data
        Map<String, String> data = nodeData.remove(nodeId);
        if (data != null) {
            for (Map.Entry<String, String> entry : data.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }
    
    /**
     * Store a key-value pair in the distributed cache
     */
    public void put(String key, String value) {
        String nodeId = getNodeForKey(key);
        nodeData.get(nodeId).put(key, value);
    }
    
    /**
     * Retrieve a value by key from the distributed cache
     */
    public String get(String key) {
        String nodeId = getNodeForKey(key);
        return nodeData.get(nodeId).get(key);
    }
    
    /**
     * Remove a key from the distributed cache
     */
    public void remove(String key) {
        String nodeId = getNodeForKey(key);
        nodeData.get(nodeId).remove(key);
    }
    
    /**
     * Check if a key exists in the cache
     */
    public boolean containsKey(String key) {
        String nodeId = getNodeForKey(key);
        return nodeData.get(nodeId).containsKey(key);
    }
    
    /**
     * Get the node responsible for a given key
     */
    private String getNodeForKey(String key) {
        int hash = hash(key);
        Map.Entry<Integer, String> entry = ring.ceilingEntry(hash);
        
        if (entry == null) {
            entry = ring.firstEntry();
        }
        
        return entry.getValue();
    }
    
    /**
     * Hash function for consistent hashing
     */
    private int hash(String key) {
        return key.hashCode();
    }
    
    /**
     * Get the distribution of data across nodes
     */
    public Map<String, Integer> getNodeDistribution() {
        Map<String, Integer> distribution = new HashMap<>();
        for (Map.Entry<String, Map<String, String>> entry : nodeData.entrySet()) {
            distribution.put(entry.getKey(), entry.getValue().size());
        }
        return distribution;
    }
    
    /**
     * Get all data from a specific node
     */
    public Map<String, String> getNodeData(String nodeId) {
        return new HashMap<>(nodeData.getOrDefault(nodeId, new HashMap<>()));
    }
    
    /**
     * Get the total number of nodes
     */
    public int getNodeCount() {
        return nodeData.size();
    }
    
    /**
     * Get the total number of virtual nodes
     */
    public int getVirtualNodeCount() {
        return ring.size();
    }
    
    /**
     * Get all node IDs
     */
    public Set<String> getNodeIds() {
        return new HashSet<>(nodeData.keySet());
    }
    
    /**
     * Get all keys in the cache
     */
    public Set<String> getAllKeys() {
        Set<String> allKeys = new HashSet<>();
        for (Map<String, String> data : nodeData.values()) {
            allKeys.addAll(data.keySet());
        }
        return allKeys;
    }
    
    /**
     * Get all values in the cache
     */
    public Set<String> getAllValues() {
        Set<String> allValues = new HashSet<>();
        for (Map<String, String> data : nodeData.values()) {
            allValues.addAll(data.values());
        }
        return allValues;
    }
    
    /**
     * Clear all data from the cache
     */
    public void clear() {
        for (Map<String, String> data : nodeData.values()) {
            data.clear();
        }
    }
    
    /**
     * Get cache statistics
     */
    public CacheStats getStats() {
        int totalKeys = nodeData.values().stream()
            .mapToInt(Map::size)
            .sum();
        
        return new CacheStats(
            getNodeCount(),
            getVirtualNodeCount(),
            totalKeys,
            virtualNodes
        );
    }
    
    /**
     * Get hash ring information (for debugging)
     */
    public Map<Integer, String> getHashRing() {
        return new TreeMap<>(ring);
    }
    
    /**
     * Check if a node exists
     */
    public boolean hasNode(String nodeId) {
        return nodeData.containsKey(nodeId);
    }
    
    /**
     * Get the number of keys stored on a specific node
     */
    public int getNodeKeyCount(String nodeId) {
        Map<String, String> data = nodeData.get(nodeId);
        return data != null ? data.size() : 0;
    }
    
    public static class CacheStats {
        public final int nodeCount;
        public final int virtualNodeCount;
        public final int totalKeys;
        public final int virtualNodesPerNode;
        
        CacheStats(int nodeCount, int virtualNodeCount, int totalKeys, int virtualNodesPerNode) {
            this.nodeCount = nodeCount;
            this.virtualNodeCount = virtualNodeCount;
            this.totalKeys = totalKeys;
            this.virtualNodesPerNode = virtualNodesPerNode;
        }
        
        @Override
        public String toString() {
            return String.format("CacheStats{nodeCount=%d, virtualNodeCount=%d, totalKeys=%d, virtualNodesPerNode=%d}",
                nodeCount, virtualNodeCount, totalKeys, virtualNodesPerNode);
        }
    }
} 