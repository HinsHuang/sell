package com.hins.sell.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class OrderForm {

    @NotEmpty(message = "姓名必填")
    private String name;

    @NotEmpty(message = "手机号必填")
    private String phone;

    @NotEmpty(message = "地址必填")
    private String address;

    /** 微信openid */
    @NotEmpty(message = "openid必填")
    private String openid;

    /** 购物车产品 */
    @NotEmpty(message = "购物车不能为空")
    private String items;

}
