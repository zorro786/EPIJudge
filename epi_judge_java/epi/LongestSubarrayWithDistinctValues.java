package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.HashSet;
import java.util.List;
public class LongestSubarrayWithDistinctValues {
  @EpiTest(testDataFile = "longest_subarray_with_distinct_values.tsv")

  public static int longestSubarrayWithDistinctEntries(List<Integer> a) {
    int maxLength = 0;
    HashSet<Integer> set = new HashSet<>();
    int start = 0, end = 0;
    while (end < a.size()) {
      while (set.contains(a.get(end))) {
        set.remove(a.get(start++));
      }
      set.add(a.get(end++));
      maxLength = Math.max(maxLength, end-start);
    }
    return maxLength;
  }



  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "LongestSubarrayWithDistinctValues.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
