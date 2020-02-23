package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class LevenshteinDistance {
  @EpiTest(testDataFile = "levenshtein_distance.tsv")

  public static int levenshteinDistance(String A, String B) {
    if (A == null) {
      return B.length();
    } else if (B == null) {
      return A.length();
    }
    int[][] dp = new int[A.length()+1][B.length()+1];
    for (int i = 0; i <= A.length(); i++) {
      dp[i][0] = i;
    }
    for (int j = 0; j <= B.length(); j++) {
      dp[0][j] = j;
    }
    for (int i = 1; i <= A.length(); i++) {
      for (int j = 1; j <= B.length(); j++) {
        if (A.charAt(i-1) != B.charAt(j-1)) {
          dp[i][j] = Math.min(dp[i-1][j-1] + 1, dp[i-1][j] + 1);
          dp[i][j] = Math.min(dp[i][j], dp[i][j-1] + 1);
        } else {
          dp[i][j] = dp[i-1][j-1];
        }
      }
    }
    return dp[A.length()][B.length()];
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "LevenshteinDistance.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
