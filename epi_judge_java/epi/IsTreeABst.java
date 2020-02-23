package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class IsTreeABst {
  @EpiTest(testDataFile = "is_tree_a_bst.tsv")
  public static boolean isBinaryTreeBST(BinaryTreeNode<Integer> tree) {
    return isBSTHelper(tree, Integer.MIN_VALUE, Integer.MAX_VALUE);
  }

  private static boolean isBSTHelper(BinaryTreeNode<Integer> root, int min, int max) {
    if (root == null) {
      return true;
    }
    if (root.data < min || root.data > max) {
      return false;
    }
    return isBSTHelper(root.left, min, root.data) && isBSTHelper(root.right, root.data, max);
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IsTreeABst.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
