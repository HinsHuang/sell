package com.hins.sell.controller;

import com.hins.sell.service.RedisLock;
import com.hins.sell.service.SecKillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/skill")
@Slf4j
public class SecKillController {

    @Autowired
    private SecKillService secKillService;

    @GetMapping("/query/{productId}")
    public String query(@PathVariable String productId) {
        return secKillService.querySecKillProductinfo(productId);
    }

    @GetMapping("/order/{productId}")
    public String skill(@PathVariable String productId) {
        log.info("@skill request, productid={}", productId);
        secKillService.orderProductMockDiffUser(productId);
        return secKillService.querySecKillProductinfo(productId);

    }
}
