package com.hins.sell.utils;

public class MathUtil {

    private static final Double MONEY_RANGE = 0.01;

    /**
     * 比较2个金额是否相等
     * @param d1
     * @param d2
     * @return
     */
    public static Boolean equal(Double d1, Double d2) {
        Double result = Math.abs(d1 - d2);
        return result < MONEY_RANGE;
    }
}
