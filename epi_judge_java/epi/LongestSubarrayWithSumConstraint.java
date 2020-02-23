package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.max;

public class LongestSubarrayWithSumConstraint {
  @EpiTest(testDataFile = "longest_subarray_with_sum_constraint.tsv")

  public static int findLongestSubarrayLessEqualK(List<Integer> A, int k) {
    // Build the prefix sum according to A.
    List<Integer> prefixSum = new ArrayList<>();
    int sum = 0;
    for (int a : A) {
      sum += a;
      prefixSum.add(sum);
    }

    // Early returns if the sum of A is smaller than or equal to k.
    if (prefixSum.get(prefixSum.size() - 1) <= k) {
      return A.size();
    }

    // Builds minPrefixSum.
    List<Integer> minPrefixSum = new ArrayList<>(prefixSum);
    for (int i = minPrefixSum.size() - 2; i >= 0; --i) {
      minPrefixSum.set(i,
              Math.min(minPrefixSum.get(i), minPrefixSum.get(i + 1)));
    }

    int a = 0, b = 0, maxLength = 0;
    while (b < A.size()) {
      int minCurrSum = a > 0 ? minPrefixSum.get(b) - prefixSum.get(a - 1)
              : minPrefixSum.get(b);
      if (minCurrSum <= k) {
        int currLength = b - a + 1;
        if (currLength > maxLength) {
          maxLength = currLength;
        }
        ++b;
      } else { // minCurrSum > k.
        ++a;
      }
      if (a > b) {
        b++;
      }
    }
    return maxLength;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "LongestSubarrayWithSumConstraint.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
