package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiTestComparator;
import epi.test_framework.LexicographicalListComparator;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Permutations {
  @EpiTest(testDataFile = "permutations.tsv")

  public static List<List<Integer>> permutations(List<Integer> A) {
    List<List<Integer>> result = new ArrayList<>();
    int[] indices = IntStream.range(0, A.size()).toArray();
    permutationsHelper(A, 0, result);
    return result;
  }

  private static void permutationsHelper(List<Integer> l, int index, List<List<Integer>> result) {
    if (index == l.size()) {
      result.add(new ArrayList<>(l));
      return;
    }

    for (int i = index; i < l.size() ; i++) {
        Collections.swap(l, i, index);
        permutationsHelper(l, index+1, result);
        Collections.swap(l, i, index);
    }
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
            .runFromAnnotations(args, "Permutations.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
