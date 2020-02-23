package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import java.util.List;
public class SearchFirstKey {
  @EpiTest(testDataFile = "search_first_key.tsv")


  // F F F F T T, need to find first T
  public static int searchFirstOfK(List<Integer> A, int k) {

    int lo = 0, hi = A.size() - 1;
    while (lo < hi) {
      int mid = lo + (hi - lo)/2;
      if (A.get(mid) >= k) {
        hi = mid;
      } else {
        lo = mid + 1;
      }
    }
    if (A.size() > 0 && A.get(lo) == k) {
      return lo;
    }
    return -1;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SearchFirstKey.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
