package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;
import epi.test_framework.TimedExecutor;
import java.util.HashSet;
import java.util.Set;
public class DoListsOverlap {

  public static ListNode<Integer> overlappingLists(ListNode<Integer> l0,
                                                   ListNode<Integer> l1) {
    ListNode<Integer> entryPoint0 = getEntryNodeOfCycle(l0);
    ListNode<Integer> entryPoint1 = getEntryNodeOfCycle(l1);
    if (entryPoint0 != null && entryPoint1 != null) {
      // Check if they both belong to same cycle with different entry points
      if (entryPoint0 != entryPoint1) {
        ListNode<Integer> current = entryPoint1.next;
        while (current != entryPoint1) {
          if (current == entryPoint0) {
            return current;
          }
          current = current.next;
        }
        // No node matches entry point in cycle which means cycles are independent
        return null;
      } else {
        // they either meet at the same starting point of circle or some point before cycle starts
        return getMergingPoint(l0, l1, entryPoint0, entryPoint1);
      }
    } else if (entryPoint0 != null || entryPoint1 != null) {
      return null;
    } else {
      return getMergingPoint(l0, l1, entryPoint0, entryPoint1);
    }
  }

  // Get merging point if entry points to two
  private static ListNode<Integer> getMergingPoint(ListNode<Integer> l0, ListNode<Integer> l1, ListNode<Integer> entryPoint0, ListNode<Integer> entryPoint1) {
    int n0 = 0;
    ListNode<Integer> current = l0;
    while (current != entryPoint0) {
      current = current.next;
      n0++;
    }
    current = l1;
    int n1 = 0;
    while (current != entryPoint1) {
      current = current.next;
      n1++;
    }
    if (n0 == n1 && entryPoint0 != null && entryPoint1 != null) {
      // This is the start of the circle as well as meeting point
      return entryPoint0;
    } else {
      if (n1 > n0) {
        current = l0;
        l0 = l1;
        l1 = current;
        int t = n0;
        n0 = n1;
        n1 = t;
      }
      current = l0;

      while (n1 != n0) {
        n0--;
        current = current.next;
      }
      ListNode<Integer> temp = l1;
      while (n0-- > 0 && current != temp) {
        current = current.next;
        temp = temp.next;
      }
      if (current == temp) {
        return current;
      } else {
        return null;
      }
    }
  }

  private static ListNode<Integer> getEntryNodeOfCycle(ListNode<Integer> l) {
    ListNode<Integer> fast = l, slow = l;

    while(fast != null && fast.next != null) {
      slow = slow.next;
      fast = fast.next.next;
      if (slow == fast) {
        slow = l;
        while (slow != fast) {
          slow = slow.next;
          fast = fast.next;
        }
        return slow;
      }
    }
    return null;
  }

  @EpiTest(testDataFile = "do_lists_overlap.tsv")
  public static void
  overlappingListsWrapper(TimedExecutor executor, ListNode<Integer> l0,
                          ListNode<Integer> l1, ListNode<Integer> common,
                          int cycle0, int cycle1) throws Exception {
    if (common != null) {
      if (l0 == null) {
        l0 = common;
      } else {
        ListNode<Integer> it = l0;
        while (it.next != null) {
          it = it.next;
        }
        it.next = common;
      }

      if (l1 == null) {
        l1 = common;
      } else {
        ListNode<Integer> it = l1;
        while (it.next != null) {
          it = it.next;
        }
        it.next = common;
      }
    }

    if (cycle0 != -1 && l0 != null) {
      ListNode<Integer> last = l0;
      while (last.next != null) {
        last = last.next;
      }
      ListNode<Integer> it = l0;
      while (cycle0-- > 0) {
        if (it == null) {
          throw new RuntimeException("Invalid input data");
        }
        it = it.next;
      }
      last.next = it;
    }

    if (cycle1 != -1 && l1 != null) {
      ListNode<Integer> last = l1;
      while (last.next != null) {
        last = last.next;
      }
      ListNode<Integer> it = l1;
      while (cycle1-- > 0) {
        if (it == null) {
          throw new RuntimeException("Invalid input data");
        }
        it = it.next;
      }
      last.next = it;
    }

    Set<Integer> commonNodes = new HashSet<>();
    ListNode<Integer> it = common;
    while (it != null && !commonNodes.contains(it.data)) {
      commonNodes.add(it.data);
      it = it.next;
    }

    final ListNode<Integer> finalL0 = l0;
    final ListNode<Integer> finalL1 = l1;
    ListNode<Integer> result =
        executor.run(() -> overlappingLists(finalL0, finalL1));

    if (!((commonNodes.isEmpty() && result == null) ||
          (result != null && commonNodes.contains(result.data)))) {
      throw new TestFailure("Invalid result");
    }
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "DoListsOverlap.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
