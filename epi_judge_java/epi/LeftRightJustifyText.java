package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class LeftRightJustifyText {
  @EpiTest(testDataFile = "left_right_justify_text.tsv")

  public static List<String> justifyText(List<String> words, int L) {
    List<String> res = new ArrayList<>();
    List<String> line = new ArrayList<>();
    int currentLength = 0;
    int i = 0;
    while (i <= words.size()) {
      if (line.isEmpty() || i < words.size() && currentLength + words.get(i).length() + 1 <= L) {
        currentLength += words.get(i).length();
        if (!line.isEmpty()) {
          currentLength += 1;
        }
        line.add(words.get(i++));
      } else {
        int remaining = L - currentLength;
        int blanksPerWord = 0, excess = 0;
        if (line.size() > 1 && i <= words.size() - 1) {
          blanksPerWord = remaining/(line.size() - 1);
          excess = remaining%(line.size() - 1);
        }
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < line.size(); j++) {
          sb.append(line.get(j));
          if (j != line.size() - 1) {
            sb.append(" ");
            sb.append(new String(new char[blanksPerWord]).replace('\0', ' '));
            if (excess > 0) {
              sb.append(" ");
              excess--;
            }
          }
        }
        if (i > words.size() - 1 || line.size() == 1) {
          sb.append(new String(new char[remaining]).replace('\0', ' '));
        }
        res.add(sb.toString());
        currentLength = 0;
        line.clear();
        if (i == words.size()) {
          break;
        }
      }
    }
    return res;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "LeftRightJustifyText.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
