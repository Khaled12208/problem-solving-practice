package com.problemsolving.systemdesign;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Load Balancer
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
 * LoadBalancer lb = new LoadBalancer(LoadBalancingStrategy.ROUND_ROBIN);
 * lb.addServer("server1", "192.168.1.10:8080", 1);
 * lb.addServer("server2", "192.168.1.11:8080", 2);
 * lb.addServer("server3", "192.168.1.12:8080", 1);
 * String server = lb.routeRequest("user123"); // Returns server address
 */
public class LoadBalancer {
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
    
    /**
     * Add a server to the load balancer
     */
    public void addServer(String id, String address, int weight) {
        Server server = new Server(id, address, weight);
        servers.add(server);
        serverMap.put(id, server);
    }
    
    /**
     * Remove a server from the load balancer
     */
    public void removeServer(String id) {
        Server server = serverMap.remove(id);
        if (server != null) {
            servers.remove(server);
        }
    }
    
    /**
     * Route a request to an appropriate server
     */
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
    
    /**
     * Release a connection from a server
     */
    public void releaseConnection(String serverId) {
        Server server = serverMap.get(serverId);
        if (server != null) {
            server.decrementConnections();
        }
    }
    
    /**
     * Select server based on the configured strategy
     */
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
    
    /**
     * Round Robin selection - distributes requests sequentially
     */
    private Server selectRoundRobin(List<Server> healthyServers) {
        Server server = healthyServers.get(currentIndex % healthyServers.size());
        currentIndex = (currentIndex + 1) % healthyServers.size();
        return server;
    }
    
    /**
     * Least Connections selection - routes to server with fewest active connections
     */
    private Server selectLeastConnections(List<Server> healthyServers) {
        return healthyServers.stream()
            .min(Comparator.comparingInt(Server::getActiveConnections))
            .orElse(healthyServers.get(0));
    }
    
    /**
     * Weighted Round Robin selection - round robin with server weights
     */
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
    
    /**
     * Random selection - randomly selects from healthy servers
     */
    private Server selectRandom(List<Server> healthyServers) {
        return healthyServers.get(random.nextInt(healthyServers.size()));
    }
    
    /**
     * IP Hash selection - consistent hashing for session affinity
     */
    private Server selectIpHash(List<Server> healthyServers, String requestId) {
        int hash = Math.abs(requestId.hashCode());
        return healthyServers.get(hash % healthyServers.size());
    }
    
    /**
     * Get list of healthy servers
     */
    private List<Server> getHealthyServers() {
        return servers.stream()
            .filter(Server::isHealthy)
            .collect(Collectors.toList());
    }
    
    /**
     * Start background health check thread
     */
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
    
    /**
     * Perform health checks on all servers
     */
    private void performHealthChecks() {
        for (Server server : servers) {
            boolean healthy = checkServerHealth(server);
            server.updateHealthStatus(healthy);
        }
    }
    
    /**
     * Check health of a specific server
     */
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
    
    /**
     * Get overall load balancer statistics
     */
    public LoadBalancerStats getStats() {
        return new LoadBalancerStats(
            servers.size(),
            getHealthyServers().size(),
            servers.stream().mapToInt(Server::getTotalRequests).sum(),
            servers.stream().mapToInt(Server::getActiveConnections).sum(),
            strategy
        );
    }
    
    /**
     * Get statistics for all servers
     */
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
    
    /**
     * Get server by ID
     */
    public Server getServer(String serverId) {
        return serverMap.get(serverId);
    }
    
    /**
     * Get all server IDs
     */
    public Set<String> getServerIds() {
        return new HashSet<>(serverMap.keySet());
    }
    
    /**
     * Get current load balancing strategy
     */
    public LoadBalancingStrategy getStrategy() {
        return strategy;
    }
    
    // Enums and Inner Classes
    public enum LoadBalancingStrategy {
        ROUND_ROBIN, LEAST_CONNECTIONS, WEIGHTED_ROUND_ROBIN, RANDOM, IP_HASH
    }
    
    public static class Server {
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
        
        public String getId() { return id; }
        public String getAddress() { return address; }
        public long getLastHealthCheck() { return lastHealthCheck; }
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