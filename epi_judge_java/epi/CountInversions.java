package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class CountInversions {
  @EpiTest(testDataFile = "count_inversions.tsv")

  public static int countInversions(List<Integer> A) {
    return countInversionsHelper(A, 0, A.size()-1);
  }

  private static int countInversionsHelper(List<Integer> a, int lo, int hi) {
    if (lo >= hi) {
      return 0;
    }
    int mid = lo + (hi-lo)/2;
    return countInversionsHelper(a, lo, mid) + countInversionsHelper(a, mid+1, hi) + merge(a, lo, hi);
  }

  private static int merge(List<Integer> a, int lo, int hi) {
    int mid = lo + (hi-lo)/2;
    List<Integer> sorted = new ArrayList<>();
    int cnt = 0;
    int i = lo, j = mid+1;
    while (i <= mid && j <= hi) {
      if (a.get(i) <= a.get(j)) {
        sorted.add(a.get(i++));
      } else {
        cnt += mid - i + 1;
        sorted.add(a.get(j++));
      }
    }
    while (i <= mid) {
      sorted.add(a.get(i++));
    }
    while (j <= hi) {
      sorted.add(a.get(j++));
    }
    i = 0;
    for (int k = lo; k <= hi; k++) {
      a.set(k, sorted.get(i++));
    }
    return cnt;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "CountInversions.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
