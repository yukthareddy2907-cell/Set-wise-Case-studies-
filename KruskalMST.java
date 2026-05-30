import java.util.*;

class Edge implements Comparable<Edge> {
    int source;
    int destination;
    int weight;

    public Edge(int source, int destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge other) {
        return this.weight - other.weight;
    }
}

class UnionFind {
    private int[] parent;
    private int[] rank;

    public UnionFind(int vertices) {
        parent = new int[vertices];
        rank = new int[vertices];

        for (int i = 0; i < vertices; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    // Path Compression
    public int find(int node) {
        if (parent[node] != node) {
            parent[node] = find(parent[node]);
        }
        return parent[node];
    }

    // Union By Rank
    public boolean union(int u, int v) {
        int rootU = find(u);
        int rootV = find(v);

        if (rootU == rootV) {
            return false;
        }

        if (rank[rootU] < rank[rootV]) {
            parent[rootU] = rootV;
        } else if (rank[rootU] > rank[rootV]) {
            parent[rootV] = rootU;
        } else {
            parent[rootV] = rootU;
            rank[rootU]++;
        }

        return true;
    }
}

public class KruskalMST {

    public static void kruskalMST(int vertices, List<Edge> edges) {

        Collections.sort(edges);

        UnionFind uf = new UnionFind(vertices);

        List<Edge> mst = new ArrayList<>();

        int totalCost = 0;

        for (Edge edge : edges) {

            if (uf.union(edge.source, edge.destination)) {

                mst.add(edge);
                totalCost += edge.weight;

                if (mst.size() == vertices - 1) {
                    break;
                }
            }
        }

        System.out.println("Minimum Spanning Tree Edges:");
        System.out.println("--------------------------------");

        for (Edge edge : mst) {
            System.out.println(
                    edge.source + " --> " +
                    edge.destination +
                    "  Cost = " + edge.weight);
        }

        System.out.println("--------------------------------");
        System.out.println("Total MST Cost = " + totalCost);
    }

    public static void main(String[] args) {

        int vertices = 7;

        List<Edge> edges = new ArrayList<>();

        // Example Metro Network

        edges.add(new Edge(0, 1, 5));
        edges.add(new Edge(0, 2, 8));
        edges.add(new Edge(1, 2, 6));
        edges.add(new Edge(1, 3, 7));
        edges.add(new Edge(2, 3, 4));
        edges.add(new Edge(2, 4, 9));
        edges.add(new Edge(3, 5, 10));
        edges.add(new Edge(4, 5, 11));
        edges.add(new Edge(4, 6, 12));
        edges.add(new Edge(5, 6, 8));
        edges.add(new Edge(0, 6, 14));
        edges.add(new Edge(3, 6, 9));

        kruskalMST(vertices, edges);
    }
}