package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.BitSet;

public class AbsentValueArray {

  @EpiTest(testDataFile = "absent_value_array.tsv")
  public static int findMissingElement(Iterable<Integer> stream) {
    int[] ints = new int[1<<16];
    int msbMask = 0xffff0000;
    for (int n : stream) {
      ints[n>>16]++;
    }

    int missingIp = 0;
    for (int i = 0; i < ints.length; i++) {
      if (ints[i] < (1<<16)) {
        missingIp = i << 16;
        break;
      }
    }
    BitSet bitSet = new BitSet();
    int lsbMask = 0xffff;
    for (int n : stream) {
      if ((n & msbMask) == missingIp) {
        bitSet.set(n&lsbMask);
      }
    }

    for (int i = 0 ; i < bitSet.size(); i++) {
      if (!bitSet.get(i)) {
        return missingIp | i;
      }
    }
    return -1;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "AbsentValueArray.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
