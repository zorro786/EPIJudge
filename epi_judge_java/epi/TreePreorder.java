package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
public class TreePreorder {
  @EpiTest(testDataFile = "tree_preorder.tsv")

  public static List<Integer> preorderTraversal(BinaryTreeNode<Integer> tree) {
    BinaryTreeNode<Integer> current = tree;
    List<Integer> result = new ArrayList<>();
    Deque<BinaryTreeNode<Integer>> stack = new ArrayDeque<>();
    while (!stack.isEmpty() || current != null) {
      if (current != null) {
        result.add(current.data);
        if (current.right != null) {
          stack.push(current.right);
        }
        current = current.left;
      } else {
        current = stack.pop();
      }
    }
    return result;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "TreePreorder.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
