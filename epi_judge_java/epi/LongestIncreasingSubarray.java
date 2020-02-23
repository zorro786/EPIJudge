package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import java.util.List;
public class LongestIncreasingSubarray {

  // Represent subarray by starting and ending indices, inclusive.
  private static class Subarray {
    public Integer start;
    public Integer end;

    public Subarray(Integer start, Integer end) {
      this.start = start;
      this.end = end;
    }
  }

  public static Subarray findLongestIncreasingSubarray(List<Integer> A) {
    if (A.size() == 1) {
      return new Subarray(0, 0);
    }
    int res = 0;
    Subarray subarray = new Subarray(0,0);
    int i = 0, j = i + 1;
    while (i < A.size()) {
      if (j < A.size() && A.get(j-1) < A.get(j)) {
        j++;
      } else {
        if (j-i > res) {
          subarray.start = i;
          subarray.end = j-1;
          res = j-i;
        }
        i = j;
        j = i + 1;
      }
    }
    return subarray;
  }
  @EpiTest(testDataFile = "longest_increasing_subarray.tsv")
  public static int findLongestIncreasingSubarrayWrapper(List<Integer> A) {
    Subarray result = findLongestIncreasingSubarray(A);
    return result.end - result.start + 1;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "LongestIncreasingSubarray.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
