package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import java.util.List;
public class TreeFromPreorderInorder {
  private static Integer current;
  @EpiTest(testDataFile = "tree_from_preorder_inorder.tsv")

  public static BinaryTreeNode<Integer>
  binaryTreeFromPreorderInorder(List<Integer> preorder, List<Integer> inorder) {
    current = 0;
    return binaryTreeFromPreorderInorderHelper(preorder, inorder, 0, inorder.size() - 1);
  }

  private static BinaryTreeNode<Integer> binaryTreeFromPreorderInorderHelper(List<Integer> preorder, List<Integer> inorder, int lo, int hi) {
    if (current == preorder.size() || hi < lo) {
      return null;
    }
    BinaryTreeNode<Integer> root = new BinaryTreeNode<>(preorder.get(current++));
    int leftEnd = inorder.indexOf(root.data) - 1; //time complexity can be improved to O(N) from O(N^2) by storing these mappings in a hashtable computed initially
    int rightStart = inorder.indexOf(root.data) + 1;
    root.left = binaryTreeFromPreorderInorderHelper(preorder, inorder, lo, leftEnd);
    root.right = binaryTreeFromPreorderInorderHelper(preorder, inorder, rightStart, hi);
    return root;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "TreeFromPreorderInorder.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
