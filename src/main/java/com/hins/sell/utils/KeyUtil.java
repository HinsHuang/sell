package com.hins.sell.utils;

import java.util.Random;


public class KeyUtil {

    public static synchronized String generateUniqueKey() {
        long time = System.currentTimeMillis();
        Random random = new Random();
        int number = random.nextInt(900000) + 100000;
        String key = String.valueOf(time) + String.valueOf(number);
        return key;
    }
}
