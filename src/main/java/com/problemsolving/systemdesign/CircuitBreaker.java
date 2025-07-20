package com.problemsolving.systemdesign;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.Lock;

/**
 * Circuit Breaker Pattern - Thread-Safe Implementation
 * 
 * Implement a thread-safe circuit breaker pattern to handle failures gracefully.
 * The circuit breaker has three states: CLOSED, OPEN, and HALF_OPEN.
 * 
 * CLOSED: Normal operation, requests are allowed through
 * OPEN: Circuit is open, requests are blocked
 * HALF_OPEN: Limited requests are allowed to test if the service is back
 * 
 * Thread Safety Features:
 * - Atomic operations for counters and timestamps
 * - Read-write locks for state changes
 * - Volatile state variable for visibility
 * - Thread-safe state transitions
 * 
 * Time Complexity: O(1) for all operations
 * Space Complexity: O(1)
 * 
 * Example:
 * CircuitBreaker cb = new CircuitBreaker(5, 10000, 2);
 * cb.execute(() -> callExternalService()); // May throw exception
 */
public class CircuitBreaker {
    private final int failureThreshold;
    private final long timeoutMs;
    private final int halfOpenMaxCalls;
    
    // Thread-safe state management
    private volatile State state;
    private final AtomicInteger failureCount;
    private final AtomicLong lastFailureTime;
    private final AtomicInteger halfOpenCallCount;
    
    // Read-write lock for state transitions
    private final ReentrantReadWriteLock lock;
    private final Lock readLock;
    private final Lock writeLock;
    
    public enum State {
        CLOSED, OPEN, HALF_OPEN
    }
    
    public CircuitBreaker(int failureThreshold, long timeoutMs, int halfOpenMaxCalls) {
        this.failureThreshold = failureThreshold;
        this.timeoutMs = timeoutMs;
        this.halfOpenMaxCalls = halfOpenMaxCalls;
        this.state = State.CLOSED;
        this.failureCount = new AtomicInteger(0);
        this.halfOpenCallCount = new AtomicInteger(0);
        this.lastFailureTime = new AtomicLong(0);
        
        this.lock = new ReentrantReadWriteLock();
        this.readLock = lock.readLock();
        this.writeLock = lock.writeLock();
    }
    
    /**
     * Execute a callable with circuit breaker protection
     * Thread-safe execution with proper state management
     */
    public <T> T execute(Supplier<T> supplier) throws Exception {
        // Check if circuit should transition from OPEN to HALF_OPEN
        if (shouldTransitionToHalfOpen()) {
            transitionToHalfOpen();
        }
        
        // Check if circuit is open
        if (isOpen()) {
            throw new RuntimeException("Circuit breaker is OPEN");
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
    
    /**
     * Check if circuit should transition from OPEN to HALF_OPEN
     * Thread-safe check using read lock
     */
    private boolean shouldTransitionToHalfOpen() {
        readLock.lock();
        try {
            if (state != State.OPEN) {
                return false;
            }
            long currentTime = System.currentTimeMillis();
            long lastFailure = lastFailureTime.get();
            return (currentTime - lastFailure) > timeoutMs;
        } finally {
            readLock.unlock();
        }
    }
    
    /**
     * Transition circuit to HALF_OPEN state
     * Thread-safe state transition using write lock
     */
    private void transitionToHalfOpen() {
        writeLock.lock();
        try {
            if (state == State.OPEN) {
                state = State.HALF_OPEN;
                halfOpenCallCount.set(0);
            }
        } finally {
            writeLock.unlock();
        }
    }
    
    /**
     * Handle successful execution
     * Thread-safe success handling
     */
    private void onSuccess() {
        if (state == State.HALF_OPEN) {
            int currentCount = halfOpenCallCount.incrementAndGet();
            if (currentCount >= halfOpenMaxCalls) {
                transitionToClosed();
            }
        }
    }
    
    /**
     * Handle failed execution
     * Thread-safe failure handling
     */
    private void onFailure() {
        long currentTime = System.currentTimeMillis();
        lastFailureTime.set(currentTime);
        
        int currentFailures = failureCount.incrementAndGet();
        
        writeLock.lock();
        try {
            if (state == State.HALF_OPEN || 
                (state == State.CLOSED && currentFailures >= failureThreshold)) {
                state = State.OPEN;
            }
        } finally {
            writeLock.unlock();
        }
    }
    
    /**
     * Transition circuit to CLOSED state
     * Thread-safe state transition
     */
    private void transitionToClosed() {
        writeLock.lock();
        try {
            state = State.CLOSED;
            failureCount.set(0);
        } finally {
            writeLock.unlock();
        }
    }
    
    /**
     * Get current circuit breaker state
     * Thread-safe read operation
     */
    public State getState() {
        readLock.lock();
        try {
            return state;
        } finally {
            readLock.unlock();
        }
    }
    
    /**
     * Get current failure count
     * Thread-safe atomic read
     */
    public int getFailureCount() {
        return failureCount.get();
    }
    
    /**
     * Get last failure timestamp
     * Thread-safe atomic read
     */
    public long getLastFailureTime() {
        return lastFailureTime.get();
    }
    
    /**
     * Get current half-open call count
     * Thread-safe atomic read
     */
    public int getHalfOpenCallCount() {
        return halfOpenCallCount.get();
    }
    
    /**
     * Reset circuit breaker to CLOSED state
     * Thread-safe reset operation
     */
    public void reset() {
        writeLock.lock();
        try {
            state = State.CLOSED;
            failureCount.set(0);
            halfOpenCallCount.set(0);
            lastFailureTime.set(0);
        } finally {
            writeLock.unlock();
        }
    }
    
    /**
     * Check if circuit is open
     * Thread-safe read operation
     */
    public boolean isOpen() {
        readLock.lock();
        try {
            return state == State.OPEN;
        } finally {
            readLock.unlock();
        }
    }
    
    /**
     * Check if circuit is closed
     * Thread-safe read operation
     */
    public boolean isClosed() {
        readLock.lock();
        try {
            return state == State.CLOSED;
        } finally {
            readLock.unlock();
        }
    }
    
    /**
     * Check if circuit is half-open
     * Thread-safe read operation
     */
    public boolean isHalfOpen() {
        readLock.lock();
        try {
            return state == State.HALF_OPEN;
        } finally {
            readLock.unlock();
        }
    }
    
    /**
     * Get circuit breaker statistics
     * Thread-safe statistics gathering
     */
    public CircuitBreakerStats getStats() {
        readLock.lock();
        try {
            return new CircuitBreakerStats(
                state,
                failureCount.get(),
                lastFailureTime.get(),
                halfOpenCallCount.get(),
                failureThreshold,
                timeoutMs,
                halfOpenMaxCalls
            );
        } finally {
            readLock.unlock();
        }
    }
    
    /**
     * Force circuit to OPEN state (for testing or manual control)
     * Thread-safe state transition
     */
    public void forceOpen() {
        writeLock.lock();
        try {
            state = State.OPEN;
        } finally {
            writeLock.unlock();
        }
    }
    
    /**
     * Force circuit to CLOSED state (for testing or manual control)
     * Thread-safe state transition
     */
    public void forceClosed() {
        writeLock.lock();
        try {
            state = State.CLOSED;
            failureCount.set(0);
            halfOpenCallCount.set(0);
        } finally {
            writeLock.unlock();
        }
    }
    
    @FunctionalInterface
    public interface Supplier<T> {
        T get() throws Exception;
    }
    
    /**
     * Thread-safe statistics class
     */
    public static class CircuitBreakerStats {
        public final State currentState;
        public final int failureCount;
        public final long lastFailureTime;
        public final int halfOpenCallCount;
        public final int failureThreshold;
        public final long timeoutMs;
        public final int halfOpenMaxCalls;
        
        CircuitBreakerStats(State currentState, int failureCount, long lastFailureTime,
                          int halfOpenCallCount, int failureThreshold, long timeoutMs, int halfOpenMaxCalls) {
            this.currentState = currentState;
            this.failureCount = failureCount;
            this.lastFailureTime = lastFailureTime;
            this.halfOpenCallCount = halfOpenCallCount;
            this.failureThreshold = failureThreshold;
            this.timeoutMs = timeoutMs;
            this.halfOpenMaxCalls = halfOpenMaxCalls;
        }
        
        @Override
        public String toString() {
            return String.format("CircuitBreakerStats{state=%s, failures=%d/%d, lastFailure=%d, halfOpenCalls=%d/%d, timeout=%dms}",
                currentState, failureCount, failureThreshold, lastFailureTime, halfOpenCallCount, halfOpenMaxCalls, timeoutMs);
        }
    }
} 