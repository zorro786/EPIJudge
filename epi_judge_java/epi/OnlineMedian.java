package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

public class OnlineMedian {
  public static List<Double> onlineMedian(Iterator<Integer> sequence) {
    PriorityQueue<Integer> maxpq = new PriorityQueue<>(Comparator.reverseOrder());
    PriorityQueue<Integer> minpq = new PriorityQueue<>();
    List<Double> result = new ArrayList<>();
    while (sequence.hasNext()) {
      Integer next = sequence.next();
      if (maxpq.isEmpty() || minpq.isEmpty()) {
        minpq.add(next);
      } else if (next < maxpq.peek()) {
          maxpq.add(next);
      } else {
          minpq.add(next);
      }

      if (Math.abs(minpq.size() - maxpq.size()) == 2) {
        if (maxpq.size() > minpq.size()) {
          minpq.add(maxpq.poll());
        } else {
          maxpq.add(minpq.poll());
        }
      }
      if (minpq.size() == maxpq.size()) {
        result.add((minpq.peek() + maxpq.peek())/(double)2);
      } else {
        result.add(maxpq.size() > minpq.size() ? (double)maxpq.peek() : (double)minpq.peek());
      }
    }
    return result;
  }
  @EpiTest(testDataFile = "online_median.tsv")
  public static List<Double> onlineMedianWrapper(List<Integer> sequence) {
    return onlineMedian(sequence.iterator());
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "OnlineMedian.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
