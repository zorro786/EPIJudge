package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import java.util.List;
public class EvenOddListMerge {
  @EpiTest(testDataFile = "even_odd_list_merge.tsv")

  public static ListNode<Integer> evenOddMerge(ListNode<Integer> head) {
    if (head == null) {
      return null;
    }
    ListNode<Integer> oddHead = head.next, ptr = oddHead, current = head;
    while (current.next != null && current.next.next != null) {
      current.next = current.next.next;
      current = current.next;
      ptr.next = current.next;
      if (ptr != null) {
        ptr = ptr.next;
      }
    }
    current.next = oddHead;
    return head;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "EvenOddListMerge.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
