package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import java.util.List;
public class BstFromPreorder {

  private static int index;

  @EpiTest(testDataFile = "bst_from_preorder.tsv")
  public static BstNode<Integer>
  rebuildBSTFromPreorder(List<Integer> preorderSequence) {
    index = 0;
    return rebuildBSTFromPreorderHelper(preorderSequence, Integer.MIN_VALUE, Integer.MAX_VALUE);
  }

  private static BstNode<Integer> rebuildBSTFromPreorderHelper(List<Integer> preorder, Integer min, Integer max) {
    if (index == preorder.size()) {
      return null;
    }
    if (preorder.get(index) > max || preorder.get(index) < min) {
      return null;
    }

    BstNode<Integer> root = new BstNode<>(preorder.get(index++));
    root.left = rebuildBSTFromPreorderHelper(preorder, min, root.data);
    root.right = rebuildBSTFromPreorderHelper(preorder, root.data, max);
    return root;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "BstFromPreorder.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
