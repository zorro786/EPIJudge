package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class CalendarRendering {
  @EpiUserType(ctorParams = {int.class, int.class})

  public static class Event {
    public int start, finish;

    public Event(int start, int finish) {
      this.start = start;
      this.finish = finish;
    }

  }

  private static class Endpoint implements Comparable<Endpoint> {
    public int time;
    public boolean isStart;

    Endpoint(int time, boolean isStart) {
      this.time = time;
      this.isStart = isStart;
    }

    // Here end time is inclusive, that means we need to consider start before finish if times conflict to include.
    public int compareTo(Endpoint that) {
      if (time == that.time) {
        if (isStart && !that.isStart) {
          return -1;
        } else {
          return 1;
        }
      }
      return Integer.compare(time, that.time);
    }
  }

  @EpiTest(testDataFile = "calendar_rendering.tsv")
  public static int findMaxSimultaneousEvents(List<Event> A) {
    int max = 0, currentCount = 0;
    List<Endpoint> endpoints = new ArrayList<>();
    for (Event e : A) {
      endpoints.add(new Endpoint(e.start, true));
      endpoints.add(new Endpoint(e.finish, false));
    }
    Collections.sort(endpoints);
    for (Endpoint e : endpoints) {
      if (e.isStart) {
        currentCount++;
      } else {
        max = Math.max(max, currentCount);
        currentCount--;
      }
    }
    return max;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "CalendarRendering.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
