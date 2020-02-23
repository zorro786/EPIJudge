package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
public class LongestContainedInterval {
  @EpiTest(testDataFile = "longest_contained_interval.tsv")

  public static int longestContainedRange(List<Integer> a) {
    int res = 0;
    HashMap<Integer, Integer> counts = new HashMap<>();
    for (int num : a) {
      counts.put(num, 1);
    }
    while (!counts.isEmpty()) {
      int i = counts.keySet().iterator().next();
      counts.remove(i);
      int lowerBound = i-1;
      while (counts.containsKey(lowerBound)) {
        counts.remove(lowerBound--);
      }
      int upperBound = i+1;
      while (counts.containsKey(upperBound)) {
        counts.remove(upperBound++);
      }
      res = Math.max(res, upperBound - lowerBound -1);
    }
    return res;
  }

  public static void main(String[] args) {

    System.exit(
        GenericTest
            .runFromAnnotations(args, "LongestContainedInterval.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
