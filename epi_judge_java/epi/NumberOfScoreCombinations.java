package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.Collections;
import java.util.List;
public class NumberOfScoreCombinations {
  @EpiTest(testDataFile = "number_of_score_combinations.tsv")

  public static int
  numCombinationsForFinalScore(int finalScore,
                               List<Integer> individualPlayScores) {

    int[][] dp = new int[individualPlayScores.size()][finalScore + 1];
    // The recurrence is defined as dp[i][j] which means number of combinations to form score of j using plays from 0 to i inclusive
    // dp[i][j] = dp[i-1][j] + dp[i][j-vj] i.e. ways without using play i and ways using play i
    // dp[i][0] = 1

    for (int i = 0; i < dp.length; i++) {
      dp[i][0] = 1;
    }
    for (int j = 0; j < dp[0].length; j++) {
      if (j%individualPlayScores.get(0) == 0) {
        dp[0][j] = 1;
      }
    }
    for (int i = 1; i < dp.length; i++) {
      for (int j = 1; j < dp[i].length; j++) {
        dp[i][j] += dp[i-1][j];
        if (j - individualPlayScores.get(i) >= 0) {
          dp[i][j] += dp[i][j - individualPlayScores.get(i)];
        }
      }
    }
    return dp[individualPlayScores.size()-1][finalScore];

//    int[] dp = new int[finalScore + 1];
//    dp[0] = 1;
//
//    for (Integer n : individualPlayScores) {
//      for (int i = 1; i < dp.length; i++) {
//        if (i - n >= 0) {
//          dp[i] += dp[i-n];
//        }
//      }
//    }

//    return dp[finalScore];
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "NumberOfScoreCombinations.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
