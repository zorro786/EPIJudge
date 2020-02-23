package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class IsStringPalindromicPunctuation {
  @EpiTest(testDataFile = "is_string_palindromic_punctuation.tsv")

  public static boolean isPalindrome(String s) {
    int[] chars = new int[256];
    for (int i = 0; i < s.length(); i++) {
      if (Character.isLetterOrDigit(s.charAt(i))) {
        chars[Character.toLowerCase(s.charAt(i))]++;
      }
    }
    boolean foundOdd = false;
    for (int i = 0; i < chars.length; i++) {
      if (chars[i]%2 != 0) {
        if (foundOdd) {
          return false;
        } else {
          foundOdd = true;
        }
      }
    }
    return true;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IsStringPalindromicPunctuation.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
