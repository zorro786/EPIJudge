package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.RandomSequenceChecker;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;
import epi.test_framework.TimedExecutor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class NonuniformRandomNumber {

  public static int
  nonuniformRandomNumberGeneration(List<Integer> values,
                                   List<Double> probabilities) {
    List<Double> cp = new ArrayList<>();
    cp.add(probabilities.get(0));
    for (int i = 1; i < probabilities.size(); i++) {
      cp.add(i, cp.get(i-1) + probabilities.get(i));
    }
    Random random = new Random();
    double p = random.nextDouble();
    int idx = Collections.binarySearch(cp, p);
    if (idx < 0) {
      return values.get(Math.abs(idx));
    } else {
      return values.get(idx);
    }
  }
  private static boolean nonuniformRandomNumberGenerationRunner(
      TimedExecutor executor, List<Integer> values, List<Double> probabilities)
      throws Exception {
    final int N = 1000000;
    List<Integer> results = new ArrayList<>(N);

    executor.run(() -> {
      for (int i = 0; i < N; ++i) {
        results.add(nonuniformRandomNumberGeneration(values, probabilities));
      }
    });

    Map<Integer, Integer> counts = new HashMap<>();
    for (Integer result : results) {
      counts.put(result, counts.getOrDefault(result, 0) + 1);
    }
    for (int i = 0; i < values.size(); ++i) {
      final int v = values.get(i);
      final double p = probabilities.get(i);
      if (N * p < 50 || N * (1.0 - p) < 50) {
        continue;
      }
      final double sigma = Math.sqrt(N * p * (1.0 - p));
      if (Math.abs(counts.get(v) - (p * N)) > 5 * sigma) {
        return false;
      }
    }
    return true;
  }

  @EpiTest(testDataFile = "nonuniform_random_number.tsv")
  public static void nonuniformRandomNumberGenerationWrapper(
      TimedExecutor executor, List<Integer> values, List<Double> probabilities)
      throws Exception {
    RandomSequenceChecker.runFuncWithRetries(
        ()
            -> nonuniformRandomNumberGenerationRunner(executor, values,
                                                      probabilities));
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "NonuniformRandomNumber.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
