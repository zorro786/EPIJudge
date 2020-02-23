package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class SortedArraysMerge {
  @EpiTest(testDataFile = "sorted_arrays_merge.tsv")

  public static List<Integer>
  mergeSortedArrays(List<List<Integer>> sortedArrays) {
    List<Integer> result = new ArrayList<>();
    PriorityQueue<List<Integer>> pq = new PriorityQueue<>(Comparator.comparing(integers -> sortedArrays.get(integers.get(0)).get(integers.get(1))));
    for (int i = 0; i < sortedArrays.size(); i++) {
      pq.add(Arrays.asList(i, 0));
    }
    while (!pq.isEmpty()) {
      List<Integer> top = pq.poll();
      result.add(sortedArrays.get(top.get(0)).get(top.get(1)));
      if (top.get(1) + 1 < sortedArrays.get(top.get(0)).size()) {
        top.set(1, top.get(1) + 1);
        pq.add(top);
      }
    }
    return result;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SortedArraysMerge.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
