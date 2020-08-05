package com.hins.sell.controller;

import com.hins.sell.dto.OrderDTO;
import com.hins.sell.enums.ResultEnum;
import com.hins.sell.exceptions.SellException;
import com.hins.sell.service.OrderService;
import com.hins.sell.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PushMessageService pushMessageService;

    /**
     * 获取所有订单列表
     * @param page 第几页，从第1页开始
     * @param size 每页的订单数
     * @param model
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> model) {
        PageRequest pageRequest = new PageRequest(page - 1, size);
        Page<OrderDTO> orderDTOPage = orderService.findList(pageRequest);
        model.put("orderDTOPage", orderDTOPage);
        model.put("currentPage", page);
        model.put("size", size);
        return new ModelAndView("order/list", model);
    }

    /**
     * 卖家取消订单
     * @param orderId
     * @param model
     * @return
     */
    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId") String orderId,
                               Map<String, Object> model) {
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.cancel(orderDTO);
        } catch (SellException ex) {
            log.error("【卖家取消订单】取消订单异常{}", ex);
            model.put("message", ex.getMessage());
            model.put("returnUrl", "/sell/seller/order/list");
            return new ModelAndView("common/error", model);
        }
        model.put("message", ResultEnum.CANCEL_ORDER_SUCCESS.getMessage());
        model.put("returnUrl", "/sell/seller/order/list");
        return new ModelAndView("common/success", model);
    }

    /**
     * 卖家订单详情页
     * @param orderId
     * @param model
     * @return
     */
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId,
                               Map<String, Object> model) {
        OrderDTO orderDTO;
        try {
            orderDTO = orderService.findOne(orderId);
        } catch (SellException ex) {
            log.error("【卖家订单详情】查询订单异常{}", ex);
            model.put("message", ex.getMessage());
            model.put("returnUrl", "/sell/seller/order/list");
            return new ModelAndView("common/error", model);
        }
        model.put("orderDTO", orderDTO);
        return new ModelAndView("order/detail", model);
    }

    /**
     * 卖家完结订单
     * @param orderId
     * @param model
     * @return
     */
    @GetMapping("/finish")
    public ModelAndView finished(@RequestParam("orderId") String orderId,
                                 Map<String, Object> model) {
        OrderDTO orderDTO;
        try {
             orderDTO = orderService.findOne(orderId);
            orderService.finish(orderDTO);
        } catch (SellException ex) {
            log.error("【卖家完结订单】完结订单异常{}", ex);
            model.put("message", ex.getMessage());
            model.put("returnUrl", "/sell/seller/order/list");
            return new ModelAndView("common/error");
        }

        //推送微信模板消息
        pushMessageService.orderStatus(orderDTO);

        model.put("message", ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
        model.put("returnUrl", "/sell/seller/order/list");
        return new ModelAndView("common/success");

    }
}
