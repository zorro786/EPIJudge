package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class NextPermutation {
  @EpiTest(testDataFile = "next_permutation.tsv")
  public static List<Integer> nextPermutation(List<Integer> perm) {
    int n = perm.size();
    if (n == 0) {
      return perm;
    }
    int i = n-2;
    while (i >= 0 && perm.get(i) >= perm.get(i+1)) {
      i--;
    }
    if (i < 0) {
      return Collections.emptyList();
    }
    int nextMax = Integer.MAX_VALUE;
    int indexOfNextMax = i;
    for (int j = i + 1; j < n; j++) {
      if (perm.get(j) > perm.get(i) && perm.get(j) < nextMax) {
        nextMax = perm.get(j);
        indexOfNextMax = j;
      }
    }
    // Can be done in O(n) time without sorting by observing that the decreasing order from i+1 to n of elements after swapping with next max, needs to be reversed
    List<Integer> result = new ArrayList<>(perm);
    Collections.swap(result, i, indexOfNextMax);
    Collections.sort(result.subList(i + 1, n));
    return result;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "NextPermutation.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
