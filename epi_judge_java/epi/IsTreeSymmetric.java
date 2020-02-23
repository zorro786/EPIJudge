package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class IsTreeSymmetric {
  @EpiTest(testDataFile = "is_tree_symmetric.tsv")

  public static boolean isSymmetric(BinaryTreeNode<Integer> tree) {
    if (tree == null) {
      return true;
    }
    return isSymmetricHelper(tree.left, tree.right);
  }

  private static boolean isSymmetricHelper(BinaryTreeNode<Integer> leftR, BinaryTreeNode<Integer> rightR) {
    if (leftR == rightR) {
      return true;
    }
    if (leftR == null || rightR == null) {
      return false;
    }
    return leftR.data.equals(rightR.data) && isSymmetricHelper(leftR.left, rightR.right) && isSymmetricHelper(leftR.right, rightR.left);
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IsTreeSymmetric.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
