package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class IntAsListAdd {
  @EpiTest(testDataFile = "int_as_list_add.tsv")

  public static ListNode<Integer> addTwoNumbers(ListNode<Integer> L1,
                                                ListNode<Integer> L2) {
    if (L1 == null) {
      return L2;
    } else if (L2 == null) {
      return L1;
    }
    ListNode<Integer> dummy = new ListNode<>(null, null);

    ListNode<Integer> head1 = L1, head2 = L2, ptr = dummy;
    int carry = 0;
    while (head1 != null || head2 != null || carry != 0) {
      int sum = carry;
      if (head1 != null) {
        sum += head1.data;
        head1 = head1.next;
      }
      if (head2 != null) {
        sum += head2.data;
        head2 = head2.next;
      }
      carry = sum/10;
      sum %= 10;
      ptr.next = new ListNode(sum, null);
      ptr = ptr.next;
    }

    return dummy.next;

  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IntAsListAdd.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
