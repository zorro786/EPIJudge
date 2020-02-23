package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class DrawingSkyline {
  @EpiUserType(ctorParams = {int.class, int.class, int.class})

  public static class Rectangle {
    public int left, right, height;

    public Rectangle(int left, int right, int height) {
      this.left = left;
      this.right = right;
      this.height = height;
    }

    public static int getHeight(int height) {
      return height;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      Rectangle rectangle = (Rectangle)o;

      if (left != rectangle.left) {
        return false;
      }
      if (right != rectangle.right) {
        return false;
      }
      return height == rectangle.height;
    }

    @Override
    public String toString() {
      return "[" + left + ", " + right + ", " + height + ']';
    }
  }

  private static class Endpoint {
    int x;
    boolean isLeft;
    Rectangle b;
    private Endpoint(int x, boolean isLeft, Rectangle b) {
      this.x = x;
      this.isLeft = isLeft;
      this.b = b;
    }
  }

  @EpiTest(testDataFile = "drawing_skyline.tsv")

  public static List<Rectangle> drawingSkylines(List<Rectangle> buildings) {
    List<Rectangle> res = new ArrayList<>();
    Rectangle cur = null;
    List<Endpoint> endpoints = new ArrayList<>();
    for (Rectangle b : buildings) {
      Endpoint left = new Endpoint(b.left, true, b);
      Endpoint right = new Endpoint(b.right, false, b);
      endpoints.add(left);
      endpoints.add(right);
    }

    Collections.sort(endpoints, (left, right) -> {
      if (left.x == right.x) {
        return right.b.height - left.b.height;
      } else {
        return left.x - right.x;
      }
    });

    PriorityQueue<Rectangle> pq = new PriorityQueue<>((l, r) -> Integer.compare(r.height, l.height));
    for (Endpoint e : endpoints) {
      if (e.isLeft) {
        pq.add(e.b);
        if (cur == null) {
          cur = new Rectangle(pq.peek().left, pq.peek().right, pq.peek().height);
        } else if (cur.height < pq.peek().height) {
          Rectangle b = new Rectangle(cur.left, pq.peek().left, cur.height);
          if (b.left != b.right) { // handles the case where we end up adding single point as rectangle
            res.add(b);
          }
          cur = new Rectangle(pq.peek().left, pq.peek().right, pq.peek().height);
        }
      } else {
        pq.remove(e.b);
        if (cur != null && cur.height == e.b.height) {
          if (!pq.isEmpty()) {
            cur = new Rectangle(cur.left, e.b.right, e.b.height);
            res.add(cur);
            if (pq.peek().right != e.b.right) {
              cur = new Rectangle(e.b.right, -1, pq.peek().height);
            } else {
              cur = null;
            }
          } else {
            cur.right = e.b.right;
            res.add(cur);
            cur = null;
          }
        }
      }
    }

    return res;
  }

  public static void main(String[] args) {
    Rectangle r1 = new Rectangle(1, 169, 30);

    Rectangle r2 = new Rectangle(2, 69, 37);

    Rectangle r3 = new Rectangle(3, 5, 95);

    Rectangle r4 = new Rectangle(5, 153, 64);
    System.out.println(drawingSkylines(Arrays.asList(r1, r2, r3, r4)));
    System.exit(
        GenericTest
            .runFromAnnotations(args, "DrawingSkyline.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
