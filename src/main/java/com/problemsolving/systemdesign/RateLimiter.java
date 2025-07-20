package com.problemsolving.systemdesign;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Rate Limiter
 * 
 * Implement a rate limiter that limits the number of requests per time window.
 * Supports different strategies: Fixed Window, Sliding Window, Token Bucket, and Leaky Bucket.
 * 
 * Time Complexity: O(1) for most operations, O(n) for cleanup in worst case
 * Space Complexity: O(n) where n is the number of requests in the window
 * 
 * Example:
 * RateLimiter limiter = new RateLimiter(10, 1000); // 10 requests per second
 * boolean allowed = limiter.allowRequestFixedWindow("user1");
 */
public class RateLimiter {
    private final int maxRequests;
    private final long windowMs;
    private final Map<String, Queue<Long>> requestTimestamps;
    private final Map<String, Integer> tokenBucket;
    private final int bucketCapacity;
    private final long refillRateMs;
    
    public RateLimiter(int maxRequests, long windowMs) {
        this.maxRequests = maxRequests;
        this.windowMs = windowMs;
        this.requestTimestamps = new HashMap<>();
        this.tokenBucket = new HashMap<>();
        this.bucketCapacity = maxRequests;
        this.refillRateMs = windowMs / maxRequests;
    }
    
    /**
     * Fixed Window Rate Limiter
     * Divides time into fixed windows and allows maxRequests per window
     */
    public boolean allowRequestFixedWindow(String userId) {
        long currentTime = System.currentTimeMillis();
        long windowStart = currentTime - (currentTime % windowMs);
        
        Queue<Long> timestamps = requestTimestamps.computeIfAbsent(userId, k -> new LinkedList<>());
        
        // Remove old timestamps outside current window
        while (!timestamps.isEmpty() && timestamps.peek() < windowStart) {
            timestamps.poll();
        }
        
        if (timestamps.size() < maxRequests) {
            timestamps.offer(currentTime);
            return true;
        }
        
        return false;
    }
    
    /**
     * Sliding Window Rate Limiter
     * Uses a sliding window to provide more accurate rate limiting
     */
    public boolean allowRequestSlidingWindow(String userId) {
        long currentTime = System.currentTimeMillis();
        long windowStart = currentTime - windowMs;
        
        Queue<Long> timestamps = requestTimestamps.computeIfAbsent(userId, k -> new LinkedList<>());
        
        // Remove timestamps outside sliding window
        while (!timestamps.isEmpty() && timestamps.peek() < windowStart) {
            timestamps.poll();
        }
        
        if (timestamps.size() < maxRequests) {
            timestamps.offer(currentTime);
            return true;
        }
        
        return false;
    }
    
    /**
     * Token Bucket Rate Limiter
     * Refills tokens at a constant rate and allows requests if tokens are available
     */
    public boolean allowRequestTokenBucket(String userId) {
        long currentTime = System.currentTimeMillis();
        
        int tokens = tokenBucket.getOrDefault(userId, bucketCapacity);
        long lastRefill = currentTime - (currentTime % refillRateMs);
        
        // Refill tokens
        if (tokens < bucketCapacity) {
            long timePassed = currentTime - lastRefill;
            int tokensToAdd = (int) (timePassed / refillRateMs);
            tokens = Math.min(bucketCapacity, tokens + tokensToAdd);
        }
        
        if (tokens > 0) {
            tokens--;
            tokenBucket.put(userId, tokens);
            return true;
        }
        
        return false;
    }
    
    /**
     * Leaky Bucket Rate Limiter
     * Processes requests at a constant rate, dropping excess requests
     */
    public boolean allowRequestLeakyBucket(String userId) {
        long currentTime = System.currentTimeMillis();
        
        Queue<Long> bucket = requestTimestamps.computeIfAbsent(userId, k -> new LinkedList<>());
        
        // Remove processed requests (leak)
        while (!bucket.isEmpty() && bucket.peek() + windowMs < currentTime) {
            bucket.poll();
        }
        
        if (bucket.size() < maxRequests) {
            bucket.offer(currentTime);
            return true;
        }
        
        return false;
    }
    
    /**
     * Get current request count for a user in the sliding window
     */
    public int getCurrentRequestCount(String userId) {
        long currentTime = System.currentTimeMillis();
        long windowStart = currentTime - windowMs;
        
        Queue<Long> timestamps = requestTimestamps.get(userId);
        if (timestamps == null) return 0;
        
        // Remove old timestamps
        while (!timestamps.isEmpty() && timestamps.peek() < windowStart) {
            timestamps.poll();
        }
        
        return timestamps.size();
    }
    
    /**
     * Get remaining requests allowed for a user
     */
    public int getRemainingRequests(String userId) {
        return Math.max(0, maxRequests - getCurrentRequestCount(userId));
    }
    
    /**
     * Reset rate limiter for a specific user
     */
    public void resetUser(String userId) {
        requestTimestamps.remove(userId);
        tokenBucket.remove(userId);
    }
    
    /**
     * Reset all rate limiters
     */
    public void reset() {
        requestTimestamps.clear();
        tokenBucket.clear();
    }
    
    /**
     * Get rate limiter configuration
     */
    public RateLimiterConfig getConfig() {
        return new RateLimiterConfig(maxRequests, windowMs, bucketCapacity, refillRateMs);
    }
    
    public static class RateLimiterConfig {
        public final int maxRequests;
        public final long windowMs;
        public final int bucketCapacity;
        public final long refillRateMs;
        
        RateLimiterConfig(int maxRequests, long windowMs, int bucketCapacity, long refillRateMs) {
            this.maxRequests = maxRequests;
            this.windowMs = windowMs;
            this.bucketCapacity = bucketCapacity;
            this.refillRateMs = refillRateMs;
        }
        
        @Override
        public String toString() {
            return String.format("RateLimiterConfig{maxRequests=%d, windowMs=%d, bucketCapacity=%d, refillRateMs=%d}",
                maxRequests, windowMs, bucketCapacity, refillRateMs);
        }
    }
} 