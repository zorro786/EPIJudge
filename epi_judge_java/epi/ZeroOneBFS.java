package epi;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class ZeroOneBFS {
    private static class Node {
        int to; // the ending vertex
        int weight; // the weight of the edge

        public Node(int to, int wt) {
            this.to = to;
            this.weight = wt;
        }
    }

    private static final int numVertex = 3;
    private ArrayList<Node>[] edges = new ArrayList[numVertex];

    public ZeroOneBFS() {
        for (int i = 0; i < edges.length; i++) {
            edges[i] = new ArrayList<Node>();
        }
    }

    public void addEdge(int u, int v, int wt) {
        edges[u].add(edges[u].size(), new Node(v, wt));
        edges[v].add(edges[v].size(), new Node(u, wt));
    }

    public void zeroOneBFS(int src) {

        // initialize distances from given source
        int[] dist = new int[numVertex];
        for (int i = 0; i < numVertex; i++) {
            dist[i] = Integer.MAX_VALUE;
        }

        // double ended queue to do BFS
        Deque<Integer> queue = new ArrayDeque<Integer>();
        dist[src] = 0;
        queue.addLast(src);

        while (!queue.isEmpty()) {
            int v = queue.removeFirst();
            for (int i = 0; i < edges[v].size(); i++) {

                // checking for optimal distance
                if (dist[edges[v].get(i).to] >
                        dist[v] + edges[v].get(i).weight) {

                    // update the distance
                    dist[edges[v].get(i).to] =
                            dist[v] + edges[v].get(i).weight;

                    // put 0 weight edges to front and 1
                    // weight edges to back so that vertices
                    // are processed in increasing order of weight
                    if (edges[v].get(i).weight == 0) {
                        queue.addFirst(edges[v].get(i).to);
                    } else {
                        queue.addLast(edges[v].get(i).to);
                    }
                }
            }
        }

        for (int i = 0; i < dist.length; i++) {
            System.out.print(dist[i] + " ");
        }
    }

    public static void main(String[] args) {
        ZeroOneBFS graph = new ZeroOneBFS();
        graph.addEdge(0, 1, 3);
        graph.addEdge(1, 2, 3);
        graph.addEdge(0, 2, 4);
        int src = 0;//source node
        graph.zeroOneBFS(src);
        return;
    }
}