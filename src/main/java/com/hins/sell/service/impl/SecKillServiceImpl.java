package com.hins.sell.service.impl;

import com.hins.sell.exceptions.SellException;
import com.hins.sell.service.RedisLock;
import com.hins.sell.service.SecKillService;
import com.hins.sell.utils.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SecKillServiceImpl implements SecKillService {

    private static final int TIMEOUT = 10 * 1000; //10s

    @Autowired
    private RedisLock redisLock;

    static Map<String, Integer> products;
    static Map<String, Integer> stock;
    static Map<String, String> orders;
    static {
        /**
         * 模拟多个表，商品表，库存表，秒杀成功订单表
         */
        products = new HashMap<>();
        stock = new HashMap<>();
        orders = new HashMap<>();
        products.put("123456", 10000);
        stock.put("123456", 10000);
    }

    private String queryMap(String productId) {
        return "国庆活动，皮蛋粥特价，限量份" +
                products.get(productId) +
                " 还剩" + stock.get(productId) + " 份" +
                " 该商品下单成功人数：" +
                orders.size();
    }


    @Override
    public String querySecKillProductinfo(String productId) {
        return this.queryMap(productId);
    }

    @Override
    public  void orderProductMockDiffUser(String productId) {
        //加锁
        long time = System.currentTimeMillis() + TIMEOUT;
        if (!redisLock.luck(productId, String.valueOf(time))) {
            throw new SellException(101, "太多人啦，稍后再试试~~");
        }

        int stockNum = stock.get(productId);
        if (stockNum <= 0) {
            throw new SellException(100, "活动结束");
        } else {
            orders.put(KeyUtil.generateUniqueKey(), productId);
            stockNum = stockNum - 1;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stock.put(productId, stockNum);
        }

        //解锁
        redisLock.unluck(productId, String.valueOf(time));
    }


}
