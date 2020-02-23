package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
public class TreePostorder {

  private static class StackElement {
    BinaryTreeNode<Integer> node;
    Boolean visitedRight;
    private StackElement(BinaryTreeNode<Integer> node, Boolean visitedRight) {
      this.node = node;
      this.visitedRight = visitedRight;
    }
  }

  @EpiTest(testDataFile = "tree_postorder.tsv")
  // We use stack and previous node pointer to simulate postorder traversal.
  public static List<Integer> postorderTraversal(BinaryTreeNode<Integer> tree) {
    BinaryTreeNode<Integer> cur = tree;
    Deque<StackElement> stack = new ArrayDeque<>();
    List<Integer> res = new ArrayList<>();
    while (cur != null || !stack.isEmpty()) {
      if (cur != null) {
        stack.push(new StackElement(cur, false));
        cur = cur.left;
      } else {
        StackElement top = stack.peek();
        if (top.visitedRight || top.node.right == null) {
          res.add(top.node.data);
          stack.pop();
          cur = null;
        } else {
          top.visitedRight = true;
          cur = top.node.right;
        }
      }
    }
    return res;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "TreePostorder.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
