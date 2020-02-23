package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class SortedListsMerge {
  @EpiTest(testDataFile = "sorted_lists_merge.tsv")
  //@include
  public static ListNode<Integer> mergeTwoSortedLists(ListNode<Integer> L1,
                                                      ListNode<Integer> L2) {
    ListNode<Integer> dummy = new ListNode<>(null, null);
    ListNode<Integer> current = dummy;
    while (current != null) {
      if (L1 != null && L2 != null) {
        if (L1.data.compareTo(L2.data) < 0) {
          current.next = L1;
          L1 = L1.next;
        } else {
          current.next = L2;
          L2 = L2.next;
        }
      } else {
        current.next = L1 != null? L1 : L2;
        break;
      }
      current = current.next;
    }
    return dummy.next;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SortedListsMerge.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
