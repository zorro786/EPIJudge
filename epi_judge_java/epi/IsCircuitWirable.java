package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;
import epi.test_framework.TimedExecutor;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class IsCircuitWirable {

  public static class GraphVertex {
    public int d = -1;
    Color color;
    public List<GraphVertex> edges = new ArrayList<>();
  }

  private enum Color {
    RED, BLUE,
  }

  public static boolean isAnyPlacementFeasible(List<GraphVertex> graph) {
    for (int i = 0; i < graph.size(); i++) {
      if (graph.get(i).color != null) {
        continue;
      }
      Queue<GraphVertex> queue = new ArrayDeque<>();
      queue.add(graph.get(i));
      graph.get(i).color = Color.RED;
      graph.get(i).d = 1;
      int currentDepth = 1;
      Color currentColor = Color.BLUE;
      while (!queue.isEmpty()) {
        GraphVertex front = queue.poll();
        if (front.d != currentDepth) {
          currentColor = currentColor == Color.BLUE ? Color.RED : Color.BLUE;
        }
        currentDepth = front.d;
        for (GraphVertex neighbor : front.edges) {
          if (neighbor.color == null) {
            neighbor.color = currentColor;
            neighbor.d = currentDepth + 1;
            queue.add(neighbor);
          } else if (neighbor.color == front.color) {
            return false;
          }
        }

      }
    }
    return true;
  }

  @EpiUserType(ctorParams = {int.class, int.class})
  public static class Edge {
    public int from;
    public int to;

    public Edge(int from, int to) {
      this.from = from;
      this.to = to;
    }
  }

  @EpiTest(testDataFile = "is_circuit_wirable.tsv")
  public static boolean isAnyPlacementFeasibleWrapper(TimedExecutor executor,
                                                      int k, List<Edge> edges)
      throws Exception {
    if (k <= 0)
      throw new RuntimeException("Invalid k value");
    List<GraphVertex> graph = new ArrayList<>();
    for (int i = 0; i < k; i++)
      graph.add(new GraphVertex());
    for (Edge e : edges) {
      if (e.from < 0 || e.from >= k || e.to < 0 || e.to >= k)
        throw new RuntimeException("Invalid vertex index");
      graph.get(e.from).edges.add(graph.get(e.to));
    }

    return executor.run(() -> isAnyPlacementFeasible(graph));
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IsCircuitWirable.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
