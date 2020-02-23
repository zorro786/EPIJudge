package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;

import java.util.Collections;
import java.util.List;
public class MinimumPointsCoveringIntervals {
  @EpiUserType(ctorParams = {int.class, int.class})

  public static class Interval implements Comparable<Interval> {
    public int left, right;

    public Interval(int l, int r) {
      this.left = l;
      this.right = r;
    }

    @Override
    public int compareTo(Interval that) {
      return Integer.compare(right, that.right);
    }
  }

  @EpiTest(testDataFile = "minimum_points_covering_intervals.tsv")

  public static Integer findMinimumVisits(List<Interval> intervals) {
    if (intervals.isEmpty()) {
      return 0;
    }
    Collections.sort(intervals);
    int res = 1, i = 0, j = 0;
    while (j < intervals.size()) {
      if (intervals.get(j).left <= intervals.get(i).right) {
        j++;
      } else {
        res++;
        i = j;
      }
    }
    return res;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "MinimumPointsCoveringIntervals.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
