package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiTestComparator;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;
public class KLargestValuesInBst {
  @EpiTest(testDataFile = "k_largest_values_in_bst.tsv")

  public static List<Integer> findKLargestInBst(BstNode<Integer> tree, int k) {
    List<Integer> res = new ArrayList<>();
    findKLargestInBstHelper(tree, k, res);
    return res;
  }

  private static void findKLargestInBstHelper(BstNode<Integer> root, int k, List<Integer> res) {
    if (root == null) {
      return;
    }
    findKLargestInBstHelper(root.right, k, res);
    if (res.size() < k) {
      res.add(root.data);
    }
    if (res.size() == k) {
      return;
    }
    findKLargestInBstHelper(root.left, k, res);
  }
  @EpiTestComparator
  public static BiPredicate<List<Integer>, List<Integer>> comp =
      (expected, result) -> {
    if (result == null) {
      return false;
    }
    Collections.sort(expected);
    Collections.sort(result);
    return expected.equals(result);
  };

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "KLargestValuesInBst.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
