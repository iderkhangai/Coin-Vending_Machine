package com.ibm.vm.util;

public enum CoinsEnum {
    TWO_HUNDRED(200),
    ONE_HUNDRED(100),
    FIFTY(50),
    TWENTY(20),
    TEN(10),
    Five(5);

    private int coin;

    private CoinsEnum(int coin) {
        this.coin = coin;
    }

    public int getCoin() {
        return coin;
    }
}
