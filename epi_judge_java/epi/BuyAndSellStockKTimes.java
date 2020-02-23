package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
public class BuyAndSellStockKTimes {
  @EpiTest(testDataFile = "buy_and_sell_stock_k_times.tsv")

  public static double buyAndSellStockKTimes(List<Double> prices, int k) {
    double[][] dp = new double[prices.size()][k+1];
    double prevMax;
    for (int j = 1; j <= k ; j++) {
      prevMax = Integer.MIN_VALUE;
      for (int i = 1; i < prices.size(); i++) {
        prevMax = Math.max(prevMax, dp[i-1][j-1] - prices.get(i-1)); // not prices.get(i) because this is a recursive step and it is already added to profit in below line
        dp[i][j] = Math.max(dp[i-1][j], prices.get(i) + prevMax);
      }
    }
    return dp[prices.size()-1][k];
  }


  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "BuyAndSellStockKTimes.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
