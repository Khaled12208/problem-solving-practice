package com.problemsolving.systemdesign;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * URL Shortener Service
 * 
 * Design and implement a URL shortening service similar to bit.ly or TinyURL.
 * The service should support:
 * - Converting long URLs to short URLs
 * - Redirecting short URLs to original URLs
 * - Tracking analytics (clicks, unique visitors, referrers)
 * - Custom short URLs
 * - URL expiration and cleanup
 * - Rate limiting and abuse prevention
 * 
 * Implementation uses Java's built-in data structures for optimal performance.
 * 
 * Time Complexity: O(1) for URL shortening and redirection
 * Space Complexity: O(n) where n is the number of URLs
 * 
 * Example:
 * URLShortenerService shortener = new URLShortenerService();
 * String shortUrl = shortener.shortenUrl("https://www.google.com/search?q=java+programming");
 * String originalUrl = shortener.getOriginalUrl(shortUrl);
 * shortener.recordClick(shortUrl, "192.168.1.1", "Mozilla/5.0");
 */
public class URLShortenerService {
    private final Map<String, URLMapping> shortToLongMap;
    private final Map<String, String> longToShortMap;
    private final Map<String, List<ClickEvent>> analytics;
    private final Set<String> customShortUrls;
    private final AtomicLong urlCounter;
    private final String baseUrl;
    private final int shortUrlLength;
    private final Random random;
    
    // Configuration
    private final int maxCustomUrlLength;
    private final int maxUrlLength;
    private final long urlExpirationDays;
    private final int maxClicksPerMinute;
    private final Map<String, Integer> rateLimitMap;
    
    public URLShortenerService() {
        this("https://short.ly", 6, 50, 2048, 365, 10);
    }
    
    public URLShortenerService(String baseUrl, int shortUrlLength, int maxCustomUrlLength, 
                              int maxUrlLength, long urlExpirationDays, int maxClicksPerMinute) {
        this.shortToLongMap = new ConcurrentHashMap<>();
        this.longToShortMap = new ConcurrentHashMap<>();
        this.analytics = new ConcurrentHashMap<>();
        this.customShortUrls = ConcurrentHashMap.newKeySet();
        this.urlCounter = new AtomicLong(0);
        this.baseUrl = baseUrl;
        this.shortUrlLength = shortUrlLength;
        this.maxCustomUrlLength = maxCustomUrlLength;
        this.maxUrlLength = maxUrlLength;
        this.urlExpirationDays = urlExpirationDays;
        this.maxClicksPerMinute = maxClicksPerMinute;
        this.rateLimitMap = new ConcurrentHashMap<>();
        this.random = new Random();
        
        // Start cleanup thread
        startCleanupThread();
    }
    
    /**
     * Shorten a long URL to a short URL
     */
    public String shortenUrl(String longUrl) {
        return shortenUrl(longUrl, null);
    }
    
    /**
     * Shorten a long URL with optional custom short URL
     */
    public String shortenUrl(String longUrl, String customShortUrl) {
        // Validate input
        if (longUrl == null || longUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("Long URL cannot be null or empty");
        }
        
        if (longUrl.length() > maxUrlLength) {
            throw new IllegalArgumentException("URL too long. Maximum length: " + maxUrlLength);
        }
        
        // Check if URL already exists
        if (longToShortMap.containsKey(longUrl)) {
            return baseUrl + "/" + longToShortMap.get(longUrl);
        }
        
        String shortCode;
        if (customShortUrl != null && !customShortUrl.trim().isEmpty()) {
            // Use custom short URL
            if (customShortUrl.length() > maxCustomUrlLength) {
                throw new IllegalArgumentException("Custom URL too long. Maximum length: " + maxCustomUrlLength);
            }
            
            if (customShortUrls.contains(customShortUrl)) {
                throw new IllegalArgumentException("Custom URL already exists");
            }
            
            shortCode = customShortUrl;
        } else {
            // Generate random short code
            shortCode = generateShortCode();
        }
        
        // Create URL mapping
        URLMapping mapping = new URLMapping(
            shortCode,
            longUrl,
            LocalDateTime.now(),
            LocalDateTime.now().plusDays(urlExpirationDays),
            urlCounter.incrementAndGet()
        );
        
        // Store mappings
        shortToLongMap.put(shortCode, mapping);
        longToShortMap.put(longUrl, shortCode);
        
        if (customShortUrl != null) {
            customShortUrls.add(customShortUrl);
        }
        
        // Initialize analytics
        analytics.put(shortCode, new ArrayList<>());
        
        return baseUrl + "/" + shortCode;
    }
    
    /**
     * Get original URL from short URL
     */
    public String getOriginalUrl(String shortUrl) {
        String shortCode = extractShortCode(shortUrl);
        URLMapping mapping = shortToLongMap.get(shortCode);
        
        if (mapping == null) {
            throw new IllegalArgumentException("Short URL not found");
        }
        
        if (mapping.isExpired()) {
            throw new IllegalArgumentException("Short URL has expired");
        }
        
        return mapping.getLongUrl();
    }
    
    /**
     * Record a click event for analytics
     */
    public void recordClick(String shortUrl, String ipAddress, String userAgent) {
        String shortCode = extractShortCode(shortUrl);
        URLMapping mapping = shortToLongMap.get(shortCode);
        
        if (mapping == null || mapping.isExpired()) {
            return;
        }
        
        // Rate limiting
        if (!isRateLimitAllowed(ipAddress)) {
            return;
        }
        
        // Create click event
        ClickEvent event = new ClickEvent(
            shortCode,
            ipAddress,
            userAgent,
            LocalDateTime.now(),
            extractReferrer(userAgent),
            extractCountry(ipAddress)
        );
        
        // Store analytics
        analytics.computeIfAbsent(shortCode, k -> new ArrayList<>()).add(event);
        
        // Update click count
        mapping.incrementClicks();
    }
    
    /**
     * Get analytics for a short URL
     */
    public URLAnalytics getAnalytics(String shortUrl) {
        String shortCode = extractShortCode(shortUrl);
        URLMapping mapping = shortToLongMap.get(shortCode);
        
        if (mapping == null) {
            throw new IllegalArgumentException("Short URL not found");
        }
        
        List<ClickEvent> events = analytics.getOrDefault(shortCode, new ArrayList<>());
        
        return new URLAnalytics(
            mapping,
            events,
            calculateUniqueVisitors(events),
            calculateTopReferrers(events),
            calculateTopCountries(events),
            calculateHourlyDistribution(events)
        );
    }
    
    /**
     * Delete a short URL
     */
    public boolean deleteUrl(String shortUrl) {
        String shortCode = extractShortCode(shortUrl);
        URLMapping mapping = shortToLongMap.remove(shortCode);
        
        if (mapping != null) {
            longToShortMap.remove(mapping.getLongUrl());
            analytics.remove(shortCode);
            customShortUrls.remove(shortCode);
            return true;
        }
        
        return false;
    }
    
    /**
     * Update URL expiration
     */
    public void updateExpiration(String shortUrl, LocalDateTime newExpiration) {
        String shortCode = extractShortCode(shortUrl);
        URLMapping mapping = shortToLongMap.get(shortCode);
        
        if (mapping != null) {
            mapping.setExpirationDate(newExpiration);
        }
    }
    
    /**
     * Get service statistics
     */
    public ServiceStats getServiceStats() {
        return new ServiceStats(
            shortToLongMap.size(),
            longToShortMap.size(),
            customShortUrls.size(),
            analytics.values().stream().mapToInt(List::size).sum(),
            getActiveUrlsCount(),
            getExpiredUrlsCount()
        );
    }
    
    /**
     * Generate a random short code
     */
    private String generateShortCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < shortUrlLength; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        
        String shortCode = sb.toString();
        
        // Ensure uniqueness
        while (shortToLongMap.containsKey(shortCode)) {
            sb = new StringBuilder();
            for (int i = 0; i < shortUrlLength; i++) {
                sb.append(chars.charAt(random.nextInt(chars.length())));
            }
            shortCode = sb.toString();
        }
        
        return shortCode;
    }
    
    /**
     * Extract short code from full short URL
     */
    private String extractShortCode(String shortUrl) {
        if (shortUrl.startsWith(baseUrl)) {
            return shortUrl.substring(baseUrl.length() + 1);
        }
        return shortUrl;
    }
    
    /**
     * Check rate limiting
     */
    private boolean isRateLimitAllowed(String ipAddress) {
        String key = ipAddress + ":" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        int currentCount = rateLimitMap.getOrDefault(key, 0);
        
        if (currentCount >= maxClicksPerMinute) {
            return false;
        }
        
        rateLimitMap.put(key, currentCount + 1);
        return true;
    }
    
    /**
     * Extract referrer from user agent
     */
    private String extractReferrer(String userAgent) {
        // Simple referrer extraction - in real implementation, this would be more sophisticated
        if (userAgent == null) return "Unknown";
        
        if (userAgent.contains("Google")) return "Google";
        if (userAgent.contains("Facebook")) return "Facebook";
        if (userAgent.contains("Twitter")) return "Twitter";
        if (userAgent.contains("LinkedIn")) return "LinkedIn";
        
        return "Direct";
    }
    
    /**
     * Extract country from IP address
     */
    private String extractCountry(String ipAddress) {
        // Simple country extraction - in real implementation, this would use GeoIP database
        if (ipAddress == null) return "Unknown";
        
        // Mock implementation
        if (ipAddress.startsWith("192.168.")) return "Local";
        if (ipAddress.startsWith("10.")) return "Local";
        
        return "Unknown";
    }
    
    /**
     * Calculate unique visitors
     */
    private int calculateUniqueVisitors(List<ClickEvent> events) {
        return (int) events.stream()
            .map(ClickEvent::getIpAddress)
            .distinct()
            .count();
    }
    
    /**
     * Calculate top referrers
     */
    private Map<String, Integer> calculateTopReferrers(List<ClickEvent> events) {
        Map<String, Integer> referrerCount = new HashMap<>();
        for (ClickEvent event : events) {
            referrerCount.merge(event.getReferrer(), 1, Integer::sum);
        }
        return referrerCount;
    }
    
    /**
     * Calculate top countries
     */
    private Map<String, Integer> calculateTopCountries(List<ClickEvent> events) {
        Map<String, Integer> countryCount = new HashMap<>();
        for (ClickEvent event : events) {
            countryCount.merge(event.getCountry(), 1, Integer::sum);
        }
        return countryCount;
    }
    
    /**
     * Calculate hourly distribution
     */
    private Map<Integer, Integer> calculateHourlyDistribution(List<ClickEvent> events) {
        Map<Integer, Integer> hourlyCount = new HashMap<>();
        for (ClickEvent event : events) {
            int hour = event.getTimestamp().getHour();
            hourlyCount.merge(hour, 1, Integer::sum);
        }
        return hourlyCount;
    }
    
    /**
     * Get count of active URLs
     */
    private int getActiveUrlsCount() {
        return (int) shortToLongMap.values().stream()
            .filter(mapping -> !mapping.isExpired())
            .count();
    }
    
    /**
     * Get count of expired URLs
     */
    private int getExpiredUrlsCount() {
        return (int) shortToLongMap.values().stream()
            .filter(URLMapping::isExpired)
            .count();
    }
    
    /**
     * Start cleanup thread for expired URLs
     */
    private void startCleanupThread() {
        Thread cleanupThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(24 * 60 * 60 * 1000); // Run daily
                    cleanupExpiredUrls();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        cleanupThread.setDaemon(true);
        cleanupThread.start();
    }
    
    /**
     * Clean up expired URLs
     */
    private void cleanupExpiredUrls() {
        Iterator<Map.Entry<String, URLMapping>> iterator = shortToLongMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, URLMapping> entry = iterator.next();
            if (entry.getValue().isExpired()) {
                iterator.remove();
                longToShortMap.remove(entry.getValue().getLongUrl());
                analytics.remove(entry.getKey());
                customShortUrls.remove(entry.getKey());
            }
        }
    }
    
    // Inner Classes
    public static class URLMapping {
        private final String shortCode;
        private final String longUrl;
        private final LocalDateTime createdAt;
        private LocalDateTime expirationDate;
        private final long id;
        private int clickCount;
        
        public URLMapping(String shortCode, String longUrl, LocalDateTime createdAt, 
                         LocalDateTime expirationDate, long id) {
            this.shortCode = shortCode;
            this.longUrl = longUrl;
            this.createdAt = createdAt;
            this.expirationDate = expirationDate;
            this.id = id;
            this.clickCount = 0;
        }
        
        public boolean isExpired() {
            return LocalDateTime.now().isAfter(expirationDate);
        }
        
        public void incrementClicks() {
            clickCount++;
        }
        
        // Getters
        public String getShortCode() { return shortCode; }
        public String getLongUrl() { return longUrl; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public LocalDateTime getExpirationDate() { return expirationDate; }
        public long getId() { return id; }
        public int getClickCount() { return clickCount; }
        
        public void setExpirationDate(LocalDateTime expirationDate) {
            this.expirationDate = expirationDate;
        }
    }
    
    public static class ClickEvent {
        private final String shortCode;
        private final String ipAddress;
        private final String userAgent;
        private final LocalDateTime timestamp;
        private final String referrer;
        private final String country;
        
        public ClickEvent(String shortCode, String ipAddress, String userAgent, 
                         LocalDateTime timestamp, String referrer, String country) {
            this.shortCode = shortCode;
            this.ipAddress = ipAddress;
            this.userAgent = userAgent;
            this.timestamp = timestamp;
            this.referrer = referrer;
            this.country = country;
        }
        
        // Getters
        public String getShortCode() { return shortCode; }
        public String getIpAddress() { return ipAddress; }
        public String getUserAgent() { return userAgent; }
        public LocalDateTime getTimestamp() { return timestamp; }
        public String getReferrer() { return referrer; }
        public String getCountry() { return country; }
    }
    
    public static class URLAnalytics {
        private final URLMapping mapping;
        private final List<ClickEvent> events;
        private final int uniqueVisitors;
        private final Map<String, Integer> topReferrers;
        private final Map<String, Integer> topCountries;
        private final Map<Integer, Integer> hourlyDistribution;
        
        public URLAnalytics(URLMapping mapping, List<ClickEvent> events, int uniqueVisitors,
                          Map<String, Integer> topReferrers, Map<String, Integer> topCountries,
                          Map<Integer, Integer> hourlyDistribution) {
            this.mapping = mapping;
            this.events = events;
            this.uniqueVisitors = uniqueVisitors;
            this.topReferrers = topReferrers;
            this.topCountries = topCountries;
            this.hourlyDistribution = hourlyDistribution;
        }
        
        // Getters
        public URLMapping getMapping() { return mapping; }
        public List<ClickEvent> getEvents() { return events; }
        public int getUniqueVisitors() { return uniqueVisitors; }
        public Map<String, Integer> getTopReferrers() { return topReferrers; }
        public Map<String, Integer> getTopCountries() { return topCountries; }
        public Map<Integer, Integer> getHourlyDistribution() { return hourlyDistribution; }
    }
    
    public static class ServiceStats {
        public final int totalUrls;
        public final int uniqueLongUrls;
        public final int customUrls;
        public final int totalClicks;
        public final int activeUrls;
        public final int expiredUrls;
        
        public ServiceStats(int totalUrls, int uniqueLongUrls, int customUrls, 
                          int totalClicks, int activeUrls, int expiredUrls) {
            this.totalUrls = totalUrls;
            this.uniqueLongUrls = uniqueLongUrls;
            this.customUrls = customUrls;
            this.totalClicks = totalClicks;
            this.activeUrls = activeUrls;
            this.expiredUrls = expiredUrls;
        }
        
        @Override
        public String toString() {
            return String.format("ServiceStats{totalUrls=%d, uniqueLongUrls=%d, customUrls=%d, " +
                               "totalClicks=%d, activeUrls=%d, expiredUrls=%d}",
                totalUrls, uniqueLongUrls, customUrls, totalClicks, activeUrls, expiredUrls);
        }
    }
} 