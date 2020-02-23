package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class IntSquareRoot {
  @EpiTest(testDataFile = "int_square_root.tsv")

  public static int squareRoot(int k) {
    if (k==0){
      return 0;
    }
    int lo = 1, hi = k;
    while (lo < hi) {
      int mid = lo + (hi-lo+1)/2;
      if (Integer.MAX_VALUE/mid < mid ||  mid*mid > k) {
        hi = mid - 1;
      } else {
        lo = mid;
      }
    }
    return lo;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IntSquareRoot.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
