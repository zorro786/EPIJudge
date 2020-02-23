package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class RealSquareRoot {
  @EpiTest(testDataFile = "real_square_root.tsv")

  public static double squareRoot(double x) {

    double lo = 0;
    double hi = x;
    if (x < 1) {
      lo = x;
      hi = 1;
    }
    for (int i = 0; i < 100; i++) {
      double mid = lo + (hi-lo)/2;
      if (Double.MAX_VALUE/mid < mid || mid*mid > x) {
        hi = mid;
      } else {
        lo = mid;
      }
    }
    return lo;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "RealSquareRoot.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
