package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import java.util.List;
public class SearchUnknownLengthArray {

  @EpiTest(testDataFile = "search_unknown_length_array.tsv")
  public static int binarySearchUnknownLength(List<Integer> a, int k) {
    int lo = 0, hi = 2;
    while (lo < hi) {
      int mid = lo + (hi-lo)/2;
      try {
        a.get(hi);
      } catch (IndexOutOfBoundsException e) {
        hi = mid;
        continue;
      }
      if (a.get(hi) < k) {
        lo = hi + 1;
        hi = 2*hi;
        continue;
      }
      if (a.get(mid) >= k) {
        hi = mid;
      } else {
        lo = mid + 1;
      }
    }
    try {
      a.get(lo);
    } catch (IndexOutOfBoundsException e) {
      return -1;
    }
    if (a.get(lo) != k) {
      return -1;
    }
    return lo;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SearchUnknownLengthArray.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
