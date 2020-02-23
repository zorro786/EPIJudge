package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class MaximumSubarrayInCircularArray {
  @EpiTest(testDataFile = "maximum_subarray_in_circular_array.tsv")

  public static int maxSubarraySumInCircular(List<Integer> a) {

    int linearMax = a.get(0);
    int currentMax = linearMax;
    for (int i = 1; i < a.size(); i++) {
      currentMax = Math.max(currentMax + a.get(i), a.get(i));
      linearMax = Math.max(linearMax, currentMax);
    }

    //O(n) space solution
//    int[] leftMax = new int[a.size()];
//    int[] rightMax = new int[a.size()];
//    int runningSum = 0;
//    currentMax = Integer.MIN_VALUE;
//    for (int i = 0; i < a.size(); i++) {
//      runningSum += a.get(i);
//      currentMax = Math.max(currentMax, runningSum);
//      leftMax[i] = currentMax;
//    }
//
//    runningSum = 0;
//    currentMax = Integer.MIN_VALUE;
//    for (int j = a.size() - 1; j >= 0; j--) {
//      runningSum += a.get(j);
//      currentMax = Math.max(currentMax, runningSum);
//      rightMax[j] = currentMax;
//    }
//
//    int i = 0, j = 1;
//    int circularMax = Integer.MIN_VALUE;
//    while (i < a.size()) {
//      circularMax = j < a.size() ? Math.max(circularMax, leftMax[i++] + rightMax[j++]) : Math.max(circularMax, leftMax[i++]);
//    }
//    return Math.max(0, Math.max(circularMax, linearMax)); // Judge expects 0 as min possible max value possibly due to empty subarray

    return Math.max(a.stream().reduce(Integer::sum).get() - minSum(a), linearMax);
  }

  private static int minSum(List<Integer> a) {
    int minSum = a.get(0);
    int curSum = minSum;
    for (int i = 1; i < a.size(); i++) {
      curSum = Math.min(curSum + a.get(i), a.get(i));
      minSum = Math.min(curSum, minSum);
    }
    return minSum;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "MaximumSubarrayInCircularArray.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
