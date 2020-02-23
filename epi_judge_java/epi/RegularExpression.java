package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.Arrays;

public class RegularExpression {

  private static boolean isMatchHelper(String regex, String s) {
    boolean isExactStart = !regex.isEmpty() && regex.charAt(0) == '^';
    boolean isExactEnd = !regex.isEmpty() && regex.charAt(regex.length()-1) == '$';

    int m = regex.length();
    int n = s.length();
//    if (isExactStart) {
//      regex = regex.substring(1);
//      m--;
//    }
//    if (isExactEnd) {
//      regex = regex.substring(0, regex.length() - 1);
//      m--;
//    }

    boolean[][] dp = new boolean[m + 1][n + 1];
    dp[0][0] = true;

    for (int i = 1; i <= m; i++) {
      if (regex.charAt(i-1) == '*') {
        dp[i][0] = dp[i-2][0];
      }
    }

    for (int i = 1; i <= m; i++) {
      for (int j = 1; j <= n; j++) {
        if (regex.charAt(i-1) == s.charAt(j-1) || regex.charAt(i-1) == '.') {
          dp[i][j] = dp[i-1][j-1];
//          if (i-1 == 0 && regex.charAt(i-1) == s.charAt(j-1)) {
//            dp[i][j] = true;
//          }
        } else if (regex.charAt(i-1) == '*') {
          if (regex.charAt(i-2) == '.') { // .*
            dp[i][j] = dp[i-1][j] || dp[i][j-1] || dp[i-1][j-1];
          } else if (regex.charAt(i-2) == s.charAt(j-1)) {
            dp[i][j] = dp[i][j-1] || dp[i-1][j-1];
          } else {
            dp[i][j] = dp[i-2][j];
          }
        }
      }
    }
//    System.out.println();
//    for (int i = 0; i <= m; i++) {
//      System.out.println(Arrays.toString(dp[i]));
//    }
//    if (isExactStart && isExactEnd) {
//      return dp[m][n];
//    } else if (!isExactStart && !isExactEnd || isExactStart) {
//      for (int j = 1; j <= n; j++) {
//        if (dp[m][j]) {
//          return true;
//        }
//      }
//    } else {
//      if (regex.charAt(m-1) == '*') {
//        if (regex.charAt(m-2) == '.')
//          return dp[m][n];
//        else {
//          return dp[m][n] || dp[m][n-1];
//        }
//      } else if (regex.charAt(m-1) == '.' || regex.charAt(m-1) == s.charAt(n-1)){
//        return dp[m][n];
//      } else {
//        return false;
//      }
//    }
    return dp[m][n];
  }

  @EpiTest(testDataFile = "regular_expression.tsv")
  public static boolean isMatch(String regex, String s) {
    if (regex.startsWith("^") && regex.endsWith("$")) {
      return isMatchHelper(regex.substring(1, regex.length()-1), s);
    }
    if (regex.startsWith("^")) {
      for (int i = 1; i <= s.length(); i++) {
        if (isMatchHelper(regex.substring(1), s.substring(0, i))) {
          return true;
        }
      }
    }
    if (!regex.startsWith("^") && !regex.endsWith("$")) {
      for (int i = 0; i < s.length(); i++) {
        for (int j = i + 1; j <= s.length(); j++) {
          if (isMatchHelper(regex, s.substring(i, j))) {
            return true;
          }
        }
      }
    }
    if (regex.endsWith("$")) {
      for (int i = 0; i < s.length(); i++) {
        if (isMatchHelper(regex.substring(0, regex.length() - 1), s.substring(i))) {
          return true;
        }
      }
    }
    return false;
  }

  public static void main(String[] args) {
    //System.out.println(isMatch("^a*a*a*a*a*a*a*a*a*a*a*a*b$", "aaaaaaaaaaaaab"));
    System.exit(
        GenericTest
            .runFromAnnotations(args, "RegularExpression.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
