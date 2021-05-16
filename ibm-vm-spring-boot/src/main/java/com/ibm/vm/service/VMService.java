package com.ibm.vm.service;

import com.ibm.vm.util.CoinsEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VMService {
    List<Integer> coins = new ArrayList<Integer>();

    private static Logger logger = LoggerFactory.getLogger(VMService.class);
    List<Integer> coinList = new ArrayList<>();

    public void generateResult(int[][] dp, int i, int j, List<Integer> coins) {
        if (i == 0) return;
        else if (dp[i][j] == dp[i - 1][j]) generateResult(dp, i - 1, j, coins);
        else {
            generateResult(dp, i, j - coins.get(i - 1), coins);
            coinList.add(coins.get(i - 1));
        }
    }

    public List<Integer> findMinCoins(int amount) throws Exception {
        logger.info("*** Calculating coins***");
        coinList.clear();
        CoinsEnum[] coin = CoinsEnum.values();
        for (CoinsEnum coinsEnum : coin) {
            coins.add(coinsEnum.getCoin());
        }

        int[][] dp = new int[coins.size() + 1][amount + 1];
        for (int i = 0; i <= coins.size(); i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
            dp[i][0] = 0;
        }
        for (int i = 0; i < coins.size(); i++) {
            for (int j = 0; j <= amount; j++) {
                dp[i + 1][j] = dp[i][j];
                if (j - coins.get(i) >= 0) {
                    if (dp[i + 1][j - coins.get(i)] != Integer.MAX_VALUE)
                        dp[i + 1][j] = Math.min(dp[i + 1][j], dp[i + 1][j - coins.get(i)] + 1);
                }
            }
        }

        if (dp[coins.size()][amount] == Integer.MAX_VALUE) {
            throw new Exception("Invalid Coin Please Enter Valid Coin!");
        }

        generateResult(dp, coins.size(), amount, coins);

        return coinList;
    }
}
