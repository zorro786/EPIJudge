package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.List;
public class AdvanceByOffsets {
  @EpiTest(testDataFile = "advance_by_offsets.tsv")
  public static boolean canReachEnd(List<Integer> maxAdvanceSteps) {
    if (maxAdvanceSteps.size() == 1) {
      return true;
    }
    int i = 0;
    while(maxAdvanceSteps.get(i) != 0) {
      int maxHop = 0;
      int nextIndex = i;
      for (int j = 1; j <= maxAdvanceSteps.get(i); j++) {
        if (i + j >= maxAdvanceSteps.size() - 1) {
          return true;
        }
        if (maxHop < maxAdvanceSteps.get(i + j) + j + i) {
          nextIndex = i + j;
          maxHop = maxAdvanceSteps.get(i + j) + j + i;
        }
        if (maxHop >= maxAdvanceSteps.size() - 1) {
          return true;
        }
      }
      i = nextIndex;
    }
    return false;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "AdvanceByOffsets.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
//    AdvanceByOffsets advanceByOffsets = new AdvanceByOffsets();
//    List<Integer> ints = new ArrayList<>(Arrays.asList(3, 4, 3, 2, 0, 0, 5));
//    System.out.println(canReachEnd(ints));
  }
}
