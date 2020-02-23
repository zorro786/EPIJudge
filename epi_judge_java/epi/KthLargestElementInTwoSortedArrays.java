package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
public class KthLargestElementInTwoSortedArrays {
  @EpiTest(testDataFile = "kth_largest_element_in_two_sorted_arrays.tsv")

  public static int findKthNTwoSortedArrays(List<Integer> A, List<Integer> B,
                                            int k) {

    int la, ra, ma;
    int lb, rb, mb;

    //index starts from 1; subtract 1 for actual index
    ra = rb = k;

    while (ra+rb != k) {
      if (A.get(ra-1) == B.get(rb-1) && ra+rb==k)
        break;

      if (A.get(ra-1) > B.get(rb-1)) {
        la = k - rb;
        ra = (la + ra) / 2;
      }
      else {
        lb = k - ra;
        rb = (lb + rb) / 2;
      }
    }

    if (ra == 0)
      return B.get(rb-1);
    else if (rb == 0)
      return A.get(ra-1);
    else if (A.get(ra-1) > B.get(rb-1))
      return A.get(ra-1);
    else
      return B.get(rb-1);
  }

  private static int kthRecursiveHelper(List<Integer> A, List<Integer> B, int i, int j, int k) {
    if (k <= 2) {
      int[] array = new int[]{A.get(i), A.get(i+1), B.get(j), B.get(j+1)};
      Arrays.sort(array);
      if (k == 2) {
        return array[1];
      } else {
        return array[0];
      }
    }
    int midA = A.get(i + k/2);
    int midB = B.get(j + k/2);
    if (midA < midB) {
      return kthRecursiveHelper(A, B, i + k/2, j, k/2);
    } else {
      return kthRecursiveHelper(A, B, i, j + k/2, k/2);
    }
  }

  public static void main(String[] args) {
    System.out.println(findKthNTwoSortedArrays(Arrays.asList(1, 3, 5), Arrays.asList(2, 4, 6), 6));
//    System.exit(
//        GenericTest
//            .runFromAnnotations(args, "KthLargestElementInTwoSortedArrays.java",
//                                new Object() {}.getClass().getEnclosingClass())
//            .ordinal());
  }


}
