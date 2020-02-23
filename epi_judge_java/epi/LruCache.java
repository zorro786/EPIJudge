package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LruCache {
  private class Node {
    Integer price, isbn;
    Node next, prev;
    private Node (Integer isbn, Integer price, Node next, Node prev) {
      this.price = price;
      this.isbn = isbn;
      this.next = next;
      this.prev = prev;
    }
  }
  private Map<Integer, Node> map;
  private int capacity;
  private int size;
  private Node head, tail;

  LruCache(final int capacity) {
    this.capacity = capacity;
    map = new HashMap<>();
    head = new Node(-1, -1, null, null);
    tail = new Node(-2, -1, null, head);
    head.next = tail;
    size = 0;
  }

  public Integer lookup(Integer key) {
    if (!map.containsKey(key)) {
      return -1;
    }
    Node node = map.get(key);
    moveToFirst(key);
    return node.price;
  }

  public void insert(Integer key, Integer value) {
    if (map.containsKey(key)) {
      moveToFirst(key);
    } else {
      if (size == capacity) {
        evict();
      }
      head.next = new Node(key, value, head.next, head);
      head.next.next.prev = head.next;
      size++;
      map.put(key, head.next);
    }
  }

  private void moveToFirst(Integer key) {
    Node node = map.get(key);
    node.prev.next = node.next;
    node.next.prev = node.prev;
    node.next = head.next;
    node.prev = head;
    head.next = node;
    head.next.next.prev = node;
  }

  private void evict() {
    if (size > 0) {
      tail.prev.prev.next = tail;
      map.remove(tail.prev.isbn);
      tail.prev = tail.prev.prev;
      size--;
    }
  }

  public Boolean erase(Object key) {
    if (!map.containsKey(key)) {
      return false;
    }
    Node node = map.get(key);
    node.prev.next = node.next;
    node.next.prev = node.prev;
    size--;
    map.remove(key);
    return true;
  }

  @EpiUserType(ctorParams = {String.class, int.class, int.class})
  public static class Op {
    String code;
    int arg1;
    int arg2;

    public Op(String code, int arg1, int arg2) {
      this.code = code;
      this.arg1 = arg1;
      this.arg2 = arg2;
    }
  }

  @EpiTest(testDataFile = "lru_cache.tsv")
  public static void runTest(List<Op> commands) throws TestFailure {
    if (commands.isEmpty() || !commands.get(0).code.equals("LruCache")) {
      throw new RuntimeException("Expected LruCache as first command");
    }
    LruCache cache = new LruCache(commands.get(0).arg1);
    for (Op op : commands.subList(1, commands.size())) {
      int result;
      switch (op.code) {
      case "lookup":
        result = cache.lookup(op.arg1);
        if (result != op.arg2) {
          throw new TestFailure("Lookup: expected " + String.valueOf(op.arg2) +
                                ", got " + String.valueOf(result));
        }
        break;
      case "insert":
        cache.insert(op.arg1, op.arg2);
        break;
      case "erase":
        result = cache.erase(op.arg1) ? 1 : 0;
        if (result != op.arg2) {
          throw new TestFailure("Erase: expected " + String.valueOf(op.arg2) +
                                ", got " + String.valueOf(result));
        }
        break;
      default:
        throw new RuntimeException("Unexpected command " + op.code);
      }
    }
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "LruCache.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
