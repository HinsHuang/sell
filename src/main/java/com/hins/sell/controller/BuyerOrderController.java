package com.hins.sell.controller;


import com.hins.sell.converter.OrderForm2OrderDTOConverter;
import com.hins.sell.dto.OrderDTO;
import com.hins.sell.enums.ResultEnum;
import com.hins.sell.exceptions.SellException;
import com.hins.sell.form.OrderForm;
import com.hins.sell.service.BuyerService;
import com.hins.sell.service.OrderService;
import com.hins.sell.utils.ResultVOUtil;
import com.hins.sell.viewobject.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确, orderForm={}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO result = orderService.create(orderDTO);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("orderId", result.getOrderId());

        return ResultVOUtil.success(resultMap);
    }

    //订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size) {
        if (StringUtils.isEmpty(openid)) {
            log.error("【订单列表查询】openId为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        PageRequest pageRequest = new PageRequest(page, size);
        Page<OrderDTO> orderDTOPage = orderService.findList(openid, pageRequest);

        return ResultVOUtil.success(orderDTOPage.getContent());
    }

    //订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId) {
        if (StringUtils.isEmpty(openid)) {
            log.error("【订单详情查询】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        if (StringUtils.isEmpty(orderId)) {
            log.error("【订单详情查询】orderId为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        OrderDTO result = buyerService.findOrderOne(openid, orderId);
        return ResultVOUtil.success(result);
    }

    //取消订单
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId) {
        if (StringUtils.isEmpty(openid)) {
            log.error("【取消订单】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        if (StringUtils.isEmpty(orderId)) {
            log.error("【取消订单】orderId为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        buyerService.cancelOrder(openid, orderId);
        return ResultVOUtil.success();
    }
}
