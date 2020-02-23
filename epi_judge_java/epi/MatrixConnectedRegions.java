package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TimedExecutor;
import java.util.ArrayList;
import java.util.List;
public class MatrixConnectedRegions {
  public static void flipColor(int x, int y, List<List<Boolean>> image) {
    Boolean initValue = image.get(x).get(y);
    image.get(x).set(y, !initValue);
    int m = image.size();
    int n = image.get(0).size();
    int[][] moves = new int[][]{{1,0}, {0,1}, {-1,0}, {0,-1}};
    for (int[] move : moves) {
      int newX = move[0] + x;
      int newY = move[1] + y;
      if (isValid(newX, newY, m, n)) {
        if (image.get(newX).get(newY) == !initValue) {
          continue;
        }
        flipColor(newX, newY, image);
      }
    }
    return;
  }


  private static boolean isValid(int x, int y, int m, int n) {
    return x >= 0 && x < m && y >= 0 && y < n;
  }


  @EpiTest(testDataFile = "painting.tsv")
  public static List<List<Integer>> flipColorWrapper(TimedExecutor executor,
                                                     int x, int y,
                                                     List<List<Integer>> image)
      throws Exception {
    List<List<Boolean>> B = new ArrayList<>();
    for (int i = 0; i < image.size(); i++) {
      B.add(new ArrayList<>());
      for (int j = 0; j < image.get(i).size(); j++) {
        B.get(i).add(image.get(i).get(j) == 1);
      }
    }

    executor.run(() -> flipColor(x, y, B));

    image = new ArrayList<>();
    for (int i = 0; i < B.size(); i++) {
      image.add(new ArrayList<>());
      for (int j = 0; j < B.get(i).size(); j++) {
        image.get(i).add(B.get(i).get(j) ? 1 : 0);
      }
    }

    return image;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "MatrixConnectedRegions.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
