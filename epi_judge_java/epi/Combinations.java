package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiTestComparator;
import epi.test_framework.LexicographicalListComparator;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.IntStream;

public class Combinations {
  @EpiTest(testDataFile = "combinations.tsv")

  public static List<List<Integer>> combinations(int n, int k) {
    List<List<Integer>> result = new ArrayList<>();
    int[] input = IntStream.rangeClosed(1, n).toArray();
    combinationHelper(input, new ArrayList<>(), 0, k, result);
    return result;
  }


  private static void combinationHelper(int[] input, List<Integer> current, int index, int k, List<List<Integer>> result) {
    if (current.size() == k) {
      result.add(new ArrayList<>(current));
      return;
    }
    if (index == input.length) {
      return;
    }

    current.add(input[index]);
    combinationHelper(input, current, index + 1, k, result);
    current.remove(current.size() - 1);
    combinationHelper(input, current, index + 1, k, result);

  }
  @EpiTestComparator
  public static BiPredicate<List<List<Integer>>, List<List<Integer>>> comp =
      (expected, result) -> {
    if (result == null) {
      return false;
    }
    expected.sort(new LexicographicalListComparator<>());
    result.sort(new LexicographicalListComparator<>());
    return expected.equals(result);
  };

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "Combinations.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
