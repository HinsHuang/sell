package com.hins.sell.repository;

import com.hins.sell.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.Max;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void testSave() {

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("1111111");
        orderDetail.setOrderId("122333");
        orderDetail.setProductIcon("http://dsfkljkl.jpg");
        orderDetail.setProductId("112345");
        orderDetail.setProductName("手机");
        orderDetail.setProductPrice(new BigDecimal(111.25));
        orderDetail.setProductQuantity(2);
        Assert.assertNotNull(repository.save(orderDetail));
    }

    @Test
    public void testFindByOrOrderId() {
        List<OrderDetail> orderDetailList = repository.findByOrOrderId("1111");
        Assert.assertNotEquals(0, orderDetailList.size());
    }

}