package com.hins.sell.service.impl;

import com.hins.sell.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @Test
    public void findOne() {
        ProductInfo productInfo = productService.findOne("HH201909221100");
        Assert.assertEquals("HH201909221100", productInfo.getProductId());
    }

    @Test
    public void findAllUp() {
        List<ProductInfo> productInfoList = productService.findAllUp();
        Assert.assertNotEquals(0, productInfoList.size());
    }

    @Test
    public void findAll() {
        PageRequest pageRequest = new PageRequest(0, 2);
        Page<ProductInfo> page = productService.findAll(pageRequest);
        Assert.assertNotEquals(0, page.getTotalElements());
    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("HH201909221222");
        productInfo.setProductName("第三个测试商品");
        productInfo.setProductDescription("第三个测试商品的描述");
        productInfo.setProductIcon("http://975.xx.xx");
        productInfo.setCategoryType(2);
        productInfo.setProductPrice(new BigDecimal(213.5));
        productInfo.setProductStock(200);
        productInfo.setProductStatus(0);

        ProductInfo result = productService.save(productInfo);
        Assert.assertNotNull(result);
    }
}