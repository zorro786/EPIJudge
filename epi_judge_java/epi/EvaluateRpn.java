package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.Deque;
import java.util.LinkedList;

public class EvaluateRpn {
  @EpiTest(testDataFile = "evaluate_rpn.tsv")

  public static int eval(String expression) {
    String[] split = expression.split(",");
    Deque<Integer> stack = new LinkedList<>();
    for (int i = 0; i < split.length; i++ ) {
      if (split[i].length() == 1 && !Character.isDigit(split[i].charAt(0))) {
        Integer b = stack.pop();
        Integer a = stack.pop();
        stack.push(applyOp(a, b, split[i].charAt(0)));
      } else {
        stack.push(Integer.valueOf(split[i]));
      }
    }
    return stack.peek();
  }

  private static Integer applyOp(Integer a, Integer b, char op) {
    switch (op) {
      case '*':
        return a*b;
      case '/':
        return a/b;
      case '+':
        return a+b;
      case '-':
        return a-b;
      default :
        throw new IllegalArgumentException("op " + op + " not found");
    }
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "EvaluateRpn.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
