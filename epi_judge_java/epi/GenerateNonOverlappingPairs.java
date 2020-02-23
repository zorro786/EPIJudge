package epi;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Created by Abdul Qadeer (abqadeer@cisco.com) on Jan, 2020
 */
class GenerateNonOverlappingPairs {

    private static void generateAllPairs(int n, int K) {
        for (int k = 1; k <=K; k++) {
            List<List<List<Integer>>> cur = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                recurse(i+1, n, k, cur, new ArrayList<>());

            }
            System.out.println(cur);
        }
    }

    private static void recurse(int cur, int n, int k, List<List<List<Integer>>> res, List<List<Integer>> running) {
        if (running.size() == k) {
            List<List<Integer>> toAdd = new ArrayList<>(running);
            res.add(toAdd);
            return;
        }
        List<Integer> temp = new ArrayList<>();
        for (int i = cur; i < n; i++) {
            temp.clear();
            temp.add(cur-1);
            temp.add(i);
            running.add(temp);
            recurse(i + 1, n, k, res, running);
            running.remove(temp);
        }
    }

    public static void main(String[] args) {
        generateAllPairs(6, 4);
    }


}