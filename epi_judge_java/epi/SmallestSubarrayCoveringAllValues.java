package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;
import epi.test_framework.TimedExecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SmallestSubarrayCoveringAllValues {

  public static class Subarray {
    // Represent subarray by starting and ending indices, inclusive.
    public Integer start;
    public Integer end;

    public Subarray(Integer start, Integer end) {
      this.start = start;
      this.end = end;
    }
  }

// First we create a map containing indices of keywords present in paragraph at various places
  // For each start index, we find non decreasing index of next keyword, removing those that are out of consideration
  public static Subarray
  findSmallestSequentiallyCoveringSubset(List<String> paragraph,
                                         List<String> keywords) {
    int start, end;
    Map<String, List<Integer>> map = new HashMap<>();
    Set<String> set = new HashSet<>(keywords);
    for (int i = 0; i < paragraph.size(); i++) {
      if (set.contains(paragraph.get(i))) {
        map.putIfAbsent(paragraph.get(i), new LinkedList<>());
        map.get(paragraph.get(i)).add(i);
      }
    }
    int minLength = Integer.MAX_VALUE;
    Iterator<Integer> startIt = map.get(keywords.get(0)).iterator();
    int minStart = -1, minEnd = -1;
    while (startIt.hasNext()) {
      start = startIt.next();
      int prev = start;
      for (int j = 1; j < keywords.size(); j++) {
        while (!map.get(keywords.get(j)).isEmpty() && map.get(keywords.get(j)).iterator().next() < prev) {
          map.get(keywords.get(j)).remove(0);
        }
        if (map.get(keywords.get(j)).isEmpty()) {
          return new Subarray(minStart, minEnd);
        }
        prev = map.get(keywords.get(j)).get(0);
      }
      end = prev;
      if (minLength > end-start+1) {
        minStart = start;
        minEnd = end;
        minLength = end-start+1;
      }
    }
    return new Subarray(minStart, minEnd);
  }
  @EpiTest(testDataFile = "smallest_subarray_covering_all_values.tsv")
  public static int findSmallestSequentiallyCoveringSubsetWrapper(
      TimedExecutor executor, List<String> paragraph, List<String> keywords)
      throws Exception {
    Subarray result = executor.run(
        () -> findSmallestSequentiallyCoveringSubset(paragraph, keywords));

    int kwIdx = 0;
    if (result.start < 0) {
      throw new TestFailure("Subarray start index is negative");
    }
    int paraIdx = result.start;

    while (kwIdx < keywords.size()) {
      if (paraIdx >= paragraph.size()) {
        throw new TestFailure("Not all keywords are in the generated subarray");
      }
      if (paraIdx >= paragraph.size()) {
        throw new TestFailure("Subarray end index exceeds array size");
      }
      if (paragraph.get(paraIdx).equals(keywords.get(kwIdx))) {
        kwIdx++;
      }
      paraIdx++;
    }
    return result.end - result.start + 1;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SmallestSubarrayCoveringAllValues.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
