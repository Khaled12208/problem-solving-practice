package com.problemsolving.systemdesign;

/**
 * System Design Problems - Overview
 * 
 * This package contains comprehensive implementations of common system design patterns and data structures.
 * Each problem has been split into its own class for better organization and maintainability.
 * All implementations use Java's built-in data structures for optimal performance and reliability.
 * 
 * Available Classes:
 * 
 * 1. LRUCache - Least Recently Used Cache implementation
 *    - Uses Java's LinkedHashMap with access-order enabled
 *    - O(1) get and put operations
 *    - Automatic eviction of least recently used items
 * 
 * 2. LFUCache - Least Frequently Used Cache implementation
 *    - Uses Java's TreeMap and LinkedHashSet
 *    - O(log n) operations with proper LRU tiebreaker
 *    - Evicts least frequently used items
 * 
 * 3. CircuitBreaker - Thread-safe circuit breaker pattern implementation
 *    - Uses Java's concurrent utilities (AtomicInteger, AtomicLong, ReentrantReadWriteLock)
 *    - Three states: CLOSED, OPEN, HALF_OPEN
 *    - Configurable failure thresholds and timeouts
 * 
 * 4. RateLimiter - Rate limiting with multiple strategies
 *    - Uses Java's HashMap, LinkedList, and Queue
 *    - Fixed Window, Sliding Window, Token Bucket, Leaky Bucket
 *    - Per-user rate limiting with configurable limits
 * 
 * 5. DistributedCache - Distributed cache with consistent hashing
 *    - Uses Java's TreeMap for hash ring and HashMap for data storage
 *    - Virtual nodes for better load distribution
 *    - Automatic data redistribution on node changes
 * 
 * 6. PriorityMessageQueue - Priority-based message queue
 *    - Uses Java's PriorityQueue with custom comparator
 *    - Higher priority messages dequeued first
 *    - Timestamp tracking and statistics
 * 
 * 7. PullRequestSystem - GitHub-style pull request management
 *    - Uses Java's HashMap, HashSet, and ArrayList
 *    - Review workflows and approval tracking
 *    - Branch protection rules
 *    - Merge eligibility checking
 * 
 * 8. LoadBalancer - Load balancer with multiple strategies
 *    - Uses Java's ArrayList, HashMap, and Stream API
 *    - Round Robin, Least Connections, Weighted Round Robin, Random, IP Hash
 *    - Health checking and failover
 *    - Real-time statistics and monitoring
 * 
 * 9. URLShortenerService - URL shortening service like bit.ly
 *    - Uses Java's ConcurrentHashMap, AtomicLong, and ArrayList
 *    - URL shortening with custom short URLs
 *    - Analytics tracking (clicks, visitors, referrers)
 *    - Rate limiting and URL expiration
 *    - Automatic cleanup of expired URLs
 * 
 * Key Benefits of Using Java Built-in Data Structures:
 * - Optimized performance and memory usage
 * - Thread-safe implementations where needed
 * - Well-tested and maintained by Oracle
 * - Consistent API and behavior
 * - Better integration with Java ecosystem
 * 
 * Usage Examples:
 * 
 * // LRU Cache using LinkedHashMap
 * LRUCache cache = new LRUCache(100);
 * cache.put(1, "value1");
 * String value = cache.get(1);
 * 
 * // Thread-safe Circuit Breaker
 * CircuitBreaker cb = new CircuitBreaker(5, 10000, 2);
 * cb.execute(() -> callExternalService());
 * 
 * // Rate Limiter with multiple strategies
 * RateLimiter limiter = new RateLimiter(10, 1000);
 * boolean allowed = limiter.allowRequestFixedWindow("user1");
 * 
 * // Load Balancer with health checking
 * LoadBalancer lb = new LoadBalancer(LoadBalancer.LoadBalancingStrategy.ROUND_ROBIN);
 * lb.addServer("server1", "192.168.1.10:8080", 1);
 * String server = lb.routeRequest("user123");
 * 
 * // Pull Request System with approval workflow
 * PullRequestSystem prs = new PullRequestSystem();
 * String prId = prs.createPullRequest("user1", "main", "feature", "New feature");
 * prs.addReviewer(prId, "user2");
 * prs.submitReview(prId, "user2", PullRequestSystem.ReviewType.APPROVE, "LGTM");
 * boolean canMerge = prs.canMerge(prId);
 * 
 * // URL Shortener Service with analytics
 * URLShortenerService shortener = new URLShortenerService();
 * String shortUrl = shortener.shortenUrl("https://www.google.com/search?q=java+programming");
 * shortener.recordClick(shortUrl, "192.168.1.1", "Mozilla/5.0");
 * URLAnalytics analytics = shortener.getAnalytics(shortUrl);
 */
public class SystemDesignProblems {
    
    /**
     * Main method demonstrating usage of all system design components
     */
    public static void main(String[] args) {
        System.out.println("System Design Problems Demo - Using Java Built-in Data Structures");
        System.out.println("================================================================");
        
        // Demo LRU Cache using LinkedHashMap
        System.out.println("\n1. LRU Cache Demo (LinkedHashMap):");
        LRUCache lruCache = new LRUCache(3);
        lruCache.put(1, 1);
        lruCache.put(2, 2);
        lruCache.put(3, 3);
        System.out.println("LRU Cache size: " + lruCache.size());
        System.out.println("Get key 1: " + lruCache.get(1));
        lruCache.put(4, 4); // Should evict key 2
        System.out.println("After eviction, get key 2: " + lruCache.get(2));
        System.out.println("Cache stats: " + lruCache.getStats());
        
        // Demo LFU Cache using TreeMap
        System.out.println("\n2. LFU Cache Demo (TreeMap + LinkedHashSet):");
        LFUCache lfuCache = new LFUCache(3);
        lfuCache.put(1, 1);
        lfuCache.put(2, 2);
        lfuCache.get(1); // Increase frequency
        lfuCache.put(3, 3);
        lfuCache.put(4, 4); // Should evict key 2 (lowest frequency)
        System.out.println("LFU Cache stats: " + lfuCache.getStats());
        System.out.println("Frequency distribution: " + lfuCache.getFrequencyDistribution());
        
        // Demo Circuit Breaker with thread safety
        System.out.println("\n3. Circuit Breaker Demo (Thread-safe):");
        CircuitBreaker cb = new CircuitBreaker(3, 5000, 2);
        System.out.println("Initial state: " + cb.getState());
        System.out.println("Circuit breaker stats: " + cb.getStats());
        
        // Demo Rate Limiter with multiple strategies
        System.out.println("\n4. Rate Limiter Demo (Multiple Strategies):");
        RateLimiter limiter = new RateLimiter(5, 1000);
        for (int i = 0; i < 7; i++) {
            boolean allowed = limiter.allowRequestFixedWindow("user1");
            System.out.println("Request " + (i+1) + " allowed: " + allowed);
        }
        System.out.println("Remaining requests: " + limiter.getRemainingRequests("user1"));
        
        // Demo Load Balancer with health checking
        System.out.println("\n5. Load Balancer Demo (Round Robin):");
        LoadBalancer lb = new LoadBalancer(LoadBalancer.LoadBalancingStrategy.ROUND_ROBIN);
        lb.addServer("server1", "192.168.1.10:8080", 1);
        lb.addServer("server2", "192.168.1.11:8080", 2);
        lb.addServer("server3", "192.168.1.12:8080", 1);
        
        for (int i = 0; i < 5; i++) {
            try {
                String server = lb.routeRequest("user" + i);
                System.out.println("Request " + i + " routed to: " + server);
            } catch (Exception e) {
                System.out.println("Request " + i + " failed: " + e.getMessage());
            }
        }
        System.out.println("Load balancer stats: " + lb.getStats());
        
        // Demo Pull Request System
        System.out.println("\n6. Pull Request System Demo:");
        PullRequestSystem prs = new PullRequestSystem();
        prs.setBranchProtectionRule("main", 2, true);
        
        String prId = prs.createPullRequest("user1", "main", "feature-branch", "Add new feature");
        prs.addReviewer(prId, "user2");
        prs.addReviewer(prId, "user3");
        
        prs.submitReview(prId, "user2", PullRequestSystem.ReviewType.APPROVE, "Looks good!");
        prs.submitReview(prId, "user3", PullRequestSystem.ReviewType.REQUEST_CHANGES, "Please fix this");
        
        PullRequestSystem.PullRequestStatus status = prs.getPullRequestStatus(prId);
        System.out.println("PR Status: " + status);
        System.out.println("Can merge: " + prs.canMerge(prId));
        
        // Demo Priority Message Queue using PriorityQueue
        System.out.println("\n7. Priority Message Queue Demo (PriorityQueue):");
        PriorityMessageQueue queue = new PriorityMessageQueue();
        queue.enqueue("Low priority message", 1);
        queue.enqueue("High priority message", 3);
        queue.enqueue("Medium priority message", 2);
        
        System.out.println("Queue size: " + queue.size());
        System.out.println("Dequeued: " + queue.dequeue()); // Should be high priority
        System.out.println("Dequeued: " + queue.dequeue()); // Should be medium priority
        System.out.println("Queue stats: " + queue.getStats());
        
        // Demo Distributed Cache using TreeMap
        System.out.println("\n8. Distributed Cache Demo (TreeMap + HashMap):");
        DistributedCache cache = new DistributedCache(3);
        cache.addNode("node1");
        cache.addNode("node2");
        cache.addNode("node3");
        
        cache.put("key1", "value1");
        cache.put("key2", "value2");
        cache.put("key3", "value3");
        
        System.out.println("Get key1: " + cache.get("key1"));
        System.out.println("Node distribution: " + cache.getNodeDistribution());
        System.out.println("Cache stats: " + cache.getStats());
        
        // Demo URL Shortener Service with analytics
        System.out.println("\n9. URL Shortener Service Demo (ConcurrentHashMap + Analytics):");
        URLShortenerService shortener = new URLShortenerService();
        
        // Create short URLs
        String longUrl = "https://www.google.com/search?q=java+programming+interview+questions";
        String shortUrl = shortener.shortenUrl(longUrl);
        System.out.println("Long URL: " + longUrl);
        System.out.println("Short URL: " + shortUrl);
        
        // Create custom short URL
        String customShortUrl = shortener.shortenUrl("https://github.com", "gh");
        System.out.println("Custom short URL: " + customShortUrl);
        
        // Record some clicks
        shortener.recordClick(shortUrl, "192.168.1.1", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
        shortener.recordClick(shortUrl, "192.168.1.2", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7)");
        shortener.recordClick(shortUrl, "192.168.1.1", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"); // Same IP
        shortener.recordClick(customShortUrl, "192.168.1.3", "Mozilla/5.0 (iPhone; CPU iPhone OS 14_7_1)");
        
        // Get analytics
        URLShortenerService.URLAnalytics analytics = shortener.getAnalytics(shortUrl);
        System.out.println("Analytics for " + shortUrl + ":");
        System.out.println("  Total clicks: " + analytics.getMapping().getClickCount());
        System.out.println("  Unique visitors: " + analytics.getUniqueVisitors());
        System.out.println("  Top referrers: " + analytics.getTopReferrers());
        
        // Get service stats
        URLShortenerService.ServiceStats serviceStats = shortener.getServiceStats();
        System.out.println("Service stats: " + serviceStats);
        
        System.out.println("\nDemo completed successfully!");
        System.out.println("All implementations use Java's built-in data structures for optimal performance.");
    }
} 