package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class DirectoryPathNormalization {
  @EpiTest(testDataFile = "directory_path_normalization.tsv")

  public static String shortestEquivalentPath(String path) {
    if (path == null || path.isEmpty()) {
      return path;
    }
    boolean isAbs = path.charAt(0) == '/' ? true : false;

    Deque<String> stack = new LinkedList<>();
    String[] s = path.split("/");
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < s.length; i++) {
      if (s[i].isEmpty() || s[i].equals(".")) {
        continue;
      } else if (s[i].equals("..")) {
        if (!stack.isEmpty() && !stack.peek().equals("..")) {
          stack.pop();
        } else {
          stack.push(s[i]);
        }
      } else {
        stack.push(s[i]);
      }
    }
    Iterator<String> it = stack.descendingIterator();
    if (isAbs) {
      sb.append('/');
    }
    while (it.hasNext()) {
      sb.append(it.next());
      if (it.hasNext()) {
        sb.append('/');
      }
    }
    return sb.toString();

  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "DirectoryPathNormalization.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
