package epi;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

class Solution {
    public int[] assignBikes(int[][] workers, int[][] bikes) {
        int[] res = new int[workers.length];
        Map<Integer, TreeSet<int[]>> map = new HashMap<>();
        Map<Integer, Map<Integer, Integer>> workerDist = new HashMap<>();
        for (int i = 0; i < bikes.length; i++) {
            for (int j = 0; j < workers.length; j++) {
                int curDist = Math.abs(bikes[i][0] - workers[j][0]) + Math.abs(bikes[i][1] - workers[j][1]);
                map.putIfAbsent(i, new TreeSet<>((l, r) -> {
                    if (l[1] != r[1]) {
                        return l[1] - r[1];
                    } else {
                        return l[0] - r[0];
                    }
                }));
                map.get(i).add(new int[]{j, curDist});
                workerDist.putIfAbsent(j, new HashMap<>());
                if (workerDist.get(j).containsKey(i) && curDist < workerDist.get(j).get(i)) {
                    workerDist.get(j).put(i, curDist);
                } else {
                    workerDist.get(j).put(i, curDist);
                }
            }
        }

        while (!map.isEmpty()) {
            int minDist = Integer.MAX_VALUE;
            int minKey = -1;
            for (Map.Entry<Integer, TreeSet<int[]>> entry : map.entrySet()) {
                if (entry.getValue().first()[1] < minDist) {
                    minKey = entry.getKey();
                    minDist = entry.getValue().first()[1];
                }
            }
            Integer workerIndex = map.get(minKey).first()[0];
            res[workerIndex] = minKey;
            map.remove(minKey);
            for (Map.Entry<Integer, TreeSet<int[]>> entry : map.entrySet()) {
                entry.getValue().remove(new int[]{workerIndex, workerDist.get(workerIndex).get(minKey)});
            }
        }
        return res;
    }

    public static void main (String args[]) {
        Solution sol = new Solution();
        System.out.println(Arrays.toString(sol.assignBikes(new int[][]{{0,0},{2,1}}, new int[][]{{1,2},{3,3}})));
    }
}
