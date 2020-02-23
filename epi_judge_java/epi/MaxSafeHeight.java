package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class MaxSafeHeight {
  @EpiTest(testDataFile = "max_safe_height.tsv")

  public static int getHeight(int cases, int drops) {
    int[][] dp = new int[cases+1][drops+1];
    for (int i = 0; i <= cases; i++) {
      for (int j = 0; j <= drops; j++) {
        if (i == 0 || j == 0) {
          dp[i][j] = 0;
        } else if (i == 1) {
          dp[i][j] = j;
        } else if (j == 1) {
          dp[i][j] = 1;
        } else {
          dp[i][j] = dp[i][j-1] + dp[i-1][j-1] + 1;
        }
      }
    }
    return dp[cases][drops];
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "MaxSafeHeight.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
