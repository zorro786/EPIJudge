package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class SortList {
  @EpiTest(testDataFile = "sort_list.tsv")

  public static ListNode<Integer> stableSortList(ListNode<Integer> L) {
    ListNode<Integer> dummy = new ListNode<>(0, null);
    ListNode<Integer> head, next;
    ListNode<Integer> current = L;
    while (current != null) {

      head = dummy;
      while (head.next != null && head.next.data <= current.data) {
        head = head.next;
      }
      next = head.next;
      head.next = current;
      current = current.next;
      head.next.next = next;
    }

    return dummy.next;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SortList.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
