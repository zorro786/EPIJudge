package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
public class StringTransformability {

  private static class GraphVertex {
    List<GraphVertex> edges;
    String word;
    Integer id;
    Integer depth = 0;

    GraphVertex(String word, int id) {
      this.word = word;
      this.id = id;
      edges = new ArrayList<>();
    }
  }

  private static class Node {
    String word;
    Integer depth = 0;
    Node(String w, Integer d) {
      word = w;
      depth = d;
    }
  }
  // This is O(D^2) in the worst case but can be optimized to O(n)
  @EpiTest(testDataFile = "string_transformability.tsv")
  public static int transformString(Set<String> dict, String s, String t) {
    Queue<Node> q = new ArrayDeque<>();
    q.add(new Node(s, 0));
    dict.remove(s);
    while (!q.isEmpty()) {
      Node front = q.poll();
      for (int i = 0; i < front.word.length(); i++) {
        String start = i == 0 ? "" : front.word.substring(0, i);
        String end = i + 1 == front.word.length() ? "" : front.word.substring(i + 1);
        for (int j = 0; j < 26 && i < front.word.length(); j++) {
          String newTransform = start + (char)('a' + j) + end;
          if (newTransform.equals(t)) {
            return front.depth + 1;
          }
          if (dict.remove(newTransform)) {
            q.add(new Node(newTransform, front.depth + 1));
          }
        }
      }
    }
    return -1;
  }

//
//  // Uses BFS to find the least steps of transformation.
//  // Takes O(D^2n) time to run and takes the worst case possible vs text book solution where optimisation is possible based on values of n vs d
//  public static int transformString(Set<String> dict, String s, String t) {
//    dict.add(s);
//    dict.add(t);
//    GraphVertex[] graph = new GraphVertex[dict.size()];
//    GraphVertex source = null, dest = null;
//    String[] words = dict.toArray(new String[0]);
//    for (int i = 0; i < words.length; i++) {
//      if (graph[i] == null) {
//        graph[i] = new GraphVertex(words[i], i);
//      }
//      if (graph[i].word.equals(s)) {
//        source = graph[i];
//      }
//      if (graph[i].word.equals(t)) {
//        dest = graph[i];
//      }
//      for (int j = i + 1; j < words.length; j++) {
//        if (graph[j] == null) {
//          graph[j] = new GraphVertex(words[j], j);
//        }
//        if (isValidSequence(words[i], words[j])) {
//          graph[i].edges.add(graph[j]);
//          graph[j].edges.add(graph[i]);
//        }
//      }
//    }
//    boolean[] visited = new boolean[graph.length];
//    visited[source.id] = true;
//    Queue<GraphVertex> q = new ArrayDeque<>();
//    q.add(source);
//    while (!q.isEmpty()) {
//      GraphVertex front = q.poll();
//      for (GraphVertex neighbor : front.edges) {
//        if (!visited[neighbor.id]) {
//          visited[neighbor.id] = true;
//          neighbor.depth = front.depth + 1;
//          if (neighbor == dest) {
//            return neighbor.depth;
//          }
//          q.add(neighbor);
//        }
//      }
//    }
//    return -1;
//  }
//
//  private static boolean isValidSequence(String s, String t) {
//    boolean singleUnequal = false;
//    for (int i = 0; i < s.length(); i++) {
//      if (s.charAt(i) != t.charAt(i)) {
//        if (!singleUnequal) {
//          singleUnequal = true;
//        } else {
//          return false;
//        }
//      }
//    }
//    return singleUnequal;
//  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "StringTransformability.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
