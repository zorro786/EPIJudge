package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class StringDecompositionsIntoDictionaryWords {

  /**
   * This is O(Nnw) algorithm, where N is the length of string s, n is the max length of word in w and w is the size of words
   * @param s
   * @param words
   * @return
   */
  @EpiTest(testDataFile = "string_decompositions_into_dictionary_words.tsv")
  public static List<Integer> findAllSubstrings(String s, List<String> words) {
    Map<String, Integer> wordCount = new HashMap<>();
    Map<Integer, String> index = new HashMap<>();
    for (String w : words) { // Takes O(Nnw) time due to O(Nn) used by naive index search. Can be improved to O(N) using boyer moore etc
      int start = 0;
      while (start != -1) {
        start = s.indexOf(w, start);
        if (start != -1) {
          index.put(start++, w);
        }
      }
      wordCount.merge(w, 1, Integer::sum);
    }
    Integer totalCount = wordCount.values().stream().reduce(Integer::sum).get();
    List<Integer> result = new ArrayList<>();
    for (int i = 0; i < s.length(); i++) {
      Map<String, Integer> currentCount = new HashMap<>();
      int count = 0;
      int start = i;
      while (index.containsKey(start)) {
        currentCount.merge(index.get(start), 1, Integer::sum);
        count++;
        if (currentCount.get(index.get(start)) > wordCount.get(index.get(start))) {
          break;
        }
        if (count == totalCount) {
          result.add(i);
          break;
        }
        start += index.get(start).length();
      }

    }
    return result;
  }

  public static void main(String[] args) {
    System.exit(GenericTest
                    .runFromAnnotations(
                        args, "StringDecompositionsIntoDictionaryWords.java",
                        new Object() {}.getClass().getEnclosingClass())
                    .ordinal());
  }
}
