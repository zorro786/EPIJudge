package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MinimumDistance3SortedArrays {

  public static class ArrayData implements Comparable<ArrayData> {
    public int val;
    public int idx;
    public int listIdx;

    public ArrayData(int idx, int val, int listIdx) {
      this.val = val;
      this.idx = idx;
      this.listIdx = listIdx;
    }

    @Override
    public int compareTo(ArrayData o) {
      int result = Integer.compare(val, o.val);
      if (result == 0) {
        result = Integer.compare(idx, o.idx);
      }
      return result;
    }
  }

  @EpiTest(testDataFile = "minimum_distance_3_sorted_arrays.tsv")
  public static int
  findMinDistanceSortedArrays(List<List<Integer>> sortedArrays) {
    List<ArrayData> triplet = new LinkedList<>(); // can use a treeset instead too
    for (int i = 0; i < sortedArrays.size(); i++) {
      triplet.add(new ArrayData(0, sortedArrays.get(i).get(0), i));
    }
    int minDist = Integer.MAX_VALUE;
    int n = sortedArrays.size();
    while (triplet.size() == n) {
      Collections.sort(triplet);
      minDist = Math.min(minDist, triplet.get(n-1).val - triplet.get(0).val);
      ArrayData toRemove = triplet.remove(0);
      if (sortedArrays.get(toRemove.listIdx).size() > toRemove.idx + 1) {
        triplet.add(new ArrayData(toRemove.idx+1, sortedArrays.get(toRemove.listIdx).get(toRemove.idx+1), toRemove.listIdx));
      }
    }
    return minDist;
  }

  private static boolean isUnique(List<ArrayData> l) {
    return l.get(0).idx != l.get(1).idx && l.get(0).idx != l.get(2).idx && l.get(1).idx != l.get(2).idx;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "MinimumDistance3SortedArrays.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
