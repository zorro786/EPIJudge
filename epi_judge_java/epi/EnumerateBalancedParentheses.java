package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiTestComparator;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;
public class EnumerateBalancedParentheses {
  @EpiTest(testDataFile = "enumerate_balanced_parentheses.tsv")

  public static List<String> generateBalancedParentheses(int numPairs) {
    List<String> result = new ArrayList<>();
    matchedParensHelper(numPairs, 0, 0, new StringBuilder(), result);
    return result;
  }

  private static void matchedParensHelper(int n, int open, int closed, StringBuilder sb, List<String> result) {
    if (open == n && closed == n) {
      result.add(sb.toString());
      return;
    }
    if (open > n || closed > n) {
      return;
    }

    if (closed < open) {
      sb.append(')');
      matchedParensHelper(n, open, closed + 1, sb, result);
      sb.deleteCharAt(sb.length() - 1);
    }
    sb.append('(');
    matchedParensHelper(n, open + 1, closed, sb, result);
    sb.deleteCharAt(sb.length() - 1);
  }
  @EpiTestComparator
  public static BiPredicate<List<String>, List<String>> comp =
      (expected, result) -> {
    if (result == null) {
      return false;
    }
    Collections.sort(expected);
    Collections.sort(result);
    return expected.equals(result);
  };

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "EnumerateBalancedParentheses.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
