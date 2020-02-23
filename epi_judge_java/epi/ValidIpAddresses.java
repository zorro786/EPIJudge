package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.EpiTestComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;

public class ValidIpAddresses {

  @EpiTest(testDataFile = "valid_ip_addresses.tsv")
  public static List<String> getValidIpAddress(String s) {
    List<String> ips = new ArrayList<>();
    for (int i = 1; i <= 3 && i < s.length(); i++) {
      if (s.length() - i > 9) {
        continue;
      }
      String first = s.substring(0, i);
      if (!validIPPart(first)) {
        continue;
      }
      for (int j = i + 1; j <= 6; j++) {
        if (s.length() - j > 6) {
          continue;
        }
        for (int k = j + 1; k < s.length(); k++) {
          if (s.length() - k > 3) {
            continue;
          }
          StringBuilder sb = new StringBuilder();
          first = s.substring(0, i);
          String second = s.substring(i, j);
          String third = s.substring(j, k);
          String fourth = s.substring(k);
          if (validIPPart(first) && validIPPart(second) && validIPPart(third) && validIPPart(fourth)) {
            sb.append(first).append('.').append(second).append('.').append(third).append('.').append(fourth);
            ips.add(sb.toString());
          }
        }
      }
    }

    return ips;
  }

  private static boolean validIPPart(String s) {
    if (s.length() > 1 && s.startsWith("0")) {
      return false;
    }
    if (s.length() < 4 && Integer.valueOf(s) <= 255) {
      return true;
    }
    return false;
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
                    .runFromAnnotations(args, "ValidIpAddresses.java",
                            new Object() {}.getClass().getEnclosingClass())
                    .ordinal());
  }
}