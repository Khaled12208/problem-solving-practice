package com.problemsolving.systemdesign;

import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

/**
 * Message Queue with Priority using Java's PriorityQueue
 * 
 * Implement a message queue that supports priority-based ordering.
 * Higher priority messages are dequeued first.
 * 
 * Implementation uses Java's PriorityQueue with custom comparator for O(log n) operations.
 * 
 * Time Complexity: O(log n) for enqueue/dequeue operations
 * Space Complexity: O(n) where n is the number of messages
 * 
 * Example:
 * PriorityMessageQueue queue = new PriorityMessageQueue();
 * queue.enqueue("message1", 1);
 * queue.enqueue("message2", 3);
 * queue.enqueue("message3", 2);
 * String message = queue.dequeue(); // Returns "message2" (highest priority)
 */
public class PriorityMessageQueue {
    private final PriorityQueue<Message> queue;
    
    public PriorityMessageQueue() {
        // Higher priority numbers are dequeued first
        this.queue = new PriorityQueue<>(Comparator.comparing(Message::getPriority).reversed()
                .thenComparing(Message::getTimestamp));
    }
    
    /**
     * Add a message to the queue with specified priority
     * Higher priority numbers are dequeued first
     */
    public void enqueue(String message, int priority) {
        queue.offer(new Message(message, priority, System.currentTimeMillis()));
    }
    
    /**
     * Remove and return the highest priority message from the queue
     */
    public String dequeue() {
        Message message = queue.poll();
        return message != null ? message.getContent() : null;
    }
    
    /**
     * Peek at the highest priority message without removing it
     */
    public String peek() {
        Message message = queue.peek();
        return message != null ? message.getContent() : null;
    }
    
    /**
     * Check if the queue is empty
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }
    
    /**
     * Get the current size of the queue
     */
    public int size() {
        return queue.size();
    }
    
    /**
     * Clear all messages from the queue
     */
    public void clear() {
        queue.clear();
    }
    
    /**
     * Get message statistics
     */
    public QueueStats getStats() {
        if (queue.isEmpty()) {
            return new QueueStats(0, 0, 0, 0);
        }
        
        int minPriority = Integer.MAX_VALUE;
        int maxPriority = Integer.MIN_VALUE;
        long totalAge = 0;
        
        for (Message message : queue) {
            minPriority = Math.min(minPriority, message.getPriority());
            maxPriority = Math.max(maxPriority, message.getPriority());
            totalAge += System.currentTimeMillis() - message.getTimestamp();
        }
        
        long avgAge = queue.isEmpty() ? 0 : totalAge / queue.size();
        
        return new QueueStats(queue.size(), minPriority, maxPriority, avgAge);
    }
    
    /**
     * Get all messages with their priorities (for debugging/monitoring)
     * Note: This creates a copy of the queue for iteration
     */
    public List<MessageInfo> getAllMessages() {
        List<MessageInfo> messages = new ArrayList<>();
        for (Message message : queue) {
            messages.add(new MessageInfo(
                message.getContent(),
                message.getPriority(),
                System.currentTimeMillis() - message.getTimestamp()
            ));
        }
        return messages;
    }
    
    /**
     * Remove messages with specific priority
     */
    public void removeByPriority(int priority) {
        queue.removeIf(message -> message.getPriority() == priority);
    }
    
    /**
     * Get count of messages by priority
     */
    public int getCountByPriority(int priority) {
        return (int) queue.stream().filter(message -> message.getPriority() == priority).count();
    }
    
    /**
     * Check if queue contains a message with specific priority
     */
    public boolean containsPriority(int priority) {
        return queue.stream().anyMatch(message -> message.getPriority() == priority);
    }
    
    public static class Message {
        private final String content;
        private final int priority;
        private final long timestamp;
        
        Message(String content, int priority, long timestamp) {
            this.content = content;
            this.priority = priority;
            this.timestamp = timestamp;
        }
        
        public String getContent() { return content; }
        public int getPriority() { return priority; }
        public long getTimestamp() { return timestamp; }
        
        @Override
        public String toString() {
            return String.format("Message{content='%s', priority=%d, timestamp=%d}", 
                content, priority, timestamp);
        }
    }
    
    public static class QueueStats {
        public final int size;
        public final int minPriority;
        public final int maxPriority;
        public final long averageAgeMs;
        
        QueueStats(int size, int minPriority, int maxPriority, long averageAgeMs) {
            this.size = size;
            this.minPriority = minPriority;
            this.maxPriority = maxPriority;
            this.averageAgeMs = averageAgeMs;
        }
        
        @Override
        public String toString() {
            return String.format("QueueStats{size=%d, minPriority=%d, maxPriority=%d, averageAgeMs=%d}",
                size, minPriority, maxPriority, averageAgeMs);
        }
    }
    
    public static class MessageInfo {
        public final String content;
        public final int priority;
        public final long ageMs;
        
        MessageInfo(String content, int priority, long ageMs) {
            this.content = content;
            this.priority = priority;
            this.ageMs = ageMs;
        }
        
        @Override
        public String toString() {
            return String.format("MessageInfo{content='%s', priority=%d, ageMs=%d}",
                content, priority, ageMs);
        }
    }
} 