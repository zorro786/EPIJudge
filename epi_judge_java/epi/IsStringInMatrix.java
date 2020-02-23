package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
public class IsStringInMatrix {
  @EpiTest(testDataFile = "is_string_in_matrix.tsv")
  public static boolean isPatternContainedInGrid(List<List<Integer>> grid,
                                                 List<Integer> pattern) {
//    int m = grid.size(), n = grid.get(0).size();
//    int p = pattern.size();
//    int[][][] dp = new int[m][n][p];
    return graphApproach(grid, pattern);
  }

  private static boolean graphApproach(List<List<Integer>> grid, List<Integer> pattern) {
    HashMap<List<Integer>, Boolean> memo = new HashMap<>();
    for (int i = 0; i < grid.size(); i++) {
      for (int j = 0; j < grid.get(i).size(); j++) {
        if (grid.get(i).get(j).equals(pattern.get(0))) {
          if (dfs(i, j, 0, grid.size(), grid.get(i).size(), pattern, grid, memo)) {
            return true;
          }
        }
      }
    }
    return false;
  }

  private static boolean dfs(int i, int j, int k, int m, int n, List<Integer> pattern, List<List<Integer>> grid, HashMap<List<Integer>, Boolean> memo) {
    if (i >= 0 && j >= 0 && i < m && j < n && k < pattern.size()) {
      if (grid.get(i).get(j).equals(pattern.get(k))) {
        boolean result = false;
        List<List<Integer>> keys = new ArrayList<>();
        keys.add(Arrays.asList(i+1, j, k+1));
        keys.add(Arrays.asList(i, j+1, k+1));
        keys.add(Arrays.asList(i-1, j, k+1));
        keys.add(Arrays.asList(i, j-1, k+1));
        for (List<Integer> key : keys) {
          if (memo.containsKey(key)) {
            result = result || memo.get(key);
          } else {
            boolean res = dfs(key.get(0), key.get(1), key.get(2), m, n, pattern, grid, memo);
            memo.put(key, res);
            result = result || res;
          }
        }
        return result;
      }
    } else return k == pattern.size();
    return false;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IsStringInMatrix.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
