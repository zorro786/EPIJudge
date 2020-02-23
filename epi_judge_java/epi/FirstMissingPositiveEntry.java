package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.Collections;
import java.util.List;
public class FirstMissingPositiveEntry {
  @EpiTest(testDataFile = "first_missing_positive_entry.tsv")

  public static int findFirstMissingPositive(List<Integer> A) {
    for (int i = 0; i < A.size(); i++) {
      while (A.get(i) > 0 && A.get(i) != i+1 && A.get(i) <= A.size() && !A.get(i).equals(A.get(A.get(i)-1))) {
        Collections.swap(A, i, A.get(i)-1);
      }
    }
    for (int i = 0; i < A.size(); i++) {
      if (A.get(i) != i+1) {
        return i+1;
      }
    }
    return A.size() == 0 ? 1 : A.size()+1;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "FirstMissingPositiveEntry.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
