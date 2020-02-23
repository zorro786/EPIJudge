package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class ReverseSublist {

  @EpiTest(testDataFile = "reverse_sublist.tsv")//Does it with single pass of list
  public static ListNode<Integer> reverseSublist(ListNode<Integer> L, int start,
                                                 int finish) {
    if (L == null || start == finish) {
      return L;
    }
    int s = start, f = finish;
    ListNode<Integer> dummy = new ListNode<>(null, L);
    ListNode<Integer> first = dummy;
    while(--start > 0 && first != null) {
      first = first.next;
    }

    first.next = reverseList(first.next, f - s + 1);

    return dummy.next;
  }

  private static ListNode<Integer> reverseList(ListNode<Integer> l, int count) {
    ListNode<Integer> prev = null, next, cur = l;
    while(cur != null && count-- > 0) {
      next = cur.next;
      cur.next = prev;
      prev = cur;
      cur = next;
      l.next = cur;
    }
    return prev;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "ReverseSublist.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
