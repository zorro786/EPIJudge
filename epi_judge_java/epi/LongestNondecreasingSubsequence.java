package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static java.lang.Math.max;

public class LongestNondecreasingSubsequence {
  private static Integer res;

  @EpiTest(testDataFile = "longest_nondecreasing_subsequence.tsv")
  public static int longestNondecreasingSubsequenceLength(List<Integer> A) {
    //return longestSubSequenceHelperIterative(A);
    res = Integer.MIN_VALUE;
    HashSet<String> memo = new HashSet<>();
    longestSubSequenceHelperRecursive(A, 0, 0, -1, memo);
    return res;
  }

  // The key insight here is that the L[i] is defined slightly differently where we also include the ith entry
  private static int longestSubSequenceHelperIterative(List<Integer> A) {
    int[] dp = new int[A.size()];
    for (int i = 0; i < A.size(); i++) {
      for (int j = 0; j < i; j++) {
        if (A.get(j) <= A.get(i)) {
          dp[i] = max(dp[i], dp[j] + 1);
        }
      }
      dp[i] = max(dp[i], 1);
    }
    return Arrays.stream(dp).max().getAsInt();
  }

  private static void longestSubSequenceHelperRecursive(List<Integer> A, int i, int currentLength, int lastInCurrent, HashSet<String> memo) {
    if (i >= A.size()) {
      res = max(res, currentLength);
      return;
    }
    String key = i + "_" + currentLength + "_" + lastInCurrent;
    if (memo.contains(key)) {
      return;
    }
    if (currentLength >= 1 && A.get(lastInCurrent) <= A.get(i)) {
      longestSubSequenceHelperRecursive(A, i + 1, currentLength + 1, i, memo);
    }
    longestSubSequenceHelperRecursive(A, i+1, 1, i, memo);
    longestSubSequenceHelperRecursive(A, i+1, currentLength, lastInCurrent, memo);
    memo.add(key);
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "LongestNondecreasingSubsequence.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
