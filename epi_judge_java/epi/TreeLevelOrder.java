package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
public class TreeLevelOrder {
  @EpiTest(testDataFile = "tree_level_order.tsv")

  public static List<List<Integer>>
  binaryTreeDepthOrder(BinaryTreeNode<Integer> tree) {
    List<List<Integer>> result = new ArrayList<>();
    if (tree == null) {
      return result;
    }
    Deque<BinaryTreeNode> parent = new LinkedList<>();
    parent.offer(tree);
    while (!parent.isEmpty()) {
      Deque<BinaryTreeNode> children = new LinkedList<>();
      LinkedList<Integer> values = new LinkedList<>();
      for (BinaryTreeNode<Integer> btn : parent) {
        if (btn.left != null) {
          children.offer(btn.left);
        }
        if (btn.right != null) {
          children.offer(btn.right);
        }
        values.add(btn.data);
      }
      result.add(values);
      parent = children;
    }

    return result;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "TreeLevelOrder.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
