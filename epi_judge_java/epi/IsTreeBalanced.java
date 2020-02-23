package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class IsTreeBalanced {

  private static class Result {
    int height;
    boolean isBalanced;
    private Result(int h, boolean b) {
      height = h;
      isBalanced = b;
    }
  }

  @EpiTest(testDataFile = "is_tree_balanced.tsv")

  public static boolean isBalanced(BinaryTreeNode<Integer> tree) {

    return isBalancedHelper(tree).isBalanced;
  }

  private static Result isBalancedHelper(BinaryTreeNode<Integer> root) {
    if (root == null) {
      return new Result(0, true);
    }
    Result left = isBalancedHelper(root.left);
    Result right = isBalancedHelper(root.right);
    int hDiff = Math.abs(left.height - right.height);
    int maxHeight = left.height > right.height ? left.height : right.height;
    return new Result(maxHeight + 1, hDiff <= 1 && left.isBalanced && right.isBalanced ? true : false);
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IsTreeBalanced.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
