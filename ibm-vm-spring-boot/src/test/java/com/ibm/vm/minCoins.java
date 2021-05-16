package com.ibm.vm;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class minCoins {
    @Test
    public void test() throws Exception {
        int[] coins = {5, 10, 20, 50, 100, 200};
        List<Integer> resultList = findMinCoins(coins, 750);
        assertEquals(5, resultList.size());
        System.out.println(resultList.toString());
    }

    List<Integer> coinList = new ArrayList<>();

    public void generateResult(int[][] dp, int i, int j, int[] currency) {
        if (i == 0) return;
        else if (dp[i][j] == dp[i - 1][j]) generateResult(dp, i - 1, j, currency);
        else {
            generateResult(dp, i, j - currency[i - 1], currency);
            coinList.add(currency[i - 1]);
        }
    }

    public List<Integer> findMinCoins(int[] currency, int amount) throws Exception {

        coinList.clear();
        int[][] dp = new int[currency.length + 1][amount + 1];
        for (int i = 0; i <= currency.length; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
            dp[i][0] = 0;
        }
        for (int i = 0; i < currency.length; i++) {
            for (int j = 0; j <= amount; j++) {
                dp[i + 1][j] = dp[i][j];
                if (j - currency[i] >= 0) {
                    if (dp[i + 1][j - currency[i]] != Integer.MAX_VALUE)
                        dp[i + 1][j] = Math.min(dp[i + 1][j], dp[i + 1][j - currency[i]] + 1);
                }
            }
        }

        if (dp[currency.length][amount] == Integer.MAX_VALUE) {
            throw new Exception("invalid coin");
        }

        generateResult(dp, currency.length, amount, currency);

        return coinList;
    }
}
