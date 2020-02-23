package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;
import epi.test_framework.TimedExecutor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
public class SmallestSubarrayCoveringSet {

  // Represent subarray by starting and ending indices, inclusive.
  private static class Subarray {
    public Integer start;
    public Integer end;

    public Subarray(Integer start, Integer end) {
      this.start = start;
      this.end = end;
    }
  }

  public static Subarray findSmallestSubarrayCoveringSet(List<String> paragraph,
                                                         Set<String> keywords) {
    Map<String, Integer> map = new HashMap<>();
    int i = 0, j=0, start = -1, end = -1;
    int minLength = Integer.MAX_VALUE;
    while (j < paragraph.size()) {
      if (map.size() < keywords.size()) {
        if (keywords.contains(paragraph.get(j))) {
          map.merge(paragraph.get(j), 1, Integer::sum);
        }
        if (map.size() < keywords.size()) {
          j++;
        }
      } else {
        while (map.size() == keywords.size() && i < paragraph.size()) {
          if (minLength > j - i + 1) {
            minLength = j - i + 1;
            start = i;
            end = j;
          }
          if (map.containsKey(paragraph.get(i))) {
            Integer val = map.get(paragraph.get(i));
            if (val == 1) {
              map.remove(paragraph.get(i));
            } else {
              map.put(paragraph.get(i), val - 1);
            }
          }
          i++;
        }
        j++;
      }
    }
    return new Subarray(start, end);
  }
  @EpiTest(testDataFile = "smallest_subarray_covering_set.tsv")
  public static int findSmallestSubarrayCoveringSetWrapper(
      TimedExecutor executor, List<String> paragraph, Set<String> keywords)
      throws Exception {
    Set<String> copy = new HashSet<>(keywords);

    Subarray result = executor.run(
        () -> findSmallestSubarrayCoveringSet(paragraph, keywords));

    if (result.start < 0 || result.start >= paragraph.size() ||
        result.end < 0 || result.end >= paragraph.size() ||
        result.start > result.end)
      throw new TestFailure("Index out of range");

    for (int i = result.start; i <= result.end; i++) {
      copy.remove(paragraph.get(i));
    }

    if (!copy.isEmpty()) {
      throw new TestFailure("Not all keywords are in the range");
    }
    return result.end - result.start + 1;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SmallestSubarrayCoveringSet.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
