package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public class LargestRectangleUnderSkyline {
  @EpiTest(testDataFile = "largest_rectangle_under_skyline.tsv")

  public static int calculateLargestRectangle(List<Integer> heights) {

    Deque<Integer> pillarIndices = new ArrayDeque<>();
    int maxRectangleArea = 0;
    // By iterating to heights.size() instead of heights.size() - 1, we can
    // uniformly handle the computation for rectangle area here.
    for (int i = 0; i <= heights.size(); ++i) {
      while (!pillarIndices.isEmpty() &&
             isNewPillarOrReachEnd(heights, i, pillarIndices.peekFirst())) {
        int height = heights.get(pillarIndices.removeFirst());
        int width =
            pillarIndices.isEmpty() ? i : i - pillarIndices.peekFirst() - 1;
        maxRectangleArea = Math.max(maxRectangleArea, height * width);
      }
      pillarIndices.addFirst(i);
    }
    return maxRectangleArea;
  }

  private static boolean isNewPillarOrReachEnd(List<Integer> heights,
                                               int currIdx, int lastPillarIdx) {
    return currIdx < heights.size()
        ? heights.get(currIdx) <= heights.get(lastPillarIdx)
        : true;
  }

  public static void main(String[] args) {
    List<Integer> test = Arrays.asList(1,3,2,4,5,6,5,3,1,2);
    System.out.println(calculateLargestRectangle(test));
//    System.exit(
//        GenericTest
//            .runFromAnnotations(args, "LargestRectangleUnderSkyline.java",
//                                new Object() {}.getClass().getEnclosingClass())
//            .ordinal());
  }
}
