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
    Deque<List<Integer>> stack = new ArrayDeque<>(); // [pos, height]
    int max = 0;
    int right = 0;
    for (int i = 0; i < heights.size(); i++) {
      if (stack.isEmpty() || stack.peek().get(1) < heights.get(i)) {
        max = Math.max(max, heights.get(i));
        stack.push(Arrays.asList(i, heights.get(i)));
      } else {
        List<Integer> last = null;
        if (!stack.isEmpty()) {
          right = stack.peek().get(0);
        }
        while (!stack.isEmpty() && stack.peek().get(1) >= heights.get(i)) {
          last = stack.pop();
          max = Math.max(max, last.get(1)*(right-last.get(0)+1));
        }
        max = Math.max(max, (i-last.get(0)+1)*heights.get(i));
        stack.push(Arrays.asList(last.get(0), heights.get(i)));
        stack.push(Arrays.asList(i, heights.get(i)));
      }
    }
    if (!stack.isEmpty()) {
      right = stack.peek().get(0);
    }
    while (!stack.isEmpty()) {
      List<Integer> top = stack.pop();
      max = Math.max(max, top.get(1)*(right-top.get(0)+1));
    }
    return max;
  }

  public static void main(String[] args) {

//    List<Integer> test = Arrays.asList(4,5,6,5);
//    System.out.println(calculateLargestRectangle(test));
    System.exit(
        GenericTest
            .runFromAnnotations(args, "LargestRectangleUnderSkyline.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
