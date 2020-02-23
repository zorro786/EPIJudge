package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import java.util.List;
public class SearchShiftedSortedArray {
  @EpiTest(testDataFile = "search_shifted_sorted_array.tsv")

  public static int searchSmallest(List<Integer> A) {
    return searchSmallestHelperIterative(A);
  }

  private static int searchSmallestHelper(List<Integer> A, int lo, int hi) {
    if (lo >= hi) {
      return lo;
    }
    int mid = lo + (hi-lo+1)/2;
    if (A.get(mid) < A.get(mid-1)) {
      return mid;
    }
    if (A.get(lo) > A.get(mid)) {
      return searchSmallestHelper(A, lo, mid-1);
    } else if (A.get(mid) > A.get(hi)) {
      return searchSmallestHelper(A, mid + 1, hi);
    } else {
      return lo;
    }
  }

  private static int searchSmallestHelperIterative(List<Integer> A) {
    int lo = 0, hi = A.size() - 1;
    while (lo < hi) {
      int mid = lo + (hi-lo+1)/2;
      if (A.get(mid) < A.get(mid-1)) {
        return mid;
      } else if (A.get(mid) < A.get(lo)) {
        hi = mid - 1;
      } else if (A.get(mid) > A.get(hi)) {
        lo = mid + 1;
      } else {
        return lo;
      }
    }
    return lo;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SearchShiftedSortedArray.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
