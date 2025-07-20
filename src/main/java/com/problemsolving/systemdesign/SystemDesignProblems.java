package com.problemsolving.systemdesign;

import java.util.*;

/**
 * System Design Problems
 * This class contains implementations of common system design patterns and data structures.
 */
public class SystemDesignProblems {
    
    /**
     * Problem: LRU (Least Recently Used) Cache
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
    public static class LRUCache {
        private final int capacity;
        private final Map<Integer, Node> cache;
        private final Node head;
        private final Node tail;
        
        public LRUCache(int capacity) {
            this.capacity = capacity;
            this.cache = new HashMap<>();
            this.head = new Node(0, 0);
            this.tail = new Node(0, 0);
            head.next = tail;
            tail.prev = head;
        }
        
        public int get(int key) {
            if (cache.containsKey(key)) {
                Node node = cache.get(key);
                moveToHead(node);
                return node.value;
            }
            return -1;
        }
        
        public void put(int key, int value) {
            if (cache.containsKey(key)) {
                Node node = cache.get(key);
                node.value = value;
                moveToHead(node);
            } else {
                Node newNode = new Node(key, value);
                cache.put(key, newNode);
                addNode(newNode);
                
                if (cache.size() > capacity) {
                    Node lru = removeTail();
                    cache.remove(lru.key);
                }
            }
        }
        
        private void addNode(Node node) {
            node.prev = head;
            node.next = head.next;
            head.next.prev = node;
            head.next = node;
        }
        
        private void removeNode(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        
        private void moveToHead(Node node) {
            removeNode(node);
            addNode(node);
        }
        
        private Node removeTail() {
            Node res = tail.prev;
            removeNode(res);
            return res;
        }
        
        private static class Node {
            int key;
            int value;
            Node prev;
            Node next;
            
            Node(int key, int value) {
                this.key = key;
                this.value = value;
            }
        }
    }
    
    /**
     * Problem: LFU (Least Frequently Used) Cache
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
     * Time Complexity: O(1) for both get and put operations
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
    public static class LFUCache {
        private final int capacity;
        private final Map<Integer, Node> cache;
        private final Map<Integer, DoublyLinkedList> frequencyMap;
        private int minFrequency;
        
        public LFUCache(int capacity) {
            this.capacity = capacity;
            this.cache = new HashMap<>();
            this.frequencyMap = new HashMap<>();
            this.minFrequency = 0;
        }
        
        public int get(int key) {
            if (cache.containsKey(key)) {
                Node node = cache.get(key);
                updateFrequency(node);
                return node.value;
            }
            return -1;
        }
        
        public void put(int key, int value) {
            if (capacity == 0) return;
            
            if (cache.containsKey(key)) {
                Node node = cache.get(key);
                node.value = value;
                updateFrequency(node);
            } else {
                if (cache.size() >= capacity) {
                    DoublyLinkedList minFreqList = frequencyMap.get(minFrequency);
                    Node lfu = minFreqList.removeLast();
                    cache.remove(lfu.key);
                }
                
                Node newNode = new Node(key, value);
                cache.put(key, newNode);
                minFrequency = 1;
                frequencyMap.computeIfAbsent(1, k -> new DoublyLinkedList()).addFirst(newNode);
            }
        }
        
        private void updateFrequency(Node node) {
            int freq = node.frequency;
            DoublyLinkedList list = frequencyMap.get(freq);
            list.remove(node);
            
            if (list.isEmpty()) {
                frequencyMap.remove(freq);
                if (freq == minFrequency) {
                    minFrequency++;
                }
            }
            
            node.frequency++;
            frequencyMap.computeIfAbsent(node.frequency, k -> new DoublyLinkedList()).addFirst(node);
        }
        
        private static class Node {
            int key;
            int value;
            int frequency;
            Node prev;
            Node next;
            
            Node(int key, int value) {
                this.key = key;
                this.value = value;
                this.frequency = 1;
            }
        }
        
        private static class DoublyLinkedList {
            Node head;
            Node tail;
            
            DoublyLinkedList() {
                head = new Node(0, 0);
                tail = new Node(0, 0);
                head.next = tail;
                tail.prev = head;
            }
            
            void addFirst(Node node) {
                node.next = head.next;
                node.prev = head;
                head.next.prev = node;
                head.next = node;
            }
            
            void remove(Node node) {
                node.prev.next = node.next;
                node.next.prev = node.prev;
            }
            
            Node removeLast() {
                if (tail.prev == head) return null;
                Node last = tail.prev;
                remove(last);
                return last;
            }
            
            boolean isEmpty() {
                return head.next == tail;
            }
        }
    }
    
    /**
     * Problem: Circuit Breaker Pattern
     * 
     * Implement a circuit breaker pattern to handle failures gracefully.
     * The circuit breaker has three states: CLOSED, OPEN, and HALF_OPEN.
     * 
     * CLOSED: Normal operation, requests are allowed through
     * OPEN: Circuit is open, requests are blocked
     * HALF_OPEN: Limited requests are allowed to test if the service is back
     * 
     * Example:
     * CircuitBreaker cb = new CircuitBreaker(5, 10000, 2);
     * cb.execute(() -> callExternalService()); // May throw exception
     */
    public static class CircuitBreaker {
        private final int failureThreshold;
        private final long timeoutMs;
        private final int halfOpenMaxCalls;
        
        private State state;
        private int failureCount;
        private long lastFailureTime;
        private int halfOpenCallCount;
        
        public enum State {
            CLOSED, OPEN, HALF_OPEN
        }
        
        public CircuitBreaker(int failureThreshold, long timeoutMs, int halfOpenMaxCalls) {
            this.failureThreshold = failureThreshold;
            this.timeoutMs = timeoutMs;
            this.halfOpenMaxCalls = halfOpenMaxCalls;
            this.state = State.CLOSED;
            this.failureCount = 0;
            this.halfOpenCallCount = 0;
        }
        
        public <T> T execute(Supplier<T> supplier) throws Exception {
            if (state == State.OPEN) {
                if (System.currentTimeMillis() - lastFailureTime > timeoutMs) {
                    state = State.HALF_OPEN;
                    halfOpenCallCount = 0;
                } else {
                    throw new RuntimeException("Circuit breaker is OPEN");
                }
            }
            
            try {
                T result = supplier.get();
                onSuccess();
                return result;
            } catch (Exception e) {
                onFailure();
                throw e;
            }
        }
        
        private void onSuccess() {
            if (state == State.HALF_OPEN) {
                halfOpenCallCount++;
                if (halfOpenCallCount >= halfOpenMaxCalls) {
                    state = State.CLOSED;
                    failureCount = 0;
                }
            }
        }
        
        private void onFailure() {
            failureCount++;
            lastFailureTime = System.currentTimeMillis();
            
            if (state == State.HALF_OPEN || 
                (state == State.CLOSED && failureCount >= failureThreshold)) {
                state = State.OPEN;
            }
        }
        
        public State getState() {
            return state;
        }
        
        public int getFailureCount() {
            return failureCount;
        }
        
        @FunctionalInterface
        public interface Supplier<T> {
            T get() throws Exception;
        }
    }
    
    /**
     * Problem: Rate Limiter
     * 
     * Implement a rate limiter that limits the number of requests per time window.
     * Supports different strategies: Fixed Window, Sliding Window, and Token Bucket.
     * 
     * Example:
     * RateLimiter limiter = new RateLimiter(10, 1000); // 10 requests per second
     * boolean allowed = limiter.allowRequest("user1");
     */
    public static class RateLimiter {
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
    }
    
    /**
     * Problem: Distributed Cache with Consistent Hashing
     * 
     * Implement a distributed cache using consistent hashing to distribute data across nodes.
     * 
     * Example:
     * DistributedCache cache = new DistributedCache();
     * cache.addNode("node1");
     * cache.addNode("node2");
     * cache.put("key1", "value1");
     * String value = cache.get("key1");
     */
    public static class DistributedCache {
        private final TreeMap<Integer, String> ring;
        private final Map<String, Map<String, String>> nodeData;
        private final int virtualNodes;
        
        public DistributedCache(int virtualNodes) {
            this.ring = new TreeMap<>();
            this.nodeData = new HashMap<>();
            this.virtualNodes = virtualNodes;
        }
        
        public void addNode(String nodeId) {
            for (int i = 0; i < virtualNodes; i++) {
                String virtualNodeId = nodeId + "#" + i;
                int hash = hash(virtualNodeId);
                ring.put(hash, nodeId);
            }
            nodeData.put(nodeId, new HashMap<>());
        }
        
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
        
        public void put(String key, String value) {
            String nodeId = getNodeForKey(key);
            nodeData.get(nodeId).put(key, value);
        }
        
        public String get(String key) {
            String nodeId = getNodeForKey(key);
            return nodeData.get(nodeId).get(key);
        }
        
        private String getNodeForKey(String key) {
            int hash = hash(key);
            Map.Entry<Integer, String> entry = ring.ceilingEntry(hash);
            
            if (entry == null) {
                entry = ring.firstEntry();
            }
            
            return entry.getValue();
        }
        
        private int hash(String key) {
            return key.hashCode();
        }
        
        public Map<String, Integer> getNodeDistribution() {
            Map<String, Integer> distribution = new HashMap<>();
            for (Map<String, String> data : nodeData.values()) {
                for (String nodeId : data.keySet()) {
                    distribution.merge(nodeId, 1, Integer::sum);
                }
            }
            return distribution;
        }
    }
    
    /**
     * Problem: Message Queue with Priority
     * 
     * Implement a message queue that supports priority-based ordering.
     * 
     * Example:
     * PriorityMessageQueue queue = new PriorityMessageQueue();
     * queue.enqueue("message1", 1);
     * queue.enqueue("message2", 3);
     * queue.enqueue("message3", 2);
     * String message = queue.dequeue(); // Returns "message2" (highest priority)
     */
    public static class PriorityMessageQueue {
        private final PriorityQueue<Message> queue;
        
        public PriorityMessageQueue() {
            this.queue = new PriorityQueue<>((a, b) -> Integer.compare(b.priority, a.priority));
        }
        
        public void enqueue(String message, int priority) {
            queue.offer(new Message(message, priority, System.currentTimeMillis()));
        }
        
        public String dequeue() {
            Message message = queue.poll();
            return message != null ? message.content : null;
        }
        
        public String peek() {
            Message message = queue.peek();
            return message != null ? message.content : null;
        }
        
        public boolean isEmpty() {
            return queue.isEmpty();
        }
        
        public int size() {
            return queue.size();
        }
        
        private static class Message {
            String content;
            int priority;
            long timestamp;
            
            Message(String content, int priority, long timestamp) {
                this.content = content;
                this.priority = priority;
                this.timestamp = timestamp;
            }
        }
    }
    
    /**
     * Problem: GitHub Pull Request Approval System
     * 
     * Design a system that manages pull request approvals similar to GitHub.
     * The system should support:
     * - Creating pull requests
     * - Adding reviewers
     * - Submitting reviews (approve, request changes, comment)
     * - Determining if a PR is ready to merge
     * - Branch protection rules
     * - Required reviewers
     * 
     * Time Complexity: O(1) for most operations, O(n) for checking approval status
     * Space Complexity: O(n) where n is the number of PRs, reviews, and users
     * 
     * Example:
     * PullRequestSystem system = new PullRequestSystem();
     * system.createPullRequest("user1", "main", "feature-branch", "Add new feature");
     * system.addReviewer("pr1", "user2");
     * system.addReviewer("pr1", "user3");
     * system.submitReview("pr1", "user2", ReviewType.APPROVE, "Looks good!");
     * system.submitReview("pr1", "user3", ReviewType.REQUEST_CHANGES, "Please fix this");
     * boolean canMerge = system.canMerge("pr1"); // false
     */
    public static class PullRequestSystem {
        private final Map<String, PullRequest> pullRequests;
        private final Map<String, User> users;
        private final Map<String, BranchProtectionRule> branchRules;
        
        public PullRequestSystem() {
            this.pullRequests = new HashMap<>();
            this.users = new HashMap<>();
            this.branchRules = new HashMap<>();
        }
        
        public String createPullRequest(String author, String baseBranch, String headBranch, String title) {
            String prId = "pr" + (pullRequests.size() + 1);
            PullRequest pr = new PullRequest(prId, author, baseBranch, headBranch, title);
            pullRequests.put(prId, pr);
            return prId;
        }
        
        public void addReviewer(String prId, String reviewerId) {
            if (pullRequests.containsKey(prId)) {
                PullRequest pr = pullRequests.get(prId);
                pr.reviewers.add(reviewerId);
            }
        }
        
        public void submitReview(String prId, String reviewerId, ReviewType type, String comment) {
            if (pullRequests.containsKey(prId)) {
                PullRequest pr = pullRequests.get(prId);
                Review review = new Review(reviewerId, type, comment, System.currentTimeMillis());
                pr.reviews.put(reviewerId, review);
                
                if (type == ReviewType.APPROVE) {
                    pr.approvedReviewers.add(reviewerId);
                } else if (type == ReviewType.REQUEST_CHANGES) {
                    pr.approvedReviewers.remove(reviewerId);
                }
            }
        }
        
        public boolean canMerge(String prId) {
            if (!pullRequests.containsKey(prId)) return false;
            
            PullRequest pr = pullRequests.get(prId);
            BranchProtectionRule rule = branchRules.get(pr.baseBranch);
            
            // Check if PR is open
            if (pr.status != PRStatus.OPEN) return false;
            
            // Check if all required reviewers have approved
            if (rule != null && rule.requiredApprovals > 0) {
                if (pr.approvedReviewers.size() < rule.requiredApprovals) return false;
            }
            
            // Check if all reviewers have approved
            if (pr.reviewers.size() > 0) {
                for (String reviewer : pr.reviewers) {
                    if (!pr.approvedReviewers.contains(reviewer)) return false;
                }
            }
            
            // Check if there are any pending changes requests
            for (Review review : pr.reviews.values()) {
                if (review.type == ReviewType.REQUEST_CHANGES) return false;
            }
            
            return true;
        }
        
        public void setBranchProtectionRule(String branch, int requiredApprovals, boolean requireUpToDate) {
            branchRules.put(branch, new BranchProtectionRule(requiredApprovals, requireUpToDate));
        }
        
        public void mergePullRequest(String prId) {
            if (canMerge(prId)) {
                PullRequest pr = pullRequests.get(prId);
                pr.status = PRStatus.MERGED;
                pr.mergedAt = System.currentTimeMillis();
            }
        }
        
        public void closePullRequest(String prId) {
            if (pullRequests.containsKey(prId)) {
                pullRequests.get(prId).status = PRStatus.CLOSED;
            }
        }
        
        public List<Review> getReviews(String prId) {
            if (pullRequests.containsKey(prId)) {
                return new ArrayList<>(pullRequests.get(prId).reviews.values());
            }
            return new ArrayList<>();
        }
        
        public PullRequestStatus getPullRequestStatus(String prId) {
            if (!pullRequests.containsKey(prId)) return null;
            
            PullRequest pr = pullRequests.get(prId);
            return new PullRequestStatus(
                pr.id,
                pr.status,
                pr.approvedReviewers.size(),
                pr.reviewers.size(),
                canMerge(prId)
            );
        }
        
        // Enums and Inner Classes
        public enum ReviewType {
            APPROVE, REQUEST_CHANGES, COMMENT
        }
        
        public enum PRStatus {
            OPEN, MERGED, CLOSED
        }
        
        private static class PullRequest {
            String id;
            String author;
            String baseBranch;
            String headBranch;
            String title;
            PRStatus status;
            long createdAt;
            long mergedAt;
            Set<String> reviewers;
            Map<String, Review> reviews;
            Set<String> approvedReviewers;
            
            PullRequest(String id, String author, String baseBranch, String headBranch, String title) {
                this.id = id;
                this.author = author;
                this.baseBranch = baseBranch;
                this.headBranch = headBranch;
                this.title = title;
                this.status = PRStatus.OPEN;
                this.createdAt = System.currentTimeMillis();
                this.reviewers = new HashSet<>();
                this.reviews = new HashMap<>();
                this.approvedReviewers = new HashSet<>();
            }
        }
        
        private static class Review {
            String reviewerId;
            ReviewType type;
            String comment;
            long timestamp;
            
            Review(String reviewerId, ReviewType type, String comment, long timestamp) {
                this.reviewerId = reviewerId;
                this.type = type;
                this.comment = comment;
                this.timestamp = timestamp;
            }
        }
        
        private static class BranchProtectionRule {
            int requiredApprovals;
            boolean requireUpToDate;
            
            BranchProtectionRule(int requiredApprovals, boolean requireUpToDate) {
                this.requiredApprovals = requiredApprovals;
                this.requireUpToDate = requireUpToDate;
            }
        }
        
        private static class User {
            String id;
            String name;
            String email;
            
            User(String id, String name, String email) {
                this.id = id;
                this.name = name;
                this.email = email;
            }
        }
        
        public static class PullRequestStatus {
            public final String prId;
            public final PRStatus status;
            public final int approvedCount;
            public final int totalReviewers;
            public final boolean canMerge;
            
            PullRequestStatus(String prId, PRStatus status, int approvedCount, int totalReviewers, boolean canMerge) {
                this.prId = prId;
                this.status = status;
                this.approvedCount = approvedCount;
                this.totalReviewers = totalReviewers;
                this.canMerge = canMerge;
            }
            
            @Override
            public String toString() {
                return String.format("PR %s: %s (%d/%d approvals, can merge: %s)", 
                    prId, status, approvedCount, totalReviewers, canMerge);
            }
        }
    }
    
    /**
     * Problem: Load Balancer
     * 
     * Design a load balancer that distributes incoming requests across multiple servers.
     * The system should support:
     * - Multiple load balancing strategies (Round Robin, Least Connections, Weighted Round Robin)
     * - Health checking for servers
     * - Server registration and deregistration
     * - Request routing with failover
     * - Load balancing metrics and statistics
     * 
     * Time Complexity: O(1) for request routing, O(n) for health checks
     * Space Complexity: O(n) where n is the number of servers
     * 
     * Example:
     * LoadBalancer lb = new LoadBalancer();
     * lb.addServer("server1", "192.168.1.10:8080", 1);
     * lb.addServer("server2", "192.168.1.11:8080", 2);
     * lb.addServer("server3", "192.168.1.12:8080", 1);
     * String server = lb.routeRequest("user123"); // Returns server address
     */
    public static class LoadBalancer {
        private final List<Server> servers;
        private final Map<String, Server> serverMap;
        private final LoadBalancingStrategy strategy;
        private int currentIndex;
        private final Random random;
        private final long healthCheckInterval;
        private final int healthCheckTimeout;
        private final int maxFailures;
        
        public LoadBalancer(LoadBalancingStrategy strategy) {
            this.servers = new ArrayList<>();
            this.serverMap = new HashMap<>();
            this.strategy = strategy;
            this.currentIndex = 0;
            this.random = new Random();
            this.healthCheckInterval = 30000; // 30 seconds
            this.healthCheckTimeout = 5000;   // 5 seconds
            this.maxFailures = 3;
            
            // Start health check thread
            startHealthCheckThread();
        }
        
        public void addServer(String id, String address, int weight) {
            Server server = new Server(id, address, weight);
            servers.add(server);
            serverMap.put(id, server);
        }
        
        public void removeServer(String id) {
            Server server = serverMap.remove(id);
            if (server != null) {
                servers.remove(server);
            }
        }
        
        public String routeRequest(String requestId) {
            if (servers.isEmpty()) {
                throw new RuntimeException("No healthy servers available");
            }
            
            List<Server> healthyServers = getHealthyServers();
            if (healthyServers.isEmpty()) {
                throw new RuntimeException("No healthy servers available");
            }
            
            Server selectedServer = selectServer(healthyServers, requestId);
            selectedServer.incrementConnections();
            selectedServer.incrementRequests();
            
            return selectedServer.address;
        }
        
        public void releaseConnection(String serverId) {
            Server server = serverMap.get(serverId);
            if (server != null) {
                server.decrementConnections();
            }
        }
        
        private Server selectServer(List<Server> healthyServers, String requestId) {
            switch (strategy) {
                case ROUND_ROBIN:
                    return selectRoundRobin(healthyServers);
                case LEAST_CONNECTIONS:
                    return selectLeastConnections(healthyServers);
                case WEIGHTED_ROUND_ROBIN:
                    return selectWeightedRoundRobin(healthyServers);
                case RANDOM:
                    return selectRandom(healthyServers);
                case IP_HASH:
                    return selectIpHash(healthyServers, requestId);
                default:
                    return selectRoundRobin(healthyServers);
            }
        }
        
        private Server selectRoundRobin(List<Server> healthyServers) {
            Server server = healthyServers.get(currentIndex % healthyServers.size());
            currentIndex = (currentIndex + 1) % healthyServers.size();
            return server;
        }
        
        private Server selectLeastConnections(List<Server> healthyServers) {
            return healthyServers.stream()
                .min(Comparator.comparingInt(Server::getActiveConnections))
                .orElse(healthyServers.get(0));
        }
        
        private Server selectWeightedRoundRobin(List<Server> healthyServers) {
            int totalWeight = healthyServers.stream().mapToInt(Server::getWeight).sum();
            int currentWeight = 0;
            
            for (Server server : healthyServers) {
                currentWeight += server.weight;
                if (currentIndex < currentWeight) {
                    currentIndex = (currentIndex + 1) % totalWeight;
                    return server;
                }
            }
            
            currentIndex = (currentIndex + 1) % totalWeight;
            return healthyServers.get(0);
        }
        
        private Server selectRandom(List<Server> healthyServers) {
            return healthyServers.get(random.nextInt(healthyServers.size()));
        }
        
        private Server selectIpHash(List<Server> healthyServers, String requestId) {
            int hash = Math.abs(requestId.hashCode());
            return healthyServers.get(hash % healthyServers.size());
        }
        
        private List<Server> getHealthyServers() {
            return servers.stream()
                .filter(Server::isHealthy)
                .collect(Collectors.toList());
        }
        
        private void startHealthCheckThread() {
            Thread healthCheckThread = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(healthCheckInterval);
                        performHealthChecks();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            });
            healthCheckThread.setDaemon(true);
            healthCheckThread.start();
        }
        
        private void performHealthChecks() {
            for (Server server : servers) {
                boolean healthy = checkServerHealth(server);
                server.updateHealthStatus(healthy);
            }
        }
        
        private boolean checkServerHealth(Server server) {
            // Simulate health check - in real implementation, this would make HTTP request
            try {
                // Simulate network delay
                Thread.sleep(random.nextInt(1000));
                
                // Simulate server response (90% success rate)
                boolean healthy = random.nextDouble() > 0.1;
                
                if (!healthy) {
                    server.incrementFailures();
                } else {
                    server.resetFailures();
                }
                
                return server.getFailureCount() < maxFailures;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }
        
        public LoadBalancerStats getStats() {
            return new LoadBalancerStats(
                servers.size(),
                getHealthyServers().size(),
                servers.stream().mapToInt(Server::getTotalRequests).sum(),
                servers.stream().mapToInt(Server::getActiveConnections).sum(),
                strategy
            );
        }
        
        public List<ServerStats> getServerStats() {
            return servers.stream()
                .map(server -> new ServerStats(
                    server.id,
                    server.address,
                    server.isHealthy(),
                    server.getActiveConnections(),
                    server.getTotalRequests(),
                    server.getFailureCount(),
                    server.getWeight()
                ))
                .collect(Collectors.toList());
        }
        
        // Enums and Inner Classes
        public enum LoadBalancingStrategy {
            ROUND_ROBIN, LEAST_CONNECTIONS, WEIGHTED_ROUND_ROBIN, RANDOM, IP_HASH
        }
        
        private static class Server {
            String id;
            String address;
            int weight;
            boolean healthy;
            int activeConnections;
            int totalRequests;
            int failureCount;
            long lastHealthCheck;
            
            Server(String id, String address, int weight) {
                this.id = id;
                this.address = address;
                this.weight = weight;
                this.healthy = true;
                this.activeConnections = 0;
                this.totalRequests = 0;
                this.failureCount = 0;
                this.lastHealthCheck = System.currentTimeMillis();
            }
            
            void incrementConnections() {
                activeConnections++;
            }
            
            void decrementConnections() {
                if (activeConnections > 0) {
                    activeConnections--;
                }
            }
            
            void incrementRequests() {
                totalRequests++;
            }
            
            void incrementFailures() {
                failureCount++;
            }
            
            void resetFailures() {
                failureCount = 0;
            }
            
            void updateHealthStatus(boolean healthy) {
                this.healthy = healthy;
                this.lastHealthCheck = System.currentTimeMillis();
            }
            
            boolean isHealthy() {
                return healthy;
            }
            
            int getActiveConnections() {
                return activeConnections;
            }
            
            int getTotalRequests() {
                return totalRequests;
            }
            
            int getFailureCount() {
                return failureCount;
            }
            
            int getWeight() {
                return weight;
            }
        }
        
        public static class LoadBalancerStats {
            public final int totalServers;
            public final int healthyServers;
            public final int totalRequests;
            public final int activeConnections;
            public final LoadBalancingStrategy strategy;
            
            LoadBalancerStats(int totalServers, int healthyServers, int totalRequests, 
                            int activeConnections, LoadBalancingStrategy strategy) {
                this.totalServers = totalServers;
                this.healthyServers = healthyServers;
                this.totalRequests = totalRequests;
                this.activeConnections = activeConnections;
                this.strategy = strategy;
            }
            
            @Override
            public String toString() {
                return String.format("LoadBalancer Stats: %d/%d servers healthy, %d requests, %d connections, strategy: %s",
                    healthyServers, totalServers, totalRequests, activeConnections, strategy);
            }
        }
        
        public static class ServerStats {
            public final String id;
            public final String address;
            public final boolean healthy;
            public final int activeConnections;
            public final int totalRequests;
            public final int failureCount;
            public final int weight;
            
            ServerStats(String id, String address, boolean healthy, int activeConnections,
                       int totalRequests, int failureCount, int weight) {
                this.id = id;
                this.address = address;
                this.healthy = healthy;
                this.activeConnections = activeConnections;
                this.totalRequests = totalRequests;
                this.failureCount = failureCount;
                this.weight = weight;
            }
            
            @Override
            public String toString() {
                return String.format("Server %s (%s): %s, %d connections, %d requests, %d failures, weight: %d",
                    id, address, healthy ? "HEALTHY" : "UNHEALTHY", 
                    activeConnections, totalRequests, failureCount, weight);
            }
        }
    }
} 