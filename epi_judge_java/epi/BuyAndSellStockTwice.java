package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;

import java.util.List;
public class BuyAndSellStockTwice {
  @EpiTest(testDataFile = "buy_and_sell_stock_twice.tsv")
  public static double buyAndSellStockTwice(List<Double> prices) {

    double maxTotalProfit = 0.0;
    List<Double> firstBuySellProfits  = new ArrayList<>();
    double minPriceSoFar = Double.MAX_VALUE;

    for(int i =0; i < prices.size(); i++) {
      minPriceSoFar = Math.min(minPriceSoFar, prices.get(i));
      maxTotalProfit = Math.max(maxTotalProfit, prices.get(i) - minPriceSoFar);
      firstBuySellProfits.add(maxTotalProfit);
    }

    double maxPriceSoFar = Double.MIN_VALUE;
    for (int i = prices.size()-1; i > 0;--i){
      maxPriceSoFar = Math.max(maxPriceSoFar, prices.get(i));
      maxTotalProfit = Math.max(maxTotalProfit , maxPriceSoFar - prices.get(i) + firstBuySellProfits.get(i));
    }
    return maxTotalProfit;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "BuyAndSellStockTwice.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
