package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class MaxProductAllButOne {
  @EpiTest(testDataFile = "max_product_all_but_one.tsv")
  public static int findBiggestProductNMinusOneProduct(List<Integer> a) {
    int numZeroes = 0;
    int negatives = 0;
    int minPositive = Integer.MAX_VALUE;
    int maxNegative = Integer.MIN_VALUE;

    int prod = 1;
    for (int i = 0; i < a.size(); i++) {
      if (a.get(i) == 0) {
        numZeroes++;
        if (numZeroes > 1) {
          return 0;
        }
      } else if (a.get(i) > 0) {
        minPositive = Math.min(minPositive, a.get(i));
      } else {
        negatives++;
        maxNegative = Math.max(a.get(i), maxNegative);
      }
    }
    if (negatives % 2 != 0 && numZeroes == 1) {
      return 0;
    }
    for (int i = 0; i < a.size(); i++) {
      if (negatives%2 == 0) {
        if (numZeroes == 1 && a.get(i) == 0 || numZeroes == 0 && a.get(i) == minPositive) {
          continue;
        } else {
          prod *= a.get(i);
        }
      } else {
        if (a.get(i) != maxNegative) {
          prod *= a.get(i);
        }
      }
    }
    return prod;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "MaxProductAllButOne.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
