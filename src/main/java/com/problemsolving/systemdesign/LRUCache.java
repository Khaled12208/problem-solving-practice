package com.problemsolving.systemdesign;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LRU (Least Recently Used) Cache using Java's LinkedHashMap
 * 
 * Design and implement a data structure for Least Recently Used (LRU) cache.
 * It should support the following operations: get and put.
 * 
 * get(key) - Get the value (will always be positive) of the key if the key exists in the cache,
 * otherwise return -1.
 * put(key, value) - Set or insert the value if the key is not already present.
 * When the cache reached its capacity, it should invalidate the least recently used item
 * before inserting a new item.
 * 
 * Implementation uses Java's LinkedHashMap with access-order enabled for O(1) operations.
 * 
 * Time Complexity: O(1) for both get and put operations
 * Space Complexity: O(capacity)
 * 
 * Example:
 * LRUCache cache = new LRUCache(2);
 * cache.put(1, 1);
 * cache.put(2, 2);
 * cache.get(1);       // returns 1
 * cache.put(3, 3);    // evicts key 2
 * cache.get(2);       // returns -1 (not found)
 */
public class LRUCache {
    private final Map<Integer, Integer> cache;
    
    public LRUCache(int capacity) {
        this.cache = new LinkedHashMap<Integer, Integer>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                return size() > capacity;
            }
        };
    }
    
    public int get(int key) {
        return cache.getOrDefault(key, -1);
    }
    
    public void put(int key, int value) {
        cache.put(key, value);
    }
    
    public int size() {
        return cache.size();
    }
    
    public boolean isEmpty() {
        return cache.isEmpty();
    }
    
    public void clear() {
        cache.clear();
    }
    
    public boolean containsKey(int key) {
        return cache.containsKey(key);
    }
    
    public void remove(int key) {
        cache.remove(key);
    }
    
    /**
     * Get all entries in the cache (for debugging/monitoring)
     */
    public Map<Integer, Integer> getAllEntries() {
        return new LinkedHashMap<>(cache);
    }
    
    /**
     * Get cache statistics
     */
    public CacheStats getStats() {
        return new CacheStats(cache.size(), cache.keySet().size());
    }
    
    public static class CacheStats {
        public final int size;
        public final int uniqueKeys;
        
        CacheStats(int size, int uniqueKeys) {
            this.size = size;
            this.uniqueKeys = uniqueKeys;
        }
        
        @Override
        public String toString() {
            return String.format("CacheStats{size=%d, uniqueKeys=%d}", size, uniqueKeys);
        }
    }
} 