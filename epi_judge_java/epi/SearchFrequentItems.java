package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiTestComparator;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
public class SearchFrequentItems {

  public static List<String> searchFrequentItems(int k,
                                                 Iterable<String> stream) {
    return null;
  }
  @EpiTest(testDataFile = "search_frequent_items.tsv")
  public static List<String> searchFrequentItemsWrapper(int k,
                                                        List<String> stream) {
    return searchFrequentItems(k, stream);
  }

  @EpiTestComparator
  public static BiPredicate<List<String>, List<String>> comp =
      (expected, result) -> {
    if (result == null) {
      return false;
    }
    Collections.sort(expected);
    Collections.sort(result);
    return expected.equals(result);
  };

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SearchFrequentItems.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
