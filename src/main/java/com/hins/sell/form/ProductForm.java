package com.hins.sell.form;


import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;

@Data
public class ProductForm {

    private String productId;

    private String productName;

    private BigDecimal productPrice;

    /** 库存 */
    private Integer productStock;

    private String productDescription;

    /** 商品小图，为链接地址 */
    private String productIcon;

    /** 商品类目编号 */
    private Integer categoryType;

}
