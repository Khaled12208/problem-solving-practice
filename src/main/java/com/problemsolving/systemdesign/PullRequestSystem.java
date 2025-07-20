package com.problemsolving.systemdesign;

import java.util.*;

/**
 * GitHub Pull Request Approval System
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
public class PullRequestSystem {
    private final Map<String, PullRequest> pullRequests;
    private final Map<String, User> users;
    private final Map<String, BranchProtectionRule> branchRules;
    
    public PullRequestSystem() {
        this.pullRequests = new HashMap<>();
        this.users = new HashMap<>();
        this.branchRules = new HashMap<>();
    }
    
    /**
     * Create a new pull request
     */
    public String createPullRequest(String author, String baseBranch, String headBranch, String title) {
        String prId = "pr" + (pullRequests.size() + 1);
        PullRequest pr = new PullRequest(prId, author, baseBranch, headBranch, title);
        pullRequests.put(prId, pr);
        return prId;
    }
    
    /**
     * Add a reviewer to a pull request
     */
    public void addReviewer(String prId, String reviewerId) {
        if (pullRequests.containsKey(prId)) {
            PullRequest pr = pullRequests.get(prId);
            pr.reviewers.add(reviewerId);
        }
    }
    
    /**
     * Remove a reviewer from a pull request
     */
    public void removeReviewer(String prId, String reviewerId) {
        if (pullRequests.containsKey(prId)) {
            PullRequest pr = pullRequests.get(prId);
            pr.reviewers.remove(reviewerId);
            pr.approvedReviewers.remove(reviewerId);
        }
    }
    
    /**
     * Submit a review for a pull request
     */
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
    
    /**
     * Check if a pull request can be merged
     */
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
    
    /**
     * Set branch protection rules
     */
    public void setBranchProtectionRule(String branch, int requiredApprovals, boolean requireUpToDate) {
        branchRules.put(branch, new BranchProtectionRule(requiredApprovals, requireUpToDate));
    }
    
    /**
     * Merge a pull request
     */
    public void mergePullRequest(String prId) {
        if (canMerge(prId)) {
            PullRequest pr = pullRequests.get(prId);
            pr.status = PRStatus.MERGED;
            pr.mergedAt = System.currentTimeMillis();
        }
    }
    
    /**
     * Close a pull request
     */
    public void closePullRequest(String prId) {
        if (pullRequests.containsKey(prId)) {
            pullRequests.get(prId).status = PRStatus.CLOSED;
        }
    }
    
    /**
     * Get all reviews for a pull request
     */
    public List<Review> getReviews(String prId) {
        if (pullRequests.containsKey(prId)) {
            return new ArrayList<>(pullRequests.get(prId).reviews.values());
        }
        return new ArrayList<>();
    }
    
    /**
     * Get pull request status
     */
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
    
    /**
     * Get all pull requests
     */
    public List<PullRequest> getAllPullRequests() {
        return new ArrayList<>(pullRequests.values());
    }
    
    /**
     * Get pull requests by status
     */
    public List<PullRequest> getPullRequestsByStatus(PRStatus status) {
        return pullRequests.values().stream()
            .filter(pr -> pr.status == status)
            .collect(Collectors.toList());
    }
    
    /**
     * Get pull requests by author
     */
    public List<PullRequest> getPullRequestsByAuthor(String author) {
        return pullRequests.values().stream()
            .filter(pr -> pr.author.equals(author))
            .collect(Collectors.toList());
    }
    
    // Enums and Inner Classes
    public enum ReviewType {
        APPROVE, REQUEST_CHANGES, COMMENT
    }
    
    public enum PRStatus {
        OPEN, MERGED, CLOSED
    }
    
    public static class PullRequest {
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
        
        public String getId() { return id; }
        public String getAuthor() { return author; }
        public String getBaseBranch() { return baseBranch; }
        public String getHeadBranch() { return headBranch; }
        public String getTitle() { return title; }
        public PRStatus getStatus() { return status; }
        public long getCreatedAt() { return createdAt; }
        public long getMergedAt() { return mergedAt; }
        public Set<String> getReviewers() { return new HashSet<>(reviewers); }
        public Map<String, Review> getReviews() { return new HashMap<>(reviews); }
        public Set<String> getApprovedReviewers() { return new HashSet<>(approvedReviewers); }
    }
    
    public static class Review {
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
        
        public String getReviewerId() { return reviewerId; }
        public ReviewType getType() { return type; }
        public String getComment() { return comment; }
        public long getTimestamp() { return timestamp; }
    }
    
    public static class BranchProtectionRule {
        int requiredApprovals;
        boolean requireUpToDate;
        
        BranchProtectionRule(int requiredApprovals, boolean requireUpToDate) {
            this.requiredApprovals = requiredApprovals;
            this.requireUpToDate = requireUpToDate;
        }
        
        public int getRequiredApprovals() { return requiredApprovals; }
        public boolean isRequireUpToDate() { return requireUpToDate; }
    }
    
    public static class User {
        String id;
        String name;
        String email;
        
        User(String id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }
        
        public String getId() { return id; }
        public String getName() { return name; }
        public String getEmail() { return email; }
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