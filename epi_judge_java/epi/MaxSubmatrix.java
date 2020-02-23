package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import java.util.List;
public class MaxSubmatrix {

  @EpiTest(testDataFile = "max_submatrix.tsv")

  public static int maxRectangleSubmatrix(List<List<Boolean>> A) {
    int m = A.size(), n = A.get(0).size();
    boolean[][] a = new boolean[m][n];
    int[][][] memo = new int[m][n][2];

    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        a[i][j] = A.get(i).get(j);
        if (a[i][j]) {
          memo[i][j][0] = j-1 >= 0 ? memo[i][j-1][0] + 1 : 1;
          memo[i][j][1] = i-1 >= 0 ? memo[i-1][j][1] + 1 : 1;
        }
      }
    }
    int res = 0;
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (a[i][j]) {
          int maxL = memo[i][j][0];
          int maxH = memo[i][j][1];
          for (int k = j; k > j- memo[i][j][0]; k--) {
            maxH = Math.min(maxH, memo[i][k][1]);
            res = Math.max(res, maxH*(maxL-memo[i][k][0]+1));
          }

        }
      }
    }
    return res;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "MaxSubmatrix.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
