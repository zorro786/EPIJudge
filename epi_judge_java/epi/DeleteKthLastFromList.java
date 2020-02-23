package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class DeleteKthLastFromList {

  @EpiTest(testDataFile = "delete_kth_last_from_list.tsv")
  // Assumes L has at least k nodes, deletes the k-th last node in L.
  public static ListNode<Integer> removeKthLast(ListNode<Integer> L, Integer k) {
    if (L == null) {
      return null;
    }
    ListNode<Integer> current = L;
    ListNode<Integer> dummy = new ListNode<>(null, L);
    ListNode<Integer> pred = dummy;
    while (k-- > 0 && current != null) {
      current = current.next;
    }
    while (current != null) {
      current = current.next;
      pred = pred.next;
    }
    pred.next = pred.next.next;
    return dummy.next;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "DeleteKthLastFromList.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
