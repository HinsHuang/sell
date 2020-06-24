package com.hins.sell.repository;

import com.hins.sell.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository repository;

    @Test
    public void saveTest() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("HH201909221100");
        productInfo.setProductName("第二个测试商品");
        productInfo.setProductDescription("第二个测试商品的描述");
        productInfo.setProductIcon("http://456.xx.xx");
        productInfo.setCategoryType(2);
        productInfo.setProductPrice(new BigDecimal(223.5));
        productInfo.setProductStock(800);
        productInfo.setProductStatus(0);

        ProductInfo result = repository.save(productInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void updateTest() {
        ProductInfo productInfo = repository.findOne("HH201909221100");
        productInfo.setProductStock(99);
        productInfo.setProductPrice(new BigDecimal(168.8));
        ProductInfo result = repository.save(productInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByProductStatusTest() {
        List<ProductInfo> productInfoList = repository.findByProductStatus(0);
        Assert.assertNotEquals(0, productInfoList.size());
    }

}