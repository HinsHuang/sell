package com.hins.sell.service;

import com.hins.sell.dto.OrderDTO;
import com.lly835.bestpay.model.PayResponse;

public interface PayService {

    PayResponse create(OrderDTO orderDTO);

    PayResponse notify(String notifyData);

}
