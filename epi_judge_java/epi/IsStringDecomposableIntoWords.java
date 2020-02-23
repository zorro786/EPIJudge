package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;
import epi.test_framework.TimedExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
public class IsStringDecomposableIntoWords {

  public static List<String>
  decomposeIntoDictionaryWords(String domain, Set<String> dictionary) {
    if (domain == null) {
      return null;
    }
    boolean[] dp = new boolean[domain.length()];
    int[] lastLength = new int[domain.length()];
    Arrays.fill(lastLength, -1);
    dp[0] = dictionary.contains(domain.substring(0,1));
    if (dp[0]) {
      lastLength[0] = 1;
    }
    for (int i = 1; i < dp.length; i++) {
      for (int j = 0; j < i; j++) {
        boolean res = dp[j] && dictionary.contains(domain.substring(j+1, i+1));
        if (res && lastLength[i] == -1) {
          lastLength[i] = i - j;
        }
        dp[i] = dp[i] || res;
      }
      dp[i] = dp[i] || dictionary.contains(domain.substring(0, i + 1));
      if (dp[i] && lastLength[i] == -1) {
        lastLength[i] = i + 1;
      }
    }
    if (dp[domain.length() - 1]) {
      List<String> res = new ArrayList<>();
      int idx = domain.length() - 1;
      while (idx >= 0) {
        int start = idx - lastLength[idx] + 1;
        res.add(domain.substring(start, start + lastLength[idx]));
        idx = idx - lastLength[idx];
      }
      Collections.reverse(res);
      return res;
    } else {
      return Collections.emptyList();
    }
  }
  @EpiTest(testDataFile = "is_string_decomposable_into_words.tsv")
  public static void decomposeIntoDictionaryWordsWrapper(TimedExecutor executor,
                                                         String domain,
                                                         Set<String> dictionary,
                                                         Boolean decomposable)
      throws Exception {
    List<String> result =
        executor.run(() -> decomposeIntoDictionaryWords(domain, dictionary));

    if (!decomposable) {
      if (!result.isEmpty()) {
        throw new TestFailure("domain is not decomposable");
      }
      return;
    }

    if (result.stream().anyMatch(s -> !dictionary.contains(s))) {
      throw new TestFailure("Result uses words not in dictionary");
    }

    if (!String.join("", result).equals(domain)) {
      throw new TestFailure("Result is not composed into domain");
    }
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IsStringDecomposableIntoWords.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
