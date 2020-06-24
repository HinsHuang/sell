package com.hins.sell.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * 商品
 */

@Entity
@Data
public class ProductInfo {

    @Id
    private String productId;

    private String productName;

    private BigDecimal productPrice;

    /** 库存 */
    private Integer productStock;

    private String productDescription;

    /** 商品小图，为链接地址 */
    private String productIcon;

    /** 商品状态，0正常，1下架 */
    private Integer productStatus;

    /** 商品类目编号 */
    private Integer categoryType;

}
