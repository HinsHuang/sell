package com.hins.sell.controller;

import com.hins.sell.dto.OrderDTO;
import com.hins.sell.enums.ResultEnum;
import com.hins.sell.exceptions.SellException;
import com.hins.sell.service.OrderService;
import com.hins.sell.service.PayService;
import com.lly835.bestpay.model.PayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/pay")
@Slf4j
public class PayController {

    @Autowired
    private PayService payService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl") String returnUrl,
                               Map<String, Object> model) {
        //1. 查询订单
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            log.error("【支付订单】订单不存在. orderId={}", orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        //发起支付
        PayResponse payResponse = payService.create(orderDTO);
        model.put("payResponse", payResponse);
        model.put("returnUrl", returnUrl);
        log.info("returnUrl={}", returnUrl);
        //由于公众号无法支付，所以在这里直接修改订单成已支付。
        orderService.paid(orderDTO);
        return new ModelAndView("pay/create", model);
    }

    //微信异步通知付款是否成功，由于无法支付成功，这个方法无法被调用到。
    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData) {
        log.info("notifyData={}", notifyData);
        payService.notify(notifyData);
        //返回给微信处理结果
        return new ModelAndView("pay/success");
    }
}
