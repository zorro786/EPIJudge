package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TimedExecutor;
import java.util.ArrayList;
import java.util.List;
public class TreeFromPreorderWithNull {
  private static Integer current;
  public static BinaryTreeNode<Integer>
  reconstructPreorder(List<Integer> preorder) {
    current = 0;
    return builder(preorder);
  }

  private static BinaryTreeNode<Integer> builder(List<Integer> preorder) {
    if (current >= preorder.size()) {
      return null;
    }
    if (preorder.get(current) == null){
      current++;
      return null;
    }
    BinaryTreeNode<Integer> root = new BinaryTreeNode<>(preorder.get(current++));
    root.left = builder(preorder);
    root.right = builder(preorder);
    return root;
  }
  @EpiTest(testDataFile = "tree_from_preorder_with_null.tsv")
  public static BinaryTreeNode<Integer>
  reconstructPreorderWrapper(TimedExecutor executor, List<String> strings)
      throws Exception {
    List<Integer> ints = new ArrayList<>();
    for (String s : strings) {
      if (s.equals("null")) {
        ints.add(null);
      } else {
        ints.add(Integer.parseInt(s));
      }
    }

    return executor.run(() -> reconstructPreorder(ints));
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "TreeFromPreorderWithNull.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
