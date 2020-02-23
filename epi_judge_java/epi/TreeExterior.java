package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;
import epi.test_framework.TimedExecutor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class TreeExterior {

  public static List<BinaryTreeNode<Integer>>
  exteriorBinaryTree(BinaryTreeNode<Integer> tree) {
    List<BinaryTreeNode<Integer>> result = new ArrayList<>();
    if (tree == null) {
      return result;
    }
    result.add(tree);
    BinaryTreeNode<Integer> current = tree.left;
    while (current != null) {
      if (current.left == null && current.right == null) {
        break;
      }
      result.add(current);
      if (current.left != null) {
        current = current.left;
      } else {
        current = current.right;
      }
    }
    addLeaves(tree, result);
    current = tree.right;
    List<BinaryTreeNode<Integer>> rightmost = new ArrayList<>();
    while (current != null) {
      if (current.left == null && current.right == null) {
        break;
      }
      rightmost.add(current);
      if (current.right != null) {
        current = current.right;
      } else {
        current = current.left;
      }
    }
    Collections.reverse(rightmost);
    result.addAll(rightmost);
    return result;
  }

  private static void addLeaves(BinaryTreeNode<Integer> tree, List<BinaryTreeNode<Integer>> result) {
    if (tree == null) {
      return;
    }
    if (tree.left == null && tree.right == null) {
      if (result.get(result.size() - 1) != tree) {
        result.add(tree);
      }
      return;
    }
    addLeaves(tree.left, result);
    addLeaves(tree.right, result);
  }
  private static List<Integer> createOutputList(List<BinaryTreeNode<Integer>> L)
      throws TestFailure {
    if (L.contains(null)) {
      throw new TestFailure("Resulting list contains null");
    }
    List<Integer> output = new ArrayList<>();
    for (BinaryTreeNode<Integer> l : L) {
      output.add(l.data);
    }
    return output;
  }

  @EpiTest(testDataFile = "tree_exterior.tsv")
  public static List<Integer>
  exteriorBinaryTreeWrapper(TimedExecutor executor,
                            BinaryTreeNode<Integer> tree) throws Exception {
    List<BinaryTreeNode<Integer>> result =
        executor.run(() -> exteriorBinaryTree(tree));

    return createOutputList(result);
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "TreeExterior.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
