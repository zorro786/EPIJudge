package epi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by Abdul Qadeer (abqadeer@cisco.com) on Jan, 2020
 */
public class ViewFromAbove {

    public static class Point {
        int x;
        int y;

        private Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || !(o instanceof Point)) {
                return false;
            }
            Point that = (Point) o;
            return x == that.x && y == that.y;
        }
    }

    private static class TopView {
        LineSegment l;
        Point left, right;
        private TopView(Point left, Point right, LineSegment l) {
            this.left = left;
            this.right = right;
            this.l = l;
        }
    }

    public static class LineSegment {
        Point left;
        Point right;

        private LineSegment(Point first, Point second) {
            left = first;
            right = second;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || !(o instanceof LineSegment)) {
                return false;
            }
            LineSegment that = (LineSegment) o;
            return left.equals(that.left) && right.equals(that.right);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("(" + left.x + ", " + left.y + ") ");
            sb.append("(" + right.x + ", " + right.y + ") ");
            return sb.toString();
        }
    }

    public class Endpoint implements Comparable<Endpoint> {
        boolean isLeft;
        int x;
        LineSegment line;
        private Endpoint(boolean isLeft, int x, LineSegment line) {
            this.isLeft = isLeft;
            this.x = x;
            this.line = line;
        }

        @Override
        public int compareTo(Endpoint that) {
            if (x != that.x ) {
                return Integer.compare(x, that.x);
            }
            return Integer.compare(that.line.left.y, line.left.y);
        }
    }

    public List<LineSegment> getViewFromAbove(List<LineSegment> lines) {
        List<Endpoint> endpoints = new ArrayList<>();
        for (LineSegment line : lines) {
            endpoints.add(new Endpoint(true, line.left.x, line));
            endpoints.add(new Endpoint(false, line.right.x, line));
        }
        PriorityQueue<LineSegment> pq = new PriorityQueue<>((l1, l2) -> Integer.compare(l2.left.y, l1.left.y));
        TopView cur = null;
        Collections.sort(endpoints);
        List<LineSegment> res = new ArrayList<>();

        for (Endpoint endpoint : endpoints) {
            if (endpoint.isLeft) {
                pq.add(endpoint.line);
                if (cur == null) {
                    cur = new TopView(pq.peek().left, null, pq.peek());
                } else if (cur.left.y < pq.peek().left.y) {
                    cur.right = new Point(pq.peek().left.x, cur.left.y);
                    if (cur.left.x != cur.right.x) {
                        res.add(new LineSegment(cur.left, cur.right));
                    }
                    cur = new TopView(pq.peek().left, null, pq.peek());
                }
            } else {
                pq.remove(endpoint.line);
                if (cur.l.equals(endpoint.line)) {
                    cur.right = endpoint.line.right;
                    if (cur.left.x != cur.right.x) {
                        res.add(new LineSegment(cur.left, cur.right));
                    }
                    if (!pq.isEmpty()) {
                        cur = new TopView(new Point(endpoint.line.right.x, pq.peek().left.y), null, pq.peek());
                    } else {
                        cur = null;
                    }
                }
            }

        }

        return res;
    }

    public static void main(String args[]) {
        ViewFromAbove viewFromAbove = new ViewFromAbove();
        List<LineSegment> lines = new ArrayList<>();
        Point p1 = new Point(0, 0);
        Point p2 = new Point(1, 0);
        Point p3 = new Point(2, 0);
        Point p4 = new Point(3, 0);

        Point p5 = new Point(1, 1);
        Point p6 = new Point(3, 1);

        Point p7 = new Point(2, 2);
        Point p8 = new Point(4, 2);
        lines.add(new LineSegment(p1, p2));
        lines.add(new LineSegment(p3, p4));
        lines.add(new LineSegment(p5, p6));
        lines.add(new LineSegment(p7, p8));
        System.out.println(viewFromAbove.getViewFromAbove(lines));


    }



}
