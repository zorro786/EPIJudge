package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class ListCyclicRightShift {
  @EpiTest(testDataFile = "list_cyclic_right_shift.tsv")

  public static ListNode<Integer> cyclicallyRightShiftList(ListNode<Integer> L,
                                                           int k) {
    if (L == null || L.next == null) {
      return L;
    }
    ListNode<Integer> dummy = new ListNode<>(null, L);
    ListNode<Integer> current = L, pred = dummy;
    int n = 0;
    while (current != null) {
      n++;
      current = current.next;
    }
    k = k%n;
    current = L;
    while (k-- > 0 && current != null) {
      current = current.next;
    }
    while (current.next != null) {
      current = current.next;
      pred = pred.next;
    }
    pred = pred.next;
    current.next = dummy.next;
    dummy.next = pred.next;
    pred.next = null;

    return dummy.next;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "ListCyclicRightShift.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
