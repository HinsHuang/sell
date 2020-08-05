package com.hins.sell.dataobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hins.sell.enums.ProductStatusEnum;
import com.hins.sell.utils.EnumUtil;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品
 */

@Entity
@Data
@DynamicUpdate
public class ProductInfo implements Serializable {

    private static final long serialVersionUID = -6930065716326590593L;

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
    private Integer productStatus = ProductStatusEnum.UP.getCode();

    /** 商品类目编号 */
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum() {
        return EnumUtil.getByCode(productStatus, ProductStatusEnum.class);
    }

}
