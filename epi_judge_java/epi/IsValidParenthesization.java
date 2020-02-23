package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.Deque;
import java.util.LinkedList;
import java.util.stream.IntStream;

public class IsValidParenthesization {
  @EpiTest(testDataFile = "is_valid_parenthesization.tsv")

  public static boolean isWellFormed(String s) {
    if (s == null) {
      return true;
    }
    Deque<Character> stack = new LinkedList<>();
    for (int i = 0; i < s.length(); i++) {
      if (s.charAt(i) == ')' || s.charAt(i) == ']' || s.charAt(i) == '}') {
        if (!stack.isEmpty() && stack.peek().equals(inverse(s.charAt(i)))) {
          stack.pop();
        } else {
          return false;
        }
      } else {
        stack.push(s.charAt(i));
      }
    }
    if (!stack.isEmpty()) {
      return false;
    }
    return true;
  }

  private static Character inverse(char c) {
    switch(c) {
      case ')':
        return '(';
      case ']':
        return '[';
      case '}':
        return '{';
      default:
        throw new IllegalArgumentException("Unexpected " + c);
    }
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IsValidParenthesization.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
