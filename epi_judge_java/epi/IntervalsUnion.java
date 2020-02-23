package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;
import epi.test_framework.TimedExecutor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class IntervalsUnion {

  public static class Interval implements Comparable<Interval> {
    public Endpoint left = new Endpoint();
    public Endpoint right = new Endpoint();

    @Override
    public int compareTo(Interval o) {
      if (left.val != o.left.val) {
        return Integer.compare(left.val, o.left.val);
      } else {
        if (left.isClosed && !o.left.isClosed) {
          return -1;
        } else if (!left.isClosed && o.left.isClosed) {
          return 1;
        } else {
          return 0;
        }
      }
    }

    private static class Endpoint {
      public boolean isClosed;
      public int val;
    }
  }

  public static List<Interval> unionOfIntervals(List<Interval> intervals) {
    Collections.sort(intervals);
    Interval current = null;
    List<Interval> res = new ArrayList<>();
    for (Interval interval : intervals) {
      if (current == null || current.right.val < interval.left.val || (current.right.val == interval.left.val && !current.right.isClosed && !interval.left.isClosed)) {
        if (current != null) {
          res.add(current);
        }
        current = interval;
      } else {
        if (interval.right.val > current.right.val) {
          current.right = interval.right;
        } else if (interval.right.val == current.right.val) {
          current.right.isClosed = current.right.isClosed || interval.right.isClosed;
        }
      }
    }
    if (current != null) {
      res.add(current);
    }
    return res;
  }
  @EpiUserType(
      ctorParams = {int.class, boolean.class, int.class, boolean.class})
  public static class FlatInterval {
    int leftVal;
    boolean leftIsClosed;
    int rightVal;
    boolean rightIsClosed;

    public FlatInterval(int leftVal, boolean leftIsClosed, int rightVal,
                        boolean rightIsClosed) {
      this.leftVal = leftVal;
      this.leftIsClosed = leftIsClosed;
      this.rightVal = rightVal;
      this.rightIsClosed = rightIsClosed;
    }

    public FlatInterval(Interval i) {
      if (i != null) {
        leftVal = i.left.val;
        leftIsClosed = i.left.isClosed;
        rightVal = i.right.val;
        rightIsClosed = i.right.isClosed;
      }
    }

    public Interval toInterval() {
      Interval i = new Interval();
      i.left.val = leftVal;
      i.left.isClosed = leftIsClosed;
      i.right.val = rightVal;
      i.right.isClosed = rightIsClosed;
      return i;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      FlatInterval that = (FlatInterval)o;

      if (leftVal != that.leftVal) {
        return false;
      }
      if (leftIsClosed != that.leftIsClosed) {
        return false;
      }
      if (rightVal != that.rightVal) {
        return false;
      }
      return rightIsClosed == that.rightIsClosed;
    }

    @Override
    public int hashCode() {
      int result = leftVal;
      result = 31 * result + (leftIsClosed ? 1 : 0);
      result = 31 * result + rightVal;
      result = 31 * result + (rightIsClosed ? 1 : 0);
      return result;
    }

    @Override
    public String toString() {
      return "" + (leftIsClosed ? "<" : "(") + leftVal + ", " + rightVal +
          (rightIsClosed ? ">" : ")");
    }
  }

  @EpiTest(testDataFile = "intervals_union.tsv")
  public static List<FlatInterval>
  unionIntervalWrapper(TimedExecutor executor, List<FlatInterval> intervals)
      throws Exception {
    List<Interval> casted = new ArrayList<>(intervals.size());
    for (FlatInterval in : intervals) {
      casted.add(in.toInterval());
    }

    List<Interval> result = executor.run(() -> unionOfIntervals(casted));

    intervals = new ArrayList<>(result.size());
    for (Interval i : result) {
      intervals.add(new FlatInterval(i));
    }
    return intervals;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IntervalsUnion.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
