package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class ZipList {
  @EpiTest(testDataFile = "zip_list.tsv")

  public static ListNode<Integer> zippingLinkedList(ListNode<Integer> L) {
    ListNode<Integer> dummy = new ListNode<>(0, null);
    ListNode<Integer> slow = L, fast = L, current = dummy;
    while (fast != null && fast.next != null) {
      slow = slow.next;
      fast = fast.next.next;
    }
    if (fast != null) {
      slow = slow.next;
    }
    ListNode<Integer> second = reverseList(slow);
    ListNode<Integer> first = L;
    int turn = 0;
    while (first != null || second != null) {
      turn ^= 1;
      if (turn == 1 && first != null) {
        current.next = first;
        if (first.next == slow) {
          first.next = null;
        }
        first = first.next;
      } else if (turn == 0 && second != null) {
        current.next = second;
        second = second.next;
      }
      current = current.next;
    }

    return dummy.next;
  }

  private static ListNode<Integer> reverseList(ListNode<Integer> head) {
    ListNode<Integer> prev = null, cur = head, next;
    while (cur != null) {
      next = cur.next;
      cur.next = prev;
      prev = cur;
      cur = next;
    }
    return prev;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "ZipList.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
