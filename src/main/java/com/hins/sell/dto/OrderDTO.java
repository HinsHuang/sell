package com.hins.sell.dto;

import com.hins.sell.dataobject.OrderDetail;
import com.hins.sell.enums.OrderStatusEnum;
import com.hins.sell.enums.PayStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {

    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    private BigDecimal orderAmount;

    /** 订单状态，默认为0 新下单 */
    private Integer orderStatus;

    /** 支付状态，默认为0 未支付 */
    private Integer payStatus;

    private Date createTime;

    private Date updateTime;

    private List<OrderDetail> orderDetailList;

}
