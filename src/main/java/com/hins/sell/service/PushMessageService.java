package com.hins.sell.service;

import com.hins.sell.dto.OrderDTO;

public interface PushMessageService {

    void orderStatus(OrderDTO orderDTO);

}
