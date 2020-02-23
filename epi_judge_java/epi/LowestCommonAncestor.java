package epi;
import epi.test_framework.BinaryTreeUtils;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;
import epi.test_framework.TimedExecutor;
public class LowestCommonAncestor {
  public static BinaryTreeNode<Integer> LCA(BinaryTreeNode<Integer> tree,
                                            BinaryTreeNode<Integer> node0,
                                            BinaryTreeNode<Integer> node1) {
    Result result = new Result();
    LCAHelper(tree, node0, node1, result);
    return result.lca;
  }

  private static class Result {
    BinaryTreeNode<Integer> lca;
  }

  /*
    Returns true if either first or second node is found
   */
  private static boolean LCAHelper(BinaryTreeNode<Integer> root,
                                BinaryTreeNode<Integer> first,
                                BinaryTreeNode<Integer> second,
                                   Result result) {
    if (root == null) {
      return false;
    }
    if (root.equals(first) && root.equals(second)) {
      result.lca = root;
      return true;
    }
    boolean foundLeft = LCAHelper(root.left, first, second, result);
    if (foundLeft && (root.equals(first) || root.equals(second))) {
      result.lca = root;
      return true;
    }
    boolean foundRight = LCAHelper(root.right, first, second, result);
    if (foundRight && (root.equals(first) || root.equals(second))) {
      result.lca = root;
      return true;
    }

    if (foundLeft && foundRight && result.lca == null) {
      result.lca = root;
      return true;
    }
    if (root.equals(first) || root.equals(second)) {
      return true;
    }

    return foundLeft || foundRight;
  }
  @EpiTest(testDataFile = "lowest_common_ancestor.tsv")
  public static int lcaWrapper(TimedExecutor executor,
                               BinaryTreeNode<Integer> tree, Integer key0,
                               Integer key1) throws Exception {
    BinaryTreeNode<Integer> node0 = BinaryTreeUtils.mustFindNode(tree, key0);
    BinaryTreeNode<Integer> node1 = BinaryTreeUtils.mustFindNode(tree, key1);

    BinaryTreeNode<Integer> result =
        executor.run(() -> LCA(tree, node0, node1));

    if (result == null) {
      throw new TestFailure("Result can not be null");
    }
    return result.data;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "LowestCommonAncestor.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
