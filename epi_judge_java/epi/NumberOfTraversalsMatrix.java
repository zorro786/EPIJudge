package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class NumberOfTraversalsMatrix {
  @EpiTest(testDataFile = "number_of_traversals_matrix.tsv")

  public static int numberOfWays(int n, int m) {
    int[][] dp = new int[n][m];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        if (i == 0 || j == 0) {
          dp[i][j] = 1;
        } else {
          dp[i][j] = dp[i-1][j] + dp[i][j-1];
        }
      }
    }
    return dp[n-1][m-1];
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "NumberOfTraversalsMatrix.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
