package com.problemsolving.systemdesign;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.TreeMap;

/**
 * LFU (Least Frequently Used) Cache using Java's TreeMap and LinkedHashSet
 * 
 * Design and implement a data structure for Least Frequently Used (LFU) cache.
 * It should support the following operations: get and put.
 * 
 * get(key) - Get the value (will always be positive) of the key if the key exists in the cache,
 * otherwise return -1.
 * put(key, value) - Set or insert the value if the key is not already present.
 * When the cache reaches its capacity, it should invalidate the least frequently used item.
 * If there is a tie, remove the least recently used item.
 * 
 * Implementation uses Java's TreeMap for frequency tracking and LinkedHashSet for LRU ordering.
 * 
 * Time Complexity: O(log n) for operations due to TreeMap
 * Space Complexity: O(capacity)
 * 
 * Example:
 * LFUCache cache = new LFUCache(2);
 * cache.put(1, 1);
 * cache.put(2, 2);
 * cache.get(1);       // returns 1
 * cache.put(3, 3);    // evicts key 2
 * cache.get(2);       // returns -1 (not found)
 */
public class LFUCache {
    private final int capacity;
    private final Map<Integer, Integer> cache; // key -> value
    private final Map<Integer, Integer> keyToFreq; // key -> frequency
    private final TreeMap<Integer, LinkedHashSet<Integer>> freqToKeys; // frequency -> set of keys
    private int minFreq;
    
    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.keyToFreq = new HashMap<>();
        this.freqToKeys = new TreeMap<>();
        this.minFreq = 0;
    }
    
    public int get(int key) {
        if (!cache.containsKey(key)) {
            return -1;
        }
        
        updateFrequency(key);
        return cache.get(key);
    }
    
    public void put(int key, int value) {
        if (capacity == 0) return;
        
        if (cache.containsKey(key)) {
            cache.put(key, value);
            updateFrequency(key);
        } else {
            if (cache.size() >= capacity) {
                evictLFU();
            }
            
            cache.put(key, value);
            keyToFreq.put(key, 1);
            freqToKeys.computeIfAbsent(1, k -> new LinkedHashSet<>()).add(key);
            minFreq = 1;
        }
    }
    
    private void updateFrequency(int key) {
        int freq = keyToFreq.get(key);
        LinkedHashSet<Integer> keys = freqToKeys.get(freq);
        keys.remove(key);
        
        if (keys.isEmpty()) {
            freqToKeys.remove(freq);
            if (freq == minFreq) {
                minFreq = freqToKeys.isEmpty() ? 0 : freqToKeys.firstKey();
            }
        }
        
        freq++;
        keyToFreq.put(key, freq);
        freqToKeys.computeIfAbsent(freq, k -> new LinkedHashSet<>()).add(key);
    }
    
    private void evictLFU() {
        LinkedHashSet<Integer> keys = freqToKeys.get(minFreq);
        int keyToRemove = keys.iterator().next(); // Get first (LRU) key
        keys.remove(keyToRemove);
        
        if (keys.isEmpty()) {
            freqToKeys.remove(minFreq);
        }
        
        cache.remove(keyToRemove);
        keyToFreq.remove(keyToRemove);
    }
    
    public int size() {
        return cache.size();
    }
    
    public boolean isEmpty() {
        return cache.isEmpty();
    }
    
    public void clear() {
        cache.clear();
        keyToFreq.clear();
        freqToKeys.clear();
        minFreq = 0;
    }
    
    public boolean containsKey(int key) {
        return cache.containsKey(key);
    }
    
    public void remove(int key) {
        if (!cache.containsKey(key)) return;
        
        int freq = keyToFreq.get(key);
        LinkedHashSet<Integer> keys = freqToKeys.get(freq);
        keys.remove(key);
        
        if (keys.isEmpty()) {
            freqToKeys.remove(freq);
            if (freq == minFreq) {
                minFreq = freqToKeys.isEmpty() ? 0 : freqToKeys.firstKey();
            }
        }
        
        cache.remove(key);
        keyToFreq.remove(key);
    }
    
    /**
     * Get cache statistics
     */
    public CacheStats getStats() {
        return new CacheStats(
            cache.size(),
            keyToFreq.size(),
            minFreq,
            freqToKeys.size()
        );
    }
    
    /**
     * Get frequency distribution
     */
    public Map<Integer, Integer> getFrequencyDistribution() {
        Map<Integer, Integer> distribution = new HashMap<>();
        for (Map.Entry<Integer, LinkedHashSet<Integer>> entry : freqToKeys.entrySet()) {
            distribution.put(entry.getKey(), entry.getValue().size());
        }
        return distribution;
    }
    
    public static class CacheStats {
        public final int cacheSize;
        public final int uniqueKeys;
        public final int minFrequency;
        public final int frequencyLevels;
        
        CacheStats(int cacheSize, int uniqueKeys, int minFrequency, int frequencyLevels) {
            this.cacheSize = cacheSize;
            this.uniqueKeys = uniqueKeys;
            this.minFrequency = minFrequency;
            this.frequencyLevels = frequencyLevels;
        }
        
        @Override
        public String toString() {
            return String.format("CacheStats{size=%d, uniqueKeys=%d, minFreq=%d, freqLevels=%d}",
                cacheSize, uniqueKeys, minFrequency, frequencyLevels);
        }
    }
} 
} 