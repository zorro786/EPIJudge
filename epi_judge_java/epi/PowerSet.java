package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiTestComparator;
import epi.test_framework.LexicographicalListComparator;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;
public class PowerSet {
  @EpiTest(testDataFile = "power_set.tsv")

  public static List<List<Integer>> generatePowerSet(List<Integer> inputSet) {
    List<List<Integer>> result = new ArrayList<>();
    generatePowerSetHelper(inputSet, new ArrayList<>(), 0, result);
    return result;
  }

  private static void generatePowerSetHelper(List<Integer> input, List<Integer> current, int index, List<List<Integer>> result) {
    if (index == input.size()) {
      result.add(new ArrayList<>(current));
      return;
    }

    current.add(input.get(index));
    generatePowerSetHelper(input, current, index + 1, result);
    current.remove(current.size() - 1);
    generatePowerSetHelper(input, current, index + 1, result);

  }
  @EpiTestComparator
  public static BiPredicate<List<List<Integer>>, List<List<Integer>>> comp =
      (expected, result) -> {
    if (result == null) {
      return false;
    }
    for (List<Integer> l : expected) {
      Collections.sort(l);
    }
    expected.sort(new LexicographicalListComparator<>());
    for (List<Integer> l : result) {
      Collections.sort(l);
    }
    result.sort(new LexicographicalListComparator<>());
    return expected.equals(result);
  };

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "PowerSet.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
