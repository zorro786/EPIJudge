package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
public class TreeInorder {
  @EpiTest(testDataFile = "tree_inorder.tsv")

  public static List<Integer> inorderTraversal(BinaryTreeNode<Integer> tree) {
    List<Integer> result = new ArrayList<>();
    if (tree == null) {
      return result;
    }

    Deque<BinaryTreeNode<Integer>> stack = new ArrayDeque<>();
    BinaryTreeNode<Integer> current = tree;
    while (!stack.isEmpty() || current != null) {
      if (current == null) {
        result.add(stack.peek().data);
        current = stack.pop().right;
      } else {
        stack.push(current);
        current = current.left;
      }
    }
    return result;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "TreeInorder.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
