package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.HashMap;

public class IsAnonymousLetterConstructible {
  @EpiTest(testDataFile = "is_anonymous_letter_constructible.tsv")

  public static boolean isLetterConstructibleFromMagazine(String letterText,
                                                          String magazineText) {
    HashMap<Character, Integer> dict = new HashMap<>();
    for (int i = 0; i < magazineText.length(); i++) {
      dict.merge(magazineText.charAt(i), 1, Integer::sum);
    }
    for (int i = 0; i < letterText.length(); i++) {
      if (dict.containsKey(letterText.charAt(i))) {
        Integer value = dict.get(letterText.charAt(i));
        if (value == 1) {
          dict.remove(letterText.charAt(i));
        } else {
          dict.put(letterText.charAt(i), value - 1);
        }
      } else {
        return false;
      }
    }
    return true;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IsAnonymousLetterConstructible.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
