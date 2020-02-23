package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.HashMap;
import java.util.List;
public class NearestRepeatedEntries {
  @EpiTest(testDataFile = "nearest_repeated_entries.tsv")

  public static int findNearestRepetition(List<String> paragraph) {
    HashMap<String, Integer> map = new HashMap<>();
    Integer minDistance = Integer.MAX_VALUE;
    for (int i = 0; i < paragraph.size(); i++) {
      if (map.containsKey(paragraph.get(i))) {
        minDistance = Math.min(minDistance, i - map.get(paragraph.get(i)));
      }
      map.put(paragraph.get(i), i);
    }
    if (minDistance == Integer.MAX_VALUE) {
      return -1;
    } else {
      return minDistance;
    }
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "NearestRepeatedEntries.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
