package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class CircularQueue {

  public static class Queue {

    private int head, tail;
    private int size;
    private int capacity;
    Integer[] q;

    public Queue(int capacity) {
      assert(capacity > 0);
      q = new Integer[capacity];
      this.capacity = capacity;
    }

    public void enqueue(Integer x) {
      if (size == capacity) {
        resize();
      }
      q[tail] = x;
      tail = (tail + 1)%capacity;
      size++;
    }

    public Integer dequeue() {
      if (size == 0) {
        throw new NoSuchElementException("");
      }
      Integer toRemove = q[head];
      q[head] = null;
      head = (head + 1)%capacity;
      size--;
      return toRemove;
    }

    private void resize() {
      Integer[] resized = new Integer[capacity*2];
      System.arraycopy(q, head, resized, 0, capacity - head);
      if (head > 0) {
        System.arraycopy(q, 0, resized, capacity - head, head);
      }
      q = resized;
      head = 0;
      tail = size;
      capacity = capacity*2;
    }

    public int size() {
      return size;
    }

    @Override
    public String toString() {
      return Arrays.toString(q);
    }
  }
  @EpiUserType(ctorParams = {String.class, int.class})
  public static class QueueOp {
    public String op;
    public int arg;

    public QueueOp(String op, int arg) {
      this.op = op;
      this.arg = arg;
    }

    @Override
    public String toString() {
      return op;
    }
  }

  @EpiTest(testDataFile = "circular_queue.tsv")
  public static void queueTest(List<QueueOp> ops) throws TestFailure {
    Queue q = new Queue(1);
    int opIdx = 0;
    for (QueueOp op : ops) {
      switch (op.op) {
      case "Queue":
        q = new Queue(op.arg);
        break;
      case "enqueue":
        q.enqueue(op.arg);
        break;
      case "dequeue":
        int result = q.dequeue();
        if (result != op.arg) {
          throw new TestFailure()
              .withProperty(TestFailure.PropertyName.STATE, q)
              .withProperty(TestFailure.PropertyName.COMMAND, op)
              .withMismatchInfo(opIdx, op.arg, result);
        }
        break;
      case "size":
        int s = q.size();
        if (s != op.arg) {
          throw new TestFailure()
              .withProperty(TestFailure.PropertyName.STATE, q)
              .withProperty(TestFailure.PropertyName.COMMAND, op)
              .withMismatchInfo(opIdx, op.arg, s);
        }
        break;
      }
      opIdx++;
    }
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "CircularQueue.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
