package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import java.util.ArrayList;

import java.util.List;
public class SpiralOrderingSegments {
  @EpiTest(testDataFile = "spiral_ordering_segments.tsv")

  public static List<Integer>
  matrixInSpiralOrder(List<List<Integer>> squareMatrix) {
    int n = squareMatrix.size();
    List<Integer> result = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      for (int j = i; j < n-i-1; j++) {
        result.add(squareMatrix.get(i).get(j));
      }

      for(int k = i; k < n-i-1; k++) {
        result.add(squareMatrix.get(k).get(n-i-1));
      }

      for (int j = n-i-1; j > i; j--) {
        result.add(squareMatrix.get(n-i-1).get(j));
      }

      for (int k = n-i-1; k > i; k--) {
        result.add(squareMatrix.get(k).get(i));
      }
    }

    if (n%2 == 1) {
      result.add(squareMatrix.get(n/2).get(n/2));
    }
    return result;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SpiralOrderingSegments.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
