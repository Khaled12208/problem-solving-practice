package com.problemsolving.graphs;

import java.util.*;

/**
 * Graph Theory Problems
 * Practice problems focused on graph algorithms and traversal
 */
public class GraphProblems {
    
    /**
     * Problem 1: Implement Graph using Adjacency List
     * 
     * Implement an undirected graph using adjacency list representation.
     * 
     * TODO: Implement this class
     */
    public static class Graph {
        private int vertices;
        private List<List<Integer>> adjacencyList;
        
        public Graph(int vertices) {
            this.vertices = vertices;
            this.adjacencyList = new ArrayList<>();
            for (int i = 0; i < vertices; i++) {
                adjacencyList.add(new ArrayList<>());
            }
        }
        
        public void addEdge(int source, int destination) {
            // TODO: Your implementation here
        }
        
        public void removeEdge(int source, int destination) {
            // TODO: Your implementation here
        }
        
        public boolean hasEdge(int source, int destination) {
            // TODO: Your implementation here
            return false;
        }
        
        public List<Integer> getNeighbors(int vertex) {
            // TODO: Your implementation here
            return new ArrayList<>();
        }
        
        public int getVertexCount() {
            return vertices;
        }
    }
    
    /**
     * Problem 2: Depth First Search (DFS)
     * 
     * Implement DFS traversal of a graph starting from a given vertex.
     * 
     * Example:
     * Graph: 0 -> 1, 2
     *        1 -> 0, 3, 4
     *        2 -> 0, 4
     *        3 -> 1
     *        4 -> 1, 2
     * Starting vertex: 0
     * Output: [0, 1, 3, 4, 2]
     * 
     * TODO: Implement this method
     */
    public List<Integer> dfs(Graph graph, int startVertex) {
        List<Integer> res = new ArrayList<>();
        boolean[] visited = new boolean[graph.getVertexCount()];
        dfsHelper(graph, startVertex, visited, res);
        return res;
    }
    public void dfsHelper(Graph graph, int vertex, boolean[] visited, List<Integer> res){
        visited[vertex] = true;         
        res.add(vertex);
        for(int neighbor : graph.getNeighbors(vertex)){
            if(!visited[neighbor]){
                dfsHelper(graph, neighbor, visited, res);
            }
        }              
    }
    
    /**
     * Problem 3: Breadth First Search (BFS)
     * 
     * Implement BFS traversal of a graph starting from a given vertex.
     * 
     * Example:
     * Graph: 0 -> 1, 2
     *        1 -> 0, 3, 4
     *        2 -> 0, 4
     *        3 -> 1
     *        4 -> 1, 2
     * Starting vertex: 0
     * Output: [0, 1, 2, 3, 4]
     * 
     * TODO: Implement this method
     */
    public List<Integer> bfs(Graph graph, int startVertex) {
        // TODO: Your implementation here
        return new ArrayList<>();
    }
    
    /**
     * Problem 4: Detect Cycle in Undirected Graph
     * 
     * Detect if there is a cycle in an undirected graph.
     * 
     * Example:
     * Graph: 0 -> 1, 2
     *        1 -> 0, 3
     *        2 -> 0, 3
     *        3 -> 1, 2
     * Output: true (cycle: 0 -> 1 -> 3 -> 2 -> 0)
     * 
     * TODO: Implement this method
     */
    public boolean hasCycle(Graph graph) {
        // TODO: Your implementation here
        return false;
    }
    
    /**
     * Problem 5: Topological Sort
     * 
     * Implement topological sort for a directed acyclic graph (DAG).
     * 
     * Example:
     * Graph: 0 -> 1, 2
     *        1 -> 3
     *        2 -> 3
     *        3 -> []
     * Output: [0, 2, 1, 3] or [0, 1, 2, 3]
     * 
     * TODO: Implement this method
     */
    public List<Integer> topologicalSort(Graph graph) {
        // TODO: Your implementation here
        return new ArrayList<>();
    }
    
    /**
     * Problem 6: Shortest Path (Dijkstra's Algorithm)
     * 
     * Find the shortest path from source to all vertices using Dijkstra's algorithm.
     * 
     * Example:
     * Graph with weights:
     * 0 -> (1, 4), (2, 2)
     * 1 -> (2, 1), (3, 5)
     * 2 -> (3, 8), (4, 10)
     * 3 -> (4, 2)
     * 4 -> []
     * Source: 0
     * Output: [0, 4, 2, 9, 11] (shortest distances to each vertex)
     * 
     * TODO: Implement this method
     */
    public int[] dijkstra(Graph graph, int source) {
        // TODO: Your implementation here
        return new int[0];
    }
    
    /**
     * Problem 7: Minimum Spanning Tree (Kruskal's Algorithm)
     * 
     * Find the minimum spanning tree of a weighted undirected graph using Kruskal's algorithm.
     * 
     * Example:
     * Graph with weights:
     * 0 -> (1, 4), (2, 2)
     * 1 -> (0, 4), (2, 1), (3, 5)
     * 2 -> (0, 2), (1, 1), (3, 8), (4, 10)
     * 3 -> (1, 5), (2, 8), (4, 2)
     * 4 -> (2, 10), (3, 2)
     * Output: List of edges in MST with total weight
     * 
     * TODO: Implement this method
     */
    public List<Edge> kruskalMST(Graph graph) {
        // TODO: Your implementation here
        return new ArrayList<>();
    }
    
    /**
     * Problem 8: Strongly Connected Components (Tarjan's Algorithm)
     * 
     * Find all strongly connected components in a directed graph using Tarjan's algorithm.
     * 
     * Example:
     * Graph: 0 -> 1
     *        1 -> 2
     *        2 -> 0, 3
     *        3 -> 4
     *        4 -> 3
     * Output: [[0, 1, 2], [3, 4]]
     * 
     * TODO: Implement this method
     */
    public List<List<Integer>> findSCC(Graph graph) {
        // TODO: Your implementation here
        return new ArrayList<>();
    }
    
    /**
     * Problem 9: Bipartite Graph Check
     * 
     * Check if a graph is bipartite (can be colored with 2 colors such that no adjacent vertices have same color).
     * 
     * Example:
     * Graph: 0 -> 1, 3
     *        1 -> 0, 2
     *        2 -> 1, 3
     *        3 -> 0, 2
     * Output: true (can be colored as: 0=red, 1=blue, 2=red, 3=blue)
     * 
     * TODO: Implement this method
     */
    public boolean isBipartite(Graph graph) {
        // TODO: Your implementation here
        return false;
    }
    
    /**
     * Problem 10: Number of Islands
     * 
     * Given a 2D grid map of '1's (land) and '0's (water), count the number of islands.
     * An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically.
     * 
     * Example:
     * Grid:
     * 1 1 0 0 0
     * 1 1 0 0 0
     * 0 0 1 0 0
     * 0 0 0 1 1
     * Output: 3 islands
     * 
     * TODO: Implement this method
     */
    public int numIslands(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int count = 0;
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if(grid[i][j] == '1'){
                    count++;
                    dfs(grid, i, j);
                }
            }
        }
        return count;
    }
    public void dfs(char[][] grid, int i, int j){
        if(i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] == '0'){
            return;
        }
        grid[i][j] = '0';
    }
    
    /**
     * Problem 11: Word Ladder
     * 
     * Given two words (beginWord and endWord), and a dictionary's word list, find the length of shortest 
     * transformation sequence from beginWord to endWord.
     * 
     * Example:
     * beginWord = "hit", endWord = "cog"
     * wordList = ["hot","dot","dog","lot","log","cog"]
     * Output: 5 (hit -> hot -> dot -> dog -> cog)
     * 
     * TODO: Implement this method
     */
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> set = new HashSet<>(wordList);
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        int level = 1;
        while(!queue.isEmpty()){
            int size = queue.size();
            for(int i = 0; i < size; i++){
                String word = queue.poll();
                if(word.equals(endWord)){
                    return level;
                }
                for(int j = 0; j < word.length(); j++){
                    for(char c = 'a'; c <= 'z'; c++){
                        char[] wordArray = word.toCharArray();
                        wordArray[j] = c;
                        String newWord = new String(wordArray);
                    }
                }
            }
        }
        return 0;   
    }
    
    /**
     * Problem 12: Course Schedule
     * 
     * Given the total number of courses and a list of prerequisite pairs, determine if it's possible 
     * to finish all courses.
     * 
     * Example:
     * numCourses = 4, prerequisites = [[1,0],[2,0],[3,1],[3,2]]
     * Output: true (valid order: 0 -> 1 -> 3, 0 -> 2 -> 3)
     * 
     * TODO: Implement this method
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // TODO: Your implementation here
        return false;
    }
    
    // Helper class for weighted edges
    public static class Edge {
        int source, destination, weight;
        
        public Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }
} 