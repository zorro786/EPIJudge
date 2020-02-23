package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;
import epi.test_framework.TimedExecutor;
import java.util.List;
public class SearchEntryEqualToIndex {

  public static int searchEntryEqualToItsIndex(List<Integer> A) {
    int lo = 0, hi = A.size() - 1;
    while (lo < hi) {
      int mid = lo + (hi-lo)/2;
      if (mid <= A.get(mid)) {
        hi = mid;
      } else {
        lo = mid + 1;
      }
    }
    if (A.size() > 0 && A.get(lo).equals(lo)) {
      return lo;
    }
    return -1;
  }
  @EpiTest(testDataFile = "search_entry_equal_to_index.tsv")
  public static void searchEntryEqualToItsIndexWrapper(TimedExecutor executor,
                                                       List<Integer> A)
      throws Exception {
    int result = executor.run(() -> searchEntryEqualToItsIndex(A));

    if (result != -1) {
      if (A.get(result) != result) {
        throw new TestFailure("Entry does not equal to its index");
      }
    } else {
      for (int i = 0; i < A.size(); ++i) {
        if (A.get(i) == i) {
          throw new TestFailure("There are entries which equal to its index");
        }
      }
    }
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SearchEntryEqualToIndex.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
