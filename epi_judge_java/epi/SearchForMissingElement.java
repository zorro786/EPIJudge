package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;
import java.util.List;
public class SearchForMissingElement {
  @EpiUserType(ctorParams = {Integer.class, Integer.class})

  public static class DuplicateAndMissing {
    public Integer duplicate;
    public Integer missing;

    public DuplicateAndMissing(Integer duplicate, Integer missing) {
      this.duplicate = duplicate;
      this.missing = missing;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      DuplicateAndMissing that = (DuplicateAndMissing)o;

      if (!duplicate.equals(that.duplicate)) {
        return false;
      }
      return missing.equals(that.missing);
    }

    @Override
    public int hashCode() {
      int result = duplicate.hashCode();
      result = 31 * result + missing.hashCode();
      return result;
    }

    @Override
    public String toString() {
      return "duplicate: " + duplicate + ", missing: " + missing;
    }
  }

  @EpiTest(testDataFile = "find_missing_and_duplicate.tsv")

  public static DuplicateAndMissing findDuplicateMissing(List<Integer> a) {
      int n = a.size();
      int xor = 0;
      for (int i = 0; i < n; i++) {
        xor ^= i;
        xor ^= a.get(i);
      }
      int setBit = ~(xor-1) & xor;
      int res = 0;
      for (int i = 0; i < n; i++){
          if ((a.get(i)&setBit) != 0) {
            res ^= a.get(i);
          }
          if ((i&setBit) != 0) {
            res ^= i;
          }
      }
      for (int num : a) {
        if (num == res) {
          return new DuplicateAndMissing(res, res^xor);
        }
      }

    return new DuplicateAndMissing(res^xor, res);
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SearchForMissingElement.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
