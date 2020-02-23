package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

public class SortAlmostSortedArray {

  public static List<Integer>
  sortApproximatelySortedData(Iterator<Integer> sequence, int k) {

    PriorityQueue<Integer> pq = new PriorityQueue<>();
    List<Integer> res = new ArrayList<>();
    while (sequence.hasNext()) {
      if (pq.size() <= k) {
        pq.add(sequence.next());
      } else {
        res.add(pq.poll());
        pq.add(sequence.next());
      }
    }
    while (!pq.isEmpty()) {
      res.add(pq.poll());
    }
    return res;
  }
  @EpiTest(testDataFile = "sort_almost_sorted_array.tsv")
  public static List<Integer>
  sortApproximatelySortedDataWrapper(List<Integer> sequence, int k) {
    return sortApproximatelySortedData(sequence.iterator(), k);
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SortAlmostSortedArray.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
