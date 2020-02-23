package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public class LongestSubstringWithMatchingParentheses {
  @EpiTest(testDataFile = "longest_substring_with_matching_parentheses.tsv")

  public static int longestMatchingParentheses(String s) {
    return dp(s);
  }

  private static int dp(String s) {
    if (s.length() == 0) {
      return 0;
    }
    int[] dp = new int[s.length()];
    dp[0] = 0;
    int max = 0;
    for (int i = 1; i < s.length(); i++) {
      if (s.charAt(i) == ')') {
        if (s.charAt(i-1) == '(') {
          dp[i] = i-2 >= 0? dp[i-2] + 2 : 2;
        } else if (i-dp[i-1]-1 >= 0 && s.charAt(i-dp[i-1]-1) == '(') { // we don't need to consider s.charAt(i-dp[i-1]-1) == ')' because we only solve one level of recursion, solving that will entail solving two sub probs
          if (i-dp[i-1]-2 >= 0) {
            dp[i] = dp[i - dp[i - 1] - 2] + dp[i - 1] + 2;
          } else {
            dp[i] = dp[i - 1] + 2;
          }
        }
      }
      max = Math.max(max, dp[i]);
    }
    return max;
  }

  private static int linearScan(String s) {
    int i = 0;
    int open = 0, closed = 0;
    int max = 0;
    while (i < s.length()) {
      if (s.charAt(i++) == '(') {
        open++;
      } else {
        closed++;
      }
      if (open == closed) {
        max = Math.max(2*open, max);
      } else if (closed > open) {
        open = 0;
        closed = 0;
      }
    }
    open = 0;
    closed = 0;
    i = s.length()-1;
    while (i >= 0) {
      if (s.charAt(i--) == '(') {
        open++;
      } else {
        closed++;
      }
      if (open == closed) {
        max = Math.max(2*open, max);
      } else if (open > closed) {
        open = 0;
        closed = 0;
      }
    }
    return max;
  }


  public static void main(String[] args) {
//    System.out.println(longestMatchingParentheses("()()"));
    System.exit(GenericTest
                    .runFromAnnotations(
                        args, "LongestSubstringWithMatchingParentheses.java",
                        new Object() {}.getClass().getEnclosingClass())
                    .ordinal());
  }
}
