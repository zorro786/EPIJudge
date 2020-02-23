package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class KthLargestInArray {
  // The numbering starts from one, i.e., if A = [3,1,-1,2] then
  // findKthLargest(A, 1) returns 3, findKthLargest(A, 2) returns 2,
  // findKthLargest(A, 3) returns 1, and findKthLargest(A, 4) returns -1.
  @EpiTest(testDataFile = "kth_largest_in_array.tsv")
  public static int findKthLargest(int k, List<Integer> A) {
    Collections.shuffle(A);
    k = k > A.size() ? A.size() : k;
    return findKthLargestHelper(A.size() - k + 1, A, 0, A.size() - 1);
  }

  private static int findKthLargestHelper(int k, List<Integer> A, int lo, int hi) {
    if (lo == hi) {
      return A.get(lo);
    }
    int pivot = A.get(hi);
    int i = lo, j = lo;
    while (i <= hi) {
      if (A.get(i) <= pivot) {
        Collections.swap(A, i, j++);
      }
      i++;
    }
    int pivotIndex = j - 1;
    if (pivotIndex + 1 == k) {
      return A.get(pivotIndex);
    } else if (pivotIndex+1 < k) {
      return findKthLargestHelper(k, A, pivotIndex + 1, hi);
    } else {
      return findKthLargestHelper(k, A, lo, pivotIndex-1);
    }
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "KthLargestInArray.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
