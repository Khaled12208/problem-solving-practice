package com.problemsolving.intervals;

import java.util.*;

/**
 * Collection of interval-related problems and solutions.
 * This class contains various algorithms for handling overlapping intervals.
 */
public class IntervalProblems {
    
    /**
     * Problem: Merge Intervals
     * Given an array of intervals where intervals[i] = [starti, endi], 
     * merge all overlapping intervals, and return an array of the non-overlapping 
     * intervals that cover all the intervals in the input.
     * 
     * Example:
     * Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
     * Output: [[1,6],[8,10],[15,18]]
     * 
     * @param intervals array of intervals
     * @return merged intervals
     */
    public int[][] mergeIntervals(int[][] intervals) {
        if (intervals.length <= 1) return intervals;
        
        // Sort by start time
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        
        List<int[]> result = new ArrayList<>();
        int[] current = intervals[0];
        
        for (int i = 1; i < intervals.length; i++) {
            if (current[1] >= intervals[i][0]) {
                // Overlap - merge
                current[1] = Math.max(current[1], intervals[i][1]);
            } else {
                // No overlap - add current and move to next
                result.add(current);
                current = intervals[i];
            }
        }
        result.add(current);
        
        return result.toArray(new int[result.size()][]);
    }
    
    /**
     * Problem: Insert Interval
     * Given a set of non-overlapping intervals, insert a new interval into the intervals 
     * (merge if necessary). You may assume that the intervals were initially sorted 
     * according to their start times.
     * 
     * Example:
     * Input: intervals = [[1,3],[6,9]], newInterval = [2,5]
     * Output: [[1,5],[6,9]]
     * 
     * @param intervals array of non-overlapping intervals
     * @param newInterval interval to insert
     * @return intervals after insertion
     */
    public int[][] insertInterval(int[][] intervals, int[] newInterval) {
        List<int[]> result = new ArrayList<>();
        int i = 0;
        
        // Add intervals before newInterval
        while (i < intervals.length && intervals[i][1] < newInterval[0]) {
            result.add(intervals[i]);
            i++;
        }
        
        // Merge overlapping intervals
        while (i < intervals.length && intervals[i][0] <= newInterval[1]) {
            newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
            newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
            i++;
        }
        result.add(newInterval);
        
        // Add remaining intervals
        while (i < intervals.length) {
            result.add(intervals[i]);
            i++;
        }
        
        return result.toArray(new int[result.size()][]);
    }
    
    /**
     * Problem: Meeting Rooms
     * Given an array of meeting time intervals where intervals[i] = [starti, endi], 
     * determine if a person could attend all meetings.
     * 
     * Example:
     * Input: intervals = [[0,30],[5,10],[15,20]]
     * Output: false (person cannot attend all meetings)
     * 
     * @param intervals array of meeting intervals
     * @return true if person can attend all meetings
     */
    public boolean canAttendMeetings(int[][] intervals) {
        if (intervals.length <= 1) return true;
        
        // Sort by start time
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i-1][1] > intervals[i][0]) {
                return false; // Overlap found
            }
        }
        return true;
    }
    
    /**
     * Problem: Meeting Rooms II
     * Given an array of meeting time intervals where intervals[i] = [starti, endi], 
     * return the minimum number of conference rooms required.
     * 
     * Example:
     * Input: intervals = [[0,30],[5,10],[15,20]]
     * Output: 2 (need 2 rooms)
     * 
     * @param intervals array of meeting intervals
     * @return minimum number of rooms needed
     */
    public int minMeetingRooms(int[][] intervals) {
        if (intervals.length == 0) return 0;
        
        // Extract start and end times
        int[] starts = new int[intervals.length];
        int[] ends = new int[intervals.length];
        
        for (int i = 0; i < intervals.length; i++) {
            starts[i] = intervals[i][0];
            ends[i] = intervals[i][1];
        }
        
        Arrays.sort(starts);
        Arrays.sort(ends);
        
        int rooms = 0;
        int endIndex = 0;
        
        for (int startIndex = 0; startIndex < starts.length; startIndex++) {
            if (starts[startIndex] < ends[endIndex]) {
                rooms++; // Need new room
            } else {
                endIndex++; // Room becomes free
            }
        }
        
        return rooms;
    }
    
    /**
     * Problem: Non-overlapping Intervals
     * Given an array of intervals intervals, return the minimum number of intervals 
     * you need to remove to make the rest of the intervals non-overlapping.
     * 
     * Example:
     * Input: intervals = [[1,2],[2,3],[3,4],[1,3]]
     * Output: 1 (remove [1,3] to make non-overlapping)
     * 
     * @param intervals array of intervals
     * @return minimum number of intervals to remove
     */
    public int eraseOverlapIntervals(int[][] intervals) {
        if (intervals.length <= 1) return 0;
        
        // Sort by end time
        Arrays.sort(intervals, (a, b) -> a[1] - b[1]);
        
        int count = 0;
        int end = intervals[0][1];
        
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < end) {
                count++; // Remove this interval
            } else {
                end = intervals[i][1]; // Keep this interval
            }
        }
        
        return count;
    }
    
    /**
     * Problem: Interval List Intersections
     * Given two lists of closed intervals, firstList and secondList, where 
     * firstList[i] = [starti, endi] and secondList[j] = [startj, endj], each list 
     * of intervals is pairwise disjoint and in sorted order.
     * 
     * Return the intersection of these two interval lists.
     * 
     * Example:
     * Input: firstList = [[0,2],[5,10],[13,23],[24,25]], 
     *        secondList = [[1,5],[8,12],[15,24],[25,26]]
     * Output: [[1,2],[5,5],[8,10],[15,23],[24,24],[25,25]]
     * 
     * @param firstList first list of intervals
     * @param secondList second list of intervals
     * @return intersection of intervals
     */
    public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
        List<int[]> result = new ArrayList<>();
        int i = 0, j = 0;
        
        while (i < firstList.length && j < secondList.length) {
            // Find overlap
            int start = Math.max(firstList[i][0], secondList[j][0]);
            int end = Math.min(firstList[i][1], secondList[j][1]);
            
            if (start <= end) {
                result.add(new int[]{start, end});
            }
            
            // Move pointer with smaller end
            if (firstList[i][1] < secondList[j][1]) {
                i++;
            } else {
                j++;
            }
        }
        
        return result.toArray(new int[result.size()][]);
    }
    
    /**
     * Problem: Employee Free Time
     * We are given a list schedule of employees, which represents the working time 
     * for each employee. Each employee has a list of non-overlapping Intervals, 
     * and these intervals are in sorted order.
     * 
     * Return the list of finite intervals representing common, positive-length 
     * free time for all employees, also in sorted order.
     * 
     * Example:
     * Input: schedule = [[[1,2],[5,6]],[[1,3]],[[4,10]]]
     * Output: [[3,4]] (all employees are free during [3,4])
     * 
     * @param schedule list of employee schedules
     * @return common free time intervals
     */
    public int[][] employeeFreeTime(List<List<int[]>> schedule) {
        List<int[]> allIntervals = new ArrayList<>();
        
        // Collect all intervals
        for (List<int[]> employee : schedule) {
            allIntervals.addAll(employee);
        }
        
        // Sort by start time
        Collections.sort(allIntervals, (a, b) -> a[0] - b[0]);
        
        // Merge overlapping intervals
        List<int[]> merged = new ArrayList<>();
        int[] current = allIntervals.get(0);
        
        for (int i = 1; i < allIntervals.size(); i++) {
            if (current[1] >= allIntervals.get(i)[0]) {
                current[1] = Math.max(current[1], allIntervals.get(i)[1]);
            } else {
                merged.add(current);
                current = allIntervals.get(i);
            }
        }
        merged.add(current);
        
        // Find gaps (free time)
        List<int[]> freeTime = new ArrayList<>();
        for (int i = 1; i < merged.size(); i++) {
            freeTime.add(new int[]{merged.get(i-1)[1], merged.get(i)[0]});
        }
        
        return freeTime.toArray(new int[freeTime.size()][]);
    }
    
    /**
     * Problem: Car Pooling
     * You are driving a vehicle that has capacity empty seats initially available 
     * for passengers. The vehicle only drives east (i.e., it cannot turn around 
     * and drive west).
     * 
     * Given a list of trips, where trips[i] = [numPassengersi, fromi, toi] 
     * indicates that the ith trip has numPassengersi passengers and the locations 
     * to pick them up and drop them off are fromi and toi respectively, 
     * return true if it is possible to pick up and drop off all passengers 
     * for all the given trips, or false otherwise.
     * 
     * Example:
     * Input: trips = [[2,1,5],[3,3,7]], capacity = 4
     * Output: false (need 5 seats at some point)
     * 
     * @param trips array of trips
     * @param capacity vehicle capacity
     * @return true if all trips can be completed
     */
    public boolean carPooling(int[][] trips, int capacity) {
        int[] timeline = new int[1001];
        
        for (int[] trip : trips) {
            timeline[trip[1]] += trip[0]; // Pick up
            timeline[trip[2]] -= trip[0]; // Drop off
        }
        
        int passengers = 0;
        for (int i = 0; i < timeline.length; i++) {
            passengers += timeline[i];
            if (passengers > capacity) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Problem: Range Addition
     * You are given an integer length and an array updates where 
     * updates[i] = [startIdxi, endIdxi, inci].
     * 
     * You have an array arr of length length with all zeros, and you have some 
     * operation to apply on arr. In the ith operation, you should increment all 
     * the elements arr[startIdxi], arr[startIdxi + 1], ..., arr[endIdxi] by inci.
     * 
     * Return arr after applying all the updates.
     * 
     * Example:
     * Input: length = 5, updates = [[1,3,2],[2,4,3],[0,2,-2]]
     * Output: [-2,0,3,5,3]
     * 
     * @param length length of array
     * @param updates array of update operations
     * @return final array after all updates
     */
    public int[] rangeAddition(int length, int[][] updates) {
        int[] result = new int[length];
        
        // Apply updates
        for (int[] update : updates) {
            result[update[0]] += update[2];
            if (update[1] + 1 < length) {
                result[update[1] + 1] -= update[2];
            }
        }
        
        // Calculate prefix sum
        for (int i = 1; i < length; i++) {
            result[i] += result[i - 1];
        }
        
        return result;
    }
    
    /**
     * Problem: My Calendar I
     * Implement a MyCalendar class to store your events. A new event can be added 
     * if adding the event will not cause a double booking.
     * 
     * Your class will have the method, book(int start, int end). Formally, this 
     * represents a booking on the half open interval [start, end), the range of 
     * real numbers x such that start <= x < end.
     * 
     * A double booking happens when two events have some non-empty intersection 
     * (i.e., some moment is common to both events.)
     * 
     * Return true if the event can be added to the calendar successfully without 
     * causing a double booking. Otherwise, return false and do not add the event 
     * to the calendar.
     */
    public static class MyCalendar {
        private List<int[]> events;
        
        public MyCalendar() {
            events = new ArrayList<>();
        }
        
        public boolean book(int start, int end) {
            for (int[] event : events) {
                if (start < event[1] && end > event[0]) {
                    return false; // Overlap
                }
            }
            events.add(new int[]{start, end});
            return true;
        }
    }
    
    /**
     * Problem: My Calendar II
     * Implement a MyCalendarTwo class to store your events. A new event can be 
     * added if adding the event will not cause a triple booking.
     * 
     * Your class will have one method, book(int start, int end). Formally, this 
     * represents a booking on the half open interval [start, end), the range of 
     * real numbers x such that start <= x < end.
     * 
     * A triple booking happens when three events have some non-empty intersection 
     * (i.e., some moment is common to all three events.)
     * 
     * Return true if the event can be added to the calendar successfully without 
     * causing a triple booking. Otherwise, return false and do not add the event 
     * to the calendar.
     */
    public static class MyCalendarTwo {
        private List<int[]> events;
        private List<int[]> overlaps;
        
        public MyCalendarTwo() {
            events = new ArrayList<>();
            overlaps = new ArrayList<>();
        }
        
        public boolean book(int start, int end) {
            // Check for triple booking
            for (int[] overlap : overlaps) {
                if (start < overlap[1] && end > overlap[0]) {
                    return false;
                }
            }
            
            // Find overlaps with existing events
            for (int[] event : events) {
                if (start < event[1] && end > event[0]) {
                    overlaps.add(new int[]{
                        Math.max(start, event[0]), 
                        Math.min(end, event[1])
                    });
                }
            }
            
            events.add(new int[]{start, end});
            return true;
        }
    }
} 